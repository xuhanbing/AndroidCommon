package com.hanbing.library.android.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hanbing on 2016/6/7.
 */
public class SimpleOnItemTouchListener extends GestureDetector.SimpleOnGestureListener implements RecyclerView.OnItemTouchListener {

    GestureDetector mGestureDetector;
    OnItemClickListener mOnItemClickListener;
    OnItemLongClickListener mOnItemLongClickListener;
    RecyclerView mRecyclerView;

    boolean mIsMove = false;
    boolean mIsLongPress = false;
    boolean mIsLongPressHandled = true;


    public SimpleOnItemTouchListener(RecyclerView recyclerView, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        this.mRecyclerView = recyclerView;
        this.mOnItemClickListener = onItemClickListener;
        this.mOnItemLongClickListener = onItemLongClickListener;
        this.mGestureDetector = new GestureDetector(recyclerView.getContext(), this);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        int action = e.getAction();

        View child = rv.findChildViewUnder(e.getX(), e.getY());

        if (null != child) {

            //子控件消耗了事件
            if (child.dispatchTouchEvent(e))
            {
                //取消手势
                if (MotionEvent.ACTION_DOWN != action) {
                    MotionEvent cancel = MotionEvent.obtain(e);
                    cancel.setAction(MotionEvent.ACTION_CANCEL);
                    mGestureDetector.onTouchEvent(cancel);
                }
                return false;
            }

            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mIsMove = mIsLongPress = false;
                    mIsLongPressHandled = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    mIsMove = true;
                    break;
                case MotionEvent.ACTION_UP:
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

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {


    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

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
