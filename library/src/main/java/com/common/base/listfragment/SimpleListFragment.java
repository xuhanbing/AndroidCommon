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
import android.widget.ListAdapter;
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

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public ListView createListView() {
        return null;
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
        return null;
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
