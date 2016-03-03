/**
 * 
 */
package com.common.view;

import com.common.view.TabWidget.OnTabClickListener;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * a tool control viewpager and tabwidget
 * 
 * @author hanbing
 * @date 2015-9-9
 */
public class TabViewPager extends LinearLayout implements ViewPager.OnPageChangeListener {

	/**
	 * tabs
	 */
	TabWidget mTabWidget;

	/**
	 * viewpager
	 */
	ViewPager mViewPager;

	/**
	 * page change listener
	 */
	ViewPager.OnPageChangeListener mOnPageChangeListener;
	
	

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public TabViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public TabViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public TabViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setViewPager(TabWidget tabWidget, ViewPager viewPager) {
		setViewPager(tabWidget, viewPager, null);
	}
	
	public void setViewPager(TabWidget tabWidget, ViewPager viewPager, OnPageChangeListener lsner) {
		this.mTabWidget = tabWidget;
		this.mViewPager = viewPager;
		this.mOnPageChangeListener = lsner;
		
		mTabWidget.setOnTabClickListener(new OnTabClickListener() {

			@Override
			public void onClick(int position) {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem(position, true);
			}
		});
		
		mViewPager.setOnPageChangeListener(this);
	}

	/**
	 * get tabs
	 * 
	 * @return
	 */
	public TabWidget getTabWidget() {
		return mTabWidget;
	}

	/**
	 * get viewpager
	 * 
	 * @return
	 */
	public ViewPager getViewPager() {
		return mViewPager;
	}

	/**
	 * set custom {@link OnPageChangeListener}, must set use this function
	 * 
	 * @param onPageChangeListener
	 */
	public void setOnPageChangeListener(
			OnPageChangeListener onPageChangeListener) {
		this.mOnPageChangeListener = onPageChangeListener;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		if (null != mOnPageChangeListener) {
			mOnPageChangeListener.onPageScrollStateChanged(arg0);
		}

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		if (null != mOnPageChangeListener) {
			mOnPageChangeListener.onPageScrolled(arg0, arg1, arg2);
		}

		mTabWidget.scrollStrip(arg0, arg1, arg2);
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		if (null != mOnPageChangeListener) {
			mOnPageChangeListener.onPageSelected(arg0);
		}

		mTabWidget.setSelectedItem(arg0);
	}

	/**
	 * set current item
	 * 
	 * @param position
	 */
	public void setCurrentItem(int position) {
		setCurrentItem(position, true);
	}

	/**
	 * set current item with tab tag
	 * 
	 * @param tag
	 */
	public void setCurrentItem(String tag) {
		setCurrentItem(tag, true);
	}

	/**
	 * set current item
	 * 
	 * @param position
	 * @param smoothScroll
	 */
	public void setCurrentItem(int position, boolean smoothScroll) {
		mViewPager.setCurrentItem(position, smoothScroll);

	}

	/**
	 * set current item with tab tag
	 * 
	 * @param tag
	 * @param smoothScroll
	 */
	public void setCurrentItem(String tag, boolean smoothScroll) {
		setCurrentItem(mTabWidget.indexOfTab(tag), smoothScroll);
	}

}
