/**
 *  
 */
package com.common.http.callback;

/**
 * 一个普通的请求回调
 * @author hanbing
 * @date 2015-8-24
 */
public interface HttpSimpleCallback {

    /**
     * 请求成功
     * @param url
     * @param result 如果是下载文件，则为本地地址；如果是数据请求，返回数据
     */
    public void onSuccess(String url, String result);
    
    /**
     * 请求失败
     * @param url
     * @param msg
     */
    public void onFailure(String url, String msg);
}
