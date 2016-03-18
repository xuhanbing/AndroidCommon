/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2015年4月27日
 * Time : 下午3:03:31
 */
package com.hanbing.mytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * SideBarLayout
 * @author hanbing 
 * @date 2015年4月27日
 * @time 下午3:03:31
 */
public class SideBarLayout extends RelativeLayout {

	
	static final float CENTER_MIN_SCALE = 0.7f;
	enum Status
	{
		DEFAULT,
		LEFT_SHOW,
		RIGHT_SHOW;
	}
	
	View mLeftView;
	View mCenterView;
	View mRightView;
	
	VelocityTracker mVelocityTracker;
	Scroller mScroller;
	int mStartX;
	int mStartY;
	int mLastX;
	int mLastY;
	
	int mScrollLastX = 0;
	
	Status mStatus = Status.DEFAULT;
	
	/**
	 * @param context
	 */
	public SideBarLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public SideBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SideBarLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init()
	{
		mScroller = new Scroller(getContext());
	}

	
	/* (non-Javadoc)
	 * @see android.widget.RelativeLayout#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	/* (non-Javadoc)
	 * @see android.widget.RelativeLayout#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
//		if (changed)
//		{
//			if (null != mLeftView)
//			{
//				mLeftView.layout(-mLeftView.getWidth(), 0, 0, getHeight());
//			}
//			
//			if (null != mRightView)
//			{
//				mRightView.layout(0, 0, mRightView.getWidth(), getHeight());
//			}
//			
//			invalidate();
//		}
	}
	
	/* (non-Javadoc)
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		actTouchEvent(ev);
//		return super.dispatchTouchEvent(ev);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
	
	private void actTouchEvent(MotionEvent event)
	{
		
		
		int x = (int) event.getX();
		int y = (int) event.getY();
		
//		Log.e("", "action=" + event.getAction() + ",x=" + x + ",y=" + y);
		if (null == mVelocityTracker)
		{
			mVelocityTracker = VelocityTracker.obtain();
		}
		
		mVelocityTracker.addMovement(event);
		
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			mStartX = x;
			mStartY = y;
			
			mScrollLastX = 0;
			
		}
			break;
		case MotionEvent.ACTION_MOVE:
		{
			mVelocityTracker.computeCurrentVelocity(1000);
			float vx = mVelocityTracker.getXVelocity();
//			Log.e("", "vx=" + vx + ",mCenterView.getLeft()=" + mCenterView.getLeft());
			if (mCenterView.getLeft() > 0
					|| (mCenterView.getLeft() == 0 && vx > 0))
			{
				showLeft(event);
			}
			else if (mCenterView.getLeft() < 0
					|| (mCenterView.getLeft() == 0 && vx < 0)){
				showRight(event);
			}
		}
			break;
		case MotionEvent.ACTION_UP:
			end();
			break;
		}
		
		mLastX = x;
		mLastY = y;
	}

	/**
	 * 
	 */
	private void end() {
		// TODO Auto-generated method stub
		LayoutParams params = (LayoutParams) mCenterView.getLayoutParams();
		int l = params.leftMargin;
		int r = params.rightMargin;
		
		boolean scroll = true;
		Log.e("", "end l=" + l + ",r=" + r);
		if (l > 0 && r < 0)
		{
			if (mLeftView != null)
			{
				int w = mLeftView.getWidth();
				if (l > w / 2)
				{
					slideToRight(w - l, scroll);
				}
				else
				{
					slideToRight(-l, scroll);
				}
			}
		}
		else if (l < 0 && r > 0)
		{
			if (mRightView != null)
			{
				int w = mRightView.getWidth();
				if (r > w / 2)
				{
					slideToLeft(r - w, scroll);
				}
				else
				{
					slideToLeft(r, scroll);
				}
			}
		}
	}
	
	private void slideToLeft(int dx)
	{
		slideToLeft(dx, false);
	}
	
