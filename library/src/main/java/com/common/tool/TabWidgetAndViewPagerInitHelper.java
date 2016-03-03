/**
 * 
 */
package com.common.tool;

import com.common.util.ViewUtils;
import com.common.view.TabWidget;
import com.common.view.TabWidget.TabSpec;
import com.common.view.TabWidget.TabViewFactory;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * @author hanbing
 * 
 */
public class TabWidgetAndViewPagerInitHelper {

	ViewPager mViewPager;
	TabWidget mTabWidget;
	TabViewFactory mTabViewFactory;
	ViewPager.OnPageChangeListener mOnPageChangeListener;
	
	public TabWidgetAndViewPagerInitHelper(ViewPager viewPager, TabWidget tabWidget, TabWidget.TabViewFactory tabViewFactory
			, OnPageChangeListener onPageChangeListener)
	{
		this.mViewPager = viewPager;
		this.mTabWidget = tabWidget;
		this.mTabViewFactory = tabViewFactory;
		this.mOnPageChangeListener = onPageChangeListener;
	}
	
	public void init() {
		
		if (null == mViewPager || null == mTabWidget)
			return;
		
		mTabWidget.setTabViewFactory(mTabViewFactory);
		mTabWidget.setViewPager(mViewPager);
		mTabWidget.setOnPageChangeListener(mOnPageChangeListener);
	}
}
