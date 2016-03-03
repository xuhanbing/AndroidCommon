/**
 * 
 */
package com.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

/**
 * pinned list view
 * @author hanbing
 * @date 2015-8-11
 */
public class PinnedSectionListView extends ListView implements OnScrollListener{
    
    /**
     * adapter that sub class my extends
     * @author hanbing
     * @date 2015-9-15
     */
    public static abstract class PinnedSectionAdapter extends BaseAdapter
    {
	/**
	 * create pinned view
	 * @param position current item postion
	 * @return
	 */
	public abstract View createPinned(int position);
	
	/**
	 * check is current position pinned or not
	 * @param position
	 * @return
	 */
	public abstract boolean isPinnedSection(int position);
	
	/**
	 * config pinned view
	 * @param pinnedView
	 * @param position
	 */
	public abstract void configurePinned(View pinnedView, int position);
    }

    
    /**
     * do not show pinned view
     */
    static final int STATUS_GONE = 0;
    
    /**
     * pinned view move
     */
    static final int STATUS_MOVE = 1;
    
    /**
     * pinned view show
     */
    static final int STATUS_VISIBLE = 2;
    
    /**
     * current status
     */
    int mStatus = STATUS_GONE;
    
    
    /**
     * pinned view
     */
    View mPinnedView = null;
    
    /**
     * pinned view width
     */
    int mPinnedViewWidth;
    
    /**
     * pinned view height
     */
    int mPinnedViewHeight;
    
    /**
     * custom OnScrollListener
     */
    OnScrollListener mOnScrollLisntener;
    
    /**
     * draw pinned view or not
     */
    boolean mDrawPinnedView = true;
    
    /**
     * real adapter
     */
    PinnedSectionAdapter mAdapter;
    
    /**
     * @param context
     */
    public PinnedSectionListView(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
	init();
    }

    /**
     * @param context
     * @param attrs
     */
    public PinnedSectionListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
	init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public PinnedSectionListView(Context context, AttributeSet attrs,
	    int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	// TODO Auto-generated constructor stub
	init();
    }


    /**
     * init 
     */
    void init()
    {
	/**
	 * default 
	 */
	mPinnedView = new LinearLayout(getContext());
	mPinnedView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	
	setOnScrollListener(new OnScrollListener() {
	    
	    @Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	    }
	    
	    @Override
	    public void onScroll(AbsListView view, int firstVisibleItem,
		    int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	    }
	});
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        if (null != mPinnedView)
        {
            measureChild(mPinnedView, widthMeasureSpec, heightMeasureSpec);
            
            mPinnedViewWidth = mPinnedView.getMeasuredWidth();
            mPinnedViewHeight = mPinnedView.getMeasuredHeight();
        }
        
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
        
        if (null != mPinnedView)
        {
            mPinnedView.layout(0, 0, mPinnedViewWidth, mPinnedViewHeight);
            mAdapter.configurePinned(mPinnedView, getFirstVisiblePosition());
        }
    }
    
    @Override
    protected void dispatchDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.dispatchDraw(canvas);
        
        if (null != mPinnedView &&  mDrawPinnedView)
        drawChild(canvas, mPinnedView, getDrawingTime());
        
    }
    
    @Override
    public void setOnScrollListener(OnScrollListener l) {
        // TODO Auto-generated method stub
//        super.setOnScrollListener(l);
	mOnScrollLisntener = l;
	super.setOnScrollListener(this);
    }
    
    
    
    @Override
    public void setAdapter(ListAdapter adapter) {
        // TODO Auto-generated method stub
	this.mAdapter = (PinnedSectionAdapter) adapter;
        super.setAdapter(adapter);
    }
    
    /**
     * set pinned view
     * @param view
     */
    public void setPinnedView(View view)
    {
	this.mPinnedView = view;
	
	requestLayout();
    }
    
    /**
     * control show
     * @param position
     */
    private void control(int position)
    {
	if (null == mPinnedView)
	    return;
	
	switch (mStatus)
	{
	case STATUS_GONE:
	{
	    mDrawPinnedView = false;
	}
	    break;
	case STATUS_MOVE:
	{
	    View topItem = getChildAt(0);
	    
	    int bottom = topItem.getBottom();
	    int height = mPinnedView.getHeight();
	    int y = 0;
	    if (bottom < height)
	    {
		y = bottom - height;
	    }
	    else
	    {
		y = 0;
	    }
	    
	    mDrawPinnedView = true;
	    mAdapter.configurePinned(mPinnedView, position);
	    if (mPinnedView.getTop() != y)
		mPinnedView.layout(0, y, mPinnedViewWidth, mPinnedViewHeight + y);
	    
	}
	    break;
	case STATUS_VISIBLE:
	{
	    mDrawPinnedView = true;
	    mAdapter.configurePinned(mPinnedView, position);
	    mPinnedView.layout(0, 0, mPinnedViewWidth, mPinnedViewHeight);
	}
	    break;
	}
    }

    
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
	// TODO Auto-generated method stub
	
	mOnScrollLisntener.onScrollStateChanged(view, scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
	    int visibleItemCount, int totalItemCount) {
	// TODO Auto-generated method stub

	if (getCount() <= 0)
	{
	    mStatus = STATUS_GONE;
	}
	else if (mAdapter.isPinnedSection(firstVisibleItem))
	{
	    mStatus = STATUS_VISIBLE;
	}
	else if ((firstVisibleItem + 1) < totalItemCount && mAdapter.isPinnedSection(firstVisibleItem + 1))
	{
	    mStatus = STATUS_MOVE;
	}
	else
	{
	    mStatus = STATUS_VISIBLE;
	}
	Log.e("", "first=" + firstVisibleItem + ",status=" + mStatus);
	control(firstVisibleItem);
	
	mOnScrollLisntener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }
    
}
