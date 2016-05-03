/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��5��13�� 
 * Time : ����10:48:14
 */
package com.hanbing.mytest.activity.view;

import java.util.ArrayList;
import java.util.List;


import com.hanbing.mytest.R;
import com.hanbing.mytest.fragment.NumFragment;
import com.hanbing.mytest.listener.MulitPointTouchListener;
import com.hanbing.mytest.view.CustomImageView;
import com.hanbing.mytest.view.TabsAdapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


/**
 * TestViewPager.java 
 * @author hanbing 
 * @date 2014��5��13�� 
 * @time ����10:48:14
 */
public class TestViewPager extends FragmentActivity {

    
    int resIds[] = {/*R.drawable.a, R.drawable.b, 
                    R.drawable.a00, */R.drawable.n1,
                    R.drawable.n2, R.drawable.n3, R.drawable.n4
                    };
    String titles[] = {"Image 1", "Image 2", "Image 3", "image 4"};
    
    ViewPager viewPager;
    
    ViewFlipper viewFlipper;
    
    class MyPagerAdatper extends PagerAdapter
    {

    	int size = count;
        List<ImageView> list = new ArrayList<ImageView>();
        
        public MyPagerAdatper()
        {
        	this(count);
        }
        
        public MyPagerAdatper(int size)
        {
        	this.size = size;
            for (int i = 0; i < size; i++)
            {
        	CustomImageView image = new CustomImageView(TestViewPager.this);
                image.setImageResource(resIds[i % resIds.length]);
//                image.setScaleType(ScaleType.FIT_CENTER);
//                image.setLayoutParams(new LayoutParams(200, 200));
                image.setOnTouchListener(new MulitPointTouchListener(
                    TestViewPager.this, new OnLongClickListener() {
                        
                        @Override
                        public boolean onLongClick(View v) {
                            // TODO Auto-generated method stub
                            System.out.println("long click2");
                            Toast.makeText(TestViewPager.this, "long click", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }, null));
                image.setOnLongClickListener(new OnLongClickListener() {
                    
                    @Override
                    public boolean onLongClick(View v) {
                        // TODO Auto-generated method stub
                        System.out.println("long click1");
                        return true;
                    }
                });
                list.add(image);
            }
        }
        /* (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#getCount()
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return size;
        }

        /* (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View, java.lang.Object)
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
//            super.destroyItem(container, position, object);
            container.removeView(getView(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            
            ImageView image = getView(position);
            if (container.indexOfChild(image) >= 0)
            	container.removeView(image);
            	container.addView(image);	
            return image;
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
        	// TODO Auto-generated method stub
//        	return titles[position % titles.length];
        	return "image " + position;
        }
        
        
        public ImageView getView(int position)
        {
        	return list.get(position);
        }
        
        
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        initViewPager6();
//        initViewPager2();
//        layout.addView(viewPager);
//        
//        setContentView(layout);z
    }
    
    
    TabViewPager tabViewPager;
	com.common.widget.tab.TabWidget tabWidget;
    int count = 5;
    
    private void initViewPager6()
    {
    	setContentView(R.layout.activity_viewpager3);
    	viewPager = (ViewPager) findViewById(R.id.viewpager);
    	MyPagerAdatper pageAdapter = new MyPagerAdatper();
    	viewPager.setAdapter(pageAdapter);
    	
    	com.common.view.TabWidget customTabWidget = (com.common.view.TabWidget) findViewById(R.id.tabwidget);
    	customTabWidget.initTabs(pageAdapter);
    	customTabWidget.setCanScroll(false);
    	customTabWidget.setViewPager(viewPager);
    	
    	tabWidget = customTabWidget;
    	
//    	tabViewPager = new TabViewPager(this);
//    	tabViewPager.setViewPager(customTabWidget, viewPager);
    }
    
    private void initViewPager5()
    {
    	viewPager = new ViewPager(this);
    	viewPager.setId(R.id.viewpager);
    	viewPager.setAdapter(new MyPagerAdatper());
    	
    	setContentView(viewPager);
    }
    /**
	 * 
	 */
	private void initViewPager4() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_viewpager2);
		
		LinearLayout tabs = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_tabs, null);
		
