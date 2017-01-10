package com.hanbing.library.android.fragment.list;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.hanbing.library.android.tool.AbsListViewHelper;
import com.hanbing.library.android.tool.DataViewHelper;

/**
 * Created by hanbing on 2016/3/21.
 */
public abstract class AbsListFragment<DataView extends AbsListView, Bean> extends DataViewFragment<DataView, BaseAdapter, Bean> implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,  AbsListView.OnScrollListener {


    @Override
    public int getItemCount() {
        if (null != getDataAdapter())
            return getDataAdapter().getCount();
        return 0;
    }

    @Override
    protected DataViewHelper<DataView, BaseAdapter> createDataViewHelper() {
        return new AbsListViewHelper<DataView, BaseAdapter>(getContext()) {

            @Override
            public void onLoadData(boolean isRefresh, int pageIndex, int pageSize) {
                AbsListFragment.this.onLoadData(isRefresh, pageIndex, pageSize);
            }
        };
    }

    @Override
    public DataView getDataView() {
        return super.getDataView();
    }

    @Override
    public BaseAdapter getDataAdapter() {
        return super.getDataAdapter();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        ((AbsListViewHelper)mDataViewHelper).onScrollStateChanged(view, scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        ((AbsListViewHelper)mDataViewHelper).onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }
}
