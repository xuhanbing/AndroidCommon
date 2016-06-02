package com.hanbing.mytest.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Scroller;
import android.widget.TextView;

import com.common.base.BaseAppCompatActivity;
import com.common.util.LogUtils;
import com.common.util.ViewUtils;
import com.hanbing.mytest.R;

public class BaseActivity extends BaseAppCompatActivity {


	float downX = 0;
	float lastX = 0;
	int scrollX = 0;

	boolean canSwipe = false;
	boolean isFastSwipe = false;
	boolean isMove = false;
	Scroller scroller = null;

	VelocityTracker velocityTracker;
	
	boolean enableSwipeToFinish = false;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		if (!enableSwipeToFinish)
			return super.dispatchTouchEvent(ev);
		
		float x = ev.getRawX();
		float y = ev.getRawY();
		final View view = getSwitchView();

		if (null == view)
			return super.dispatchTouchEvent(ev);

		if (null == scroller) {
			scroller = new Scroller(this);
		}

		if (null == velocityTracker)
			velocityTracker = VelocityTracker.obtain();

		velocityTracker.addMovement(ev);

		// Log.e("", "dispatchTouchEvent " + ev.getAction());
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			canSwipe = true;
			isFastSwipe = false;
			isMove = false;
			if (!scroller.isFinished()) {
				scroller.abortAnimation();
				view.scrollTo(scrollX, 0);
			}
			downX = lastX = x;
			scrollX = view.getScrollX();

			break;
		case MotionEvent.ACTION_MOVE: {

			
				
			velocityTracker.computeCurrentVelocity(1000);

			float vx = velocityTracker.getXVelocity();
			float vy = velocityTracker.getYVelocity();

			LogUtils.e("", "vx=" + vx + ",vy=" + vy);
			
			
			if (!canSwipe)
				return super.dispatchTouchEvent(ev);
			
			if (isFastSwipe)
				return true;
			
			
			
			if ((Math.abs(vx) < Math.abs(vy)))
			{
				/**
				 * 如果第一次滑动，y方向的速度大于x方向，表示是上下滑动的趋势，禁止左右滑动
				 */
				if (!isMove)
				{
					canSwipe = false;
				}
				
			} else {
				canSwipe = true;
				isMove = true;
			}
			
			if (isMove) {
				
				if (vx > 3000)
				{
					
					if (!isFastSwipe)
					{
						swipeFinish(view, view.getScrollX(), -view.getWidth());
						isFastSwipe = true;
					}
					return true;
				}
				
				float deltaX = x - lastX;
				float scale = 0.8f;
				deltaX *= scale;

				// Log.e("", "move deltaX=" + deltaX);

				if (view.getScrollX() - deltaX < 0) {
					view.scrollBy((int) -deltaX, 0);
				}

				lastX = x;
				
				/**
				 * 左右滑动，直接消费事件
				 */
				return true;
			} 

		}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL: {

			/**
			 * 如果不是快速滑动结束
			 */
			if (!isFastSwipe)
			{
				int startX = view.getScrollX();
				int endX = scrollX;
				if (Math.abs(startX - scrollX) > view.getWidth() / 2) {
					endX = -view.getWidth();
				}
				swipeFinish(view, startX, endX);
			}
			
			lastX = x;
			
		}
			break;
		}

		lastX = x;
		return super.dispatchTouchEvent(ev);
	}

	private void swipeFinish(final View view, int startX, int endX) {
		

		int deltaX = endX - startX;
		int duration = Math.abs(deltaX);
		scroller.startScroll(startX, 0, deltaX, 0, duration);
		view.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				if (scroller.computeScrollOffset()) {
					view.scrollTo(scroller.getCurrX(), 0);

					view.postDelayed(this, 10);
				} else {
					view.removeCallbacks(this);
				}
			}
		});
		
		if (endX == -view.getWidth())
		{
			view.postDelayed(new Runnable() {
				public void run() {
					finish();
				}
			}, duration);
		}
	}

	private View getSwitchView() {
		// TODO Auto-generated method stub
		ViewGroup decor = (ViewGroup) getWindow().getDecorView();

		if (null != decor && decor.getChildCount() > 0) {
			return decor.getChildAt(0);
		}
		return null;
	}



}
