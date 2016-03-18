package com.hanbing.mytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView2 extends ScrollView{

	public MyScrollView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyScrollView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyScrollView2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		
		if (t == oldt)
		{
			if (0 == t)
			{
				if (onPositionChangedListener != null)
				{
					onPositionChangedListener.onTop();
				}
			}
			else
			{
				if (onPositionChangedListener != null)
				{
					onPositionChangedListener.onBottom();
				}
			}
		}
		
		int height = getHeight();
		int range = this.computeVerticalScrollRange();
		
		System.out.println("add=" + (height + t));
		
		System.out.println("onScrollChanged=" + l +","+ t+"," + oldl+"," + oldt + ",range=" + range);
	}
	
	/**
	 * @return the onPositionChangedListener
	 */
	public OnPositionChangedListener getOnPositionChangedListener() {
		return onPositionChangedListener;
	}

	/**
	 * @param onPositionChangedListener the onPositionChangedListener to set
	 */
	public void setOnPositionChangedListener(
			OnPositionChangedListener onPositionChangedListener) {
		this.onPositionChangedListener = onPositionChangedListener;
	}

	OnPositionChangedListener onPositionChangedListener;
	
	
	
	public interface OnPositionChangedListener 
	{
		public abstract void onTop();
		public abstract void onBottom();
	}
}
