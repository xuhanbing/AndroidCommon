package com.common.widget.plugin;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.common.util.LogUtils;
import com.common.util.ViewUtils;

/**
 * Created by hanbing
 */
public class PinnedSectionWrapper<T extends ViewGroup> implements IPluginWrapper {


    /**
     * adapter that sub class my extends
     */
    public interface Adapter {

        /**
         * check is current position pinned or not
         *
         * @param position
         * @return
         */
        boolean isPinnedSection(int position);

        /**
         * current pinned section position
         * @param position
         * @return
         */
        int currentPinnedSectionPosition(int position);

        /**
         * next pinned section position
         * @param position current first visible item
         * @return
         */
        int nextPinnedSectionPosition(int position);

        /**
         * config pinned view
         *
         * @param pinnedView
         * @param position   first visible item position
         */
        void configurePinnedSection(View pinnedView, int position);
    }

    /**
     * pinned view
     */
    View mPinnedView = null;

    /**
     * pinned view width
     */
    int mPinnedViewWidth;

    /**
     * pinned view height
     */
    int mPinnedViewHeight;

    /**
     * draw pinned view or not
     */
    boolean mDrawPinnedView = true;

    /**
     * real adapter
     */
    Adapter mAdapter;

    /**
     * parent
     */
    T mParent;

    public PinnedSectionWrapper(T parent) {
        this.mParent = parent;
    }

    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void measure(ViewGroup parent, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        if (null == parent || null == mPinnedView)
            return;

        ViewUtils.measureChild(parent, mPinnedView, parentWidthMeasureSpec, parentHeightMeasureSpec);

        mPinnedViewWidth = mPinnedView.getMeasuredWidth();
        mPinnedViewHeight = mPinnedView.getMeasuredHeight();

        LogUtils.e("measure " + mPinnedViewWidth + ", " + mPinnedViewHeight);

    }

    @Override
    public void layout(ViewGroup parent) {
     if (null == mParent)
         return;
        layout(0, getFirstVisibleItemPosition());
    }

    void layout(int offsetY, int position) {
        if (null == mParent || null == mAdapter || null == mPinnedView)
            return;

        int left = mParent.getPaddingLeft();
        int top = mParent.getPaddingTop() + offsetY;
        mPinnedView.layout(left, top, left + mPinnedViewWidth, top + mPinnedViewHeight);
        mAdapter.configurePinnedSection(mPinnedView, position);
    }

    @Override
    public void draw(ViewGroup parent, Canvas canvas) {
        if (null != mPinnedView && mDrawPinnedView)
            ViewUtils.drawChild(parent, mPinnedView, canvas);
    }

    @Override
    public boolean interceptTouchEvent(MotionEvent ev) {
        return false;
    }


    protected int getFirstVisibleItemPosition() {
        return -1;
    }

    protected void onScroll(T viewGroup, int firstVisibleItem, int totalItemCount) {

        if (0 == totalItemCount) {
            mDrawPinnedView = false;
        } else if (null != mAdapter && firstVisibleItem >= 0){
            int curPinnedSectionPosition = mAdapter.currentPinnedSectionPosition(firstVisibleItem);
            int nextPinnedSectionPosition = mAdapter.nextPinnedSectionPosition(firstVisibleItem);

            View next = viewGroup.getChildAt(nextPinnedSectionPosition - firstVisibleItem);

            if (null == next) {
                mDrawPinnedView = true;
                layout(0, curPinnedSectionPosition);
            } else {
                int bottom = mPinnedViewHeight + viewGroup.getPaddingTop();
                int nextTop = next.getTop();

                if (nextTop <= bottom) {
                    mDrawPinnedView = true;
                    layout(nextTop - bottom, curPinnedSectionPosition);
                } else {
                    mDrawPinnedView = true;
                    layout(0, curPinnedSectionPosition);
                }
            }
        }
    }


    ViewGroup.LayoutParams generateDefaultPinnedSectionLayout() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setPinnedView(View pinnedView) {
        if (null != pinnedView) {
            ViewGroup.LayoutParams params = pinnedView.getLayoutParams();
            if(null == params) pinnedView.setLayoutParams(generateDefaultPinnedSectionLayout());
        }
        mPinnedView = pinnedView;
    }
}
