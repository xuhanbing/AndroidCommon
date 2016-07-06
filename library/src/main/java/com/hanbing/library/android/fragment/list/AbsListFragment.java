package com.hanbing.library.android.fragment.list;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

/**
 * Created by hanbing on 2016/3/21.
 */
public abstract class AbsListFragment<DataView extends AbsListView> extends DataViewFragment<DataView, BaseAdapter> implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,  AbsListView.OnScrollListener {

    /**
     * AbsListView
     */
    DataView mListView;


    /**
     * 手动滚动
     */
    boolean mIsManScroll = false;

    /**
     * 返回当前数据个数
     * @return
     */
    public int getItemCount()
    {
        BaseAdapter mListAdapter = getDataAdapter();
        return null != mListAdapter ? mListAdapter.getCount() : 0;
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

            if (isLoadMoreEnabled() && isScrollLoadMoreEnabled())
            onLoadMore();
        }
    }
}
