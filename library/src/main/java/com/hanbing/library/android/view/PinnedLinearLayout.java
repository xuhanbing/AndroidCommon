package com.hanbing.library.android.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.hanbing.library.android.R;
import com.hanbing.library.android.view.scroll.CallbackScrollView;

/**
 * A Layout that used as direct child of ScrollView and will support a pinned view when ScrollView scrolling.
 * Created by hanbing on 2016/7/25.
 */
public class PinnedLinearLayout extends LinearLayout {

    public static final String TAG = PinnedLinearLayout.class.getSimpleName();

    ScrollView mScrollView;
    View mPinnedView;
    int mPinnedViewTop = 0;

    int mPinnedViewId;

    public PinnedLinearLayout(Context context) {
        super(context, null);
    }

    public PinnedLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinnedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        if (null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PinnedLinearLayout);
            mPinnedViewId = a.getResourceId(R.styleable.PinnedLinearLayout_pinnedViewId, 0);
        }

        init();

    }

    void init() {
        scrollToTop();
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
                    mScrollView.scrollTo(0, mPinnedViewTop);
                }
            }
        });
    }

    public void scrollToPinnedViewIfPinned() {
        if (isPinned())
            scrollToPinnedView();
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


        if (null == mPinnedView && mPinnedViewId > 0)
            mPinnedView = findViewById(mPinnedViewId);

        final View child = mPinnedView;

        if ( null != child) {

            if (changed) {
                mPinnedViewTop = child.getTop();
            }

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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (isPinned()) {

            Rect rect = new Rect();
            mPinnedView.getLocalVisibleRect(rect);

            float x = ev.getX();
            float y = ev.getY() - mScrollView.getScrollY();

            MotionEvent event = MotionEvent.obtain(ev);
            event.setLocation(x, y);

            if (rect.contains((int)x, (int)y)) {
                if (mPinnedView.dispatchTouchEvent(event))
                    return true;
            }
        }

        return super.dispatchTouchEvent(ev);
    }


    public boolean isPinned() {
        if (null != mPinnedView && null != mScrollView) {
            return mPinnedView.getTop() == mScrollView.getScrollY();
        }
        return false;
    }


    private void layoutPinnedView() {

        View child = mPinnedView;
        if (null == child)
            return;

        final int scrollY = mScrollView.getScrollY();
        int t;

        if (scrollY > mPinnedViewTop) {
            t = scrollY;
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
