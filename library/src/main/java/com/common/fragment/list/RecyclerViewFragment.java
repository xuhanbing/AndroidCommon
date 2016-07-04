package com.common.fragment.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.listener.OnItemClickListener;
import com.common.listener.OnItemLongClickListener;
import com.common.util.ViewUtils;
import com.common.widget.recyclerview.HeaderRecyclerView;
import com.common.widget.recyclerview.animator.FadeInItemAnimator;
import com.common.widget.recyclerview.decoration.LineItemDecoration;

/**
 * Created by hanbing
 */
public class RecyclerViewFragment extends DataViewFragment<RecyclerView, RecyclerView.Adapter> implements OnItemClickListener, OnItemLongClickListener{

    HeaderRecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    /**
     * 手动滚动
     */
    boolean mIsManScroll = false;

    class OnScrollListener extends RecyclerView.OnScrollListener{

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

            int lastVisibleItemPosition = 0;
            int itemCount = 0;
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;

                lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                itemCount = linearLayoutManager.getItemCount();

            } else if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;

                 lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                 itemCount = gridLayoutManager.getItemCount();
            }

            if (mIsManScroll
                && lastVisibleItemPosition == itemCount - 1) {
                if (isLoadMoreEnabled() && isScrollLoadMoreEnabled() ) {
                    onLoadMore();
                }
            }


        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            if (RecyclerView.SCROLL_STATE_DRAGGING == newState
                    || RecyclerView.SCROLL_STATE_SETTLING == newState )
                mIsManScroll = true;
            else
                mIsManScroll = false;

        }
    }


    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = new HeaderRecyclerView(getActivity());
        return mRecyclerView;
    }

    @Override
    public RecyclerView createDataView() {
        return mRecyclerView;
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return null;
    }

    /**
     * 添加header，footer
     * @param recyclerView
     */
    public  void initHeadersAndFooters(HeaderRecyclerView recyclerView) {

    }


    @Override
    public int getItemCount() {
        mAdapter = getDataAdapter();

        if (null == mAdapter) return 0;

        return mAdapter.getItemCount();
    }

    @Override
    public View createEmptyView() {
        return null;
    }

    @Override
    public View createLoadingView() {
        return null;
    }

    @Override
    public View createLoadMoreView() {
        return null;
    }


    @Override
    public void initDataView(RecyclerView view) {

        view.setLayoutManager(new LinearLayoutManager(getContext()));
        view.setItemAnimator(new FadeInItemAnimator());
        view.addItemDecoration(new LineItemDecoration.Builder(getContext()).setColor(Color.LTGRAY).setSize(1).create());
        view.addOnScrollListener(new OnScrollListener());
        ViewUtils.bindOnItemClickListener(view, this, this);

        if (view instanceof HeaderRecyclerView) {
            initHeadersAndFooters((HeaderRecyclerView)view);
            addLoadMoreIfNeed();

        }
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
    }

    @Override
    public boolean onItemLongClick(RecyclerView recyclerView, View view, int position) {

        return false;
    }



}