	private void slideToLeft(int dx, boolean scroll)
	{
		int w = mRightView.getWidth();
		LayoutParams params = (LayoutParams) mCenterView.getLayoutParams();
		
		
		
		if (scroll)
		{
			mScroller.startScroll(params.leftMargin, 0, dx, 0, 500);
		}
		else
		{
			params.leftMargin += dx;
			
			Log.e("", "lmargin=" + params.leftMargin + ",rmargin=" + params.rightMargin);
			
			params.leftMargin = Math.min(Math.max(params.leftMargin, -w), 0);
			params.rightMargin = -params.leftMargin;
			
			LayoutParams params2 = (LayoutParams) mRightView.getLayoutParams();
			params2.rightMargin = params.rightMargin - w;
			
			params2.rightMargin = Math.min(Math.max(params2.rightMargin, -w), 0);
			params2.leftMargin = getWidth() - params2.rightMargin - w;
			
			mCenterView.setLayoutParams(params);
			mCenterView.requestLayout();
			
			mRightView.setLayoutParams(params2);
			mRightView.requestLayout();
			
			
		}
		invalidate();
	}
	
	private void slideToRight(int dx)
	{
		slideToRight(dx, false);
	}
	
	private void slideToRight(int dx, boolean scroll)
	{
		LayoutParams params = (LayoutParams) mCenterView.getLayoutParams();
		
		if (scroll)
		{
			Log.e("", "slide to right scart scroll start=" + (params.leftMargin - dx) + ",dx=" + dx);
			mScroller.startScroll(params.leftMargin, 0, dx, 0, 500);
		}
		else
		{
			
			params.leftMargin  += dx;
			
			int w = mLeftView.getWidth();
			params.leftMargin = Math.min(Math.max(params.leftMargin, 0), w);
			params.rightMargin = - params.leftMargin;
			
			
			LayoutParams params2 = (LayoutParams) mLeftView.getLayoutParams();
			params2.leftMargin = params.leftMargin - w;
			
			
			params2.leftMargin =  Math.min(Math.max(params2.leftMargin, -w), 0);
			params2.rightMargin = getWidth() - params2.leftMargin - w;
			
			mCenterView.setLayoutParams(params);
			mCenterView.requestLayout();
			
			mLeftView.setLayoutParams(params2);
			mLeftView.requestLayout();
			
		}
		invalidate();
	}

	private void showLeft(MotionEvent event)
	{
		if (null == mLeftView)
			return;
		mStatus = Status.LEFT_SHOW;
		if (null != mRightView)
		{
			mRightView.setVisibility(View.GONE);
		}
		mLeftView.setVisibility(View.VISIBLE);
		int dx = -(int) (mLastX - event.getX());
		
		slideToRight(dx);
		
		
	}
	
	private void showRight(MotionEvent event)
	{
		if (null == mRightView)
			return;
		if (null != mLeftView)
		{
			mLeftView.setVisibility(View.GONE);
		}
		mRightView.setVisibility(View.VISIBLE);
		
		mStatus = Status.RIGHT_SHOW;
		
		int dx = -(int) (mLastX - event.getX());
		int dy = -(int) (mLastY - event.getY());
		
		
		slideToLeft(dx);
	}
	
	
	
	/* (non-Javadoc)
	 * @see android.view.View#computeScroll()
	 */
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
//		super.computeScroll();
		boolean scroll = mScroller.computeScrollOffset();
		Log.e("", "can scroll=" + scroll);
		if (scroll)
		{
			LayoutParams params = (LayoutParams) mCenterView.getLayoutParams();
			int l = params.leftMargin;
			int r = params.rightMargin;
			
			Log.e("", "scroll startX=" + mScroller.getStartX() + ",finalX=" + mScroller.getFinalX()
					+ ",currentX=" + mScroller.getCurrX());
			
			if (mScrollLastX <= 0)
			{
				mScrollLastX = mScroller.getStartX();
			}
			
			int dx = mScroller.getCurrX() - mScrollLastX;
			
			if (l > 0)
			{
				slideToRight(dx);
			}
			else if (l < 0)
			{
				slideToLeft(dx);
			}
			
			mScrollLastX = mScroller.getCurrX();
			
		}
	}
	public void addView(View left, View center, View right)
	{
		this.mLeftView = left;
		this.mCenterView = center;
		this.mRightView = right;
			
		if (null != left)
		{
			addView(left);
		}
		
		if (null != right)
		{
			addView(right);
		}
		
		if (center != null)
		{
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			addView(center, lp);
		}
		
	}
}
