package com.common.base.listfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.util.ViewUtils;

/**
 * Created by hanbing on 2016/3/29.
 */
public class SwipeRefreshListFragment extends SimpleListFragment {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSwipeRefreshLayout = new SwipeRefreshLayout(getActivity());

        configSwipeRefreshLayout(mSwipeRefreshLayout);

        View child = super.onCreateViewImpl(inflater, container, savedInstanceState);

        ViewUtils.removeFromParent(mLoadingView);

        mSwipeRefreshLayout.addView(child);

        return mSwipeRefreshLayout;
    }

    @Override
    protected void onViewCreatedOrVisible() {
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
}
