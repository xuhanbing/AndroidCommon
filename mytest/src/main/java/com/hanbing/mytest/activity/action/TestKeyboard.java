/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2015年5月11日
 * Time : 下午2:45:58
 */
package com.hanbing.mytest.activity.action;

import com.hanbing.mytest.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * TestKeyboard
 * @author hanbing 
 * @date 2015年5月11日
 * @time 下午2:45:58
 */
public class TestKeyboard extends Activity {

	
	RelativeLayout layoutMain;
	ViewGroup layoutTop;
	ViewGroup layoutBottom;
	
	EditText input;
	
	Rect rect = null;
	
	Handler handler = new Handler()
	{
		public void dispatchMessage(android.os.Message msg) {
			
		};
	};
	/**
	 * 
	 */
	public TestKeyboard() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		int mode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
		getWindow().setSoftInputMode(mode);
		
		setContentView(R.layout.activity_keyboard);
		
		calcSize();
		
		input = (EditText) findViewById(R.id.et_input);
		
		final View parent = findViewById(R.id.layout_main);
		
		layoutMain = (RelativeLayout) findViewById(R.id.layout_main);
		layoutTop = (ViewGroup) findViewById(R.id.layout_top);
		layoutBottom = (ViewGroup) findViewById(R.id.layout_bottom);
		
		RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) layoutBottom.getLayoutParams();
		
		if (null == params)
		{
			params = new android.widget.RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
		}
		params.alignWithParent = false;
		params.addRule(RelativeLayout.BELOW, R.id.layout_top);
		layoutBottom.setLayoutParams(params);
		
		layoutMain.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				
				
				
				Rect r = new Rect();
				
				layoutMain.getGlobalVisibleRect(r);
				
				Log.e("", "rect=" + r.toString());
				
				if (rect == null)
				{
					rect = new Rect();
				}
				else if(rect.height() != r.height())
				{
					
					RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) layoutBottom.getLayoutParams();
					if (rect.height() > r.height())
					{
						params.alignWithParent = true;
						params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
					}
					else
					{
						params.alignWithParent = false;
						params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
						params.addRule(RelativeLayout.BELOW, R.id.layout_top);
					}
					
					layoutBottom.setLayoutParams(params);
					layoutBottom.requestLayout();
					
					
				}
				
				rect.set(r);
			}
		});
		
		layoutBottom.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				
				
				
				Rect r = new Rect();
				
				layoutBottom.getGlobalVisibleRect(r);
				
				Log.e("", "bottom rect=" + r.toString());
				
			}
		});
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				
//				while (true)
//				{
//					Rect rect  = new Rect();
//					
//					parent.getGlobalVisibleRect(rect);
//					
//					Log.e("", "rect=" + rect.toString());
//					
//					SystemClock.sleep(500);
//				}
//			}
//		}).start();
		
		
		
//		input.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				if (event.getAction() == MotionEvent.ACTION_DOWN)
//				{
//					startNewActivity();
//				}
//				return true;
//			}
//
//		});
	}

	/**
	 * 
	 */
	private void calcSize() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	protected void startNewActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, TestKeyboardDialog.class);
		intent.putExtra("isNew", true);
		startActivity(intent);
	}
	
	
	
}