		DefaultTabViewPager tabViewPager = (DefaultTabViewPager) findViewById(R.id.viewpager);
		this.tabViewPager = tabViewPager;
		com.common.view.UnderlineTabWidget tabWidget = (com.common.view.UnderlineTabWidget) tabViewPager.getTabWidget();
		tabWidget.setCanScroll(true);
		tabWidget.setStripEnabled(true);
		
		for (int i = 0; i < count; i++)
    	{
    		Bundle bundle = new Bundle();
    		bundle.putInt("num", i+1000);
    		TextView text = new TextView(this);
    		text.setText("tab " + i);
    		text.setGravity(Gravity.CENTER);
    		text.setTextSize(24);
    		double ratio = Math.random();
    		text.setLayoutParams(new LayoutParams((int) (160 + 100 * ratio), 90));
//    		text.setBackgroundResource(R.drawable.bg_tab2);
    		text.setTextColor(Color.WHITE);
    		
    		tabWidget.addTab(tabWidget.newTabSpec(""+i).setIndicator(text));
    		
    	}
		
		tabViewPager.setTabGravity(Gravity.BOTTOM);
//		tabViewPager.setBackgroundColor(Color.RED);
		tabWidget.setBackgroundColor(Color.GREEN);
		tabWidget.setStripHeight(5);
//		tabWidget.setInnerLayout(tabs);
		
		viewPager = tabViewPager.getViewPager();
		viewPager.setAdapter(new MyPagerAdatper());
//		viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
		viewPager.setBackgroundColor(Color.BLUE);
	}
	
	class MyFragmentAdapter extends FragmentPagerAdapter
	{

		/**
		 * @param fm
		 */
		public MyFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return new Fragment();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return count;
		}
		
		
		
	}
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 10 ;i++)
		{
			menu.add(0, i, 0, "item " + i);
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
//		customTabWidget.setSelectedItem("" + id);
//		tabViewPager.setCurrentItem(id);
		tabWidget.setCurrentItem(id);
		
		return super.onOptionsItemSelected(item);
	}
    private void initViewPager3()
    {
    	setContentView(R.layout.activity_viewpager);
    	
    	TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
    	tabHost.setup();
    	
    	final TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
    	final HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.sv_tabs);
    	
    	
    	
    	viewPager = (ViewPager) findViewById(R.id.viewpager);
    	
    	final List<View> tabViews = new ArrayList<View>();
    	OnPageChangeListener onPageChangeListener= new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 >= tabViews.size())
					return;
				View tab = tabViews.get(arg0);
				
				int left = tab.getLeft();
				int right = tab.getRight();
				int width = tab.getWidth();
				int mesureWidth = tab.getMeasuredWidth();
				
				Rect rect = new Rect();
				int[] location = new int[2];
				tab.getLocationInWindow(location);
				
				int x = location[0];
				int y = location[1];
				
				Log.e("", "location=" + location[0] + "," + location[1]);
				Log.e("", "w=" + tabWidget.getWidth() + ",ww=" + tab.getMeasuredWidth());
				Log.e("", "l=" + left + ",r=" + right + ",r - l=" + (right - left) + ",w=" +width + ",mw=" + mesureWidth);
				
				int dx = 0;
				if (x < 0)
				{
					dx = x;
				}
				else if (x > 1080 - width)
				{
					dx = x - (1080 - width);
				}
				
