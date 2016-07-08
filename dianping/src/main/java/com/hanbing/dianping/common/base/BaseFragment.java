package com.hanbing.dianping.common.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends com.hanbing.library.android.fragment.BaseFragment {


    public BaseFragment() {
        // Required empty public constructor
    }
    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    protected void onVisible(boolean isFirstVisibleToUser) {

    }

}
