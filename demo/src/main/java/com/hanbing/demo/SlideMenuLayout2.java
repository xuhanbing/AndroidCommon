package com.hanbing.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.v4.widget.ScrollerCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ReflectUtils;

/**
 * Created by hanbing on 2016/10/18
 */

public class SlideMenuLayout2 extends RelativeLayout {


    ViewDragHelper mDragger;

    ViewDragHelper.Callback mViewDragHelperCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //mEdgeTrackerView禁止直接移动
            return child == mDragView || child == mAutoBackView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (child == mDragView) {
                return mDragView.getTop();
            }
            return top;
        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //mAutoBackView手指释放时可以自动回去
            if (releasedChild == mAutoBackView) {
                mDragger.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                invalidate();
            } else if (releasedChild == mDragView) {
                LogUtils.e("mDragView " + mDragView.getLeft()
                        + ", " + mDragView.getTop());
                mDragger.settleCapturedViewAt(0, 0);
                invalidate();
            }
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
        }

        @Override
        public void onViewDragStateChanged(int state) {
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            LogUtils.e("onViewPositionChanged " + changedView + ", left = " + left + ", top = " + top);
            if (changedView == mDragView) {
                View view = mEdgeTrackerView;
                left = view.getLeft() + dx;
                top = view.getTop() + dy;
                view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());

                postInvalidate();
            }
        }

        //在边界拖动时回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mDragger.captureChildView(mDragView, pointerId);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 0;
        }
    };
    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;
    private Point mAutoBackOriginPos = new Point();

    public SlideMenuLayout2(Context context) {
        super(context);
        init();

    }

    public SlideMenuLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public SlideMenuLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDragger = ViewDragHelper.create(this, mViewDragHelperCallback);
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        mDragView.layout(0, 0, mDragView.getMeasuredWidth(), mDragView.getMeasuredHeight());
        mAutoBackView.layout(0, mDragView.getMeasuredHeight(), mAutoBackView.getMeasuredWidth(), mDragView.getMeasuredHeight() + mAutoBackView.getMeasuredHeight());
        mEdgeTrackerView.layout(-mEdgeTrackerView.getMeasuredWidth(), 0, 0, mEdgeTrackerView.getMeasuredHeight());

        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }

    @Override
    public void computeScroll() {
        boolean settling = mDragger.continueSettling(true);

        ScrollerCompat scrollerCompat = ReflectUtils.getValue(mDragger, "mScroller", null);

        LogUtils.e("computeScroll settling = " + settling + ", scroller " + scrollerCompat.computeScrollOffset() + ", x = " + scrollerCompat.getCurrX() + ", fx = " + scrollerCompat.getFinalX());
        if (settling) {
            invalidate();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
}