//				hsv.scrollBy(dx, 0);
				hsv.smoothScrollBy(dx, 0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		TabsAdapter adapter = new TabsAdapter(this, tabHost, viewPager, onPageChangeListener);
    	
    	for (int i = 0; i < 10; i++)
    	{
    		Bundle bundle = new Bundle();
    		bundle.putInt("num", i+1000);
    		TextView text = new TextView(this);
    		text.setText("" + i);
    		text.setLayoutParams(new LayoutParams(160, 90));
    		text.setBackgroundResource(R.drawable.bg_tab2);
    		text.setTextColor(Color.BLACK);
//    		tabViews.add(text);
    		adapter.addTab(tabHost.newTabSpec("fragment " + i).setIndicator(""+i), NumFragment.class, bundle);
    		
    	}
    	tabWidget.setStripEnabled(true);
    	viewPager.setAdapter(adapter);
    }
    
    private void initViewPager2()
    {
//	TabViewPager tabViewPager = new TabViewPager(this);
//	tabViewPager.getViewPager().setAdapter(new MyPagerAdatper());
//	setContentView(tabViewPager, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	
	viewPager = new ViewPager(this);
	viewPager.setAdapter(new MyPagerAdatper());
	viewPager.setPageTransformer(true, new DepthPageTransformer());
	viewPager.setOnPageChangeListener(new OnPageChangeListener() {
	    
	    @Override
	    public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	    }
	    
	    @Override
	    public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		Log.e("", "arg0=" + arg0 + ",arg1=" + arg1 + ",arg2=" + arg2);
	    }
	    
	    @Override
	    public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	    }
	});
	setContentView(viewPager);
	
//	ScrollLayout scrollLayout = new ScrollLayout(this);
//	
//	for (int i = 0; i < resIds.length; i++)
//        {
//            ImageView image = new ImageView(TestViewPager.this);
//            image.setImageResource(resIds[i]);
////            image.setScaleType(ScaleType.FIT_CENTER);
////            image.setLayoutParams(new LayoutParams(200, 200));
//            image.setOnTouchListener(new MulitPointTouchListener(
//                TestViewPager.this, new OnLongClickListener() {
//                    
//                    @Override
//                    public boolean onLongClick(View v) {
//                        // TODO Auto-generated method stub
//                        System.out.println("long click2");
//                        Toast.makeText(TestViewPager.this, "long click", Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//                }, null));
//            image.setOnLongClickListener(new OnLongClickListener() {
//                
//                @Override
//                public boolean onLongClick(View v) {
//                    // TODO Auto-generated method stub
//                    System.out.println("long click1");
//                    return true;
//                }
//            });
//            scrollLayout.addView(image);
//        }
//	scrollLayout.setBackgroundResource(R.drawable.a);
//	setContentView(scrollLayout);
    }
    
    /**
     * 
     */
    private void initViewPager1() {
	// TODO Auto-generated method stub
	
	setContentView(R.layout.activity_viewpager);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
	List<String> titles = new ArrayList<String>();
        
        for (int i = 0; i < resIds.length; i++)
        {
        	titles.add("Image " + i);
        }
        
	final ViewPagerSelecter selecter = new ViewPagerSelecter(this);
        selecter.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
        		LayoutParams.WRAP_CONTENT));
        selecter.setItems(titles);
        selecter.setOnPageSelectedListener(new OnPageSelectedListener() {
			
			@Override
			public void onSelect(int position) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(position);
			}
		});
//        layout.addView(selecter);
        
        PagerTabStrip pagerTabStrip = new PagerTabStrip(this);
        PagerTitleStrip pagerTitleStrip = new PagerTitleStrip(this);
        
