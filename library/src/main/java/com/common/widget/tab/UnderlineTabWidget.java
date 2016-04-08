/**
 * 
 */
package com.common.widget.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.androidcommon.R;
import com.common.util.LogUtils;

/**
 * tabwidget with a strip
 * 
 * @author hanbing
 * @date 2015-9-14
 */
public class UnderlineTabWidget extends TabStripWidget {


	/**
	 * strip location start.
	 * if is horizontal start is top, end is bottom,
	 * otherwise start is left, end is right;
	 */
	static final int GRAVITY_START = 0;
	static final int GRAVITY_END = 1;
	/**
	 * strip view
	 */
	protected View mStripView;
	
	/**
	 * strip background
	 */
	protected View mStripBackgroundView;
	
	/**
	 * strip color
	 */
	protected int mStripColor = Color.BLACK;

	protected Drawable mStripDrawable = null;

	/**
	 * strip background color
	 */
	protected int mStripBackgroundColor = Color.BLACK;

	protected Drawable mStripBackground;
	/**
	 * height of strip
	 */
	protected int mStripHeight = 4;

	/**
	 * padding start and end
	 */
	protected int mStripPadding = 0;

	/**
	 * strip gravity only support top or bottom default is bottom
	 */
	protected int mStripGravity = GRAVITY_END;
	
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
		init(context, attrs);
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

		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UnderlineTabWidget);

		mStripHeight = a.getDimensionPixelSize(R.styleable.UnderlineTabWidget_stripSize, mStripHeight);
		mStripGravity = a.getInt(R.styleable.UnderlineTabWidget_stripGrivaty, GRAVITY_END);
		mStripDrawable = a.getDrawable(R.styleable.UnderlineTabWidget_stripDrawable);
		mStripBackground = a.getDrawable(R.styleable.UnderlineTabWidget_stripTrackBackground);
		mStripPadding = a.getDimensionPixelSize(R.styleable.UnderlineTabWidget_stripPadding, 0);

		mStripView = new View(getContext());
		if (null == mStripDrawable)
			mStripView.setBackgroundColor(mStripBackgroundColor);
		else
			mStripView.setBackgroundDrawable(mStripDrawable);
		mStripView.setLayoutParams(new android.view.ViewGroup.LayoutParams(
				LayoutParams.WRAP_CONTENT, mStripHeight));

		mStripBackgroundView = new View(getContext());
		mStripBackgroundView.setBackgroundDrawable(mStripBackground);
		mStripBackgroundView.setLayoutParams(new android.view.ViewGroup.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		

	}

	@Override
	protected void onUpdateStrip(int left, int width) {

		int t = 0;
		int b = 0;
		int l = 0;
		int r = 0;

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

			if (GRAVITY_START == mStripGravity) {
				t = 0;
				b = mStripHeight;

			} else {
				b = getHeight();
				t = b - mStripHeight;
			}

			int padding = mStripPadding;

			if (mStripEnabled) {
				l = left;
				r = l + width;

				l += padding;
				r -= padding;

				mStripView.layout(l, t, r, b);
			}

			postInvalidate();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (mStripEnabled && null != mStripView) {
			measureChild(mStripView, 0, 0);
			measureChild(mStripBackgroundView, 0,
					0);
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
	public void scrollStripFollowViewPager(int position, float positionOffset,
										   int positionOffsetPixels) {
		// TODO Auto-generated method stub
		super.scrollStripFollowViewPager(position, positionOffset, positionOffsetPixels);
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

//		if (mStripEnabled)
//		setPadding(0, mStripHeight, 0, mStripHeight);
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
		
//		if (enable)
//		{
//			setPadding(0, mStripHeight, 0, mStripHeight);
//		}
//		else
//		{
//			setPadding(0, 0, 0, 0);
//		}
//
//		requestLayout();
	}

}
