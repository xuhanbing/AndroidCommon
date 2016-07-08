package com.hanbing.dianping.common.base;

import android.view.View;

import com.hanbing.library.android.fragment.list.SrainPtrListFragment;
import com.hanbing.dianping.view.LoadMoreView;

/**
 * Created by hanbing on 2016/6/13.
 */
public abstract class BaseListFragment extends SrainPtrListFragment {
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
        return new LoadMoreView(getContext());
    }


}
