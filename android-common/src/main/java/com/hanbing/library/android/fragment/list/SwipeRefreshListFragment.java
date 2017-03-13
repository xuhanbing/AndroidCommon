package com.hanbing.library.android.fragment.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

/**
 * Created by hanbing on 2016/3/29.
 */
public class SwipeRefreshListFragment extends ListFragment {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSwipeRefreshLayout = new SwipeRefreshLayout(getActivity());

        configSwipeRefreshLayout(mSwipeRefreshLayout);

        View child = super.onCreateViewImpl(inflater, container, savedInstanceState);

        mSwipeRefreshLayout.addView(child);

        return mSwipeRefreshLayout;
    }

    @Override
    protected void onVisible(boolean isFirstVisibleToUser) {
        if (isFirstVisibleToUser)
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    protected void configSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshListFragment.this.onRefresh();
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW, Color.BLACK);
    }

    @Override
    public void onLoadCompleted() {
        super.onLoadCompleted();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        View first = view.getChildAt(firstVisibleItem);

        //解决下拉冲突
        if (0 == firstVisibleItem && (null == first || 0 == first.getTop())) {
            mSwipeRefreshLayout.setEnabled(true);
        } else {
            mSwipeRefreshLayout.setEnabled(false);
        }

        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }
}
