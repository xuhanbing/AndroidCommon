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

/**
 * @author hanbing
 */
public abstract class BaseFragment extends Fragment {

	View mCacheView;

	boolean mIsViewCreated = false;
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
			return mCacheView;


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
		return view;
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mIsViewCreated = true;
		onViewVisible(true);
	}

	protected  abstract View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	/**
	 * @param view
	 */
	protected void initViews(View view) {
		// TODO Auto-generated method stub

	}

	/**
	 * view is created
	 * or fragment real visiable to user and
	 * @param isCreated first created or visable
	 */
	protected abstract void onViewVisible(boolean isCreated);


	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		if (mIsViewCreated
				&& isVisibleToUser)
		{
			onViewVisible(false);
		}
	}
}
