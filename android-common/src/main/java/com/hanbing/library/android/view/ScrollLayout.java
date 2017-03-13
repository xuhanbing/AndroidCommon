/**
 */
package com.hanbing.library.android.view;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by hanbing
 */

public class ScrollLayout extends ViewGroup {

    /**
     * animate view
     *
     * @author hanbing
     */
    public interface PageTransformer extends ViewPager.PageTransformer {

        public void transformPage(View view, float position);
    }

    private static final String TAG = "ScrollLayout";

    private Scroller mScroller;

    private VelocityTracker mVelocityTracker;

    private int mCurScreen;

    private int mDefaultScreen = 0;

    private static final int TOUCH_STATE_REST = 0;

    private static final int TOUCH_STATE_SCROLLING = 1;

    private static final int SNAP_VELOCITY = 300;

    private static final int SNAP_DURATION = 0;

    private int mTouchState = TOUCH_STATE_REST;

    private int mTouchSlop;

    private float mLastMotionX;

    private float mLastMotionY;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int mOrientation = VERTICAL;// HORIZONTAL;

    private boolean mEnableScroll = true;

    private int snapDuration = SNAP_DURATION;
    /**
     * double click max interval. If the two click interval is less than the
     * value, it is considered a double click.
     */
    private static final int DOUBLE_CLICK_MAX_INTERVAL = 200;

    boolean mIsFirstDown = true;
    long mLastDownTime = 0;

    int mMinimumVelocity;
    int mMaximumVelocity;

    PageTransformer mPageTransformer;

    private OnScreenChangedListener mScreenListener = new OnScreenChangedListener() {

        @Override
        public void onScreenScroll(int current, float percent, int next) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onScreenChanged(int screen) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onDoubleClick(int screen) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onScreenScrollStart() {
            // TODO Auto-generated method stub

        }
    };

    public ScrollLayout(Context context) {

        this(context, null);

    }

    public ScrollLayout(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public ScrollLayout(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        mScroller = new Scroller(context);

        mCurScreen = mDefaultScreen;


        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childLeft = 0;
        int childTop = 0;

        final int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {

            final View childView = getChildAt(i);

            if (childView.getVisibility() != View.GONE) {

                final int childWidth = childView.getMeasuredWidth();
                final int childHeight = childView.getMeasuredHeight();

                if (isHorizontal()) {
                    childView.layout(childLeft, 0, childLeft + childWidth,
                            childHeight);

                    childLeft += childWidth;
                } else {
                    childView.layout(0, childTop, childWidth, childTop
                            + childHeight);

                    childTop += childHeight;
                }

            }

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int width = MeasureSpec.getSize(widthMeasureSpec);

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);


        final int height = MeasureSpec.getSize(heightMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        // The children are given the same width and height as the scrollLayout
        final int count = getChildCount();

        for (int i = 0; i < count; i++) {

            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);

        }

        scrollToCurrentScreen();
    }

    /**
     * According to the position of current layout scroll to the destination
     * page.
     */

    public void snapToDestination() {

        final int screenWidth = getWidth();
        final int screenHeight = getHeight();

        final int destScreen = isHorizontal() ? (getScrollX() + screenWidth / 2)
                / screenWidth
                : (getScrollY() + screenHeight / 2) / screenHeight;

        snapToScreen(destScreen);

    }

    public void snapToScreen(int whichScreen) {

        // get the valid layout page

        int width = getWidth();
        int height = getHeight();

        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));

