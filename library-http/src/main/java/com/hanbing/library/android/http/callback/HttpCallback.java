package com.hanbing.library.android.http.callback;

/**
 * Created by hanbing on 2016/6/17.
 */
public interface HttpCallback {
    /**
     * 请求成功
     * @param requestCode
     * @param requestUrl
     * @param result 如果是下载文件，则为本地地址；如果是数据请求，返回数据
     */
    public abstract void onSuccess(int requestCode, String requestUrl, String result);

    /**
     * 请求失败
     * @param requestCode
     * @param requestUrl
     * @param msg
     */
    public abstract void onFailure(int requestCode, String requestUrl, String msg);

    /**
     * 取消
     * @param requestCode
     * @param requestUrl
     * @param msg
     */
    public void onCancelled(int requestCode, String requestUrl, String msg);
}
