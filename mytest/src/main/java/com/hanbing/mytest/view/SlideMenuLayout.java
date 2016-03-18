package com.hanbing.mytest.view;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class SlideMenuLayout extends RelativeLayout {
	
	
	private View mSlidingView;	// 当前可滑动的view，默认为center view
	private View mMenuView;		// 左边菜单view
	private View mDetailView;	// 右边详情view
	private RelativeLayout bgShade;
	private int screenWidth;		// 屏幕宽
	private int screenHeight;		// 屏幕高
	private Context mContext;
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;
	private int mTouchSlop;			// 滑动的最短距离
	private float mLastX;
	private float mLastY;
	private static final int VELOCITY = 50;
	private boolean mIsBeingDragged = true;
	private boolean tCanSlideLeft = true;
	private boolean tCanSlideRight = false;
	private boolean hasClickLeft = false;
	private boolean hasClickRight = false;
	private final static int SHADE_WIDTH = 10;
	

	private void init(Context context) {
		
		mContext = context;
		bgShade = new RelativeLayout(context);
		mScroller = new Scroller(getContext());
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		WindowManager windowManager = ((Activity) context).getWindow()
				.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		screenWidth = display.getWidth();	
		screenHeight = display.getHeight();
		LayoutParams bgParams = new LayoutParams(screenWidth, screenHeight);
		bgParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		bgShade.setLayoutParams(bgParams);

	}


	public void addViews(View left, View center, View right) {
		setLeftView(left);
		setRightView(right);
		setCenterView(center);
	}

	public void setLeftView(View view) {
		LayoutParams behindParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		addView(view, behindParams);
		mMenuView = view;
	}

	public void setRightView(View view) {
		LayoutParams behindParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		behindParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		addView(view, behindParams);
		mDetailView = view;
	}

	public void setCenterView(View view) {
		LayoutParams aboveParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		LayoutParams bgParams = new LayoutParams(screenWidth, screenHeight);
		bgParams.addRule(RelativeLayout.CENTER_IN_PARENT);

		View bgShadeContent = new View(mContext);
//		bgShadeContent.setBackgroundResource(R.drawable.shade_bg);
		bgShade.addView(bgShadeContent, bgParams);

		addView(bgShade, bgParams);

		addView(view, aboveParams);
		mSlidingView = view;
		mSlidingView.bringToFront();
	}


	private boolean canSlideLeft = true;
	private boolean canSlideRight = false;
	public void setCanSliding(boolean left, boolean right) {
		canSlideLeft = left;
		canSlideRight = right;
	}

//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//		System.out.println("onInterceptTouchEvent action=" + ev.getAction());
//		final int action = ev.getAction();
//		final float x = ev.getX();
//		final float y = ev.getY();
//		switch (action) {
//		case MotionEvent.ACTION_DOWN:
//			mLastX = x;
//			mLastY = y;
//			mIsBeingDragged = false;
////			if (canSlideLeft) {
////				mMenuView.setVisibility(View.VISIBLE);
////				mDetailView.setVisibility(View.INVISIBLE);
////			}
////			if (canSlideRight) {
////				mMenuView.setVisibility(View.INVISIBLE);
////				mDetailView.setVisibility(View.VISIBLE);
////			}
//			break;
//
//		case MotionEvent.ACTION_MOVE:
//			final float dx = x - mLastX;
//			final float xDiff = Math.abs(dx);	// 得到滑动X偏移量
//			final float yDiff = Math.abs(y - mLastY);	// 得到滑动Y偏移量
//			if (xDiff > mTouchSlop && xDiff > yDiff) {	// 达到滑动条件
//				if (canSlideLeft) {
//					float oldScrollX = mSlidingView.getScrollX();
//					if (oldScrollX < 0) {
//						mIsBeingDragged = true;
//						mLastX = x;
//					} else {
//						if (dx > 0) {
//							mIsBeingDragged = true;
//							mLastX = x;
//						}
//					}
//				} else if (canSlideRight) {
//					float oldScrollX = mSlidingView.getScrollX();
//					if (oldScrollX > 0) {
//						mIsBeingDragged = true;
//						mLastX = x;
//					} else {
//						if (dx < 0) {
//							mIsBeingDragged = true;
//							mLastX = x;
//						}
//					}
//				}
//
//			}
//			break;
//
//		}
//		return mIsBeingDragged;
//	}


	private int getMenuViewWidth() {
		if (mMenuView == null) {
			return 0;
		}
		return mMenuView.getWidth();
	}

	private int getDetailViewWidth() {
		if (mDetailView == null) {
			return 0;
		}
		return mDetailView.getWidth();
	}

	void smoothScrollTo(int dx) {
		int duration = 500;
		int oldScrollX = mSlidingView.getScrollX();
		mScroller.startScroll(oldScrollX, mSlidingView.getScrollY(), dx,
				mSlidingView.getScrollY(), duration);
		invalidate();
	}

	/*
	 * 鏄剧ず宸︿晶杈圭殑view
	 * */
	public void showLeftView() {
		int menuWidth = mMenuView.getWidth();
		int oldScrollX = mSlidingView.getScrollX();
		if (oldScrollX == 0) {
			mMenuView.setVisibility(View.VISIBLE);
			mDetailView.setVisibility(View.INVISIBLE);
			smoothScrollTo(-menuWidth);
			tCanSlideLeft = canSlideLeft;
			tCanSlideRight = canSlideRight;
			hasClickLeft = true;
			setCanSliding(true, false);
		} else if (oldScrollX == -menuWidth) {
			smoothScrollTo(menuWidth);
			if (hasClickLeft) {
				hasClickLeft = false;
				setCanSliding(tCanSlideLeft, tCanSlideRight);
			}
		}
	}

	/*鏄剧ず鍙充晶杈圭殑view*/
	public void showRightView() {
		int menuWidth = mDetailView.getWidth();
		int oldScrollX = mSlidingView.getScrollX();
		if (oldScrollX == 0) {
			mMenuView.setVisibility(View.INVISIBLE);
			mDetailView.setVisibility(View.VISIBLE);
			smoothScrollTo(menuWidth);
			tCanSlideLeft = canSlideLeft;
			tCanSlideRight = canSlideRight;
			hasClickRight = true;
			setCanSliding(false, true);
		} else if (oldScrollX == menuWidth) {
			smoothScrollTo(-menuWidth);
			if (hasClickRight) {
				hasClickRight = false;
				setCanSliding(tCanSlideLeft, tCanSlideRight);
			}
		}
	}
	
	public interface OnMenuChangedListener
	{
		/**
		 * 
		 * @param left ��˵������Ҳ˵�
		 * @param isShow ��ʾ��������
		 */
		public abstract void onMenuChanged(boolean left, boolean isShow);
	}


	
	OnMenuChangedListener onMenuChangedListener;
	
	
	boolean mSlideLeft = true;
	boolean mShowingLeftMenu = false;
	

	public SlideMenuLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public SlideMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public SlideMenuLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	
	private void init()
	{
		init(getContext());
	}
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		intercept1(event);
//		intercept2(event);
		
		return mIsBeingDragged;
	}
	
	private void intercept1(MotionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("onInterceptTouchEvent action=" + event.getAction());
		float x = event.getX();
		float y = event.getY();
		
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			mLastX = x;
			mLastY = y;
			
			mIsBeingDragged = false;
			break;
		case MotionEvent.ACTION_MOVE:
		final float dx = x - mLastX;
		final float xDiff = Math.abs(dx);	// 得到滑动X偏移量
		final float yDiff = Math.abs(y - mLastY);	// 得到滑动Y偏移量
		if (xDiff > mTouchSlop && xDiff > yDiff) {	// 达到滑动条件
			if (mMenuView != null) {
				float oldScrollX = mSlidingView.getScrollX();
				if (oldScrollX < 0) {
					mIsBeingDragged = true;
					mLastX = x;
				} else {
					if (dx > 0) {
						mIsBeingDragged = true;
						mLastX = x;
					}
				}
			} 
			
			if (mDetailView != null) {
				float oldScrollX = mSlidingView.getScrollX();
				if (oldScrollX > 0) {
					mIsBeingDragged = true;
					mLastX = x;
				} else {
					if (dx < 0) {
						mIsBeingDragged = true;
						mLastX = x;
					}
				}
			}

		}
		break;
		}

	}


	private void intercept2(MotionEvent ev) {
		// TODO Auto-generated method stub
		System.out.println("onInterceptTouchEvent action=" + ev.getAction());
		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastX = x;
			mLastY = y;
			mIsBeingDragged = false;
			break;

		case MotionEvent.ACTION_MOVE:
			final float dx = x - mLastX;
			final float xDiff = Math.abs(dx);	// 得到滑动X偏移量
			final float yDiff = Math.abs(y - mLastY);	// 得到滑动Y偏移量
			if (xDiff > mTouchSlop && xDiff > yDiff) {	// 达到滑动条件
				if (canSlideLeft) {
					float oldScrollX = mSlidingView.getScrollX();
					if (oldScrollX < 0) {
						mIsBeingDragged = true;
						mLastX = x;
					} else {
						if (dx > 0) {
							mIsBeingDragged = true;
							mLastX = x;
						}
					}
				} else if (canSlideRight) {
					float oldScrollX = mSlidingView.getScrollX();
					if (oldScrollX > 0) {
						mIsBeingDragged = true;
						mLastX = x;
					} else {
						if (dx < 0) {
							mIsBeingDragged = true;
							mLastX = x;
						}
					}
				}

			}
			break;

		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		
		doMotionEvent1(event);
//		doMotionEvent2(event);

		return true;
	}
	
	
	private void doMotionEvent2(MotionEvent ev) {
		System.out.println("onTouchEvent action=" + ev.getAction());
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(ev);

		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastX = x;
			mLastY = y;
//			if (mSlidingView.getScrollX() == -getMenuViewWidth()
//					&& mLastX < getMenuViewWidth()) {
//				return false;
//			}
//
//			if (mSlidingView.getScrollX() == getDetailViewWidth()
//					&& mLastX > getMenuViewWidth()) {
//				return false;
//			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (mIsBeingDragged) {
				final float deltaX = mLastX - x;
				mLastX = x;
				float oldScrollX = mSlidingView.getScrollX();
				float scrollX = oldScrollX + deltaX;
//				if (canSlideLeft) {
//					if (scrollX > 0)
//						scrollX = 0;
//				}
//				if (canSlideRight) {
//					if (scrollX < 0)
//						scrollX = 0;
//				}
//				if (deltaX < 0 && oldScrollX < 0) { // left view
//					final float leftBound = 0;
//					final float rightBound = -getMenuViewWidth();
//					if (scrollX > leftBound) {
//						scrollX = leftBound;
//					} else if (scrollX < rightBound) {
//						scrollX = rightBound;
//					}
//				} else if (deltaX > 0 && oldScrollX > 0) { // right view
//					final float rightBound = getDetailViewWidth();
//					final float leftBound = 0;
//					if (scrollX < leftBound) {
//						scrollX = leftBound;
//					} else if (scrollX > rightBound) {
//						scrollX = rightBound;
//					}
//				}
				if (mSlidingView != null) {
					mSlidingView.scrollTo((int) scrollX,
							mSlidingView.getScrollY());
//					if (scrollX < 0)
//						bgShade.scrollTo((int) scrollX + SHADE_WIDTH,
//								mSlidingView.getScrollY());
//					else
//						bgShade.scrollTo((int) scrollX - SHADE_WIDTH,
//								mSlidingView.getScrollY());
				}

			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			if (mIsBeingDragged) {
				final VelocityTracker velocityTracker = mVelocityTracker;
				velocityTracker.computeCurrentVelocity(100);
				float xVelocity = velocityTracker.getXVelocity();// 婊戝姩鐨勯�搴�				
				int oldScrollX = mSlidingView.getScrollX();
				int dx = 0;
				if (oldScrollX <= 0 && canSlideLeft) {// left view
					if (xVelocity > VELOCITY) {
						dx = -getMenuViewWidth() - oldScrollX;
					} else if (xVelocity < -VELOCITY) {
						dx = -oldScrollX;
						if (hasClickLeft) {
							hasClickLeft = false;
							setCanSliding(tCanSlideLeft, tCanSlideRight);
						}
					} else if (oldScrollX < -getMenuViewWidth() / 2) {
						dx = -getMenuViewWidth() - oldScrollX;
					} else if (oldScrollX >= -getMenuViewWidth() / 2) {
						dx = -oldScrollX;
						if (hasClickLeft) {
							hasClickLeft = false;
							setCanSliding(tCanSlideLeft, tCanSlideRight);
						}
					}

				}
				if (oldScrollX >= 0 && canSlideRight) {
					if (xVelocity < -VELOCITY) {
						dx = getDetailViewWidth() - oldScrollX;
					} else if (xVelocity > VELOCITY) {
						dx = -oldScrollX;
						if (hasClickRight) {
							hasClickRight = false;
							setCanSliding(tCanSlideLeft, tCanSlideRight);
						}
					} else if (oldScrollX > getDetailViewWidth() / 2) {
						dx = getDetailViewWidth() - oldScrollX;
					} else if (oldScrollX <= getDetailViewWidth() / 2) {
						dx = -oldScrollX;
						if (hasClickRight) {
							hasClickRight = false;
							setCanSliding(tCanSlideLeft, tCanSlideRight);
						}
					}
				}
				smoothScrollTo(dx);
			}

			break;
		}
	}

	private void doMotionEvent1(MotionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("action=" + event.getAction());
		if (null == mVelocityTracker)
		{
			mVelocityTracker = VelocityTracker.obtain();
		}
		
		float x = event.getRawX();
		float y = event.getRawY();
		
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			mLastX = x;
			mLastY = y;
			
			
			mVelocityTracker.addMovement(event);
			
			
		}
			break;
		case MotionEvent.ACTION_MOVE:
		{
			mIsBeingDragged = true;
			if (mIsBeingDragged)
			{
				mVelocityTracker.computeCurrentVelocity(1000);
				
				float xVeclocity = mVelocityTracker.getXVelocity();
				
				int deltaX = (int) (mLastX - x);
				mLastX = x;
				
				float oldScrollX = mSlidingView.getScrollX();
				float scrollX = oldScrollX + deltaX;
				
				scrollX = getScrollX(scrollX);
				
				if (scrollX < 0)
				{
					mMenuView.setVisibility(View.VISIBLE);
					mDetailView.setVisibility(View.INVISIBLE);
				}
				else if (scrollX > 0)
				{
					mMenuView.setVisibility(View.INVISIBLE);
					mDetailView.setVisibility(View.VISIBLE);
				}
				
//				mSlidingView.scrollTo((int) scrollX,
//						mSlidingView.getScrollY());
				
				slideScrollTo((int) scrollX, mSlidingView.getScrollY());
			}
			
			
		}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			
			if (mIsBeingDragged)
			{
				int scrollX = mSlidingView.getScrollX();
				
				if (scrollX < 0)
				{
					if (Math.abs(scrollX) >= mMenuView.getWidth() / 2)
					{
						scrollX = -mMenuView.getWidth();
					}
					else
					{
						scrollX = 0;
					}
				}
				else if (scrollX > 0)
				{
					if (Math.abs(scrollX) >= mDetailView.getWidth() / 2)
					{
						scrollX = mDetailView.getWidth();
					}
					else
					{
						scrollX = 0;
					}
					
					
				}
				
//				mSlidingView.scrollTo((int) scrollX, mSlidingView.getScrollY());
				
				mScroller.startScroll(mSlidingView.getScrollX(), mSlidingView.getScrollY(), 
						scrollX - mSlidingView.getScrollX(),
						mSlidingView.getScrollY(), 500);
				invalidate();
			}
			
			
			break;
		}
		
