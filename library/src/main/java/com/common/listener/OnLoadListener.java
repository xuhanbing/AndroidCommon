package com.common.listener;

/**
 * Created by hanbing on 2016/3/28.
 */
public interface OnLoadListener {
    /**
     * 开始加载
     */
    public void onLoadStart();

    /**
     * 加载成功
     */
    public void onLoadSuccess();

    /**
     * 加载成功，但没有数据
     */
    public void onLoadSuccessNoData();

    /**
     * 加载失败
     * @param msg 错误信息
     */
    public void onLoadFailure(String msg);

    /**
     * 加载结束
     * 不管成功还是失败都要调用
     */
    public void onLoadCompleted();
}
