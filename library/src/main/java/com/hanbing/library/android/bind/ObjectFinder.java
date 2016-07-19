package com.hanbing.library.android.bind;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.hanbing.library.android.R;

/**
 * Created by hanbing on 2016/7/7
 */
public class ObjectFinder {

    Activity mActivity;
    Fragment mFragment;
    View mRootView;
    FragmentActivity mFragmentActivity;

    public ObjectFinder(Activity activity) {
        this.mActivity = activity;

        if (activity instanceof FragmentActivity) {
            mFragmentActivity = (FragmentActivity) activity;
        }
    }

    public ObjectFinder(View view) {
        this.mRootView = view;
    }

    public ObjectFinder(Object object, View view) {
        if (object instanceof Fragment)
            mFragment = (Fragment) object;
        this.mRootView = view;
    }

    public View findViewById(int id) {
        View view = null;
        if (null != mActivity) {
            view = mActivity.findViewById(id);
        }

        if (null == view
                && null != mRootView) {
            view = mRootView.findViewById(id);
        }

        return view;
    }


    public View findViewWithTag(String tag) {
        View view = null;

        if (null != mRootView) {
            view = mRootView.findViewWithTag(tag);
        }

        return view;
    }

    public View findView(int id, String tag) {
        View v = findViewById(id);

        if (null == v)
            v = findViewWithTag(tag);

        return v;
    }

    public Fragment findFragmentById(int id) {
        Fragment fragment = null;
        if (null != mFragmentActivity) {
            fragment = mFragmentActivity.getSupportFragmentManager().findFragmentById(id);

        }
        if (null == fragment && null != mFragment) {
            fragment = mFragment.getChildFragmentManager().findFragmentById(id);
        }

        return fragment;
    }

    public Fragment findFragmentByTag(String tag) {
        Fragment fragment = null;
        if (null != mFragmentActivity) {
            fragment = mFragmentActivity.getSupportFragmentManager().findFragmentByTag(tag);

        }

        if (null == fragment && null != mFragment) {
            fragment = mFragment.getChildFragmentManager().findFragmentByTag(tag);
        }
        return fragment;
    }

    public Fragment findFragment(int id, String tag) {
        Fragment f = findFragmentById(id);

        if (null == f) {
            f = findFragmentByTag(tag);
        }

        return f;
    }
}
