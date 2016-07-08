package com.hanbing.library.android.bind;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by hanbing on 2016/7/7
 */
public class ObjectFinder {

    Activity mActivity;
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

        if (null != mFragmentActivity) {
            return mFragmentActivity.getSupportFragmentManager().findFragmentById(id);
        }

        return null;
    }

    public Fragment findFragmentByTag(String tag) {
        if (null != mFragmentActivity) {
            return mFragmentActivity.getSupportFragmentManager().findFragmentByTag(tag);
        }
        return null;
    }

    public Fragment findFragment(int id, String tag) {
        Fragment f = findFragmentById(id);

        if (null == f) {
            f = findFragmentByTag(tag);
        }

        return f;
    }
}
