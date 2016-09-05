package com.hanbing.library.android.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.Scroller;

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
    float mContentLeft;
    float mContentTop;

    int mMaximumFlingVelocity;
    int mMinimumFlingVelocity;

    int mScaledEdgeSlop;

    /**
     * 临界比例，大于该比例将关闭，否则回到初始状态
     */
    float mThresholdRatio = 0.5f;

    /**
     * 当打开下一个activity时向左移动的比例
     */
    float mNestingScrollRatio = 0.2f;

    /**
     * 是否支持嵌套滚动，类似微信的效果
     */
    boolean mNestingScrollEnabled = false;

    /**
     * 只有从左侧滑入才滑动
     */
    boolean mOnlyScrollIfTouchEdge = true;

    /**
     *
     */
    boolean mIsScrollEnabled = true;

    /**
     * 是否支持结束activity，如果true，在达到条件是马上结束activity，否则只是抛出事件
     */
    boolean mFinishActivityEnabled = true;

    int mLastDownX;
    int mLastDownY;

    int mLastMotionX;
    int mLastMotionY;

    boolean mIsBeingDragged;

    VelocityTracker mVelocityTracker;

    Scroller mScroller;

    int mScrollDuration = 500;

    /**
     * 阴影
     */
    Drawable mShadowDrawable;

    /**
     * 模糊基础颜色
     */
    int mDimColor = 0;

    Handler mHandler = new Handler();

    OnScrollChangedListener mOnScrollChangedListener;

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
            mNestingScrollRatio = a.getFloat(R.styleable.SwipeBackLayout_nestingScrollRatio, mNestingScrollRatio);
            mNestingScrollEnabled = a.getBoolean(R.styleable.SwipeBackLayout_nestingScrollEnabled, false);
            mShadowDrawable = a.getDrawable(R.styleable.SwipeBackLayout_shadowDrawable);
            mDimColor = a.getColor(R.styleable.SwipeBackLayout_dimColor, DEFAULT_DIM_COLOR);
            mScrollDuration = a.getInt(R.styleable.SwipeBackLayout_swipeScrollDuration, mScrollDuration);
            mOnlyScrollIfTouchEdge = a.getBoolean(R.styleable.SwipeBackLayout_onlyScrollIfTouchEdge, mOnlyScrollIfTouchEdge);
            mFinishActivityEnabled = a.getBoolean(R.styleable.SwipeBackLayout_finishActivityEnabled, mFinishActivityEnabled);

            a.recycle();


        init();
    }



    private void init() {
        ViewConfiguration configuration = ViewConfiguration.get(getContext());

        mScaledEdgeSlop = configuration.getScaledEdgeSlop();
        mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mLastDownX = mLastMotionX = (int) ev.getRawX();
            mLastDownY = mLastMotionY = (int) ev.getRawY();


            mIsScrollEnabled = true;
            mIsBeingDragged = false;
            if (null == mVelocityTracker)
                mVelocityTracker = VelocityTracker.obtain();
            else
                mVelocityTracker.clear();


            if (mOnlyScrollIfTouchEdge) {
                //判断是否从左侧滑入
                if (mLastMotionX < mScaledEdgeSlop)
                {
                    mIsScrollEnabled = true;
                } else {
                    mIsScrollEnabled = false;
                }
            }


        } else {
        }

        mVelocityTracker.addMovement(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mIsScrollEnabled)
            return false;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                checkBeingDragged();
                if (mIsBeingDragged) {
                    return true;
                }
            }
            break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void checkBeingDragged() {
        mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
        float xVelocity = mVelocityTracker.getXVelocity();
        float yVelocity = mVelocityTracker.getYVelocity();

        if (!mIsBeingDragged) {
            //横向滑动
            if (Math.abs(xVelocity) > Math.abs(yVelocity) && xVelocity > 0) {
                mIsBeingDragged = true;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mIsScrollEnabled)
            return false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (onTouchDown(event))
                    return true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (onTouchMove(event))
                    return true;
                break;
            case MotionEvent.ACTION_UP:
                if (onTouchUp(event))
                    return true;
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean onTouchDown(MotionEvent event) {
        return true;
    }

    private boolean onTouchMove(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        if (!mIsBeingDragged) {
            checkBeingDragged();
        }

        if (mIsBeingDragged) {

            //如果没有结束scroll，结束
            if (null != mScroller && !mScroller.isFinished()) {
                mScroller.forceFinished(true);
            }

            int deltaX = x - mLastMotionX;

            //最小为0
            if (mContentLeft + deltaX < 0)
                deltaX = (int) -mContentLeft;

            if (null != mOnScrollChangedListener) {
                mOnScrollChangedListener.onScroll(deltaX, 0);
            }
            //滑动
            scrollContentBy(deltaX, 0);
        }


        mLastMotionX = x;
        mLastMotionY = y;

        return true;
    }

    private boolean onTouchUp(MotionEvent event) {

        if (mIsBeingDragged) {
            if (!flingToFinishActivity()) {
                //已经拖动，判断拖动的距离是否达到临界值
                int width = getContentWidth();
                int distance = (int) (width * mThresholdRatio);
                if (Math.abs(getContentScrollX()) > distance) {
                    //
                    scrollToFinishActivity();
                } else {
                    scrollToOriginal();
                }
            }
        } else {

            flingToFinishActivity();
        }

        return true;
    }

    private boolean flingToFinishActivity() {
        //计算速度是否达到关闭速度
        mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
        float xVelocity = mVelocityTracker.getXVelocity();
        float yVelocity = mVelocityTracker.getYVelocity();


        if (Math.abs(xVelocity) > Math.abs(yVelocity)
                && xVelocity > mMinimumFlingVelocity) {
            //水平向右滑动，且速度大于最小速度，关闭
            scrollToFinishActivity();

            return true;
        }
        return false;
    }


    private int getContentWidth() {
        return  getContentRealWidth() + getShadowDrawableWidth();
    }

    private int getContentRealWidth(){
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

    private float getContentScrollX() {
        return mContentLeft;
    }

    private float getContentScrollY() {
        return mContentTop;
    }

    private  float getContentScrollPercent(){
        return mContentLeft * 1.0f / getContentWidth();
    }

    public void scrollContentBy(float x, float y) {
        mContentLeft += x;
        mContentTop += y;

        invalidate();
        requestLayout();
    }

    public void scrollContentTo(float x, float y) {
        mContentLeft = x;
        mContentTop = y;

        invalidate();
        requestLayout();
    }

    public void scrollContentToSmooth(int x, int y, final boolean finishActivity) {
        if (null == mScroller)
            mScroller = new Scroller(getContext());

        if (!mScroller.isFinished())
            mScroller.forceFinished(true);

        int startX = (int) getContentScrollX();
        int startY = (int) getContentScrollY();

        mScroller.startScroll(startX, startY, x - startX, y - startY, mScrollDuration);
        postInvalidate();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                computeScroll();
                if (!mScroller.isFinished()) {
                    mHandler.postDelayed(this, 20);
                } else {
                    mHandler.removeCallbacks(this);
                    postScrollFinished(finishActivity);
                }
            }
        }, 20);
    }

    /**
     * 滚动到最右侧，然后关闭activity
     */
    public void scrollToFinishActivity() {
        scrollContentToSmooth(getContentWidth(), 0, true);
    }

    /**
     * 滚动到初始位置
     */
    public void scrollToOriginal() {
        scrollContentToSmooth(0, 0, false);
    }


    /**
     * 当嵌套的一个view打开时，向左移动（仿微信）
     */
    public void scrollToPositionWhenNesting() {
        if (mNestingScrollEnabled)
            scrollContentToSmooth((int) getNestingMinLeft(), 0, false);
    }

    /**
     * 当下一个view打开时，会向左移动，可以达到的最小位置
     *
     * @return 最小位置
     */
    private float getNestingMinLeft() {
        return -getContentWidth() * mNestingScrollRatio;
    }

    /**
     * 当下一个view打开时，会向左移动，可以达到的最大位置
     *
     * @return 最大位置
     */
    private float getNestingMaxLeft() {
        return 0;
    }

    public void followScrollWithNext(int x, int y) {
        if (!mNestingScrollEnabled)
            return;

        //如果还没有结束，强制结束
        if (null != mScroller && !mScroller.isFinished()) {
            mScroller.forceFinished(true);

            mContentLeft = getNestingMinLeft();
            mContentTop = 0;

            postInvalidate();
            requestLayout();
        }

        float min = getNestingMinLeft();
        float max = getNestingMaxLeft();

        //计算移动的比例
        float ratio = x * 1.0f / (getContentWidth());

        //计算当前需要移动的距离
        float dx = ((max - min) * ratio);

        float contentLeft = mContentLeft;
        //控制边界
        if (dx + contentLeft < min) {
            dx = min - contentLeft;
        } else if (dx + contentLeft > max) {
            dx = max - contentLeft;
        }
        scrollContentBy(dx, y);

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View child = mContentView;

        if (null != child) {
            child.layout((int) mContentLeft, (int) mContentTop,
                    (int) mContentLeft + child.getMeasuredWidth(),
                    (int) mContentTop + child.getMeasuredHeight());
        }
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

            canvas.clipRect(0, 0, mContentLeft, getContentHeight());

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
            int right = (int) mContentLeft;
            int bottom = getContentHeight();
            float percent = getContentScrollPercent();
            mShadowDrawable.setAlpha((int) ((1 - percent) * 255));
            mShadowDrawable.setBounds(right - getShadowDrawableWidth(), 0, right, bottom);
            mShadowDrawable.draw(canvas);
        }
    }

    @Override
    public void computeScroll() {
        if (null != mScroller) {
            if (mScroller.computeScrollOffset()) {

                int x = mScroller.getCurrX();
                int y = mScroller.getCurrY();

                if (null != mOnScrollChangedListener) {
                    mOnScrollChangedListener.onScroll(x - (int) mContentLeft, y - (int) mContentTop);
                }
                scrollContentTo(x, y);
            } else {

            }
        }
    }

    protected void postScrollFinished(boolean finishActivity) {
        if (null != mOnScrollChangedListener) {
            mOnScrollChangedListener.onFinishActivity();
        }

        if (finishActivity && mFinishActivityEnabled) {
            if (null != mActivity)
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
    protected void onDetachedFromWindow() {
        if (null != mVelocityTracker) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }

        super.onDetachedFromWindow();
    }


    public void setDimColor(int dimColor) {
        mDimColor = dimColor;
        postInvalidate();
    }

    public void setNestingScrollEnabled(boolean nestingScrollEnabled) {
        mNestingScrollEnabled = nestingScrollEnabled;
    }

    public void setNestingScrollRatio(float nestingScrollRatio) {
        mNestingScrollRatio = nestingScrollRatio;
    }

    public void setScrollDuration(int scrollDuration) {
        mScrollDuration = scrollDuration;
    }

    public void setShadowDrawable(Drawable shadowDrawable) {
        mShadowDrawable = shadowDrawable;
        postInvalidate();
    }

    public void setThresholdRatio(float thresholdRatio) {
        mThresholdRatio = thresholdRatio;
    }
}
