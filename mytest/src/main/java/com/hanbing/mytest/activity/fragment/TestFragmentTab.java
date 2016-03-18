/**
 * 
 */
package com.hanbing.mytest.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import com.hanbing.mytest.R;
import com.hanbing.mytest.fragment.NumFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author hanbing
 * @date 2015��6��15��
 */
public class TestFragmentTab extends FragmentActivity {


	List<Fragment> fragmentList = new ArrayList<Fragment>();
	ViewPager viewPager;
	LinearLayout tabLayout;
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		
		
		
		setContentView(R.layout.activity_fragmenttab2);
		
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		
		
		tabLayout = (LinearLayout) findViewById(R.id.tab);
		
		
		for (int i = 0; i < 4; i++)
		{
			Bundle bundle = new Bundle();
			bundle.putInt("num", i);
			
			
			Fragment f = new NumFragment();
			f.setArguments(bundle);
			
			fragmentList.add(f);
			
			TextView text = new TextView(this);
			text.setText("Fragment " + i);
			text.setBackgroundResource(R.drawable.bg_tab);
			text.setTextColor(getResources().getColorStateList(R.drawable.sel_text));
			
			LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
			text.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					int index = tabLayout.indexOfChild(v);
					
					viewPager.setCurrentItem(index);
				}
			});
			
			tabLayout.addView(text, params);
		}
		
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Log.e("", "onPageSelected " + arg0);
				updateTab(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		
		updateTab(0);
		
//		setContentView(R.layout.activity_fragmenttab);
//		
//		initTabs();
	}
	
	public void updateTab(View v)
	{
		for (int j = 0; j < tabLayout.getChildCount(); j++)
		{
			View view = tabLayout.getChildAt(j);
			
			if (view == v)
			{
				viewPager.setCurrentItem(j);
			}
			view.setSelected(view == v);
		}
	}

	private void updateTab(int position)
	{
		for (int j = 0; j < tabLayout.getChildCount(); j++)
		{
			View view = tabLayout.getChildAt(j);
			
			view.setSelected(j == position);
		}
	}
	
	class MyAdapter extends FragmentPagerAdapter
	{

		/**
		 * @param fm
		 */
		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		/* (non-Javadoc)
		 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
		 */
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			
			
			
			return fragmentList.get(arg0);
		}

		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragmentList.size();
		}
		
	}

	/**
	 * 
	 */
	private void initTabs() {
		// TODO Auto-generated method stub
		FragmentTabHost tabhost = (FragmentTabHost) findViewById(R.id.tabhost);
		
		tabhost.setup(this, getSupportFragmentManager(), R.id.realcontent);
		
		for (int i = 0; i < 4; i++)
		{
			Bundle bundle = new Bundle();
			bundle.putInt("num", i);
			tabhost.addTab(tabhost.newTabSpec("" + i)
					.setIndicator("fragment " + i), 
					NumFragment.class,
					bundle
					);
			
		}
		
		
	}
	
	

}
