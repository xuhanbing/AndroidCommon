package com.hanbing.library.android.view.ptr;

/**
 * Created by hanbing
 */
public interface IPtrHandler {

    /**
     * Callback when is pulled.
     * If is refreshing ,this will not be invoked.
     * @param ptrLayout
     * @param delta     current move distance
     * @param percent   total move distance of header or footer size
     */
    void onPull(PtrLayout ptrLayout, int delta, float percent);

    /**
     * Default state.
     */
    void onReset();

    /**
     * Pull to refresh
     */
    void onPullToRefresh();

    /**
     * Release to refresh
     */
    void onReleaseToRefresh();

    /**
     * There is going to refresh now or after scroll finished
     */
    void onRefreshPrepared();

    /**
     * Refreshing is started
     */
    void onRefreshStarted();

    /**
     * Refreshing is completed
     */
    void onRefreshCompleted();

}