        if (isHorizontal() && getScrollX() != (whichScreen * getWidth())) {

            final int delta = whichScreen * getWidth() - getScrollX();
            snapToScreen(whichScreen, delta);

        } else if (!isHorizontal() && getScrollY() != (whichScreen * height)) {

            final int delta = whichScreen * height - getScrollY();

            snapToScreen(whichScreen, delta);
        }

    }

    private void snapToScreen(int whichScreen, int delta) {

        /**
         * use duration which set
         */
        int duration = snapDuration;

        /**
         * if do not set duration, we calc duration base delta
         */
        if (0 >= duration) {
            duration = Math.abs((int) (delta / 0.75f));
        }

        if (isHorizontal()) {
            mScroller.startScroll(getScrollX(), 0, delta, 0, duration);
        } else {
            mScroller.startScroll(0, getScrollY(), 0, delta, duration);
        }

        mCurScreen = whichScreen;

        if (null != mScreenListener)
            mScreenListener.onScreenChanged(mCurScreen);

        invalidate(); // Redraw the layout
    }

    public void setToScreen(int whichScreen) {

        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));

        mCurScreen = whichScreen;

        if (null != mScreenListener)
            mScreenListener.onScreenChanged(mCurScreen);

        scrollToCurrentScreen();
    }

    public void scrollToCurrentScreen() {

        if (isHorizontal()) {
            scrollTo(mCurScreen * getWidth(), 0);
        } else {
            scrollTo(0, mCurScreen * getHeight());
        }
    }

    public int getCurScreen() {

        return mCurScreen;

    }


    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {

            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            postInvalidate();
            populate();
        }


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        if (MotionEvent.ACTION_DOWN == ev.getAction()) {

            if (null != mVelocityTracker) mVelocityTracker.clear();

            if (mIsFirstDown) {
                mLastDownTime = SystemClock.elapsedRealtime();
                mIsFirstDown = false;
            } else {
                long time = SystemClock.elapsedRealtime();
                if (time - mLastDownTime <= DOUBLE_CLICK_MAX_INTERVAL) {
                    if (null != mScreenListener) {
                        mScreenListener.onDoubleClick(mCurScreen);
                    }

                    mIsFirstDown = true;
                }

                mLastDownTime = time;
            }



            mLastMotionX = ev.getX();
            mLastMotionY = ev.getY();
        }

        mVelocityTracker.addMovement(ev);

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        final int action = event.getAction();

        final float x = event.getX();

        final float y = event.getY();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE: {

                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                int deltaX = (int) (mLastMotionX - x);
                int deltaY = (int) (mLastMotionY - y);

                int delta = 0;
                mLastMotionX = x;
                mLastMotionY = y;

                // current scroll distance
                int scrollDis = 0;
                // min scoll distance
                int scrollMin = 0;
                // max scroll distance
                int scrollMax = 0;

                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);

                int velocityX = (int) mVelocityTracker.getXVelocity();
                int velocityY = (int) mVelocityTracker.getYVelocity();

                int screenSize = 0;
                if (isHorizontal()) {

                    if (Math.abs(velocityX) < Math.abs(velocityY)) {
                        deltaX = 0;
                    } else if (Math.abs(velocityX) > SNAP_VELOCITY) {
                        if (null != mScreenListener) {
                            mScreenListener.onScreenScrollStart();
                        }
                    }
                    delta = deltaX;
                    deltaY = 0;
                    screenSize = getWidth();
                    scrollDis = getScrollX();
                    scrollMax = getWidth() * (getChildCount() - 1);
                } else {

                    if (Math.abs(mVelocityTracker.getXVelocity()) > Math
                            .abs(velocityY)) {
                        deltaY = 0;
                    } else if (Math.abs(velocityY) > SNAP_VELOCITY) {
                        if (null != mScreenListener) {
                            mScreenListener.onScreenScrollStart();
                        }
                    }
                    delta = deltaY;
                    deltaX = 0;
                    screenSize = getHeight();
                    scrollDis = getScrollY();
                    scrollMax = getHeight() * (getChildCount() - 1);
                }

                if (mCurScreen == 0 && (delta + scrollDis) <= scrollMin) {

                } else if (mCurScreen == this.getChildCount() - 1
                        && (delta + scrollDis) >= scrollMax) {

                } else {

                    if (mEnableScroll) {

                        this.scrollBy(deltaX, deltaY);

                        if (null != mScreenListener) {

                            float percent = (scrollDis - mCurScreen * screenSize)
                                    * 1.0f / screenSize;

                            int next = mCurScreen;

                            if (percent > 0) {
                                next++;
                            } else if (percent < 0) {
                                next--;
                            }

                            next = Math.max(0, Math.min(next, getChildCount() - 1));
                            mScreenListener.onScreenScroll(mCurScreen, percent,
                                    next);
                        }

                        populate();
                    }

                }
            }
            break;

            case MotionEvent.ACTION_UP:

                final VelocityTracker velocityTracker = mVelocityTracker;

                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);

                int velocityX = (int) velocityTracker.getXVelocity();
                int velocityY = (int) velocityTracker.getYVelocity();

                if (((velocityX > SNAP_VELOCITY && isHorizontal()) || (velocityY > SNAP_VELOCITY && !isHorizontal()))
                        && mCurScreen > 0) {
                    snapToScreen(mCurScreen - 1);

                } else if (((velocityX < -SNAP_VELOCITY && isHorizontal()) || (velocityY < -SNAP_VELOCITY && !isHorizontal()))
                        && mCurScreen < getChildCount() - 1) {
                    snapToScreen(mCurScreen + 1);

                } else {
                    snapToDestination();
                }



                mTouchState = TOUCH_STATE_REST;
                break;

            case MotionEvent.ACTION_CANCEL:

                mTouchState = TOUCH_STATE_REST;

                break;

        }

        return true;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();

