package com.hanbing.library.android.http;

import android.content.Context;
import android.os.Handler;

import com.hanbing.library.android.http.callback.HttpCallback;
import com.hanbing.library.android.http.callback.HttpProgressCallback;
import com.hanbing.library.android.http.callback.HttpSimpleProgressCallback;
import com.hanbing.library.android.util.FileUtils;

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


	//缓存策略
	public enum  CacheProxy{
		NO , //不实用缓存
		ONLY, //只使用缓存
		AUTO //自动，当请求失败时，所以缓存
	}

	/**
	 * 默认缓存大小
	 */
	public static final long DEFAULT_CACHE_SIZE = 20 * 1024 * 1024;
	/**
	 * 默认缓存时间
	 */
	public static final long DEFAULT_CACHE_AGE = 1000 * 3600 * 24;


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

	/**
	 * 缓存策略
	 */
	CacheProxy mCacheProxy = CacheProxy.NO;

	/**
	 * 缓存路径
	 */
	String mCacheDir;

	/**
	 * 缓存时间，单位ms
	 */
	long mCacheMaxAge = DEFAULT_CACHE_AGE;

	/**
	 * 缓存大小
	 */
	long mCacheSize = DEFAULT_CACHE_SIZE;


	public HttpRequest(){

	}

	public HttpRequest(String cacheDir, long cacheMaxAge, long cacheSize){
		setCache(cacheDir, cacheMaxAge, cacheSize);

	}

	public static String createCacheDir(Context context){
		String cachePath = FileUtils.getCacheDirAuto(context) + "/http";

		return cachePath;
	}

	public void setCache(String cacheDir, long cacheMaxAge, long cacheSize) {
		mCacheDir = cacheDir;
		mCacheMaxAge = cacheMaxAge;
		mCacheSize = cacheSize;
	}

	public void setCacheDir(String cacheDir) {
		setCache(cacheDir, mCacheMaxAge, mCacheSize);
	}

	public void setCacheMaxAge(long cacheMaxAge) {
		setCache(mCacheDir, cacheMaxAge, mCacheSize);
	}

	public void setCacheSize(long cacheSize) {
		setCache(mCacheDir, mCacheMaxAge, cacheSize);
	}

	public void setCacheProxy(CacheProxy cacheProxy) {
		mCacheProxy = cacheProxy;
	}

	/**
	 * 是指支持缓存
	 * @return
	 */
	protected boolean isCacheEnabled(){
		return CacheProxy.NO != mCacheProxy;
	}

	/**
	 * 是否使用缓存，可以根据需要控制
	 * 默认只要支持即使用
	 *
	 * @return
	 */
	protected boolean useCache(){
		return isCacheEnabled();
	}


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
	public  abstract Cancelable download(String localPath, final String downloadUrl, final HttpCallback callback);




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
