package com.hanbing.library.android.tool;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;


/**
 * A AbsListView data helper that make u control request data simpler.
 * Created by hanbing on 2016/11/14
 */

public abstract class AbsListViewHelper<DataView extends AbsListView, DataAdapter extends BaseAdapter> extends DataViewHelper<DataView, DataAdapter> implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener {

    public AbsListViewHelper(Context context) {
        super(context);
    }

    /**
     * man scroll
     */
    boolean mIsManScroll = false;

    @Override
    public DataView createDataView() {
        return null;
    }

    @Override
    public DataAdapter createAdapter() {
        return null;
    }

    /**
     * Return current item count
     *
     * @return
     */
    public int getItemCount() {
        return null != getDataAdapter() ? getDataAdapter().getCount() : 0;
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
    public void addLoadMoreIfNeed() {
        if (null != mLoadMoreContainer) {
            if (mDataView instanceof ListView) {
                ((ListView) mDataView).addFooterView(mLoadMoreContainer);
            }
        }
    }

    @Override
    public void setEmptyView(View view) {
        if (null != mEmptyView)
            mDataView.setEmptyView(mEmptyView);
    }

    @Override
    public void initDataView(DataView view) {
        view.setOnScrollListener(this);
        view.setOnItemClickListener(this);
        view.setOnItemLongClickListener(this);
    }

    @Override
    public void notifyDataSetChanged() {
        if (null != getDataAdapter()) getDataAdapter().notifyDataSetChanged();
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
