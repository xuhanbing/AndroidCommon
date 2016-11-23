package com.hanbing.library.android.tool;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;


/**
 * Created by hanbing on 2016/11/14
 */

public class AbsListViewHelper<DataView extends AbsListView> extends DataViewHelper<DataView, BaseAdapter> implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,  AbsListView.OnScrollListener{

    public AbsListViewHelper(Context context) {
        super(context);
    }



    /**
     * AbsListView
     */
    DataView mListView;

    /**
     * 手动滚动
     */
    boolean mIsManScroll = false;

    @Override
    public DataView createDataView() {
        return null;
    }

    @Override
    public BaseAdapter createAdapter() {
        return null;
    }

    /**
     * 返回当前数据个数
     * @return
     */
    public int getItemCount()
    {
        android.widget.BaseAdapter mListAdapter = getDataAdapter();
        return null != mListAdapter ? mListAdapter.getCount() : 0;
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
    public void initDataView(DataView view) {
        view.setOnScrollListener(this);
        view.setOnItemClickListener(this);
        view.setOnItemLongClickListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (SCROLL_STATE_TOUCH_SCROLL == scrollState || SCROLL_STATE_FLING == scrollState) {
            mIsManScroll = true;
        } else {
            mIsManScroll = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (mIsManScroll
                && lastVisibleItem == totalItemCount) {

            onLastItemVisible();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
