package com.hanbing.library.android.view.ptr;

/**
 * Created by hanbing
 */
public interface IPtrOnRefreshListener {

    /**
     * Pull to refresh from start(top or left).
     * @param ptrLayout
     */
    void onRefreshFromStart(PtrLayout ptrLayout);

    /**
     * Pull to refresh from end(bottom or right).
     * @param ptrLayout
     */
    void onRefreshFromEnd(PtrLayout ptrLayout);
}
