/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014閿熸枻鎷�7閿熸枻鎷�23閿熸枻鎷�
 * Time : 閿熸枻鎷烽敓鏂ゆ嫹11:00:58
 */
package com.hanbing.mytest.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 
 * 閿熸枻鎷稬auncher閿熷彨纰夋嫹WorkSapce閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸彮浼欐嫹閿熸枻鎷烽敓鍙紮鎷烽敓鏂ゆ嫹骞曢敓鏂ゆ嫹閿熸枻鎷�
 * 
 * @author cmm
 * 
 *         @ date: 2011-05-04
 */

public class ScrollLayout extends ViewGroup {

    private static final String TAG = "ScrollLayout";

    private Scroller mScroller;

    private VelocityTracker mVelocityTracker;

    private int mCurScreen;

    private int mDefaultScreen = 0;

    private static final int TOUCH_STATE_REST = 0;

    private static final int TOUCH_STATE_SCROLLING = 1;

    private static final int SNAP_VELOCITY = 300;

    private int mTouchState = TOUCH_STATE_REST;

    private int mTouchSlop;

    private float mLastMotionX;

    private float mLastMotionY;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int mOritention = VERTICAL;// HORIZONTAL;

    private boolean isAbleScroll = true;

    /**
     * double click max interval. If the two click interval is less than the
     * value, it is considered a double click.
     */
    private static final int DOUBLE_CLICK_MAX_INTERVAL = 200;

    boolean mIsFirstDown = true;
    long mLastDownTime = 0;

    private OnScreenChangedListener mScreenListener = new OnScreenChangedListener() {

	@Override
	public void onScreenScroll(int current, float percent, int next) {
	    // TODO Auto-generated method stub
	    Log.e("", "onScreenScroll current:" + current + ",next=" + next
		    + ",percent=" + percent);
	}

	@Override
	public void onScreenChanged(int screen) {
	    // TODO Auto-generated method stub
	    Log.e("", "onScreenChanged " + screen);
	}

	@Override
	public void onDoubleClick(int screen) {
	    // TODO Auto-generated method stub
	    Log.e("", "onDoubleClick " + screen);
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

	mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

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

	// if (widthMode != MeasureSpec.EXACTLY) {

	// throw new
	// IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");

	// }

	/**
	 * 
	 * wrap_content 閿熸枻鎷烽敓鏂ゆ嫹鍘婚敓鏂ゆ嫹閿熸枻鎷稟T_MOST 閿熸暀璁规嫹閿熸枻鎷峰�奸敓鏂ゆ嫹fill_parent
	 * 閿熸枻鎷烽敓鏂ゆ嫹閿熶茎锛枻鎷烽敓绱糥ACTLY
	 */

	final int height = MeasureSpec.getSize(heightMeasureSpec);
	final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

	// if (heightMode != MeasureSpec.EXACTLY) {

	// throw new
	// IllegalStateException("ScrollLayout only can run at EXACTLY mode!");

	// }

	// The children are given the same width and height as the scrollLayout

	final int count = getChildCount();

	for (int i = 0; i < count; i++) {

	    getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);

	}

	scrollToCurrentScreen();
    }

    /**
     * 
     * According to the position of current layout scroll to the destination
     * 
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
	if (isHorizontal()) {
	    mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta));
	} else {
	    mScroller.startScroll(0, getScrollY(), 0, delta, Math.abs(delta));
	}

	mCurScreen = whichScreen;

	if (null != mScreenListener)
	    mScreenListener.onScreenChanged(mCurScreen);

	invalidate(); // Redraw the layout
    }

    public void setToScreen(int whichScreen) {

	whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));

	mCurScreen = whichScreen;

	scrollToCurrentScreen();
    }

    public void scrollToCurrentScreen() {
	if (isHorizontal()) {
	    scrollTo(mCurScreen * getWidth(), 0);
	} else {
	    scrollTo(0, mCurScreen * getHeight());
	}
    }

    /**
     * 
     * 閿熸枻鎷风帿閿熻鑰欑鎷烽敓锟�
     */

    public int getCurScreen() {

	return mCurScreen;

    }

    /**
     * 
     * 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹鐗￠敓瑙掕�欑鎷烽敓锟�
     */

    // public int getPage() {

    // return page;

    // }

