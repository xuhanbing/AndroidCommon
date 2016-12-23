package com.hanbing.library.android.fragment.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by hanbing on 2016/3/29.
 */
public class ListFragment<Bean> extends AbsListFragment<ListView, Bean> {

    ListView mListView;
    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mListView = new ListView(getContext());
    }


    @Override
    public BaseAdapter createAdapter() {
        return null;
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
    public final void initDataView(ListView listView) {

        if (null != listView)
        {
            initHeadersAndFooters(listView);
            addLoadMoreIfNeed();
            setEmptyViewIfNeed();

            listView.setOnScrollListener(this);
            listView.setOnItemClickListener(this);
            listView.setOnItemLongClickListener(this);
            initListView(listView);
        }
    }

    @Override
    public ListView createDataView() {
        return createListView();
    }

    public ListView createListView() {
        return mListView;
    }

    public  void initHeadersAndFooters(ListView dataView) {

    }

    public  void initListView(ListView listView) {
        listView.setAdapter(getDataAdapter());
    }


    @Override
    public void onLoadData(final boolean isRefresh, int pageIndex, final int pageSize) {


    }


}
