package com.hanbing.library.android.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hanbing.library.android.R;

/**
 * Created by hanbing on 2016/9/2
 */
public class SwipeBackLayout extends FrameLayout {

    public interface OnScrollChangedListener {
        public void onScroll(int x, int y);

        public void onFinishActivity();
    }

    public static final int DEFAULT_DIM_COLOR = 0x88000000;
    Activity mActivity;
    View mContentView;


    /**
     * 临界比例，大于该比例将关闭，否则回到初始状态
     */
    float mThresholdRatio = 0.5f;

    /**
     * 只有从左侧滑入才滑动
     */
    boolean mSwipeOnlyIfTouchEdge = true;

    /**
     * 是否支持结束activity，如果true，在达到条件是马上结束activity，否则只是回调事件
     */
    boolean mFinishActivityEnabled = true;

    /**
     * 判断是否拖动
     */
    boolean mIsBeingDragged;

    /**
     * 阴影
     */
    Drawable mShadowDrawable;

    /**
     * 模糊基础颜色
     */
    int mDimColor = 0;

    OnScrollChangedListener mOnScrollChangedListener;

    boolean mScrollToFinishActivity = false;

    ViewDragHelper mViewDragHelper;
    ViewDragHelper.Callback mViewDragHelperCallback = new ViewDragHelper.Callback() {


        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mContentView == child;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return getWidth() - getPaddingLeft() - getPaddingRight();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getPaddingTop();
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            mIsBeingDragged = true;
        }


        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

            if (null != mOnScrollChangedListener) {
                mOnScrollChangedListener.onScroll(getContentScrollX(), getContentScrollY());
            }

            //draw dim
            invalidate();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            mIsBeingDragged = false;

            int width = getContentWidth();
            int distance = (int) (width * mThresholdRatio);

