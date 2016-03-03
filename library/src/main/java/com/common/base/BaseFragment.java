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
public class BaseFragment extends Fragment {

	View mCacheView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if (null != mCacheView)
			return mCacheView;

		View view = x.view().inject(this, inflater, container);

		initViews(view);

		mCacheView = view;
		return view;
	}

	/**
	 * @param view
	 */
	protected void initViews(View view) {
		// TODO Auto-generated method stub

	}

}
