package com.hanbing.library.android.view.ptr;

/**
 * Created by hanbing
 */
public interface IPtrPullChecker {

    /**
     * Check if can pull from start(pull down or pull right).
     * @param ptrLayout
     * @return
     */
    boolean canPullFromStart(PtrLayout ptrLayout);

    /**
     * Check if can pull from end(pull up or pull left).
     * @param ptrLayout
     * @return
     */
    boolean canPullFromEnd(PtrLayout ptrLayout);
}
