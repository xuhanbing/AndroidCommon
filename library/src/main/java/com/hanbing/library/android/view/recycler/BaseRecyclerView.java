package com.hanbing.library.android.view.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hanbing on 2016/5/3.
 */
public class BaseRecyclerView extends RecyclerView {


    View mEmptyView;

    Adapter mAdapter;
    AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            updateEmptyStatus();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            updateEmptyStatus();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            updateEmptyStatus();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            updateEmptyStatus();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            updateEmptyStatus();
        }
    };

    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean drawItemDecoration(int position)
    {
        return true;
    }


    @Override
    public void setAdapter(Adapter adapter) {

        //旧的adapter
        Adapter oldAdapter = mAdapter;

        if (adapter != oldAdapter) {
            //移除原来的监听
            if (null != oldAdapter) {
                oldAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
            }

            if (null != adapter) {
                adapter.registerAdapterDataObserver(mAdapterDataObserver);
            }
        }

        mAdapter = adapter;
        super.setAdapter(adapter);

        updateEmptyStatus();
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;

        updateEmptyStatus();
    }

    public View getEmptyView() {
        return mEmptyView;
    }


    protected boolean isEmpty() {
        final Adapter adapter = getAdapter();
        return ((adapter == null) || adapter.getItemCount() <= 0);
    }

    private void updateEmptyStatus() {
        updateEmptyStatus(isEmpty());
    }

    private void updateEmptyStatus(boolean empty) {
        if (empty) {
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.VISIBLE);
                setVisibility(View.GONE);
            } else {
                // If the caller just removed our empty view, make sure the list view is visible
                setVisibility(View.VISIBLE);
            }

            // We are now GONE, so pending layouts will not be dispatched.
            // Force one here to make sure that the state of the list matches
            // the state of the adapter.
//            if (mDataChanged) {
//                this.onLayout(false, mLeft, mTop, mRight, mBottom);
//            }
        } else {
            if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
            setVisibility(View.VISIBLE);
        }
    }


}