    @Override
    public void computeScroll() {

	if (mScroller.computeScrollOffset()) {

	    scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

	    postInvalidate();

	}

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

	if (mVelocityTracker == null) {

	    mVelocityTracker = VelocityTracker.obtain();

	}

	mVelocityTracker.addMovement(event);

	final int action = event.getAction();

	final float x = event.getX();

	final float y = event.getY();

	switch (action) {

	case MotionEvent.ACTION_DOWN:

	    if (mIsFirstDown) {
		mLastDownTime = SystemClock.elapsedRealtime();
		mIsFirstDown = false;
	    } else {
		long time = SystemClock.elapsedRealtime();
		Log.e("", "interval " + (time - mLastDownTime));
		if (time - mLastDownTime <= DOUBLE_CLICK_MAX_INTERVAL) {
		    if (null != mScreenListener) {
			mScreenListener.onDoubleClick(mCurScreen);
		    }

		    mIsFirstDown = true;
		}

		mLastDownTime = time;
	    }

	    if (!mScroller.isFinished()) {

		mScroller.abortAnimation();

	    }

	    mLastMotionX = x;
	    mLastMotionY = y;

	    break;

	case MotionEvent.ACTION_MOVE:

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

	    mVelocityTracker.computeCurrentVelocity(1000);

	    int screenSize = 0;
	    if (isHorizontal()) {

		if (Math.abs(mVelocityTracker.getXVelocity()) < Math
			.abs(mVelocityTracker.getYVelocity())) {
		    deltaX = 0;
		}
		delta = deltaX;
		deltaY = 0;
		screenSize = getWidth();
		scrollDis = getScrollX();
		scrollMax = getWidth() * (getChildCount() - 1);
	    } else {
		if (Math.abs(mVelocityTracker.getXVelocity()) > Math
			.abs(mVelocityTracker.getYVelocity())) {
		    deltaY = 0;
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

		if (isAbleScroll) {
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
		}

	    }

	    break;

	case MotionEvent.ACTION_UP:

	    final VelocityTracker velocityTracker = mVelocityTracker;

	    velocityTracker.computeCurrentVelocity(1000);

	    int velocityX = (int) velocityTracker.getXVelocity();
	    int velocityY = (int) velocityTracker.getYVelocity();

	    if (((velocityX > SNAP_VELOCITY && isHorizontal()) || (velocityY > SNAP_VELOCITY && !isHorizontal()))
		    && mCurScreen > 0) {

		// Fling enough to move left

		snapToScreen(mCurScreen - 1);

		// --page;

	    } else if (((velocityX < -SNAP_VELOCITY && isHorizontal()) || (velocityY < -SNAP_VELOCITY && !isHorizontal()))
		    && mCurScreen < getChildCount() - 1) {

		// Fling enough to move right

		snapToScreen(mCurScreen + 1);

		// ++page;

	    } else {

		snapToDestination();

	    }

	    if (mVelocityTracker != null) {

		mVelocityTracker.recycle();

		mVelocityTracker = null;

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

	if ((action == MotionEvent.ACTION_MOVE)
		&& (mTouchState != TOUCH_STATE_REST)) {

	    return true;

	}

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

    public void setPageListener(OnScreenChangedListener aScreenListener) {

	this.mScreenListener = aScreenListener;

    }

    private boolean isHorizontal() {
	return HORIZONTAL == mOritention;
    }

    /**
     * @param mOritention
     *            the mOritention to set
     */
    public void setOritention(int oritention) {
	this.mOritention = oritention;
    }

    /**
     * @return the isAbleScroll
     */
    public boolean isAbleScroll() {
	return isAbleScroll;
    }

    /**
     * @param isAbleScroll
     *            the isAbleScroll to set
     */
    public void setAbleScroll(boolean isAbleScroll) {
	this.isAbleScroll = isAbleScroll;
    }

    public void setOnScreenChangedListener(OnScreenChangedListener lsner) {
	mScreenListener = lsner;
    }

    public interface OnScreenChangedListener {

	/**
	 * callback when screen changed
	 * 
	 * @param screen
	 */
	public void onScreenChanged(int screen);

	/**
	 * callback when screen is scrolling
	 * 
	 * @param current
	 *            current show page
	 * @param percent
	 *            percent of current page scroll
	 * @param next
	 *            next show page
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
