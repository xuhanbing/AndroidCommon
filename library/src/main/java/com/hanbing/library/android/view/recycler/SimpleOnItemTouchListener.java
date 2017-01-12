package com.hanbing.library.android.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by hanbing on 2016/6/7.
 */
public class SimpleOnItemTouchListener extends GestureDetector.SimpleOnGestureListener implements RecyclerView.OnItemTouchListener {

    GestureDetector mGestureDetector;
    OnItemClickListener mOnItemClickListener;
    OnItemLongClickListener mOnItemLongClickListener;
    RecyclerView mRecyclerView;


    boolean mDisallowIntercept = false;
    boolean mIsMove = false;
    boolean mIsLongPress = false;
    boolean mIsLongPressHandled = true;

    int mMinimumFlingVelocity;
    int mMaximumFlingVelocity;
    int mTouchSlop;

    VelocityTracker mVelocityTracker;

    float mLastDownX;
    float mLastDownY;
    float mLastMotionX;
    float mLastMotionY;


    //view receive touch events.
    View mTargetChild;

    public SimpleOnItemTouchListener(RecyclerView recyclerView, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        this.mRecyclerView = recyclerView;
        this.mOnItemClickListener = onItemClickListener;
        this.mOnItemLongClickListener = onItemLongClickListener;
        this.mGestureDetector = new GestureDetector(recyclerView.getContext(), this);


        ViewConfiguration viewConfiguration = ViewConfiguration.get(recyclerView.getContext());

        mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mTouchSlop = viewConfiguration.getScaledTouchSlop();

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mDisallowIntercept)
            return false;


        if (e.getAction() == MotionEvent.ACTION_DOWN)
        {
            mTargetChild = null;
        }


        int action = e.getAction();

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (null != child) {
            boolean handled = false;
            //dispatch to child first.
            handled = child.dispatchTouchEvent(transformMotionEvent(rv, child, e));

            if (handled)
            {
                //We cancel gesture here because events will never be sent to gesture detector until next touch down.
                if (MotionEvent.ACTION_DOWN != action) {
                    MotionEvent cancel = MotionEvent.obtain(e);
                    cancel.setAction(MotionEvent.ACTION_CANCEL);
                    mGestureDetector.onTouchEvent(cancel);
                }

                //save target child who will handle following events.
                mTargetChild = child;

                //will return true here, and then handle touch event onTouchEvent
                return true;
            }


            if (null == mVelocityTracker)
                mVelocityTracker = VelocityTracker.obtain();

            mVelocityTracker.addMovement(e);

            float x = e.getRawX();
            float y = e.getRawY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mLastDownX = mLastMotionX = x;
                    mLastDownY = mLastMotionY = y;

                    mIsMove = mIsLongPress = false;
                    mIsLongPressHandled = true;

                    break;
                case MotionEvent.ACTION_MOVE:

                    mIsMove = checkMove(e);

                    mLastMotionX = x;
                    mLastMotionY = y;

                    break;
                case MotionEvent.ACTION_UP:

                    mIsMove = checkMove(e);

                    if (null != mVelocityTracker)
                    {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }

                    //If no move and a long press occur but never handled, we perform as a single tap.
                    if (!mIsMove && mIsLongPress && !mIsLongPressHandled) {
                        return onSingleTapUp(e);
                    }

                    break;
            }

            if (mGestureDetector.onTouchEvent(e))
                return true;
        }


        return false;
    }

    private boolean checkMove(MotionEvent e) {
        float x = e.getRawX();
        float y = e.getRawY();

        mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
        float vx = mVelocityTracker.getXVelocity();
        float vy = mVelocityTracker.getYVelocity();

        float dx = x - mLastDownX;
        float dy = y - mLastDownY;

        //calc move distance
        float delta = (float) Math.sqrt(dx * dx + dy * dy);

        //check if move
        if (delta > mTouchSlop || Math.sqrt(vx * vx + vy * vy) > mMinimumFlingVelocity) {
            return  true;
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        if (null != mTargetChild) {
            //child handle event,
            mTargetChild.dispatchTouchEvent(transformMotionEvent(rv, mTargetChild, e));
        }
    }

    private MotionEvent transformMotionEvent(ViewGroup parent, View child, MotionEvent event) {
        MotionEvent e = MotionEvent.obtain(event);

        final float offsetX = parent.getScrollX() - child.getLeft();
        final float offsetY = parent.getScrollY() - child.getTop();
        e.offsetLocation(offsetX, offsetY);

        return e;
    }

    @Override

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        mDisallowIntercept = disallowIntercept;
    }


    private boolean checkClickable(RecyclerView recyclerView, int position) {

        if (recyclerView instanceof HeaderRecyclerView) {
            return ((HeaderRecyclerView) recyclerView).isClickable(position);
        }

        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        RecyclerView rv = mRecyclerView;
        View child = rv.findChildViewUnder(e.getX(), e.getY());

        int position = rv.getChildAdapterPosition(child);

        if (position >= 0 && checkClickable(rv, position)) {

            /**
             * In some cases such us slide to delete RecyclerView,
             * item's scrollX may not be 0 which means that item is opened,
             * so we do not consume event here
             */
            if (child.getScrollX() != 0)
                return false;

            if (null != mOnItemClickListener) {
                mOnItemClickListener.onItemClick(rv, child, position);
                return true;
            }
        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        mIsLongPress = true;
        RecyclerView rv = mRecyclerView;
        View child = rv.findChildViewUnder(e.getX(), e.getY());

        if (null != child) {
            int position = rv.getChildAdapterPosition(child);

            boolean handled = false;
            if (position >= 0 && checkClickable(rv, position)) {

                if (null != mOnItemLongClickListener) handled = mOnItemLongClickListener.onItemLongClick(mRecyclerView, child, position);

                mIsLongPressHandled = handled;
            }


        }
    }




}
