package com.hanbing.library.android.view.plugin;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by hanbing
 */
public abstract class DrawerItemWrapper<VG extends ViewGroup> implements IPluginWrapper {

    public interface Actor {
        /**
         * Super dispatch touch event
         * Use to send cancel touch events
         * @param ev
         * @return
         */
        public boolean dispatchTouchEventSuper(MotionEvent ev);

        /**
         * Close drawer
         */
        public void closeDrawer();
    }


    public interface Adapter {
        /**
         *
         * @param position position of total ItemCount, not the real index in the ViewGroup
         * @return return max scroll the child at specify position can reach
         */
        public int getMaxScroll(int position);
    }

    /**
     * Last touch down position
     */
    int mLastDownPosition = -1;


    /**
     * Discard touch events or not
     */
    boolean mDiscardTouchEvent = false;

    /**
     * Last down x, y
     */
    int mLastDownX, mLastDownY;

    /**
     * Last motion x, y
     */
    int mLastMotionX, mLastMotionY;


    int mTouchSlop;

    int mMaximumFlingVelocity;

    int mMinimumFlingVelocity;

    boolean mIsBeingDragged = false;

    MoveScroller mScroller;

    boolean mOpened = false;

    /**
     *
     */
    VelocityTracker mVelocityTracker;


    /**
     *
     */
    VG mParent;

    /**
     *
     */
    Adapter mAdapter;


    Handler mHandler = new Handler();

    LongPressChecker mLongPressChecker;

    int mScrollDuration = 750;


    public DrawerItemWrapper(VG parent) {

        if (null == parent)
            throw new IllegalArgumentException("Parent must not be null.");

        this.mParent = parent;

        ViewConfiguration configuration = ViewConfiguration.get(parent.getContext());

        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();

        mScroller = new MoveScroller(parent.getContext());

    }


    @Override
    public void measure(ViewGroup parent, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {

    }

    @Override
    public void layout(ViewGroup parent) {

    }

    @Override
    public void draw(ViewGroup parent, Canvas canvas) {

    }

    public MotionEvent makeCancelTouchEvent(MotionEvent ev) {
        MotionEvent cancelEvent = MotionEvent.obtain(ev);
        cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
        return  cancelEvent;

    }

    @Override
    public boolean interceptTouchEvent(MotionEvent ev) {

        if (null == mAdapter)
            return false;

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mIsBeingDragged = false;
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(ev);

                int position = findPosition(ev);

                if (position >= 0) {

                    mLastDownX = mLastMotionX = x;
                    mLastDownY = mLastMotionY = y;


                    if (mOpened) {
                        if (position == mLastDownPosition) {
                            //touch same position, continue move
                            //at the same time, we start check long press
                            preventLongPress(ev);
                        } else {
                            //close last open position
                            close(mLastDownPosition);

                            //intercept all follow touch events, and do nothing
                            mDiscardTouchEvent = true;
                            return true;
                        }
                    } else {
                        //open new position
                        mLastDownPosition = position;
                    }

                }
                return false;
            }
//            break;
            case MotionEvent.ACTION_MOVE: {
                //just discard touches, and do nothing
                if (mDiscardTouchEvent)
                {
                    return true;
                }

                //if scroller is active, abort it
                mScroller.abort();

                mVelocityTracker.addMovement(ev);
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);

                float vx = mVelocityTracker.getXVelocity();
                float vy = mVelocityTracker.getYVelocity();

                //move vertical, do not intercept
                if (Math.abs(vy) > Math.abs(vx)) {
                    cancelPreventLongPress();
                    return false;
                }

                //begin move ,send cancel event
                sendCancelTouchEventDirectly(ev);

                mIsBeingDragged = true;

                //do move
                int deltaX = x - mLastMotionX;

                scrollBy(mLastDownPosition, -deltaX);

                mLastMotionX = x;
                mLastMotionY = y;

            }
            break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                //just discard touches, and do nothing
                if (mDiscardTouchEvent)
                {
                    mDiscardTouchEvent = false;
                    return true;
                }

                if (mIsBeingDragged) {
                    mVelocityTracker.addMovement(ev);
                    mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);

                    float vx = mVelocityTracker.getXVelocity();

