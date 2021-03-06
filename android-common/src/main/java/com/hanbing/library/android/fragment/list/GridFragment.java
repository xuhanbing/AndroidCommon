package com.hanbing.library.android.fragment.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

/**
 * Created by hanbing
 */
public class GridFragment<Bean> extends AbsListFragment<GridView, Bean> {

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
        return createGridView();
    }

    public GridView createGridView() {
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


}
