package com.hanbing.library.android.http;

import android.os.Handler;

import com.hanbing.library.android.http.callback.HttpCallback;
import com.hanbing.library.android.http.callback.HttpProgressCallback;
import com.hanbing.library.android.http.callback.HttpSimpleProgressCallback;

import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http请求
 * 
 * @author hanbing
 * @date 2016年1月18日
 */
public abstract class HttpRequest {

	public enum Type{
		OKHTTP,
		XUTILS
	}

	public interface Cancelable {
		boolean isCanceled();
		void cancel();
	}

	static Map<Type, HttpRequest> mHttpRequests = new HashMap<>();
	public static HttpRequest getInstance(Type type) {
		HttpRequest mHttpRequest = mHttpRequests.get(type);
		if (null == mHttpRequest)
		{
			switch (type) {
				case XUTILS:
					mHttpRequest = new XHttpRequest();
					break;
				default:
					mHttpRequest = new OkHttpRequest();
			}

			mHttpRequests.put(type, mHttpRequest);
		}

		return mHttpRequest;
	}

	/**
	 * tasks
	 */
	List<Cancelable> mTasks = new ArrayList<>();

	/**
	 *
	 * 如果为true，不管有没有请求参数都将使用POST方式
	 * 如果为false, 如果没有请求参数，默认使用GET请求方式
	 * 只针对请求是，下载时无效
	 */
	boolean mForcePost = false;

	public boolean isForcePost() {
		return mForcePost;
	}

	public void setForcePost(boolean forcePost) {
		mForcePost = forcePost;
	}

	public Cancelable doRequest(String requestUrl, final Map<String, String> params, final HttpCallback callback) {
		return doRequest(0, requestUrl, null, params, null, callback);
	}
	
	public Cancelable doRequest(final int requestCode, final String requestUrl, final HttpCallback callback) {
		return doRequest(requestCode, requestUrl, null, null, null, callback);
	}

	public Cancelable doRequest(final int requestCode, final String requestUrl, final Map<String, String> params,
			final HttpCallback callback) {
		return doRequest(requestCode, requestUrl, null, params, null, callback);
	}

	/**
	 * http 请求 Post或Get, 返回String
	 * 
	 * @param requestCode
	 *            请求code，代表某种请求
	 * @param requestUrl
	 *            请求地址
	 * @param headers
	 *            头
	 * @param params
	 *            请求参数
	 * @param uploads
	 *            上传文件 可以是1.文件路径。2.Inputstream.3,byte[]
	 * @param callback
	 *            回调
	 */
	public abstract Cancelable doRequest(final int requestCode, final String requestUrl, final Map<String, String> headers,
			final Map<String, String> params, final Map<String, Object> uploads, final HttpCallback callback);


	/**
	 * 下载
	 * @param localPath  本地保存路径
	 * @param downloadUrl 下载地址
	 * @param callback 回调
	 */
	public abstract Cancelable download(String localPath, final String downloadUrl, final HttpCallback callback);




	/**
	 * 请求失败
	 *
	 * @param errorCode
	 * @param errorMessage
	 * @return
	 */
	protected HttpResultError makeError(int errorCode, String errorMessage) {
		HttpResultError error = new HttpResultError();
		error.setErrorCode(errorCode);
		error.setErrorMessage(errorMessage);

		return error;
	}


	/**
	 * 保存任务
	 * @param task
	 */
	Cancelable cache(Cancelable task) {
		if (null != task)
		{
			synchronized (mTasks) {
				mTasks.add(task);
			}
		}

		return task;
	}

	/**
	 * 取消任务
	 * @param task
	 */
	public void cancel(Cancelable task) {
		if (null != task) {
			synchronized (mTasks) {
				mTasks.remove(task);
			}
			task.cancel();
		}
	}

	/**
	 * 取消全部
	 */
	public void cancel() {
		synchronized (mTasks) {
			for (Cancelable task : mTasks) {
				task.cancel();
			}
			mTasks.clear();
		}
	}


	class HttpCallbackWrapper{
		HttpCallback mHttpCallback;
		HttpProgressCallback mHttpProgressCallback;
		Handler mHandler = new Handler();

		public HttpCallbackWrapper (HttpCallback httpCallback) {
			this.mHttpCallback = httpCallback;

			if (httpCallback instanceof HttpProgressCallback) {
				mHttpProgressCallback = (HttpProgressCallback) httpCallback;
			}

		}

		public HttpCallback get() {
			if (null == mHttpCallback)
				return null;

			return new HttpSimpleProgressCallback() {

				@Override
				public void onUpdateProgress(final String key, final long total, final long current) {
					if (null != mHttpProgressCallback) {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								mHttpProgressCallback.onUpdateProgress(key, total, current);
							}
						});
					}
				}

				@Override
				public void onStarted(final String key) {
					if (null != mHttpProgressCallback) {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								mHttpProgressCallback.onStarted(key);
							}
						});
					}
				}

				@Override
				public void onFinished(final String key) {
					if (null != mHttpProgressCallback) {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								mHttpProgressCallback.onFinished(key);
							}
						});
					}
				}

				@Override
				public void onSuccess(final int requestCode, final String requestUrl, final String result) {
					if (null != mHttpCallback) {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								mHttpCallback.onSuccess(requestCode, requestUrl, result);
							}
						});
					}
				}

				@Override
				public void onFailure(final int requestCode, final String requestUrl, final String msg) {
					if (null != mHttpCallback) {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								mHttpCallback.onFailure(requestCode, requestUrl, msg);
							}
						});
					}
				}

				@Override
				public void onCancelled(final int requestCode, final String requestUrl, final String msg) {
					if (null != mHttpCallback) {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								mHttpCallback.onCancelled(requestCode, requestUrl, msg);
							}
						});
					}
				}
			};
		}
	}

	/**
	 * 使用handler返回回调
	 * @param httpCallback
	 * @return
	 */
	HttpCallback wrapHttpCallback(HttpCallback httpCallback) {
		return new HttpCallbackWrapper(httpCallback).get();
	}
}