                    if (Math.abs(vx) > mMinimumFlingVelocity) {
                        if (vx > 0) {
                            close();
                        } else {
                            open();
                        }
                    } else {
                        checkOpenOrClose();
                    }
                    sendCancelTouchEventDirectly(ev);
                    return true;
                } else {

                    if (mOpened) {

                        //if touch in extra, return false to dispatch up event
                        int right = mParent.getMeasuredWidth() - mParent.getPaddingRight();
                        int left = right - getMaxScroll(mLastDownPosition);
                        if (mLastDownX >= left && mLastDownX <= right) {
                            return false;
                        } else {
                            //cancel touch event and send directly
                            sendCancelTouchEventDirectly(ev);

                            //if touch without move and drawer is opened, close it
                            close();
                            return true;
                        }

                    }

                    return false;
                }

            }
        }

        return false;
    }

    void preventLongPress(MotionEvent event) {

        cancelPreventLongPress();

        int longPressTimeout = ViewConfiguration.getLongPressTimeout();
        mLongPressChecker = new LongPressChecker(event);
        mHandler.postDelayed(mLongPressChecker, longPressTimeout - 10);

    }

    void cancelPreventLongPress() {
        if (null != mLongPressChecker) {
            mHandler.removeCallbacks(mLongPressChecker);
            mLongPressChecker = null;
        }
    }

    /**
     * At this time, time is no up, so we send event directly
     * @param event
     */
    void sendCancelTouchEventDirectly(MotionEvent event) {
        cancelPreventLongPress();
        sendCancelTouchEvent(event);
        mLongPressChecker = null;
    }

    void sendCancelTouchEvent(MotionEvent event) {
        if (mParent instanceof Actor) {
            ((Actor)mParent).dispatchTouchEventSuper(makeCancelTouchEvent(event));
        }
    }

    /**
     * find child position by touch event
     * @param ev
     * @return
     */
    public abstract int findPosition(MotionEvent ev);

    /**
     * get child at position
     * @param position return by {@link #findPosition(MotionEvent)}
     * @return
     */
    public abstract View getChildAt(int position);

    int getMaxScroll(int position) {
        if (null != mAdapter) return mAdapter.getMaxScroll(position);

        return 0;
    }


    void scrollTo(int position, int x) {
        View child = getChildAt(position);

        if (null == child)
            return;

        int maxScroll = getMaxScroll(position);

        int scrollX = Math.max(0, Math.min(maxScroll, x));

        child.scrollTo(scrollX, 0);
    }

    void smoothScrollTo(int position, int x) {

        mScroller.abort();
        mScroller.scrollTo(position, x);
    }

    void scrollBy(int position, int deltaX) {

        View child = getChildAt(position);

        if (null == child)
            return;

        int maxScroll = getMaxScroll(position);

        int scrollX = child.getScrollX();

        scrollX = Math.max(0, Math.min(maxScroll, scrollX + deltaX));

        child.scrollTo(scrollX, 0);
    }

    void checkOpenOrClose() {
        View child = getChildAt(mLastDownPosition);
        if (null != child) {

            int maxScroll = getMaxScroll(mLastDownPosition);
            if (child.getScrollX() > maxScroll / 2) {
                open();
            } else {
                close();
            }

        }
    }

    public void open() {
        open(mLastDownPosition);
    }

    public void open(int position) {
        mOpened = true;
        smoothScrollTo(position, getMaxScroll(position));
    }

    public void close() {
        close(mLastDownPosition);
    }

    public void close(int position) {
        mOpened = false;
        mLastDownPosition = -1;
        smoothScrollTo(position, 0);
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
    }


    class  LongPressChecker implements Runnable {

        MotionEvent mDownEvent;

        public LongPressChecker(MotionEvent event) {
            mDownEvent = MotionEvent.obtain(event);
        }

        @Override
        public void run() {
            sendCancelTouchEvent(mDownEvent);
            mLongPressChecker = null;
        }
    }

    class MoveScroller implements Runnable {

        Scroller mScroller;
        Handler mHandler;
        int mDelay = 20;
        int mPosition = -1;

        public MoveScroller(Context context) {
            mScroller = new Scroller(context);
            mHandler = new Handler();
        }

        public void abort() {
            if (!mScroller.isFinished() || mScroller.computeScrollOffset()) {
                mScroller.forceFinished(true);
                mHandler.removeCallbacks(this);
            }
        }

        public void scrollTo(int position, int x) {

            mPosition = position;
            View child = getChildAt(position);
            if (null == child)
                return;

            int scrollX = child.getScrollX();
            mScroller.startScroll(scrollX, 0, x - scrollX, mScrollDuration);
            mHandler.postDelayed(this, mDelay);
        }

        @Override
        public void run() {

            if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
                DrawerItemWrapper.this.scrollTo(mPosition, mScroller.getCurrX());

                mHandler.postDelayed(this, mDelay);
            } else {
                mHandler.removeCallbacks(this);
            }
        }
    }
}
