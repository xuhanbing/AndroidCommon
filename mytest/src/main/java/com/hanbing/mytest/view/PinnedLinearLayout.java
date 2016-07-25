package com.hanbing.mytest.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.hanbing.library.android.util.LogUtils;

/**
 * Created by hanbing on 2016/7/25.
 */
public class PinnedLinearLayout extends LinearLayout {


    PinnedScrollView mScrollView;
    View mPinnedView;
    int mPinnedViewTop = 0;


    public PinnedLinearLayout(Context context) {
        super(context);
        init();
    }

    public PinnedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PinnedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {

        post(new Runnable() {
            @Override
            public void run() {

                if (null != mScrollView) {
                    mScrollView.scrollTo(0, mPinnedViewTop + getPaddingTop());
                }
            }
        });
    }

    public void setPinnedView(View pinnedView) {
        this.mPinnedView = pinnedView;

        if (null != mPinnedView) {
            if (indexOfChild(mPinnedView) < 0)
                mPinnedView = null;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        final View child = mPinnedView;

        if ( null != child) {

            if (changed) {
                mPinnedViewTop = child.getTop();
            }

            if (getParent() instanceof ScrollView) {

                mScrollView = (PinnedScrollView) getParent();

                layoutPinnedView(child);


                mScrollView.addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {

                        layoutPinnedView(child);
                    }
                });


            }
        }



    }

    private void layoutPinnedView(View child) {
        final int scrollY = mScrollView.getScrollY();
        int t;

        if (scrollY > mPinnedViewTop + getPaddingTop()) {
            t = scrollY - getPaddingTop();
        } else {
            t = mPinnedViewTop;
        }

        child.layout(child.getLeft(), t, child.getRight(), t + child.getMeasuredHeight());
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (null != mPinnedView) {
            drawChild(canvas, mPinnedView, getDrawingTime());
        }
    }
}
