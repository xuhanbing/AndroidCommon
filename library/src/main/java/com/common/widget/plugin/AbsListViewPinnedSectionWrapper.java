package com.common.widget.plugin;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.common.util.ViewUtils;

/**
 * Created by hanbing
 */
public class AbsListViewPinnedSectionWrapper extends PinnedSectionWrapper<AbsListView> implements AbsListView.OnScrollListener {

    /**
     * custom OnScrollListener
     */
    AbsListView.OnScrollListener mOnScrollListener;

    public AbsListViewPinnedSectionWrapper(AbsListView parent) {
        super(parent);
    }


    public AbsListView.OnScrollListener wrapOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        if (onScrollListener == this)
            mOnScrollListener = null;
        else
            mOnScrollListener = onScrollListener;
        return this;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (null != mOnScrollListener)
            mOnScrollListener.onScrollStateChanged(view, scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        onScroll(view, firstVisibleItem, totalItemCount);

        if (null != mOnScrollListener)
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }

    @Override
    protected int getFirstVisibleItemPosition() {
        if (null != mParent)
            return mParent.getFirstVisiblePosition();
        return super.getFirstVisibleItemPosition();
    }
}