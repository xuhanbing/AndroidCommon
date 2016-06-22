package com.common.base.listfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.common.base.AbsListFragment;

/**
 * Created by hanbing
 */
public class GridFragment extends AbsListFragment<GridView> {

    GridView mGridView;
    @Override
    public BaseAdapter createAdapter() {
        return null;
    }

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mGridView = new GridView(getContext());

        mGridView.setNumColumns(2);
        mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        return mGridView;
    }

    @Override
    public GridView createDataView() {
        return mGridView;
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
    public void initDataView(GridView view) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
