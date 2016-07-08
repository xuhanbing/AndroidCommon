package com.hanbing.mytest.activity.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ToastUtils;
import com.hanbing.mytest.R;

import java.util.ArrayList;
import java.util.List;

public class TestQQWaterDrop extends Activity {

	public TestQQWaterDrop() {
		// TODO Auto-generated constructor stub
	}

	final List<View> views = new ArrayList<View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		// View view = new QQWaterDrop(this);
		//
		// layout.addView(view, new LayoutParams(-1, -1));

//		TextView left = new TextView(this);
//		left.setText("Left");
//		left.setGravity(Gravity.CENTER);
//		// left.setBackgroundColor(Color.BLUE);
//		left.setBackgroundResource(R.drawable.a);
//		left.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				ToastUtils.showToast(getApplicationContext(), "click left");
//			}
//		});
		
		final ListView left = new ListView(this);
		left.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView textView = new TextView(getApplicationContext());
				
				textView.setText("item " + position);
				
				textView.setTextSize(25);
				textView.setPadding(40, 40, 40, 40);
				
				return textView;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 20;
			}
		});
		
		left.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				ToastUtils.showToast(getApplicationContext(), "click item " + position);
			}
		});

		TextView center = new TextView(this);
		center.setText("Center");
		center.setGravity(Gravity.CENTER);
		center.setBackgroundColor(Color.parseColor("#80000000"));
		center.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastUtils.showToast(getApplicationContext(), "click center");
			}
		});

		TextView right = new TextView(this);
		right.setText("Right");
		right.setGravity(Gravity.CENTER);
		right.setBackgroundResource(R.drawable.b);
		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastUtils.showToast(getApplicationContext(), "click right");
			}
		});

		LinearLayout layout2 = new LinearLayout(this);
		layout2.setBackgroundColor(Color.GRAY);

		final LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// layout2.addView(left);
		// layout2.addView(center);
		// layout2.addView(right);

		views.add(left);
		views.add(center);
		views.add(right);

		DrawerLayout drawerLayout = new DrawerLayout(this);
		drawerLayout.addView(left, params);
		drawerLayout.addView(right, params);

		setContentView(drawerLayout);

		// ScrollLayout scrollView = new ScrollLayout(this);
		// scrollView.setOritention(ScrollLayout.HORIZONTAL);
		// scrollView.setPageTransformer(new ScrollLayout.PageTransformer() {
		//
		// @Override
		// public void transformPage(View view, float position) {
		// // TODO Auto-generated method stub
		// transform(view, position);
		// }
		// });
		//// scrollView.addView(layout2);
		// scrollView.addView(left);
		// scrollView.addView(center);
		//// scrollView.addView(right);
		// scrollView.setToScreen(1);
		// setContentView(scrollView);

		// ViewPager pager = new ViewPager(this);
		// pager.setAdapter(new PagerAdapter() {
		//
		//
		//
		// @Override
		// public void destroyItem(ViewGroup container, int position, Object
		// object) {
		// // TODO Auto-generated method stub
		// container.removeView((View) object);
		// }
		//
		// @Override
		// public Object instantiateItem(ViewGroup container, int position) {
		// // TODO Auto-generated method stub
		//
		// View view = views.get(position);
		//
		// container.addView(view);
		//
		// return view;
		// }
		//
		// @Override
		// public boolean isViewFromObject(View arg0, Object arg1) {
		// // TODO Auto-generated method stub
		// return arg0 == arg1;
		// }
		//
		// @Override
		// public int getCount() {
		// // TODO Auto-generated method stub
		// return views.size();
		// }
		// });
		// pager.setPageTransformer(true, new PageTransformer() {
		//
		// @Override
		// public void transformPage(View arg0, float arg1) {
		// // TODO Auto-generated method stub
		// transform(arg0, arg1);
		// }
		// });
		//
		// setContentView(pager);

		// DrawerLayout sView = new DrawerLayout(this);
		// sView.setChild(left, center, right);
		// layout.addView(sView, params);
		// setContentView(sView);
	}

	private void transform(View view, float position) {
		Log.e("", "index=" + views.indexOf(view) + ",position=" + position);
		int width = view.getWidth();
		float base = width * 0.8f;

		if (position < -1.0 || position > 1) {
			view.setAlpha(0);
			view.setScaleX(0);
			view.setScaleY(0);

			float translationX = base;
			view.setTranslationX(translationX);
		} else if (position >= -1.0 && position < 0) {
			// view.setAlpha(1+position);
			//
			// float scaleX = 0;
			// float scaleY = 0;
			// scaleX = scaleY = position + 1;
			// view.setScaleX(scaleX);
			// view.setScaleY(scaleY);

			view.setAlpha(1);
			view.setScaleX(1);
			view.setScaleY(1);

			float translationX = base + width * 0.1f * position;
			view.setTranslationX(translationX);
		} else if (position >= 0) {
			view.setAlpha(1);
			view.setScaleX(1);
			view.setScaleY(1);
			view.setTranslationX(0);
		} else if (position > 0 && position <= 1.0) {
			view.setAlpha(1 - position);
			float scaleX = 0;
			float scaleY = 0;
			scaleX = scaleY = 1 - position;
			view.setScaleX(scaleX);
			view.setScaleY(scaleY);
		}

	}

	public static class DrawerLayout extends ViewGroup {

		int LEFT = 200;
		int LEFT2 = 100;
		float ratio = 1;

		View mLeft;
		View mRight;

		int mRightLeft;
		int mLeftLeft;
		private int mTouchSlop;

		/**
		 * @param context
		 * @param attrs
		 * @param defStyleAttr
		 */
		public DrawerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
			// TODO Auto-generated constructor stub
			init();
		}

		/**
		 * @param context
		 * @param attrs
		 */
		public DrawerLayout(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param context
		 */
		public DrawerLayout(Context context) {
			super(context);
			// TODO Auto-generated constructor stub

		}

		void init() {
			mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.LinearLayout#onMeasure(int, int)
		 */
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// TODO Auto-generated method stub

			super.onMeasure(widthMeasureSpec, heightMeasureSpec);

			int width = getMeasuredWidth();
			int height = getMeasuredHeight();

			widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

			if (getChildCount() > 1) {
				mLeft = getChildAt(0);
				mRight = getChildAt(1);

				 mLeft.measure(widthMeasureSpec,heightMeasureSpec);
				 mRight.measure(widthMeasureSpec, heightMeasureSpec);
				// MeasureSpec.EXACTLY), heightMeasureSpec);
				// mRight.measure(widthMeasureSpec, heightMeasureSpec);
				//
				// requestLayout();

				// FrameLayout;

				// LayoutParams params1 = new
				// LayoutParams(LayoutParams.MATCH_PARENT,
				// LayoutParams.MATCH_PARENT);
				// mLeft.setLayoutParams(params1);
				//
				// LayoutParams params2 = new
				// LayoutParams(LayoutParams.MATCH_PARENT,
				// LayoutParams.MATCH_PARENT);
				// mRight.setLayoutParams(params2);

				ratio = (getWidth() - LEFT) * 1.0f / (getWidth() - LEFT2);

			}

			reset();

		}

		boolean mFirstLayout = true;

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.HorizontalScrollView#onLayout(boolean, int, int,
		 * int, int)
		 */
		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			// TODO Auto-generated method stub
