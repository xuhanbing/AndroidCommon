package com.hanbing.library.android.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.hanbing.library.android.view.scroll.CallbackScrollView;

/**
 * Created by hanbing on 2016/7/25.
 */
public class PinnedLinearLayout extends LinearLayout {


    ScrollView mScrollView;
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
    }


    public void setPinnedView(View pinnedView) {
        this.mPinnedView = pinnedView;

        if (null != mPinnedView) {
            if (indexOfChild(mPinnedView) < 0)
                mPinnedView = null;
        }
    }

    public void scrollToTop() {
        post(new Runnable() {
            @Override
            public void run() {

                if (null != mScrollView) {
                    mScrollView.scrollTo(0, 0);
                }
            }
        });
    }

    public void scrollToPinnedView() {
        post(new Runnable() {
            @Override
            public void run() {

                if (null != mPinnedView && null != mScrollView) {
                    mScrollView.scrollTo(0, mPinnedViewTop + getPaddingTop());
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        for (int i = 0; i < getChildCount();i ++) {
            View child  = getChildAt(i);

            if (View.GONE != child.getVisibility())
            {
                if (child instanceof ListView) {
                    if (getParent() instanceof ScrollView) {

                        child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY));
                    }
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        final View child = mPinnedView;

        if ( null != child) {

            if (changed) {
                mPinnedViewTop = child.getTop();
            }

            ViewParent parent = getParent();

            if (parent instanceof CallbackScrollView) {

                mScrollView = (CallbackScrollView) getParent();

                layoutPinnedView();

                ((CallbackScrollView)mScrollView).setOnScrollChangedListener(new CallbackScrollView.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged(int l, int t, int oldl, int oldt) {
                        layoutPinnedView();
                    }
                });


            } else if (parent instanceof ScrollView) {
                mScrollView = (ScrollView) parent;

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                {

                    mScrollView.setOnScrollChangeListener(new OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                            layoutPinnedView();
                        }
                    });
                } else {
                    mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                            layoutPinnedView();
                        }
                    });
                }

            }
        }



    }

    private void layoutPinnedView() {

        View child = mPinnedView;
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
