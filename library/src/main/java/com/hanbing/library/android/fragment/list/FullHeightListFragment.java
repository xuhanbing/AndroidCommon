package com.hanbing.library.android.fragment.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.hanbing.library.android.tool.PagingManager;
import com.hanbing.library.android.view.list.FullHeightListView;

/**
 * Created by hanbing on 2016/7/29
 */
public class FullHeightListFragment extends ListFragment {

    FullHeightListView mFullHeightListView;

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mFullHeightListView = new FullHeightListView(getContext());
        mFullHeightListView.setFullHeight(isFullHeight());
        return mFullHeightListView;
    }

    @Override
    public ListView createDataView() {
        return mFullHeightListView;
    }

    public boolean shouldLoadMore(ScrollView scrollView) {

        PagingManager pagingManager = getPagingManager();

        if (pagingManager.isBusy() || pagingManager.isLastPage())
            return false;

        if (null == getDataView())
            return false;

        int[] loc = new int[2];

        scrollView.getLocationOnScreen(loc);

        int bottom = loc[1] + scrollView.getMeasuredHeight();

        View child = getDataView().getChildAt(getItemCount() - 1);

        if (null != child) {
            child.getLocationOnScreen(loc);

            if (loc[1] < bottom) {
                onLoadMore();
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onLastItemVisible() {
        //全高度listview时，最后一个item会一直展示，因此该判断不准确
        if (isFullHeight())
            return;
        super.onLastItemVisible();
    }

    protected boolean isFullHeight() {
        return true;
    }
}
