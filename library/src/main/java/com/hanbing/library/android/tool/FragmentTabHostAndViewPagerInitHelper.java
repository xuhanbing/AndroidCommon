/**
 * 
 */
package com.hanbing.library.android.tool;

import java.util.Arrays;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

/**
 * @author hanbing
 */
public class FragmentTabHostAndViewPagerInitHelper implements ViewPager.OnPageChangeListener, OnTabChangeListener {

	public static interface ViewFactory {

		public View create(int position, String tag);
	}

	Context mContext;

	FragmentManager mFragmentManager;

	/**
	 * 标签
	 */
	String[] mTags;

	/**
	* 
	*/
	FragmentTabHost mFragmentTabHost;

	/**
	* 
	*/
	ViewPager mViewPager;

	/**
	 * 用于创建indicator
	 */
	ViewFactory mViewFactory;

	/**
	 * 自定义回调
	 */
	OnPageChangeListener mOnPageChangeListener;

	/**
	 * 自定义的回调
	 */
	OnTabChangeListener mOnTabChangeListener;

	/**
	 * @param context
	 * @param viewPager
	 * @param fragmentManager
	 * @param tags;
	 */
	public FragmentTabHostAndViewPagerInitHelper(Context context, ViewPager viewPager, FragmentTabHost fragmentTabHost,
			FragmentManager fragmentManager, String[] tags) {
		this(context, viewPager, fragmentTabHost, fragmentManager, tags, null);
	}

	/**
	 * @param context
	 * @param viewPager
	 * @param fragmentTabHost
	 * @param fragmentManager
	 * @param tags
	 * @param viewFactory
	 */
	public FragmentTabHostAndViewPagerInitHelper(Context context, ViewPager viewPager, FragmentTabHost fragmentTabHost,
			FragmentManager fragmentManager, String[] tags, ViewFactory viewFactory) {
		this(context, viewPager, fragmentTabHost, fragmentManager, viewFactory, tags, null, null);
	}

	/**
	 * @param context
	 * @param viewPager
	 * @param fragmentTabHost
	 * @param fragmentManager
	 * @param viewFactory
	 * @param tags
	 * @param onPageChangeListener
	 * @param onTabChangeListener
	 */
	public FragmentTabHostAndViewPagerInitHelper(Context context, ViewPager viewPager, FragmentTabHost fragmentTabHost,
			FragmentManager fragmentManager, ViewFactory viewFactory, String[] tags,
			OnPageChangeListener onPageChangeListener, OnTabChangeListener onTabChangeListener) {
		super();
		this.mContext = context;
		this.mTags = tags;
		this.mFragmentTabHost = fragmentTabHost;
		this.mViewPager = viewPager;
		this.mFragmentManager = fragmentManager;
		this.mViewFactory = viewFactory;
		this.mOnPageChangeListener = onPageChangeListener;
		this.mOnTabChangeListener = onTabChangeListener;


	}

	private ViewFactory generateDefaultViewFactory() {
		return new ViewFactory() {

			@Override
			public View create(int position, String title) {
				// TODO Auto-generated method stub
				TextView text = new TextView(mContext);
				text.setText(title);
				text.setTextSize(15);
				text.setGravity(Gravity.CENTER);

				return text;
			}
		};

	}

	public void init() {

		if (null == mContext || null == mTags || null == mFragmentTabHost || null == mViewPager)
			return;

		if (null == mViewFactory) {
			mViewFactory = generateDefaultViewFactory();
		}

		int containerId = mViewPager.getId();

		mFragmentTabHost.setup(mContext, mFragmentManager, containerId);

		for (int i = 0; i < mTags.length; i++) {

			final String tag = mTags[i];

			View view = mViewFactory.create(i, tag);

			mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec(tag).setIndicator(view), Fragment.class, null);
		}
		mFragmentTabHost.getTabWidget().setDividerDrawable(null);
		mFragmentTabHost.setOnTabChangedListener(this);
		mViewPager.setOnPageChangeListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#
	 * onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
		if (null != mOnPageChangeListener)
			mOnPageChangeListener.onPageScrollStateChanged(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
	 * (int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		if (null != mOnPageChangeListener)
			mOnPageChangeListener.onPageScrolled(arg0, arg1, arg2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
	 * (int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		if (null != mFragmentTabHost) {
			TabWidget tabWidget = mFragmentTabHost.getTabWidget();
			int descendantFocusability = tabWidget.getDescendantFocusability();
			tabWidget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mFragmentTabHost.setCurrentTab(arg0);
			tabWidget.setDescendantFocusability(descendantFocusability);
		}

		
		if (null != mOnPageChangeListener)
			mOnPageChangeListener.onPageSelected(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.
	 * String)
	 */
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		if (null != mViewPager)
			mViewPager.setCurrentItem(Arrays.asList(mTags).indexOf(tabId));

		if (null != mOnTabChangeListener)
			mOnTabChangeListener.onTabChanged(tabId);
	}
}