//        viewPager = new CustomViewPager(this);
        
        MyPagerAdatper adapter = new MyPagerAdatper();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(adapter.getCount() / 2);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				selecter.select(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				selecter.move(arg0, arg1, arg2);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        addContentView(selecter, lp);
    }

    public interface  OnPageSelectedListener
	{
		public void onSelect(int position);
	}
    
    public class ViewPagerSelecter extends LinearLayout
    {
    	
    	OnPageSelectedListener mOnPageSelectedListener;
    	
    	List<String> mItems = null;
    	LinearLayout mLayout;
    	View mCursor;
    	
    	int defaultColor = Color.BLACK;
    	int selectColor = Color.GREEN;

		public ViewPagerSelecter(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
		public void setItems(List<String> items)
		{
			this.mItems = items;
			
			init();
		}
		
		private void init()
		{
			removeAllViews();
			setOrientation(LinearLayout.VERTICAL);
			
			int count = getCount();
			
			LinearLayout layout = new LinearLayout(getContext());
			
			for (int i = 0; i < count; i++)
			{
				layout.addView(getTitle(i));
			}
			
			addView(layout);
			mLayout = layout;
			
			
			RelativeLayout l = new RelativeLayout(getContext());
			View cursor = new View(getContext());
			cursor.setLayoutParams(new LayoutParams(200, 10));
			cursor.setBackgroundColor(Color.BLUE);
			
			l.addView(cursor);
			
			addView(l);
			
			mCursor = cursor;
			
			select(0);
		}
		
		private int getCount()
		{
			return null == mItems ? 0
					: mItems.size();
		}
		
		private TextView getTitle(final int position)
		{
			TextView text = new TextView(getContext());
			text.setBackgroundColor(Color.WHITE);
			text.setText(mItems.get(position));
			text.setGravity(Gravity.CENTER);
			
			LinearLayout.LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
			text.setLayoutParams(lp);
			
			text.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (null != mOnPageSelectedListener)
					{
						mOnPageSelectedListener.onSelect(position);
					}
				}
			});
			
			return text;
		}
		
		public void setOnPageSelectedListener(OnPageSelectedListener lsner)
		{
			this.mOnPageSelectedListener = lsner;
		}
		
		public void select(int position)
		{
			int count = mLayout.getChildCount();
			
			for (int i = 0; i < count; i++)
			{
				
				TextView text = (TextView) mLayout.getChildAt(i);
				
				text.setTextColor(i == position ? selectColor : defaultColor);
			}
		}
		
		public void move(int postion, float percent, int distance)
		{
			int count = getCount();
			
			if (count == 0)
				return;
			
			float width = getWidth() * 1.0f / count;
			
			
			RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) mCursor.getLayoutParams();
			
			int x = (int) ((postion +  percent) * width);
			int y = mCursor.getTop();
			
			System.out.println("x=" + x + ",y=" + y);
			
			
			int w = mCursor.getWidth();
			
//			if (w >= width)
//			{
//				w = (int) width;
//			}
//			else
//			{
//				x += (width - w) / 2;
//			}
			
			x += (width - w) / 2;
			
			x = Math.max(0, Math.min(x, getWidth() - w));
			
			
			lp.leftMargin = x;
			lp.width = w;
			
			mCursor.setLayoutParams(lp);
			mCursor.requestLayout();
			
		}
    }
    
    public class DepthPageTransformer implements ViewPager.PageTransformer {  
        private static final float MIN_SCALE = 0.75f;  
      
        public void transformPage(View view, float position) {  
            int pageWidth = view.getWidth();  
      
            if (position < -1) { // [-Infinity,-1)  
                // This page is way off-screen to the left.  
                view.setAlpha(0);  
      
            } else if (position <= 0) { // [-1,0]  
                // Use the default slide transition when moving to the left page  
                view.setAlpha(1);  
                view.setTranslationX(0);  
                view.setScaleX(1);  
                view.setScaleY(1);  
      
            } else if (position <= 1) { // (0,1]  
                // Fade the page out.  
                view.setAlpha(1 - position);  
      
                // Counteract the default slide transition  
                view.setTranslationX(pageWidth * -position);  
      
                // Scale the page down (between MIN_SCALE and 1)  
                float scaleFactor = MIN_SCALE  
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));  
                view.setScaleX(scaleFactor);  
                view.setScaleY(scaleFactor);  
      
            } else { // (1,+Infinity]  
                // This page is way off-screen to the right.  
                view.setAlpha(0);  
            }  
        }  
    }  
    
}
