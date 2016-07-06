package com.common.view.scroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * @author hanbing
 * @Date 2015-7-8
 */
public class StrengthScrollView extends ScrollView {

    public interface OnPullListener {
        /**
         * 下拉
         *
         * @param dy
         *            针对于上次变化的距离
         * @param y
         *            变化总距离
         * @param max
         *            最大距离
         */
        public void onPull(float dy, float y, float max);

        /**
         * 恢复
         *
         * @param dy
         *            针对于上次变化的距离
         * @param y
         *            变化总距离
         * @param max
         *            最大距离
         */
        public void onMoveBack(float dy, float y, float max);
    }

    /**
     * 子控件
     */
    View mChild;

    /**
     * 子空间原始top
     */
    int mChildOriginalTop = 0;
    /**
     * 子控件大小
     */
    int mChildOriginalMeasureHeight = 0;

    /**
     * 最大可移动距离
     */
    int mMaxPullY = 300;

    /**
     * 上一次位置
     */
    float mLastY;
    /**
     * 按下位置
     */
    float mDownY;

    /**
     * 拉动的距离，每次针对与按下时候的位置
     */
    float mPullY;

    /**
     * 是否支持下拉
     */
    boolean mCanPullDown = true;
    /**
     * 是否支持上拉
     */
    boolean mCanPullUp = true;

    /**
     * 当下拉或上拉时是否移动
     */
    boolean mCanMoveWhenPull = true;

    /**
     * 回弹时间
     */
    static final int ANIMATION_DURATION = 500;
    /**
     * 最小比例，越大则移动玉箫
     */
    static final float MIN_RATIO = 3;
    /**
     * 阻尼系数，越大则移动越小
     */
    static final float RATIO_SCALE = 3;
    /**
     * 比例
     */
    float mRatio = 50;

    MyScroller mScroller;

    class MyScroller extends Scroller {

        /**
         * @param context
         */
        public MyScroller(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        /**
         * 下拉还是上拉
         */
        public boolean isPullDown = true;

    }

    /**
     * 拉动监听
     */
    OnPullListener mOnPullListener;

    OnScrollChangedListener mOnScrollChangedListener;

    /**
     * 是否是第一次，第一次记录子空间的高
     */
    boolean mIsFirst = true;

    public void setOnPullListener(OnPullListener lsner) {
        mOnPullListener = lsner;
    }

    /**
     * @param context
     */
    public StrengthScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public StrengthScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public StrengthScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @SuppressLint("NewApi")
    public StrengthScrollView(Context context, AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        // TODO Auto-generated constructor stub
        init();
    }

    protected void init() {
        if (null == mScroller) {
            mScroller = new MyScroller(getContext());
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);

        if (mIsFirst) {
            if (getChildCount() > 0) {
                mChild = getChildAt(0);
                mChildOriginalTop = mChild.getPaddingTop();
                mIsFirst = false;
            }
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        doPull(ev);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param ev
     */
    private void doPull(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (null != mChild) {

            float y = ev.getRawY();

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (null != mScroller && !mScroller.isFinished()) {
                        if (null != mOnPullListener) {
                            mOnPullListener.onMoveBack(0, 0, mMaxPullY);
                        }
                        ;
                        mScroller.abortAnimation();
                    }
                    mDownY = y;
                    mPullY = 0;
                    break;
                case MotionEvent.ACTION_MOVE:

                    int childMeasureHeight = mChild.getMeasuredHeight();

                    float dy = (y - mLastY) / mRatio;
                    mPullY += dy;

                    mRatio = (float) (MIN_RATIO + RATIO_SCALE
                            * Math.tan(Math.PI / 2 / getMeasuredHeight()
                            * (Math.abs(mPullY))));
                    if (mRatio <= 1)
                        mRatio = 1;

                    if (dy >= 0) {

                        if (mCanPullDown) {

                            /**
                             * 向下拉
                             */
                            if (0 == getScrollY()) {

                                fixPullY();

                                onPull(dy, mPullY);

                                callbackPullEvent(dy);
                            }

                        }
                    } else {
                        if (mCanPullUp) {
                            /**
                             * 最底部向上拉
                             */
                            if (getScrollY() + getHeight() >= childMeasureHeight
                                    || getHeight() >= childMeasureHeight || mPullY > 0) {

                                fixPullY();

                                onPull(dy, mPullY);

                                callbackPullEvent(dy);
                            }

                        }
                    }

                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    reset();
                    break;
            }

            mLastY = y;

        }
    }

    /**
     * 重置
     */
    protected void reset() {
        // TODO Auto-generated method stub

        int startY = 0;
        int dy = 0;

        /**
         * 如果支持移动，回调以子空间的位置为准，否则以移动距离为准
         */
        if (mCanMoveWhenPull) {
            startY = mChild.getTop();
            dy = mChildOriginalTop - startY;

        } else {
            startY = (int) mPullY;
            dy = (int) (0 - mPullY);
        }

        if (dy != 0) {
            mScroller.startScroll(0, startY, 0, dy, ANIMATION_DURATION);
            postInvalidate();
        }
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub

        super.computeScroll();

        if (null != mChild && null != mScroller
                && mScroller.computeScrollOffset()) {
            int y = mScroller.getCurrY();

            if (mCanMoveWhenPull)
                mChild.layout(mChild.getLeft(), y, mChild.getRight(), y
                        + mChild.getMeasuredHeight());

            callbackMoveBackEvent(0, y);

            postInvalidate();
        }

    }

    /**
     * 移动
     *
     * @param dy
     *            距离上一次变化
     * @param y
     *            移动总距离
     */
    protected void onPull(float dy, float y) {
        int t = (int) (mChild.getTop() + dy);

        int b = t + mChild.getMeasuredHeight();

        if (mCanMoveWhenPull)
            mChild.layout(mChild.getLeft(), t, mChild.getRight(), b);
    }

    /**
     * 控制移动的最大值
     */
    protected void fixPullY() {
        int y = (int) Math.min(Math.abs(mPullY), mMaxPullY);
        if (0 != mPullY) {
            mPullY = y * mPullY / Math.abs(mPullY);
        }

    }

    /**
     * 回调移动事件
     * @param dy
     */
    protected void callbackPullEvent(float dy) {
        if (null != mOnPullListener) {
            mOnPullListener.onPull(dy, mPullY, mMaxPullY);
        }
    }

    /**
     * 回弹事件
     *
     * @param dy
     * @param y
     */
    protected void callbackMoveBackEvent(float dy, float y) {
        if (null != mOnPullListener) {
            mOnPullListener.onMoveBack(0, y, mMaxPullY);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        // TODO Auto-generated method stub
        if (null != mOnScrollChangedListener)
        {
            mOnScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener)
    {
        this.mOnScrollChangedListener = listener;
    }

    public interface OnScrollChangedListener {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
