/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2015年4月20日
 * Time : 下午5:27:40
 */
package com.hanbing.mytest.activity.base;

import com.hanbing.mytest.activity.BaseActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;

/**
 * SlideRightFinishActivity
 * @author hanbing 
 * @date 2015年4月20日
 * @time 下午5:27:40
 */
public class SlideRightFinishActivity extends BaseActivity {

	
	GestureDetector mGestureDetector ;
	OnGestureListener mOnGestureListener = new OnGestureListener() {
		
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			if (Math.abs(velocityX) > 100
					&& e1.getX() + 200 < e2.getX())
			{
				finish();
				
				return true;
			}
			return false;
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	/**
	 * 
	 */
	public SlideRightFinishActivity() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		mGestureDetector = new GestureDetector(this, mOnGestureListener);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		mGestureDetector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

}
