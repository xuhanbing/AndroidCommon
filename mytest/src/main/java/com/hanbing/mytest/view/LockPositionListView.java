package com.hanbing.mytest.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;


public class LockPositionListView extends ListView {

	String TAG = getClass().getSimpleName();
	
	boolean isFirstChanged = true;
	Rect mLastChildRect = new Rect();
	int mLastChildPosition = 0;
	int mLastChildTop = 0;
	
	
	public LockPositionListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LockPositionListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LockPositionListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		
		super.onScrollChanged(l, t, oldl, oldt);
		
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		if (isFirstChanged)
		{
			isFirstChanged = false;
		}
		else
		{
			int top = h - mLastChildTop;
			setSelectionFromTop(mLastChildPosition, top);
		}
		
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int first = getFirstVisiblePosition();
		int last = getLastVisiblePosition();
		
		View v = getChildAt(last - first);
		
		if (null != v)
		{
			Rect r = mLastChildRect;
			
			v.getGlobalVisibleRect(r);
			
			mLastChildTop = r.height();
			
			mLastChildPosition = last;
		}
		
		
	}

}
