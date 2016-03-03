/**
 */
package com.common.http.callback;

/**
 * 进度回调
 * @author hanbing
 * @date 2015-8-24
 */
public interface HttpProgressCallback {
    /**
     * 更新进度
     * @param url     请求的url
     * @param total   总长度
     * @param current 当前长度
     */
    public void onUpdateProgress(String url, long total, long current);
}