//		return super.onTouchEvent(event);
	}

	private void slideScrollTo(int scrollX, int scrollY) {
		// TODO Auto-generated method stub
		
		mSlidingView.scrollTo(scrollX, scrollY);
		
		float scaleX = 0.0f;
		if (scrollX >= 0)
		{
			scaleX = Math.abs(scrollX) / mDetailView.getWidth();
		}
		else
		{
			scaleX = Math.abs(scrollX) / mMenuView.getWidth();
		}
		
		scaleX = scaleX * 0.3f + 0.7f;
		
		float scaleY = scaleX;
//		mSlidingView.setScaleX(scaleX);
//		mSlidingView.setScaleY(scaleY);
		
//		ScaleAnimation scale = new ScaleAnimation(mSlidingView.getScaleX(), scaleX, 
//				mSlidingView.getScaleY(), scaleY);
//		mSlidingView.startAnimation(scale);
		
	}


	private float getScrollX(float scrollX) {
		// TODO Auto-generated method stub
		if (null == mDetailView)
		{
			if (scrollX > 0)
				scrollX = 0;
		}
		else
		{
			if (scrollX > mDetailView.getWidth())
			{
				scrollX = mDetailView.getWidth();
			}
		}
		
		if (null == mMenuView)
		{
			if (scrollX < 0)
				scrollX = 0;
		}
		else
		{
			if (-scrollX > mMenuView.getRight())
			{
				scrollX = -mMenuView.getRight();
			}
		}
		
		return scrollX;
	}


	private void setCanSliding(Object tCanSlideLeft2, Object tCanSlideRight2) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		
		if (!mScroller.isFinished()) {
			if (mScroller.computeScrollOffset()) {
				int oldX = mSlidingView.getScrollX();
				int oldY = mSlidingView.getScrollY();
				int x = mScroller.getCurrX();
				int y = mScroller.getCurrY();
				if (oldX != x || oldY != y) {
					slideScrollTo(x, y);
				}
				invalidate();
			}
		} 
		
		
	}
	
	@Override
	public void scrollTo(int x, int y) {
		// TODO Auto-generated method stub
		super.scrollTo(x, y);
	}
}
