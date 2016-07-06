/**
 * 
 */
package com.common.view.pager;

import com.common.view.tab.TabWidget.OnTabClickListener;
import com.androidcommon.R;
import com.common.view.tab.UnderlineTabWidget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;

/**
 * underline strip
 * @author hanbing
 * @date 2015-9-9
 */
public class DefaultTabViewPager extends TabViewPager implements
		ViewPager.OnPageChangeListener {

	/**
	 * tab gravity top or bottom
	 */
	int mTabGravity = Gravity.TOP;

	/**
	 * @param context
	 */
	public DefaultTabViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public DefaultTabViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public DefaultTabViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * 
	 */
	private void init() {
		// TODO Auto-generated method stub

		setOrientation(VERTICAL);

		mTabWidget = new UnderlineTabWidget(getContext());
		mTabWidget.setOnTabClickListener(new OnTabClickListener() {

			@Override
			public void onClick(int position) {
				// TODO Auto-generated method stub
				Log.e("", "click " + position);
				mViewPager.setCurrentItem(position, true);
			}
		});

		mViewPager = new ViewPager(getContext());
		/**
		 * here you must set id otherwise it my cause notfoundexcation when u use fragment
		 */
		mViewPager.setId(R.id.viewpager);

		LayoutParams param1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		LayoutParams param2 = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);

		param1.gravity = Gravity.CENTER_HORIZONTAL;
		mTabWidget.setLayoutParams(param1);
		mViewPager.setLayoutParams(param2);
		mViewPager.setOnPageChangeListener(this);

		// set default
		mTabWidget.setSelectedItem(0);
		mViewPager.setCurrentItem(0);

		addView(mTabWidget);
		addView(mViewPager);
	}


	/**
	 * set tab gravity top or bottom default is top
	 * @param gravity
	 */
	public void setTabGravity(int gravity) {
		if (mTabGravity == gravity)
			return;
		
		mTabGravity = gravity;
		
		if (Gravity.BOTTOM == mTabGravity) {
			((UnderlineTabWidget)mTabWidget).setStripGravity(Gravity.TOP);
			removeView(mTabWidget);
			addView(mTabWidget);
		} else {
			((UnderlineTabWidget)mTabWidget).setStripGravity(Gravity.BOTTOM);
			removeView(mTabWidget);
			addView(mTabWidget, 0);
		}
	}
	

}
