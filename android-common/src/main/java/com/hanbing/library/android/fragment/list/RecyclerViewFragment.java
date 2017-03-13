package com.hanbing.library.android.fragment.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanbing.library.android.tool.DataViewHelper;
import com.hanbing.library.android.tool.RecyclerViewHelper;
import com.hanbing.library.android.view.recycler.OnItemClickListener;
import com.hanbing.library.android.view.recycler.OnItemLongClickListener;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.recycler.HeaderRecyclerView;
import com.hanbing.library.android.view.recycler.animator.FadeInItemAnimator;
import com.hanbing.library.android.view.recycler.decoration.LineItemDecoration;

/**
 * Created by hanbing
 */
public class RecyclerViewFragment<Bean> extends DataViewFragment<RecyclerView, RecyclerView.Adapter, Bean> implements OnItemClickListener, OnItemLongClickListener {

    RecyclerView mRecyclerView;

    @Override
    protected DataViewHelper<RecyclerView, RecyclerView.Adapter> createDataViewHelper() {
        return new RecyclerViewHelper<RecyclerView, RecyclerView.Adapter>(getContext()) {
            @Override
            public void onLoadData(boolean isRefresh, int pageIndex, int pageSize) {
                RecyclerViewFragment.this.onLoadData(isRefresh, pageIndex, pageSize);
            }
        };
    }

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = new HeaderRecyclerView(getActivity());
        return mRecyclerView;
    }

    @Override
    public RecyclerView createDataView() {
        return createRecyclerView();
    }

    public RecyclerView createRecyclerView() {
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
        return (null == getDataAdapter()) ? 0 : getDataAdapter().getItemCount();
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
        addItemDecoration(view);
        bindOnItemClickListener(view);

        if (view instanceof HeaderRecyclerView) {
            initHeadersAndFooters((HeaderRecyclerView)view);
            addLoadMoreIfNeed();
        }

        initRecyclerView(view);
    }

    protected void initRecyclerView(RecyclerView recyclerView){
        recyclerView.setAdapter(getDataAdapter());
    }

    protected void addItemDecoration(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new LineItemDecoration.Builder(getContext()).setColor(Color.LTGRAY).setSize(1).create());
    }

    protected void bindOnItemClickListener(RecyclerView recyclerView) {
        ViewUtils.bindOnItemClickListener(recyclerView, this, this);
    }



    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
    }

    @Override
    public boolean onItemLongClick(RecyclerView recyclerView, View view, int position) {

        return false;
    }


    @Override
    public RecyclerView getDataView() {
        return super.getDataView();
    }

    @Override
    public RecyclerView.Adapter getDataAdapter() {
        return super.getDataAdapter();
    }
}
