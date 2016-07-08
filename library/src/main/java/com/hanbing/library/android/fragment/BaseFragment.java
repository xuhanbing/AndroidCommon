/**
 * 
 */
package com.hanbing.library.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author hanbing
 */
public abstract class BaseFragment extends Fragment {

	View mCacheView;


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
	boolean mVisibleToUser = true;

	/**
	 * 是否第一次对用户可见
	 */
	boolean mFirstVisibleToUser = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if (null != mCacheView)
		{
			mViewFirstCreated = false;
			return mCacheView;
		}

		View view = onCreateViewImpl(inflater, container, savedInstanceState);

		bindViews(view, savedInstanceState);
		initViews(view);

		mCacheView = view;
		mViewFirstCreated = true;

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mViewCreated = true;
		/**
		 * 判断是否对用户可见
		 */
		if (mVisibleToUser)
		{
			onVisibleToUser();
		}

	}

	/**
	 * 继承类可以通过注入或者传统方式生成view
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	protected  abstract View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


	/**
	 *
	 * @param view
	 * @param savedInstanceState
	 */
	protected void bindViews(View view, Bundle savedInstanceState) {

	}

	/**
	 * @param view
	 */
	protected void initViews(View view) {
		// TODO Auto-generated method stub

	}


	/**
	 * fragment is visiable
	 * @param isFirstVisibleToUser isFirstVisibleToUser
	 *
	 */
	protected  void onVisible(boolean isFirstVisibleToUser) {

	}


	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		mVisibleToUser = isVisibleToUser;

		//只有view已经创建，才是正在可见
		if (mViewCreated && isVisibleToUser) {
			onVisibleToUser();
		}

	}

	void onVisibleToUser() {
		onVisible(mFirstVisibleToUser);

		if (mFirstVisibleToUser) {
			mFirstVisibleToUser  = false;
		}
	}

}