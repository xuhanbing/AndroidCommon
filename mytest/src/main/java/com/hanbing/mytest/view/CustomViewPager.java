package com.hanbing.mytest.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;

public class CustomViewPager extends ViewPager {

	public CustomViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		 init();
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
	}

	private VelocityTracker mVelocityTracker;
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager#onInterceptTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return true;
	}
	
	boolean isMove = false;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		System.out.println("touch " + event.getAction());
		// ��ָλ�õص�
		float x = event.getX();
		float y = event.getY();

		if (mVelocityTracker == null) {

			mVelocityTracker = VelocityTracker.obtain();
		}

		mVelocityTracker.addMovement(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			mDownX = x;
			mDownY = y;
			mLastMotionX = x;
			mLastMotionY = y ;

			getParent().requestDisallowInterceptTouchEvent(true);
//			if (listview != null)
//			{
//				listview.getParent().requestDisallowInterceptTouchEvent(false);
//			}

			break;
		case MotionEvent.ACTION_MOVE:
			
//			if (scrollView!=null) {
//				scrollView.requestDisallowInterceptTouchEvent(false);
//			}
			
			float deltaX = x - mLastMotionX;
			float deltaY = y - mLastMotionY;
			
			double angle = Math.atan(Math.abs(deltaX) / Math.abs(deltaY));
			
			//���»���
			if (angle < MAX_ANGLE)
			{
				getParent().requestDisallowInterceptTouchEvent(false);
				return super.onTouchEvent(event);
			}
			
			mLastMotionX = x;
			mLastMotionY = y ;

			break;
		case MotionEvent.ACTION_UP:
			
//			if(listview!=null){
//        		listview.getParent().requestDisallowInterceptTouchEvent(false);
//        	}
			
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			//���ͼƬ
			if (Math.abs(velocityTracker.getXVelocity()) < MIN_VELOCITY
					&& Math.abs(velocityTracker.getYVelocity()) < MIN_VELOCITY
					&& Math.abs(x - mDownX) < MIN_DIS
					&& Math.abs(y - mDownY) < MIN_DIS) 
			{
				return false;
			}
			
			

			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}

			getParent().requestDisallowInterceptTouchEvent(false);
			break;

		}

		return super.onTouchEvent(event);
	}
    
    private float mLastMotionX = 0;
	private float mLastMotionY = 0;
	
	private float mDownX = 0;
	private float mDownY = 0;
	
	static final double MIN_VELOCITY = 600;
	static final double MAX_ANGLE = Math.PI / 4;
	static final int MIN_DIS = 10;
    
	// ������������
	public static int SNAP_VELOCITY = 600;
    
}
