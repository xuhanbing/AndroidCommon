package com.hanbing.library.android.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.Scroller;

import com.hanbing.library.android.R;
import com.hanbing.library.android.util.LogUtils;

/**
 * Created by hanbing on 2016/9/2
 */
public class SwipeBackLayout extends FrameLayout {

    public interface OnScrollChangedListener{
        public void onScroll(int x, int y);
    }

    public static final int DEFAULT_DIM_COLOR = 0x88000000;
    Activity mActivity;
    View mContentView;
    int mContentLeft;
    int mContentTop;

    int mMaximumFlingVelocity;
    int mMinimumFlingVelocity;


    /**
     * 临界比例，大于该比例将关闭，否则回到初始状态
     */
    float mThresholdRatio = 0.5f;

    int mLastDownX;
    int mLastDownY;

    int mLastMotionX;
    int mLastMotionY;

    boolean mIsBeingDragged;

    VelocityTracker mVelocityTracker;

    Scroller mScroller;

    int mScrollAnimateDuration = 500;

    Handler mHandler = new Handler();

    OnScrollChangedListener mOnScrollChangedListener;

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        mOnScrollChangedListener = onScrollChangedListener;
    }

    public SwipeBackLayout(Context context) {
        super(context);
        init();
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        ViewConfiguration configuration = ViewConfiguration.get(getContext());

        mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();

        mShadowDrawable = AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.shadow_left);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mLastDownX = mLastMotionX = (int) ev.getRawX();
            mLastDownY = mLastMotionY = (int) ev.getRawY();

            mIsBeingDragged = false;

            if (null == mVelocityTracker)
                mVelocityTracker = VelocityTracker.obtain();
            else
                mVelocityTracker.clear();

            if (null != mScroller && !mScroller.isFinished()) {
                mScroller.forceFinished(true);
            }

        } else {
        }

        mVelocityTracker.addMovement(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
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

    private void checkBeingDragged(){
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

        if (!mIsBeingDragged)
        {
            checkBeingDragged();
        }

        if (mIsBeingDragged) {

            int deltaX = x - mLastMotionX;

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
            if (!flingToClose()) {
                //已经拖动，判断拖动的距离是否达到临界值
                int width = getContentWidth();
                int distance = (int) (width * mThresholdRatio);
                if (Math.abs(getContentScrollX()) > distance) {
                    //
                    scrollToClose();
                } else {
                    scrollToOriginal();
                }
            }
        } else {

            flingToClose();
        }

        return true;
    }

    private boolean flingToClose() {
        //计算速度是否达到关闭速度
        mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
        float xVelocity = mVelocityTracker.getXVelocity();
        float yVelocity = mVelocityTracker.getYVelocity();


        if (Math.abs(xVelocity) > Math.abs(yVelocity)
                && xVelocity > mMinimumFlingVelocity) {
            //水平向右滑动，且速度大于最小速度，关闭
            scrollToClose();

            return true;
        }
        return false;
    }



    private void correctLeftAndTop(){
        mContentLeft = Math.max(0, mContentLeft);
        mContentTop = 0;
    }

    private int getContentWidth() {
        return null != mContentView ? mContentView.getMeasuredWidth() : 0;
    }

    private int getContentHeight() {
        return null != mContentView ? mContentView.getMeasuredHeight() : 0;
    }

    private int getContentScrollX() {
        return mContentLeft;
    }

    private int getContentScrollY() {
        return mContentTop;
    }

    public void scrollContentBy(int x, int y) {
        mContentLeft += x;
        mContentTop += y;

        correctLeftAndTop();

        invalidate();
        requestLayout();
    }

    public void scrollContentTo(int x, int y) {
        mContentLeft = x;
        mContentTop = y;

        correctLeftAndTop();

        invalidate();
        requestLayout();
    }

    public void scrollContentToSmooth(int x, int y, final boolean finishActivity) {
        if (null == mScroller)
            mScroller = new Scroller(getContext());

        int startX = getContentScrollX();
        int startY = getContentScrollY();

        mScroller.startScroll(startX, startY, x - startX, y - startY, mScrollAnimateDuration);
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

    public void scrollToClose() {
        scrollContentToSmooth(getContentWidth(), 0, true);
    }

    public void scrollToOriginal() {
        scrollContentToSmooth(0, 0, false);
    }

    public void scrollWhenNextOpened(){
        scrollContentToSmooth((int) (-getContentWidth() * 0.2f), 0, false);
    }

    Drawable mShadowDrawable;
    int mDimColor = DEFAULT_DIM_COLOR;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View child = mContentView;

        if (null != child) {
            child.layout(mContentLeft, mContentTop,
                    mContentLeft + child.getMeasuredWidth(),
                    mContentTop + child.getMeasuredHeight());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawShadow(canvas);
        drawDim(canvas);
    }

    private void drawDim(Canvas canvas) {

        if (mDimColor != 0) {
            float percent = mContentLeft * 1.0f / getContentWidth();

            int alpha = (mDimColor>> 24) & 0xff;

            alpha = (int) (alpha * (1 - percent));
            int color = (alpha << 24) | (mDimColor & 0x00ffffff);

            canvas.clipRect(0, 0, mContentLeft, getContentHeight());

            canvas.drawColor(color);
        }
    }

    private void drawShadow(Canvas canvas) {
        if (null != mShadowDrawable) {
            mShadowDrawable.setBounds(mContentLeft - mShadowDrawable.getIntrinsicWidth(), 0, mContentLeft, getContentHeight());
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
                    mOnScrollChangedListener.onScroll(x - mContentLeft, y - mContentTop);
                }
                scrollContentTo(x, y);
            } else {

            }
        }
    }

    protected void postScrollFinished(boolean finishActivity){
        if (null != mOnScrollChangedListener) {
        }

            if (finishActivity) {
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
}
