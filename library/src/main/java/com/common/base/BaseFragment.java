/**
 * 
 */
package com.common.base;

import org.xutils.x;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.util.LogUtils;

/**
 * @author hanbing
 */
public abstract class BaseFragment extends Fragment {

	View mCacheView;


	/**
	 * view创建
	 */
	boolean mViewCreated = true;
	/**
	 * view第一次创建，即第一次调用onCreateView
	 */
	boolean mViewFirstCreated = true;
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


		View view = null;

		//使用注解方式生成view
		view = x.view().inject(this, inflater, container);

		//如果没有使用注解方式，使用默认的方式生成view
		if (null == view)
		{
			view = onCreateViewImpl(inflater, container, savedInstanceState);
		}

		initViews(view);

		mCacheView = view;
		mViewFirstCreated = true;

		return view;
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mViewCreated = true;
		onViewVisible(true);

	}

	protected  abstract View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	/**
	 * @param view
	 */
	protected void initViews(View view) {
		// TODO Auto-generated method stub

	}

	protected void onViewVisible(boolean isCreated) {
		onViewVisible(isCreated, mViewFirstCreated);
	}
	/**
	 * view is created
	 * or fragment real visiable to user and
	 * @param isCreated created or visable
	 * @param isFirstCreated first created
	 */
	protected abstract void onViewVisible(boolean isCreated, boolean isFirstCreated);


	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		if (mViewCreated
				&& isVisibleToUser)
		{
			onViewVisible(false);
		}
	}
}
