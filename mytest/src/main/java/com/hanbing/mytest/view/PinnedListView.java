package com.hanbing.mytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class PinnedListView extends ListView {
	
	public interface PinnedAdapterListener
	{
		public boolean isItemPinned(int position);
	}
	
	
	class PinnedItem
	{
		View view;
		int position;
	}
	
	PinnedItem mCurPinnedItem;
	PinnedItem mNextPinnedItem;

	public PinnedListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public PinnedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PinnedListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	OnScrollListener mOnScrollListener = null;
	OnScrollListener mSelfOnScrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
			if (null != mOnScrollListener)
			{
				mOnScrollListener.onScrollStateChanged(view, scrollState);
			}
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			
			int pinnedItemPosition = -1;
			
			if (null == mCurPinnedItem)
			{
				if (isPinnedItem(0))
				{
					pinnedItemPosition = 0;
				}
			}
			else
			{
				pinnedItemPosition = findNextPinnedPosition(firstVisibleItem, visibleItemCount);
			}
			
			if (pinnedItemPosition > -1)
			{
				if (null == mCurPinnedItem)
				{
					mCurPinnedItem = new PinnedItem();
				}
				
				mCurPinnedItem.position = pinnedItemPosition;
				mCurPinnedItem.view = createPinnedView(pinnedItemPosition);
			}
			
			//prev
			if (firstVisibleItem == pinnedItemPosition - 1)
			{
				int prevPinnedItemPosition = findPrevPinnedPosition(firstVisibleItem, visibleItemCount);
				
				if (prevPinnedItemPosition > -1)
				{
					if (null == mNextPinnedItem)
					{
						mNextPinnedItem = new PinnedItem();
					}
					
					mNextPinnedItem.position = prevPinnedItemPosition;
					mNextPinnedItem.view = createPinnedView(prevPinnedItemPosition);
				}
						
			}
			
			
			if (null != mOnScrollListener)
			{
				mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		}
	};
	
	private View createPinnedView(int position)
	{
		return getAdapter().getView(position, null, this);
	}
	
	private int findPrevPinnedPosition(int firstVisibleItem,
			int visibleItemCount)
	{
		int currPinnedPosition = 0;
		
		if (null != mCurPinnedItem)
		{
			currPinnedPosition = mCurPinnedItem.position;
		}
		
		int position = firstVisibleItem + visibleItemCount;
		
		for (int i = currPinnedPosition-1; i >= 0; i--)
		{
			if (isPinnedItem(i))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	private int findNextPinnedPosition(int firstVisibleItem,
			int visibleItemCount)
	{
		int currPinnedPosition = 0;
		
		if (null != mCurPinnedItem)
		{
			currPinnedPosition = mCurPinnedItem.position;
		}
		
		int position = firstVisibleItem + visibleItemCount;
		
		for (int i = currPinnedPosition + 1; i < position; i++)
		{
			if (isPinnedItem(i))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	
	private boolean isPinnedItem(int position)
	{
		ListAdapter adapter = getAdapter();
		
		if (adapter instanceof PinnedAdapterListener)
		{
			PinnedAdapterListener pa = (PinnedAdapterListener) adapter;
			
			return pa.isItemPinned(position);
		}
		
		return false;
	}
	
	
	
	public void setOnScrollListener(OnScrollListener l) {
		this.mOnScrollListener = l;
		super.setOnScrollListener(mSelfOnScrollListener);
	};
	

}
