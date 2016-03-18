/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2015楠烇拷4閺堬拷20閺冿拷
 * Time : 娑撳宕�6:00:01
 */
package com.hanbing.mytest.activity.action;

import java.util.HashMap;
import java.util.Map;

import com.hanbing.mytest.R;
import com.hanbing.mytest.view.ScrollLayout.OnScreenChangedListener;
import com.hanbing.mytest.view.UnlockView;
import com.hanbing.mytest.view.ScrollLayout;
import com.hanbing.mytest.view.UnlockView.OnStatusChangedListener;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * TestKeyguard
 * @author hanbing 
 * @date 2015楠烇拷4閺堬拷20閺冿拷
 * @time 娑撳宕�6:00:01
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN) public class TestKeyguard extends Activity {

	
	private static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
	UnlockView mUnlockView;
	/**
	 * 
	 */
	public TestKeyguard() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		
		 this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//鍏抽敭浠ｇ爜
		 
		 
		 KeyguardManager mKeyguardManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);  
	     KeyguardLock   mKeyguardLock = mKeyguardManager.newKeyguardLock("my_lockscreen");   
	     
	     mKeyguardLock.disableKeyguard(); 
	     Log.e("", "mKeyguardManager.isKeyguardLocked():"+mKeyguardManager.isKeyguardLocked());
	     Log.e("", "mKeyguardManager.isKeyguardSecure():"+mKeyguardManager.isKeyguardSecure());
		 
		setTitle("Key Guard");
		
//		KeywordView view = new KeywordView(this);
//		
//		view.setOnVerifyListener(new OnVerifyListener() {
//			
//			@Override
//			public void onSuccess() {
//				// TODO Auto-generated method stub
//				finish();
//			}
//			
//			@Override
//			public void onError() {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "闁挎瑨顕�", Toast.LENGTH_SHORT).show();
//			}
//		});
		
//		setContentView(view);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		ViewPager viewPager = new ViewPager(this);
		
		viewPager.setAdapter(new MyAdatper());
		
		
		
		UnlockView view = new UnlockView(this);
		view.setOnStatusChangedListener(new OnStatusChangedListener() {
			
			@Override
			public void onSlideRight() {
				// TODO Auto-generated method stub
				unlock();
			}
			
			@Override
			public void onSlideLeft() {
				// TODO Auto-generated method stub
				unlock();
			}
			
			@Override
			public void onClickSlider() {
				// TODO Auto-generated method stub
				mUnlockView.changeSliderImage();
			}
		});
		view.setBackgroundColor(0xffff00ff);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		params.weight = 1;
		layout.addView(viewPager, params);
		layout.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, 400));
		
		
		mUnlockView = view;
		
//	        lockLayer = new LockLayer(this);  
//	        lockLayer.setLockView(layout);  
//	        lockLayer.lock();  
		setContentView(layout);
		
//		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//		WindowManager.LayoutParams wlp = new WindowManager.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		wlp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;  
//		wlp.flags = 1280;
//		
//		wm.addView(layout, wlp);
		
//		lockLayer = new LockLayer(this);
//		lockLayer.setLockView(layout); 
//		
//		lockLayer.lock();
	}
	
	private void unlock()
	{
		if (null != lockLayer)
		{
			lockLayer.unlock();
		}
		
		finish();
	}
	
	@Override
	protected void onPause() {
	// TODO Auto-generated method stub
	    super.onPause();
	}
	
	public void onAttachedToWindow() {
	    this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG); 
	    super.onAttachedToWindow();
	};
	
	LockLayer lockLayer = null;
	
	int count = 4;
	Map<Integer, View> map = new HashMap<Integer, View>();
	
	class MyAdatper extends PagerAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return count;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
//			return super.instantiateItem(container, position);
			
			View view = null;
			if (map.containsKey(position))
			{
				view = map.get(position);
			}
			else
			{
				view = getView(position);
				map.put(position, view);
			}
			
			container.addView(view);
			return view;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
