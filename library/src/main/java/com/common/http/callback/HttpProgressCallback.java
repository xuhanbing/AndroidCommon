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
     * @param key     下载是请求的url，上传时是key
     * @param total   总长度
     * @param current 当前长度
     */
    public void onUpdateProgress(String key, long total, long current);

    /**
     * 开始
     */
    public void onStarted();

    /**
     * 结束
     */
    public void onFinished();
}
