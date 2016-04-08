package com.common.base.listfragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidcommon.R;
import com.common.base.BaseListFragment;
import com.common.listener.OnLoadListener;
import com.common.util.LogUtils;
import com.common.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing on 2016/3/29.
 */
public class SimpleListFragment extends BaseListFragment {

    ListView mListView;
    List<String> mList = new ArrayList<>();
    BaseAdapter adapter;
    Handler mHandler = new Handler();

    View mLoadingView = null;

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout layout = new RelativeLayout(getActivity());
        mListView = new ListView(getActivity());
        mLoadingView = ViewUtils.inflate(getActivity(), R.layout.layout_loading);

        layout.addView(mListView);
        layout.addView(mLoadingView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return layout;
    }

    @Override
    public ListView createListView() {
        return mListView;
    }

    @Override
    public BaseAdapter createListAdapter() {
        return null;
    }

    @Override
    public View createEmptyView() {
        return null;
    }

    @Override
    public View createLoadingView() {
        return mLoadingView;
    }

    @Override
    public View createLoadMoreView() {

      return null;
    }

    @Override
    public void initListView(ListView listView) {
    }

    @Override
    public void initHeadersAndFooters(ListView listView) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoadData(final boolean isRefresh, int pageIndex, final int pageSize) {


    }


}
