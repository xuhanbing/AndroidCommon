package com.common.http;

import android.os.Handler;
import android.support.annotation.MainThread;

import com.common.http.callback.HttpCallback;
import com.common.http.callback.HttpProgressCallback;
import com.common.http.callback.HttpSimpleProgressCallback;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求
 * 
 * @author hanbing
 * @date 2016年1月18日
 */
public abstract class HttpRequest {

	enum Type{
		OKHTTP,
		XUTILS
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

	public void doRequest(String requestUrl, final Map<String, String> params, final HttpCallback callback) {
		doRequest(0, requestUrl, null, params, null, callback);
	}
	
	public void doRequest(final int requestCode, final String requestUrl, final HttpCallback callback) {
		doRequest(requestCode, requestUrl, null, null, null, callback);
	}

	public void doRequest(final int requestCode, final String requestUrl, final Map<String, String> params,
			final HttpCallback callback) {
		doRequest(requestCode, requestUrl, null, params, null, callback);
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
	public abstract void doRequest(final int requestCode, final String requestUrl, final Map<String, String> headers,
			final Map<String, String> params, final Map<String, Object> uploads, final HttpCallback callback);


	/**
	 * 下载
	 * @param localPath  本地保存路径
	 * @param downloadUrl 下载地址
	 * @param callback 回调
	 */
	public abstract void download(String localPath, final String downloadUrl, final HttpCallback callback);




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
	 * 取消请求
	 */
	public abstract void cancelRequest();


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

		if (null == httpCallback) {
			return null;
		}

		return new HttpCallbackWrapper(httpCallback).get();
	}
}
