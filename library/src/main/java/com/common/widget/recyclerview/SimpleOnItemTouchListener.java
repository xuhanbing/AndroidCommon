package com.common.widget.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.common.listener.OnItemClickListener;
import com.common.listener.OnItemLongClickListener;
import com.common.util.LogUtils;

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

        View child = rv.findChildViewUnder(e.getX(), e.getY());

        if (null != child) {

            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mIsMove = mIsLongPress = false;
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
            if (null != mOnItemClickListener) mOnItemClickListener.onItemClick(rv, child, position);
        }

        return true;
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
