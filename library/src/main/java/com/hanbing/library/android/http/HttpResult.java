/**
 * 
 */
package com.hanbing.library.android.http;

import java.io.Serializable;

/**
 * @author hanbing
 * 
 */
public class HttpResult<T extends Object> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1222762152994052705L;
	
	
	int requestCode;
	String requestUrl;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	String response;
	
	T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
}
