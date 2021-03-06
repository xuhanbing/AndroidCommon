/**
 *
 */
package com.hanbing.library.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author hanbing
 */
public abstract class BaseFragment extends Fragment {

    View mCacheView;

    /**
     * 是否使用旧的view
     */
    boolean mUserCacheView = true;

    /**
     * view创建
     */
    boolean mViewCreated = false;
    /**
     * view第一次创建，即第一次调用onCreateView
     */
    boolean mViewFirstCreated = true;

    /**
     * 是否对用户可见
     */
    boolean mVisibleToUser = false;

    /**
     * 是否第一次对用户可见
     */
    boolean mFirstVisibleToUser = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (isUserCacheView() && null != mCacheView) {
            mViewFirstCreated = false;
            return mCacheView;
        }

        View view = onCreateViewImpl(inflater, container, savedInstanceState);

        initData(savedInstanceState);
        bindViews(view, savedInstanceState);
        initViews(view, savedInstanceState);

        mCacheView = view;
        mViewFirstCreated = true;

        return view;
    }

    final static int MSG_CHECK_VIEW_PREPARED = 1;

    Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case MSG_CHECK_VIEW_PREPARED:

                    View view = (View) msg.obj;
                    if (view.getMeasuredWidth() > 0 && view.getMeasuredHeight() > 0) {
                        onVisibleToUser();
                    } else {
                        //不可见，延迟再次检查
                        checkViewPrepared(view, 20);
                    }
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewCreated = true;

        //对用户可见，检查view大小
        if (getUserVisibleHint()) {
            if (shouldCheckViewPrepared())
                checkViewPrepared(view, 0);
            else
                onVisibleToUser();
        }
    }

    protected boolean shouldCheckViewPrepared() {
        return false;
    }

    private void checkViewPrepared(View view, long delay) {
        Message message = mHandler.obtainMessage(MSG_CHECK_VIEW_PREPARED, view);
        if (delay <= 0)
            message.sendToTarget();
        else {
            mHandler.sendMessageDelayed(message, delay);
        }
    }


    /**
     * 继承类可以通过注入或者传统方式生成view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected void initData(Bundle savedInstanceState) {

    }

    /**
     * 绑定view
     *
     * @param view
     * @param savedInstanceState
     */
    protected void bindViews(View view, Bundle savedInstanceState) {

    }

    /**
     * 初始化views
     *
     * Use initViews(View, Bundle) instead.
     * @param view
     */
    @Deprecated
    protected void initViews(View view) {
        // TODO Auto-generated method stub
    }

    /**
     *
     * @param view
     * @param savedInstanceState
     */
    protected void initViews(View view, Bundle savedInstanceState) {
        initViews(view);
    }


    /**
     * fragment is visible
     *
     * @param isFirstVisibleToUser isFirstVisibleToUser
     */
    protected void onVisible(boolean isFirstVisibleToUser) {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mVisibleToUser = isVisibleToUser;

        //只有view已经创建，才是真正可见
        if (mViewCreated && isVisibleToUser) {
            onVisibleToUser();
        }

    }

    void onVisibleToUser() {

        onVisible(mFirstVisibleToUser);

        if (mFirstVisibleToUser) {
            mFirstVisibleToUser = false;
        }
    }

    protected boolean isUserCacheView() {
        return mUserCacheView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //遍历所有子fragment
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (null != fragments && !fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                if (null != fragment)
                    fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (null == getActivity())
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");

        Fragment root = getRootParentFragment();

        if (null == root)
            root = this;

        getActivity().startActivityFromFragment(root, intent, requestCode);
    }

    /**
     * 获取最上层的fragment，即直接在activity中的fragment
     * 这样在activity调用onActivityResult时能够找到正确的fragment
     */
    private Fragment getRootParentFragment() {

        Fragment parent = getParentFragment();

        while (null != parent) {
            Fragment fragment = parent.getParentFragment();
            if (null == fragment) {
                //上层已经没有fragment，表明parent是最底层的fragment
                break;
            }

            parent = fragment;
        }
        return parent;
    }

    /**
     * 如果消耗了返回事件，返回true
     *
     * @return
     */
    public boolean consumeOnBackPressed() {
        //不可见，不处理
        if (!isVisible())
            return false;

        List<Fragment> fragments = getChildFragmentManager().getFragments();
        //遍历子fragment，检查是否消耗onBack
        if (null != fragments && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                if (null == fragment)
                    continue;
                if (fragment instanceof BaseFragment) {
                    if (((BaseFragment) fragment).consumeOnBackPressed())
                        return true;
                } else {
                    boolean ret = false;

                    try {
                        //直接pop
                        ret = fragment.getChildFragmentManager().popBackStackImmediate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (ret)
                        return true;
                }
            }
        }

        return getChildFragmentManager().popBackStackImmediate();
    }
}