            if ((Math.abs(getContentScrollX()) > distance)
                    || (Math.abs(xvel) > Math.abs(yvel) && xvel > mViewDragHelper.getMinVelocity())) {
                //水平向右滑动的距离超过threshold，或向右fling，关闭
                scrollToFinishActivity();
            } else {
                scrollToOriginal();
            }
        }

        @Override
        public void onViewDragStateChanged(int state) {

            if (ViewDragHelper.STATE_IDLE == state) {
                postScrollFinished(mScrollToFinishActivity);
                mScrollToFinishActivity = false;
            }

        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return Math.max(getPaddingLeft(), Math.min(left, getWidth() - getPaddingRight()));
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return getPaddingTop();
        }
    };

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        mOnScrollChangedListener = onScrollChangedListener;
    }

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeBackLayout, R.attr.swipeBackLayoutStyle, 0);

        mThresholdRatio = a.getFloat(R.styleable.SwipeBackLayout_thresholdRatio, mThresholdRatio);
        mShadowDrawable = a.getDrawable(R.styleable.SwipeBackLayout_shadowDrawable);
        mDimColor = a.getColor(R.styleable.SwipeBackLayout_dimColor, DEFAULT_DIM_COLOR);
        mSwipeOnlyIfTouchEdge = a.getBoolean(R.styleable.SwipeBackLayout_swipeOnlyIfTouchEdge, mSwipeOnlyIfTouchEdge);
        mFinishActivityEnabled = a.getBoolean(R.styleable.SwipeBackLayout_finishActivityEnabled, mFinishActivityEnabled);

        a.recycle();


        init();
    }


    private void init() {
    }

    /**
     * 是否可以中断事件
     */
    boolean mCanInterceptTouchEvent = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                mCanInterceptTouchEvent = true;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mCanInterceptTouchEvent)
            return false;

        boolean ret = mViewDragHelper.shouldInterceptTouchEvent(ev);
        if (mSwipeOnlyIfTouchEdge) {
            if (mIsBeingDragged) {
                //从边界划入，可以继续中断
                mCanInterceptTouchEvent = true;
                return ret;
            } else {
                //禁止中断
                mCanInterceptTouchEvent = false;
                return false;
            }

        } else {
            return ret;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果只支持从边界划入，但条件没有成立，返回
        if (mSwipeOnlyIfTouchEdge && !mIsBeingDragged) {
            return false;
        }

        mViewDragHelper.processTouchEvent(event);
        return true;

    }

    private int getContentWidth() {
        return getContentRealWidth() + getShadowDrawableWidth();
    }

    private int getContentRealWidth() {
        return (null != mContentView ? mContentView.getMeasuredWidth() : 0);
    }

    private int getShadowDrawableWidth() {
        if (null != mShadowDrawable) {

            int width = mShadowDrawable.getIntrinsicWidth();

            if (width <= 0) {
                //color drawable
                width = (int) (getContentRealWidth() * 0.1f);
            }

            return width;
        }
        return 0;
    }

    private int getContentHeight() {
        return null != mContentView ? mContentView.getMeasuredHeight() : 0;
    }

    private int getContentLeft() {
        return null == mContentView ? 0 : mContentView.getLeft();
    }

    private int getContentTop() {
        return null == mContentView ? 0 : mContentView.getTop();
    }

    private int getContentScrollX() {
        if (null == mContentView)
            return 0;
        return mContentView.getLeft() - getPaddingLeft();
    }

    private int getContentScrollY() {
        if (null == mContentView)
            return 0;
        return mContentView.getTop() - getPaddingTop();
    }

    private float getContentScrollPercent() {
        return getContentScrollX() * 1.0f / getContentWidth();
    }

    public void scrollContentToSmooth(int x, int y, final boolean finishActivity) {
        mScrollToFinishActivity = finishActivity;
        mViewDragHelper.settleCapturedViewAt(x, y);
        invalidate();
    }

    /**
     * 滚动到最右侧，然后关闭activity
     */
    public void scrollToFinishActivity() {
        scrollContentToSmooth(getContentWidth() + getPaddingLeft(), getPaddingTop(), true);
    }

    /**
     * 滚动到初始位置
     */
    public void scrollToOriginal() {
        scrollContentToSmooth(getPaddingLeft(), getPaddingTop(), false);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawShadow(canvas);
        drawDim(canvas);
    }

    /**
     * 模糊渐变
     *
     * @param canvas
     */
    private void drawDim(Canvas canvas) {

        if (mDimColor != 0) {
            //计算alpha
            float percent = getContentScrollPercent();

            int alpha = (mDimColor >> 24) & 0xff;

            alpha = (int) (alpha * (1 - percent));
            int color = (alpha << 24) | (mDimColor & 0x00ffffff);

            canvas.clipRect(getPaddingLeft(), getPaddingTop(), getContentLeft(), getHeight() - getPaddingBottom());

            canvas.drawColor(color);
        }
    }

    /**
     * 阴影
     *
     * @param canvas
     */
    private void drawShadow(Canvas canvas) {
        if (null != mShadowDrawable) {
            //阴影从内容左侧开始
            int top = getPaddingTop();
            int right = (int) getContentLeft();
            int bottom = getHeight() - getPaddingBottom();
            int left = right - getShadowDrawableWidth();
            float percent = getContentScrollPercent();
            mShadowDrawable.setAlpha((int) ((1 - percent) * 255));
            mShadowDrawable.setBounds(left, top, right, bottom);
            mShadowDrawable.draw(canvas);
        }
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true))
            invalidate();
    }

    protected void postScrollFinished(boolean finishActivity) {


        if (finishActivity) {

            if (null != mOnScrollChangedListener) {
                mOnScrollChangedListener.onFinishActivity();
            }

            if (mFinishActivityEnabled && null != mActivity)
                mActivity.finish();
        }
    }

    public void attachToActivity(Activity activity) {
        if (null == activity || mActivity == activity)
            return;

        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();

        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mActivity.getWindow().getDecorView().setBackgroundDrawable(null);

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mViewDragHelper = ViewDragHelper.create(this, mViewDragHelperCallback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onDetachedFromWindow() {

        if (null != mViewDragHelper) {
            mViewDragHelper.cancel();
            mViewDragHelper = null;
        }

        super.onDetachedFromWindow();
    }


    public void setDimColor(int dimColor) {
        mDimColor = dimColor;
        postInvalidate();
    }



    public void setShadowDrawable(Drawable shadowDrawable) {
        mShadowDrawable = shadowDrawable;
        postInvalidate();
    }

    public void setThresholdRatio(float thresholdRatio) {
        mThresholdRatio = thresholdRatio;
    }

    public void setFinishActivityEnabled(boolean finishActivityEnabled) {
        mFinishActivityEnabled = finishActivityEnabled;
    }

    public void setSwipeOnlyIfTouchEdge(boolean swipeOnlyIfTouchEdge) {
        mSwipeOnlyIfTouchEdge = swipeOnlyIfTouchEdge;
    }
}
