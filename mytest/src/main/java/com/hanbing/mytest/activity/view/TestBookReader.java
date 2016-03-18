/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2015年4月21日
 * Time : 上午11:38:34
 */
package com.hanbing.mytest.activity.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hanbing.mytest.activity.base.SlideRightFinishActivity;
import com.hanbing.mytest.activity.view.TestBookReader.MyScrollView.OnScrollChangedListener;

/**
 * TestBookReader
 * @author hanbing 
 * @date 2015年4月21日
 * @time 上午11:38:34
 */
public class TestBookReader extends SlideRightFinishActivity {
	
	
	public static class MyScrollView extends ScrollView
	{
		public interface OnScrollChangedListener
		{
			public void onScrollToTop();
			public void onScrollToBottom();
			public void onScroll(int l, int t);
		}

		
		OnScrollChangedListener mOnScrollChangedListener;
		/**
		 * @param context
		 */
		public MyScrollView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
		
		/* (non-Javadoc)
		 * @see android.view.View#onScrollChanged(int, int, int, int)
		 */
		@Override
		protected void onScrollChanged(int l, int t, int oldl, int oldt) {
			// TODO Auto-generated method stub
			
			Log.e("", "t=" + t + ",h=" + getHeight() + ",range=" + computeVerticalScrollRange());
			
			if (0 == t)
			{
				if (null != mOnScrollChangedListener)
				{
					mOnScrollChangedListener.onScrollToTop();
				}
			}
			else if (t + getHeight() >= computeVerticalScrollRange())
			{
				if (null != mOnScrollChangedListener)
				{
					mOnScrollChangedListener.onScrollToBottom();
				}
			}
			
			if (null != mOnScrollChangedListener)
			{
				mOnScrollChangedListener.onScroll(l, t);
			}
			super.onScrollChanged(l, t, oldl, oldt);
		}
		
		public void setOnScrollChangedListener(OnScrollChangedListener lsner)
		{
			this.mOnScrollChangedListener = lsner;
		}
	}
	
	
	boolean isFirst = true;
	static int mScrollY = 0;
	
	static final long BLOCK_SIZE = 2 * 1024;

	long mCurrentLength = 0;
	String mFilePath = "/sdcard/2.txt";
	RandomAccessFile mFileReader = null;
	
	StringBuilder mStringBuilder = new StringBuilder();
	
	MyScrollView mScrollView;
	LinearLayout mLayout;
	TextView mTextView;
	/**
	 * 
	 */
	public TestBookReader() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.xhb.mytest.activity.base.SlideRightFinishActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		
		init();
		
	}
	
	
	
	private void init()
	{
		mScrollView = new MyScrollView(this);
		mScrollView.setOnScrollChangedListener(new OnScrollChangedListener() {

			@Override
			public void onScrollToTop() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScrollToBottom() {
				// TODO Auto-generated method stub
				loadMoreDataAsync();
			}

			@Override
			public void onScroll(int l, int t) {
				// TODO Auto-generated method stub
				Log.e("", "onscroll l=" + l + ",t=" + t);
				mScrollY = t;
			}
		});
		mLayout = new LinearLayout(this);
		mLayout.setOrientation(LinearLayout.VERTICAL);
		mTextView = new TextView(this);
		mTextView.setText(mStringBuilder);
		
		mLayout.addView(mTextView);
		mScrollView.addView(mLayout);
		
		setContentView(mScrollView);
		
		loadMoreDataAsync();
	}

	
	boolean mIsLoading = false;
	/**
	 * 
	 */
	protected void loadMoreDataAsync() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				loadMoreData();
			}
		}).start();
		
	}
	
	protected synchronized void loadMoreData()
	{
		if (mIsLoading)
		{
			return;
		}
		
		mIsLoading = true;
		
		if (null == mFileReader)
		{
			try {
				mFileReader = new RandomAccessFile(mFilePath, "rws");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if (null != mFileReader)
		{
			
			
			try {
				
//				if (mCurrentLength < mFileReader.length())
//				{
//					mFileReader.seek(mCurrentLength);
//					
//					byte[] buffer = new byte[(int) BLOCK_SIZE];
//					int ret = mFileReader.read(buffer);
//					
//					mCurrentLength += ret;
//					
//					mStringBuilder.append(new String(buffer));
//					
//					runOnUiThread(new Runnable() {
//						
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							mTextView.setText(mStringBuilder);
//						}
//					});
//				}
				
				final StringBuilder sb = new StringBuilder();
				int lines = 0;
				String string = null;
				while (lines < BLOCK_SIZE
						&& (string = mFileReader.readLine()) != null)
				
				{
					sb.append(new String(string.getBytes("ISO-8859-1"), "utf-8"));
					sb.append("\n");
					lines++;
					
				}

				if (lines > 0)
				{
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							mTextView.append(sb);
							
						}
					});
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		mIsLoading = false;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if (null != mFileReader)
		{
			try {
				mFileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mFileReader = null;
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 0, 0, "load");
		menu.add(0, 1,0, "save");
		return super.onCreateOptionsMenu(menu);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId())
		{
		case 0:
		{
			SharedPreferences sp = getSharedPreferences("Mark", 0);
			
			float scrollY  = sp.getFloat("ScrollY", 0.0f);
			
			mScrollView.scrollTo(0, (int) scrollY);
		}
			break;
		case 1:
		{
			float scrollY = mScrollView.getScrollY();
			
			
				SharedPreferences sp = getSharedPreferences("Mark", 0);
				SharedPreferences.Editor editor = sp.edit();
				
				editor.putFloat("ScrollY", scrollY);
				
				editor.commit();
		}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