//			super.destroyItem(container, position, object);
//			View view = list.get(position);
//			if (container.indexOfChild(view) >= 0)
//			{
//				container.removeView(view);
//			}
			
			container.removeView((View) object);
			
		}
	}
	
	private View getView(final int position)
	{
		
//		ScrollView scroll = new ScrollView(this);
//		
//		LinearLayout layout = new LinearLayout(this);
//		
//		layout.setOrientation(LinearLayout.VERTICAL);
//		
//		for (int i = 0; i < count; i++)
//		{
//			ImageView im = new ImageView(this);
//			
//			im.setImageResource((i % 2 == 0) ? R.drawable.a : R.drawable.b);
//			
//			layout.addView(im);
//		}
//		
//		scroll.addView(layout);
//		
//		return scroll;
		
		ScrollLayout layout = new ScrollLayout(this);
		
		layout.setOnScreenChangedListener(new OnScreenChangedListener() {
			
			@Override
			public void onScreenScroll(int current, float percent, int next) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScreenChanged(int screen) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDoubleClick(int screen) {
				// TODO Auto-generated method stub
				mUnlockView.changeSliderImage();
			}
		});
//		layout.setOnLongClickListener(new OnLongClickListener() {
//			
//			@Override
//			public boolean onLongClick(View v) {
//				// TODO Auto-generated method stub
//				
//				Log.e("", "onLongClick " + position);
//				return true;
//			}
//		});
//		int[] resIds = {R.drawable.p41, R.drawable.p42, R.drawable.p43, R.drawable.p44, R.drawable.p45};
		int[] resIds = {R.drawable.p1, R.drawable.p2, R.drawable.p3};
		
		for (int i = 0; i < resIds.length; i++)
		{
			ImageView im = new ImageView(this);
			
			im.setImageResource(resIds[i % resIds.length]);
			
			layout.addView(im);
		}
		
		return layout;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(KeyEvent.KEYCODE_HOME==keyCode)
		{
			Log.e("", "onKeyDown home");
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	} 
	
	/* (non-Javadoc)
	 * @see android.app.Activity#dispatchKeyEvent(android.view.KeyEvent)
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		Log.e("", "dispatchKeyEvent " + event.getKeyCode() + "," + event.getAction());
		return super.dispatchKeyEvent(event);
	}

	
    public class LockLayer {  
        private Activity mActivty;  
        private WindowManager mWindowManager;  
        private View mLockView;  
        private android.view.WindowManager.LayoutParams mLockViewLayoutParams;  
        private boolean isLocked;  
        // 杩欎釜鍊煎叿浣撶敤浜庡疄鐜板叏灞�  
        private final static int FLAG_APKTOOL_VALUE = 1280;  
      
        public LockLayer(Activity act) {  
            mActivty = act;  
            init();  
        }  
      
        private void init() {  
            isLocked = false;  
            mWindowManager = mActivty.getWindowManager();  
            mLockViewLayoutParams = new android.view.WindowManager.LayoutParams();  
            mLockViewLayoutParams.width = LayoutParams.MATCH_PARENT;  
            mLockViewLayoutParams.height = LayoutParams.MATCH_PARENT;  
            // 杩欎竴琛屽疄鐜板睆钄紿ome  
            mLockViewLayoutParams.type = android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;  
            mLockViewLayoutParams.flags = FLAG_APKTOOL_VALUE;  
            mLockViewLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
    		| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | FLAG_APKTOOL_VALUE;
        }  
      
        public synchronized void lock() {  
            if (mLockView != null && !isLocked) {  
                mWindowManager.addView(mLockView, mLockViewLayoutParams);  
            }  
            isLocked = true;  
        }  
      
        public synchronized void unlock() {  
            if (mWindowManager != null && isLocked) {  
                mWindowManager.removeView(mLockView);  
            }  
            isLocked = false;  
        }  
      
        public synchronized void setLockView(View v) {  
            mLockView = v;  
        }  
    }  

}
