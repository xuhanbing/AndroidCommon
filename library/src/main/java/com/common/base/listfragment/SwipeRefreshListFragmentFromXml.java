package com.common.base.listfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidcommon.R;
import com.common.util.ViewUtils;

/**
 * Created by hanbing on 2016/3/29.
 */
public class SwipeRefreshListFragmentFromXml extends SwipeRefreshListFragment {

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = ViewUtils.inflate(getActivity(), R.layout.layout_swiperefresh_list);

        mSwipeRefreshLayout = ViewUtils.findViewById(view, R.id.swipeRefreshLayout);
        mListView = ViewUtils.findViewById(view, R.id.listView);

        configSwipeRefreshLayout(mSwipeRefreshLayout);


        return view;
    }

}
