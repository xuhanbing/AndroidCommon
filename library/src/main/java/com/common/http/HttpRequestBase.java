package com.common.http;

import com.common.http.callback.HttpProgressCallback;
import com.common.http.callback.HttpCallback;

import java.util.Map;

/**
 * http请求
 * 
 * @author hanbing
 * @date 2016年1月18日
 */
public abstract class HttpRequestBase {


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

}
