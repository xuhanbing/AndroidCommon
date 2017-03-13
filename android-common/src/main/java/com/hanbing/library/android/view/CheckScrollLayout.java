package com.hanbing.library.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;

/**
 * Created by hanbing on 2016/7/26
 */
public class CheckScrollLayout extends ScrollLayout {

    public interface ScrollChecker {
        boolean canScrollFromStart();
        boolean canScrollFromEnd();
    }

    VelocityTracker mVelocityTracker;
    ScrollChecker mScrollChecker;

    public CheckScrollLayout(Context context) {
        super(context);
    }

    public CheckScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckScrollLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (null == mVelocityTracker)
            mVelocityTracker = VelocityTracker.obtain();

        if (ev.getAction() == MotionEvent.ACTION_DOWN)
            mVelocityTracker.clear();
        mVelocityTracker.addMovement(ev);

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (null != mScrollChecker) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:

                    mVelocityTracker.computeCurrentVelocity(1000);

                    float vx = mVelocityTracker.getXVelocity();
                    float vy = mVelocityTracker.getYVelocity();

                    float v = isHorizontal() ? vx : vy;

                    if (v > 0 && !mScrollChecker.canScrollFromStart()) {
                        return false;
                    } else if (v < 0 && !mScrollChecker.canScrollFromEnd()) {
                        return false;
                    }

                    break;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    public void setScrollChecker(ScrollChecker scrollChecker) {
        mScrollChecker = scrollChecker;
    }
}
