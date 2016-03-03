/**
 * 
 */
package com.common.base;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * @author hanbing
 */
public class BaseFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

	List<Fragment> mFragments;
	String[] mTitles;

	/**
	 * @param fm
	 */
	public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
		super(fm);
		// TODO Auto-generated constructor stub

		if (null != fragments && null != titles && fragments.size() != titles.length) {
			throw new IllegalArgumentException("fragmens.size() must equal titles.length");
		}

		this.mFragments = fragments;
		this.mTitles = titles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return null == mFragments ? null : mFragments.get(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == mFragments ? 0 : mFragments.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return null == mTitles ? null : mTitles[position];
	}

}
