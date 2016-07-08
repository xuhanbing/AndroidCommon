/**
 * 
 */
package com.hanbing.mytest.activity.view;

import java.util.ArrayList;
import java.util.List;

import com.hanbing.library.android.image.ImageLoader;
import com.hanbing.library.android.view.CircleImageView;
import com.hanbing.mytest.R;
import com.hanbing.mytest.activity.BaseActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * @author hanbing
 * 
 */
public class TestArcMenu extends BaseActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xhb.mytest.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		LinearLayout layout = new LinearLayout(this);
		LinearLayout layout1 = new LinearLayout(this);
		LinearLayout layout2 = new LinearLayout(this);

		ArcMenu[] menus = new ArcMenu[4];
		ArcMenu.Direction[] directions = { ArcMenu.Direction.TOP_LEFT, ArcMenu.Direction.TOP_RIGHT,
				ArcMenu.Direction.BOTTOM_LEFT, ArcMenu.Direction.BOTTOM_RIGHT };
		for (int index = 0; index < 4; index++) {
			int[] resIds = { R.drawable.n1, R.drawable.n2, R.drawable.n3, R.drawable.n4, R.drawable.p1, R.drawable.a, };

			final ArcMenu arcMenu = new ArcMenu(this);

			final int size = 70;
			LayoutParams params = new LayoutParams(size, size);
			for (int i = 0; i < resIds.length; i++) {
				CircleImageView imageView = new CircleImageView(this);

				ImageLoader.getInstance(getApplicationContext()).displayImage(imageView, "drawable://" + resIds[i]);

				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						arcMenu.close();
					}
				});

				arcMenu.addView(imageView, params);
				arcMenu.setBackgroundColor(Color.GRAY);
			}

			arcMenu.setBackgroundColor(((int)Math.random() * 0xffffff) & 0xff000000);
			menus[index] = arcMenu;
			arcMenu.setDirection(directions[index]);
		}

		{
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -2);
			params.weight = 1;
			layout1.addView(menus[0], params);
			layout1.addView(menus[1], params);

			layout2.addView(menus[2], params);
			layout2.addView(menus[3], params);
		}

		{
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, 0);
			params.weight = 1;

			layout.addView(layout1, params);
			layout.addView(layout2, params);
		}
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
	}

	public static class ArcMenu extends ViewGroup {

		public static enum Direction {
			TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;
		}

		final float DEFAULT_EXTRA_RATIO = 1.0f / 8;

		Direction mDirection = Direction.TOP_LEFT;
		int MAX_DISTANCE = 0;
		float MAX_EXTRA_RATIO = DEFAULT_EXTRA_RATIO;
		
		View mExpandChild;

		boolean mEnableStrength = true;
		int mSize = 0;

		boolean mOpenned = false;

		/**
		 * @param context
		 * @param attrs
		 * @param defStyleAttr
		 */
		public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param context
		 * @param attrs
		 */
		public ArcMenu(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param context
		 */
		public ArcMenu(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.View#onMeasure(int, int)
		 */
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// TODO Auto-generated method stub
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);

			for (int i = 0; i < getChildCount(); i++) {
				View child = getChildAt(i);
				LayoutParams params = child.getLayoutParams();

				int widthSize = params.width;
				int heightSize = params.height;
				int widthMode = MeasureSpec.UNSPECIFIED;
				int heightMode = MeasureSpec.UNSPECIFIED;

				if (widthSize > 0) {
					widthMode = MeasureSpec.EXACTLY;
				} else if (LayoutParams.WRAP_CONTENT == widthSize) {
					widthMode = MeasureSpec.UNSPECIFIED;
					widthSize = 0;
				} else if (LayoutParams.MATCH_PARENT == widthSize) {
					widthMode = MeasureSpec.EXACTLY;
					widthSize = getMeasuredWidth();
				}

				if (heightSize > 0) {
					heightMode = MeasureSpec.EXACTLY;
				} else if (LayoutParams.WRAP_CONTENT == heightSize) {
					heightMode = MeasureSpec.UNSPECIFIED;
					heightSize = 0;
				} else if (LayoutParams.MATCH_PARENT == heightSize) {
					widthMode = MeasureSpec.EXACTLY;
					heightMode = getMeasuredHeight();
				}

				widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);
				heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);

				child.measure(widthMeasureSpec, heightMeasureSpec);

				mSize = child.getMeasuredWidth();
				if (getChildCount() - 1 == i) {
					mExpandChild = child;

					mExpandChild.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (mOpenned)
								close();
							else

							open();
						}
					});
				}
			}

			calcMaxDistance();
		}

		protected void calcMaxDistance() {
			MAX_DISTANCE = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
					getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) - mSize;

			MAX_DISTANCE /= (1 + MAX_EXTRA_RATIO);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.ViewGroup#onLayout(boolean, int, int, int, int)
		 */
		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			// TODO Auto-generated method stub

			int childCount = getChildCount();

			switch (mDirection) {

			case TOP_LEFT: {
				for (int i = 0; i < childCount; i++) {
					getChildAt(i).layout(0, 0, mSize, mSize);
				}
			}
				break;
			case TOP_RIGHT: {
				for (int i = 0; i < childCount; i++) {
					getChildAt(i).layout(getWidth() - mSize, 0, getWidth(), mSize);
				}
			}
				break;
			case BOTTOM_LEFT: {
				for (int i = 0; i < childCount; i++) {
					getChildAt(i).layout(0, getHeight() - mSize, mSize, getHeight());
				}
			}
				break;
			case BOTTOM_RIGHT: {
				for (int i = 0; i < childCount; i++) {
					getChildAt(i).layout(getWidth() - mSize, getHeight() - mSize, getWidth(), getHeight());
				}
			}
				break;
			}

		}

		public void open() {

			float extraX = MAX_DISTANCE * MAX_EXTRA_RATIO;
			float dx = MAX_DISTANCE + extraX;
			float startX = 0;
			float endX = 0;

			switch (mDirection) {
			case TOP_LEFT:
			case BOTTOM_LEFT: {
				startX = 0;
				endX = startX + dx;
			}
				break;
			case TOP_RIGHT:
			case BOTTOM_RIGHT: {

				startX = getWidth() - mSize;
				endX = startX - dx;
			}
				break;
			}
			startAnimate(startX, endX, true);

			mOpenned = true;
		}

		public void close() {

			float extraX = MAX_DISTANCE * MAX_EXTRA_RATIO;
			float dx = MAX_DISTANCE + extraX;
			float startX = 0;
			float endX = 0;

			switch (mDirection) {
			case TOP_LEFT:
			case BOTTOM_LEFT: {
				endX = 0;
				startX = endX + dx;
			}
				break;
			case TOP_RIGHT:
			case BOTTOM_RIGHT: {

				endX = getWidth() - mSize;
				startX = endX - dx;
			}
				break;
			}
			startAnimate(startX, endX, false);

			mOpenned = false;
		}

		List<Scroller> mScrollers;

		void startAnimate(final float startX, final float endX, boolean isOpen) {

			final int duration = 1000;
			RotateAnimation animation = null;

			int fromDegrees = 0;
			int toDegrees = 0;

			switch (mDirection) {
			case TOP_LEFT: {
				fromDegrees = 0;
				toDegrees = 180;
			}
				break;
			case BOTTOM_LEFT: {
				fromDegrees = 180;
				toDegrees = 0;
			}
				break;
			case TOP_RIGHT: {
				fromDegrees = 0;
				toDegrees = -180;
			}
				break;
			case BOTTOM_RIGHT: {
				fromDegrees = 270;
				toDegrees = 360;
			}
				break;
			}

			animation = new RotateAnimation(isOpen ? fromDegrees : toDegrees, isOpen ? toDegrees : fromDegrees,
					RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			animation.setFillAfter(true);
			animation.setDuration(300);

			if (null == mScrollers) {
				mScrollers = new ArrayList<Scroller>();
			} else {
				clearScrollers();
			}

			for (int i = 0; i < getChildCount(); i++) {
				View child = getChildAt(i);

				child.startAnimation(animation);

				if (i < getChildCount() - 1) {
					final Scroller scroller = new Scroller(getContext());
					mScrollers.add(scroller);

					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							scroller.startScroll((int) startX, 0, (int) (endX - startX), 0, duration);
						}
					}, i * 20);
				}
			}

		}

		protected void clearScrollers() {
			if (null == mScrollers)
				return;
			for (int i = 0; i < mScrollers.size(); i++) {
				mScrollers.get(i).abortAnimation();
			}

			mScrollers.clear();
		}

		Handler mHandler = new Handler();

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.View#computeScroll()
		 */
		@Override
		public void computeScroll() {
			// TODO Auto-generated method stub

			for (int i = 0; null != mScrollers && i < mScrollers.size(); i++) {
				Scroller scroller = mScrollers.get(i);

				if (null != scroller && scroller.computeScrollOffset()) {
					int radius = scroller.getCurrX() - scroller.getStartX();

					switch (mDirection) {
					case TOP_LEFT:
					case BOTTOM_LEFT: {
						radius = scroller.getCurrX() - scroller.getStartX();

						if (radius < 0) {
							radius = scroller.getCurrX() - scroller.getFinalX();
						}
					}
						break;
					case TOP_RIGHT:
					case BOTTOM_RIGHT: {
						radius = scroller.getStartX() - scroller.getCurrX();

						if (radius < 0) {
							radius = scroller.getFinalX() - scroller.getCurrX();
						}
					}
						break;
					}

					int dx = 0;
					if (radius > MAX_DISTANCE) {
						dx = radius - MAX_DISTANCE;
						radius = MAX_DISTANCE - dx;
					}

					int menuCount = getChildCount() - 1;
					// for (int i = 0; i < menuCount; i++)
					{
						View child = getChildAt(i);

						double arc = Math.PI / 2 / (menuCount - 1) * i;

						int x = (int) (Math.cos(arc) * radius);
						int y = (int) (Math.sin(arc) * radius);

						switch (mDirection) {
						case TOP_LEFT: {
							x = x + getPaddingLeft();
							y = y + getPaddingTop();
						}
							break;
						case BOTTOM_LEFT: {
							y = getHeight() - getPaddingBottom() - y - mSize;
						}
							break;
						case TOP_RIGHT: {
							x = getWidth() - getPaddingRight() - x - mSize;
						}
							break;
						case BOTTOM_RIGHT: {

							x = getWidth() - getPaddingRight() - x - mSize;
							y = getHeight() - getPaddingBottom() - y - mSize;
						}
							break;
						}

						child.layout(x, y, x + mSize, y + mSize);

					}

				}
			}
		}

		public void setEnableStrength(boolean enable) {
			mEnableStrength = enable;

			MAX_EXTRA_RATIO = enable ? DEFAULT_EXTRA_RATIO : 0;
			
			calcMaxDistance();
		}

		public void setDirection(Direction direction) {
			this.mDirection = direction;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.ViewGroup#onDetachedFromWindow()
		 */
		@Override
		protected void onDetachedFromWindow() {
			// TODO Auto-generated method stub
			clearScrollers();
			super.onDetachedFromWindow();
		}

	}
}
