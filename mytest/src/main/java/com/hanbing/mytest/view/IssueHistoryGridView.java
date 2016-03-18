package com.hanbing.mytest.view;

import java.util.ArrayList;
import java.util.List;

import com.hanbing.mytest.R;
import com.hanbing.mytest.module.IssueItem;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IssueHistoryGridView extends GridView{

	
	public static final int COL_COUNT = 3;
	public static final int ROW_COUNT = 4;
	public static final int PAGE_SIZE = COL_COUNT * ROW_COUNT;
	
	int mCurrentPage = 0;
	int mTotalPage = 0;
	int mColWidth = 0;
	
	int mLastDownY = 0;
	private static final int MIN_DISTANCE = 100;
	
	
	FixSizeAdapter mAdapter;
	
	List<IssueItem> mItemList = new ArrayList<IssueItem>();
	
	IssueHistoryGridView mGridView = this;
	
	OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(IssueItem item) {
			// TODO Auto-generated method stub
			Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
		}
		
	};
	
	
	public IssueHistoryGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public IssueHistoryGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public IssueHistoryGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		init();
	}
	
	
	private void init() {
		// TODO Auto-generated method stub
		setNumColumns(COL_COUNT);
		setBackgroundColor(Color.BLUE);
	}

	public void initValues(int startNumber, int totalCount)
	{
		if (startNumber <= 0 || totalCount <= 0)
			return;
		
		int itemCount = (totalCount + IssueItem.ITEM_SIZE - 1) / IssueItem.ITEM_SIZE;
		
		mItemList.clear();
		
		for (int i = 0; i < itemCount; i++)
		{
			int offset = i * IssueItem.ITEM_SIZE;
			
			IssueItem item = new IssueItem();
			item.startNumber = startNumber + offset;
			item.totalCount = IssueItem.ITEM_SIZE;
			
			if (i == itemCount - 1)
			{
				item.totalCount = totalCount - offset;
			}
			
			mItemList.add(item);
			
		}
		
		
		mTotalPage = (mItemList.size() + PAGE_SIZE - 1 ) / PAGE_SIZE;
		
		mAdapter =  new FixSizeAdapter();
		setAdapter(mAdapter);
	}
	
	public void prev()
	{
		if (mCurrentPage <= 0)
		{
			Toast.makeText(getContext(), "first", Toast.LENGTH_SHORT).show();
			return;
		}
			
		
		mCurrentPage--;
		
		mAdapter.notifyDataSetChanged();
	}
	
	public void next()
	{
		if (mCurrentPage >= mTotalPage - 1)
		{
			Toast.makeText(getContext(), "last", Toast.LENGTH_SHORT).show();
			return;
		}
		
		mCurrentPage++;
		
		mAdapter.notifyDataSetChanged();
	}
	
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		
		switch (ev.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			mLastDownY = y;
			break;
		case MotionEvent.ACTION_UP:
			
			if (Math.abs(y - mLastDownY) > MIN_DISTANCE)
			{
				if (y > mLastDownY)
				{
					prev();
				}
				else if (y < mLastDownY)
				{
					next();
				}
			}
			
			break;
		}
		
		return super.onTouchEvent(ev);
	}
	
	
	class FixSizeAdapter extends BaseAdapter
    {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return (mCurrentPage == mTotalPage - 1)
            		? (mItemList.size() - mCurrentPage * PAGE_SIZE)
            		: PAGE_SIZE;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mItemList.get(position + PAGE_SIZE * mCurrentPage);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
        	
        	System.out.println("areAllItemsEnabled()=" + areAllItemsEnabled());
        	System.out.println("getView=" + position);
        	Holder holder = null;
        	System.out.println("convertView=" + convertView);
        	if (null == convertView)
        	{
        		
        		holder = new Holder();
        		convertView = LayoutInflater.from(getContext())
        				.inflate(R.layout.item_issue_history, null, false);
        		
        		holder.layout = convertView;
        		holder.title = (TextView) convertView.findViewById(R.id.tv_issuehistory_title);
        		
        		convertView.setTag(holder);
        	}
        	else
        	{
        		holder = (Holder) convertView.getTag();
        	}
        	
        	int gridWidth = getWidth();
        	int gridHeight = getHeight();
        	
        	int itemWidth = gridWidth /  COL_COUNT;
        	int itemHeight = gridHeight / ROW_COUNT;
        	
        	
        	AbsListView.LayoutParams lp = new LayoutParams(itemWidth, itemHeight);
        	convertView.setLayoutParams(lp);
        	
        	final IssueItem item = (IssueItem) getItem(position);
        	
        	holder.title.setText(item.getTitle());
        	
        	RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
        			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        	titleParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        	
        	switch (position % COL_COUNT)
        	{
        	case 0:
        		titleParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        		break;
        	case 1:
        		titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        		break;
        	case 2:
        		titleParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        		break;
        	}
        	
        	holder.title.setLayoutParams(titleParams);
        	
        	holder.title.setTag(item);
        	
        	holder.title.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					
					if (event.getAction() == MotionEvent.ACTION_UP)
					{
						if (null != onItemSelectedListener)
						{
							onItemSelectedListener.onItemSelected(item);
						}
					}
					
					return true;
				}
			});
        	
        	int color = Color.YELLOW;
        	
        	switch (position % 3)
        	{
        	case 0:
        		color = Color.WHITE;
        		break;
        	case 1:
        		color = Color.YELLOW;
        		break;
        	case 2:
        		color = Color.GREEN;
        		break;
        	
        	}
        	
        	convertView.setBackgroundColor(color);
        	
        	return convertView;
        	
        }
        
        class Holder
        {
        	public View layout;
        	public TextView title;
        }
        
    }



	
	public interface OnItemSelectedListener
	{
		public abstract void onItemSelected(IssueItem item);
	}

}