//		if ((action == MotionEvent.ACTION_MOVE)
//				&& (mTouchState != TOUCH_STATE_REST)) {
//
//			return true;
//
//		}

        final float x = ev.getX();

        final float y = ev.getY();

        switch (action) {

            case MotionEvent.ACTION_MOVE:

                final int xDiff = (int) Math.abs(mLastMotionX - x);
                final int yDiff = (int) Math.abs(mLastMotionY - y);

                if (xDiff > mTouchSlop && isHorizontal()) {

                    mTouchState = TOUCH_STATE_SCROLLING;

                } else if (yDiff > mTouchSlop && !isHorizontal()) {
                    mTouchState = TOUCH_STATE_SCROLLING;
                }

                break;

            case MotionEvent.ACTION_DOWN:

                mLastMotionX = x;

                mLastMotionY = y;

                mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
                        : TOUCH_STATE_SCROLLING;

                break;

            case MotionEvent.ACTION_CANCEL:

            case MotionEvent.ACTION_UP:

                mTouchState = TOUCH_STATE_REST;

                break;

        }

        return mTouchState != TOUCH_STATE_REST;

    }


    void populate() {
        if (null == mPageTransformer)
            return;

        int screen = mCurScreen;
        int scroll = 0;

        if (isHorizontal()) {
            scroll = getScrollX();
        } else {
            scroll = getScrollY();
        }

        int childCount = getChildCount();

        int delta = 0;
        int start = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);


            if (isHorizontal()) {
                delta = child.getMeasuredWidth();
                start = i * delta;

            } else {
                delta = child.getMeasuredHeight();
                start = i * delta;
            }


            float position = (start - scroll) * 1.0f / delta;

            mPageTransformer.transformPage(child, position);

        }
    }


    public void setPageListener(OnScreenChangedListener aScreenListener) {

        this.mScreenListener = aScreenListener;

    }

    public boolean isHorizontal() {
        return HORIZONTAL == mOrientation;
    }


    public void setOritention(int oritention) {
        this.mOrientation = oritention;
    }

    /**
     * @return the isAbleScroll
     */
    public boolean isEnableScroll() {
        return mEnableScroll;
    }

    /**
     * @param enableScroll the isAbleScroll to set
     */
    public void setEnableScroll(boolean enableScroll) {
        this.mEnableScroll = enableScroll;
    }

    public void setOnScreenChangedListener(OnScreenChangedListener lsner) {
        mScreenListener = lsner;
    }

    public void setPageTransformer(PageTransformer pageTransformer) {
        this.mPageTransformer = pageTransformer;
    }

    public interface OnScreenChangedListener {

        /**
         * callback when start real scroll
         */
        public void onScreenScrollStart();

        /**
         * callback when screen changed
         *
         * @param screen
         */
        public void onScreenChanged(int screen);

        /**
         * callback when screen is scrolling
         *
         * @param current current show page
         * @param percent percent of current page scroll
         * @param next    next show page
         */
        public void onScreenScroll(int current, float percent, int next);

        /**
         * callback when double click screen
         *
         * @param screen
         */
        public void onDoubleClick(int screen);
    }
}
