/**
 * 
 */
package com.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout.LayoutParams;

/**
 * tabwidget with a strip
 * 
 * @author hanbing
 * @date 2015-9-14
 */
public class UnderlineTabWidget extends TabWidget {

	/**
	 * strip view
	 */
	View mStripView;
	
	/**
	 * strip background
	 */
	View mStripBackgroundView;
	
	/**
	 * strip color
	 */
	final int mStripColor = Color.YELLOW;
	/**
	 * strip background color
	 */
	final int mStripBackgroundColor = Color.TRANSPARENT;
	
	/**
	 * @param context
	 */
	public UnderlineTabWidget(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public UnderlineTabWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public UnderlineTabWidget(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();

		mStripView = new View(getContext());
		mStripView.setBackgroundColor(mStripColor);
		mStripView.setLayoutParams(new android.view.ViewGroup.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		mStripBackgroundView = new View(getContext());
		mStripBackgroundView.setBackgroundColor(mStripBackgroundColor);
		mStripBackgroundView.setLayoutParams(new android.view.ViewGroup.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		setStripEnabled(true);
		setStripScrollEnabled(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (mStripEnabled && null != mStripView) {
			measureChild(mStripView, widthMeasureSpec, heightMeasureSpec);
			measureChild(mStripBackgroundView, widthMeasureSpec,
					heightMeasureSpec);
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);

		if (mStripEnabled && null != mStripView) {

			if (null != mStripBackgroundView) {
				if (Gravity.TOP == mStripGravity) {
					mStripBackgroundView.layout(0, 0, mInnerLayoutContentWidth,
							mStripHeight);
				} else {
					mStripBackgroundView.layout(0, getHeight() - mStripHeight,
							mInnerLayoutContentWidth, getHeight());
				}

			}

			if (Gravity.TOP == mStripGravity) {
				t = 0;
				b = mStripHeight;

			} else {
				b = getHeight();
				t = b - mStripHeight;
			}

			if (mScrollStripEnabled) {
				l = mStripLeft;
				r = l + mStripWidth;

				mStripView.layout(l, t, r, b);
			} else {
				View tab = mInnerLayout.getChildAt(mSelectedTab);

				if (null != tab) {

					l = tab.getLeft();
					r = l + tab.getMeasuredWidth();
					mStripView.layout(l, t, r, b);
				}
			}

			postInvalidate();
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);

		if (mStripEnabled && null != mStripView) {

			drawChild(canvas, mStripBackgroundView, getDrawingTime());

			View tab = getTab(mSelectedTab);

			if (null != tab) {
				drawChild(canvas, mStripView, getDrawingTime());
			}
		}
	}

	@Override
	public void scrollStrip(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub
		super.scrollStrip(position, positionOffset, positionOffsetPixels);

		requestLayout();
	}
	
	

	/**
	 * set custom strip
	 * 
	 * @param stripView
	 */
	public void setStripView(View stripView) {
		this.mStripView = stripView;
		mStripView.setLayoutParams(new android.view.ViewGroup.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		requestLayout();
	}

	/**
	 * set strip color resid
	 * 
	 * @param resid
	 */
	public void setStripColorResource(int resid) {

		if (null != mStripView) {
			mStripView.setBackgroundResource(resid);
		}
	}
	
	public void setStripColor(int color) {

		if (null != mStripView) {
			mStripView.setBackgroundResource(color);
		}
	}
	
	/**
	 * set strip background color
	 * @param color
	 */
	public void setStripBackgroundColor(int color)
	{
		mStripBackgroundView.setBackgroundColor(color);
	}
	
	/**
	 * set strip background resid
	 * @param resid
	 */
	public void setStripBackgroundResource(int resid)
	{
		mStripBackgroundView.setBackgroundResource(resid);
	}

	/**
	 * set strip height, you need better call this after set gravity
	 * 
	 * @param height
	 */
	public void setStripHeight(int height) {
		mStripHeight = height;

		if (mStripEnabled)
		setPadding(0, mStripHeight, 0, mStripHeight);
	}
	
	/**
	 * set strip gravity only support top and bottom
	 * @param gravity
	 */
	public void setStripGravity(int gravity)
	{
		this.mStripGravity = gravity;
	}
	
	@Override
	public void setStripEnabled(boolean enable) {
		// TODO Auto-generated method stub
		super.setStripEnabled(enable);
		
		if (enable)
		{
			setPadding(0, mStripHeight, 0, mStripHeight);
		}
		else
		{
			setPadding(0, 0, 0, 0);
		}
		
		requestLayout();
	}

}