//			super.onLayout(changed, l, t, r, b);
			
			
			final int count = getChildCount();

	        for (int i = 0; i < count; i++) {
	            View child = getChildAt(i);
	            if (child.getVisibility() != GONE) {
//	                RelativeLayout.LayoutParams st =
//	                        (RelativeLayout.LayoutParams) child.getLayoutParams();
	                child.layout(0, 0, getWidth(), getHeight());
	            }
	        }
		}

		int mLastDownX;
		int mLastDownY;

		int mLastX;
		int mLastY;

		boolean mIsMove;

		VelocityTracker mVelocityTracker;
		
		int mLeftOriginLeft = 0;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
		 */
		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			// TODO Auto-generated method stub

			return super.dispatchTouchEvent(ev);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.ViewGroup#onInterceptTouchEvent(android.view.
		 * MotionEvent)
		 */
		@Override
		public boolean onInterceptTouchEvent(MotionEvent ev) {
			// TODO Auto-generated method stub

			float x = ev.getRawX();
			float y = ev.getRawY();

			if (null == mVelocityTracker) {
				mVelocityTracker = VelocityTracker.obtain();
			}

			mVelocityTracker.addMovement(ev);

			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				mLastDownX = mLastX = (int) x;
				mLastDownY = mLastY = (int) y;
				mIsMove = false;

			}
				break;

			case MotionEvent.ACTION_MOVE: {
				if (Math.abs(mLastDownX - x) > mTouchSlop || Math.abs(mLastDownY - y) > mTouchSlop)
					mIsMove = true;
				
				
				mVelocityTracker.computeCurrentVelocity(1000);
				
				float vx = mVelocityTracker.getXVelocity();
				float vy = mVelocityTracker.getYVelocity();
				
				if (Math.abs(vy) > Math.abs(vx))
				{
					return false;
				}
					
			}
				break;
			}

			LogUtils.e("onInterceptTouchEvent");
			return mIsMove;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
		 */
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			Log.e("", "onTouchEvent " + event.getAction());
			float x = event.getRawX();
			float y = event.getRawY();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
			}
				// break;
			case MotionEvent.ACTION_MOVE: {

				int dx = (int) (x - mLastX);

				mVelocityTracker.computeCurrentVelocity(1000);

				float vx = mVelocityTracker.getXVelocity();
				float vy = mVelocityTracker.getYVelocity();

				ViewConfiguration configuration = ViewConfiguration.get(getContext());
				int touchSlop = configuration.getScaledTouchSlop();

				int scaledMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();

				LogUtils.e("", "move vx=" + vx + ",vy=" + vy + ",f v=" + scaledMinimumFlingVelocity + ",dx=" + dx
						+ ",touch slop=" + touchSlop);
				if ((Math.abs(vx) > scaledMinimumFlingVelocity || Math.abs(vy) > scaledMinimumFlingVelocity)
						) {
					mIsMove = true;
					mLastX = (int) x;
					mLastY = (int) y;
					slide(dx);
					return true;
				}

			}
				break;
			case MotionEvent.ACTION_UP: {
				// reset();
				release();
			}
				break;
			}

			mLastX = (int) x;
			mLastY = (int) y;

			return super.onTouchEvent(event);
		}

		/**
		 * @param dx
		 */
		private void slide(int dx) {
			// TODO Auto-generated method stub
			{

				if (mRightLeft + dx > getWidth() - LEFT2
						|| mRightLeft + dx < 0)
				{
					return;
				}
				
				mRightLeft += dx;

				mLeftLeft += dx * ratio;

				// LayoutParams lp = (LayoutParams) mRight.getLayoutParams();
				//
				// lp.leftMargin += dx;
				// lp.rightMargin -= dx;

				// mRight.setLayoutParams(lp);

				int l = 0;
				int t = 0;

				
					

				l = mRightLeft;
				mRight.layout(l, t, l + getWidth(), t + getHeight());

				l = mLeftLeft;
				mLeft.layout(l, t, l + getWidth(), t + getHeight());

				postInvalidate();

			}

			// postInvalidate();

			// mRight.layout(params.left, params.top, params.right,
			// params.bottom);

		}

		// void slideToRight(int dx)
		// {
		// mRight.scrollBy(dx, 0);
		// }
		//
		// void slideToLeft(int dx)
		// {
		// mRight.scrollBy(dx, 0);
		// }

		public void computeScroll() {
			if (null != mScroller && mScroller.computeScrollOffset()) {

				Log.e("", "computeScroll true");
				int x = mScroller.getCurrX();

				int l = 0;
				int t = 0;

				l = x;
				mRight.layout(l, t, l + getWidth(), t + getHeight());

				l = (int) (mLeftOriginLeft + x * ratio);
				mLeft.layout(l, t, l + getWidth(), t + getHeight());

				postInvalidate();
			}
		};

		Scroller mScroller;

		void release() {

			if (null == mScroller) {
				mScroller = new Scroller(getContext());
			}

			int startX = mRightLeft;
			int endX = 0;
			
			if (startX > getWidth() / 2)
			{
				endX = getWidth() - LEFT2;
				
				mRightLeft = endX;
				mLeftLeft = (int) (mLeftOriginLeft + endX * ratio);
				
			} else {
				endX = 0;
				mRightLeft = 0;
				mLeftLeft = mLeftOriginLeft;
			}
			
			mScroller.startScroll(startX, 0, endX-startX, 0);

			postInvalidate();
		}

		void reset() {

			
			mLeftOriginLeft = LEFT - getWidth();
			mLeftLeft = mLeftOriginLeft;
			
//			{
//				LayoutParams lp = (LayoutParams) mLeft.getLayoutParams();
//
//				lp.leftMargin = -(mLeft.getWidth() - LEFT);
//				lp.rightMargin = -lp.leftMargin;
//				mLeft.setLayoutParams(lp);
//			}

			{
				// LayoutParams lp = (LayoutParams) mRight.getLayoutParams();
				//
				// lp.leftMargin = 0;
				// lp.rightMargin = 0;
				//
				// mRight.setLayoutParams(lp);
			}

			// postInvalidate();
		}

	}

}
