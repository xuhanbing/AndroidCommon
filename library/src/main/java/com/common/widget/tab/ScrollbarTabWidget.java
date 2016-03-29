/**
 * 
 */
package com.common.widget.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.androidcommon.R;
import com.common.view.TextDrawable;

/**
 * @author hanbing
 * @date 2015-9-14
 */
public class ScrollbarTabWidget extends TabStripWidget {

	/**
	 * 
	 */
	TextDrawable[] mTextDrawables = null;
	
	float mTabTextSize = 18;
	
	Rect mTextRect = new Rect();
	
	Drawable mIndicator = null;
	
	/**
	 * @param context
	 */
	public ScrollbarTabWidget(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ScrollbarTabWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public ScrollbarTabWidget(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub

	}
	
	private void init(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		mTextDrawables = new TextDrawable[3];
		for (int i = 0; i < mTextDrawables.length; i++)
		{
			mTextDrawables[i] = new TextDrawable(getContext());
		}
		
		mIndicator = getContext().getResources().getDrawable(R.drawable.bg_tabwidget_scrollbar);
		
		
	}
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		
		if (mStripEnabled)
		{
			
			int more = 10;
			mTextRect.set(mStripLeft - more, mInnerLayout.getTop(), mStripLeft + mStripWidth + more, mInnerLayout.getBottom());

			if (null == mIndicator)
			{
				mIndicator = getContext().getResources().getDrawable(R.drawable.bg_tabwidget_scrollbar);
			}
			
			if (null != mIndicator)
			{
				mIndicator.setBounds(mTextRect.left, mTextRect.top + more, mTextRect.right, mTextRect.bottom - more);
				mIndicator.draw(canvas);
			}
			
			
			canvas.clipRect(mTextRect);
			
			createTextDrawable();
			{
				View tab = getTab(mSelectedTab);
				
				TextDrawable textDrawable = mTextDrawables[0];
				
				for (int i = mSelectedTab - 1; i<= mSelectedTab + 1; i++)
				{
					tab = getTab(i);
					if (null != tab)
					{
						int save = canvas.save();
						
						
						CharSequence text = mTabSpecMap.get(tab.getTag()).label;
						textDrawable.setText(text);
						textDrawable.setTextSize(mTabTextSize);
						textDrawable.setTextAlign(Alignment.ALIGN_CENTER);
						textDrawable.setTextColor(Color.RED);
						
						int left = tab.getLeft() + (tab.getWidth() - textDrawable.getIntrinsicWidth()) / 2 + getPaddingLeft();
		                int top = tab.getTop()  + (tab.getHeight() - textDrawable.getIntrinsicHeight()) / 2 + getPaddingTop();
		                textDrawable.setBounds(left, top, textDrawable.getIntrinsicWidth() + left, textDrawable.getIntrinsicHeight() + top);
		                textDrawable.draw(canvas);
		                canvas.restoreToCount(save);
		                
		                Log.e("", "rect=" + textDrawable.getBounds());
					}
				}
				
				
				
//				dleft = dright = 20;
				
//				if (null != tab0)
//				{
//					int save = canvas.save();
//					
//					View tab = tab0;
//					TextDrawable textDrawable = textDrawable0;
//					
//					CharSequence text = mTabSpecMap.get(tab.getTag()).label;
//					textDrawable.setText(text);
//					textDrawable.setTextSize(mTabTextSize);
//					textDrawable.setTextAlign(Alignment.ALIGN_CENTER);
//					textDrawable.setTextColor(Color.RED);
//					
//					int left = textDrawable1.getBounds().left - (tab.getWidth() - textDrawable.getIntrinsicWidth()) / 2 - textDrawable.getIntrinsicWidth() - dleft;
//	                int top = tab.getTop() + (tab.getHeight() - textDrawable.getIntrinsicHeight()) / 2 + getPaddingTop();
//	                textDrawable.setBounds(left, top, textDrawable.getIntrinsicWidth() + left, textDrawable.getIntrinsicHeight() + top);
//	                textDrawable.draw(canvas);
//	                canvas.restoreToCount(save);
//				}
//				
//				if (null != tab2)
//				{
//					int save = canvas.save();
//					
//					View tab = tab2;
//					TextDrawable textDrawable = textDrawable2;
//					
//					CharSequence text = mTabSpecMap.get(tab.getTag()).label;
//					textDrawable.setText(text);
//					textDrawable.setTextSize(mTabTextSize);
//					textDrawable.setTextAlign(Alignment.ALIGN_CENTER);
//					textDrawable.setTextColor(Color.RED);
//					
//					int left = textDrawable1.getBounds().right + (tab.getWidth() - textDrawable.getIntrinsicWidth()) / 2 + dright;
//	                int top = tab.getTop() + (tab.getHeight() - textDrawable.getIntrinsicHeight()) / 2 + getPaddingTop();
//	                textDrawable.setBounds(left, top, textDrawable.getIntrinsicWidth() + left, textDrawable.getIntrinsicHeight() + top);
//	                textDrawable.draw(canvas);
//	                canvas.restoreToCount(save);
//				}
			}
			
			
		}
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
	}

	/**
	 * 
	 */
	private void createTextDrawable() {
		// TODO Auto-generated method stub
		if (null == mTextDrawables)
		{
			mTextDrawables = new TextDrawable[3];
			for (int i = 0; i < mTextDrawables.length; i++)
			{
				mTextDrawables[i] = new TextDrawable(getContext());
			}
		}
	}
	
	@Override
	public void scrollStripFollowViewPager(int position, float positionOffset,
										   int positionOffsetPixels) {
		// TODO Auto-generated method stub
		super.scrollStripFollowViewPager(position, positionOffset, positionOffsetPixels);
		if (mScrollStripEnabled)
		postInvalidate();
	}

	
}
