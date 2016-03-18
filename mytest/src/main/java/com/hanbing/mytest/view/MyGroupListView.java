/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��3��6�� 
 * Time : ����3:11:00
 */
package com.hanbing.mytest.view;

import com.hanbing.mytest.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


/**
 * ����listview 
 * @author hanbing 
 * @date 2014��3��6�� 
 * @time ����3:11:00
 */
public class MyGroupListView extends ExpandableListView {

    View mCurTop; //��ǰ����
    View mPreTop;//��һ������
    View mNextTop;//��һ������
    
    LinearLayout mLayoutTop ;
    int mLayoutWidth = 0;
    int mLayoutHeight = 0;
    
    int mCurGroupIndex = 0; //��ǰ����index
    boolean mIsScrollUp = true; //�Ϲ������¹�
    
    LayoutInflater mInflater;
    
    ExpandableListAdapter mAdapter;
    
    private static final int SCROLL_UP = 0;
    private static final int SCROLL_DOWN = 1;
    
    int mLastDir = -1;
    /**
     * @param context
     */
    public MyGroupListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public MyGroupListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MyGroupListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    
   

   
    
    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        // TODO Auto-generated method stub
        super.setAdapter(adapter);
        mAdapter = adapter;
        
//        if (mCurTop == null)
//        {
//            mCurTop = getTextView(0); 
//            mCurTop.setTag(0);
//            mLayoutTop.addView(mCurTop);
//            resetLayoutTop();
//        }
    }






    boolean mShowTop = false;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) private void init()
    {
        mInflater = ((Activity) this.getContext()).getLayoutInflater();
        mListView = this;
        
        mLayoutTop = (LinearLayout) mInflater.inflate(R.layout.item_group, this, false);
//        mLayoutTop = new LinearLayout(getContext());
//        mLayoutTop.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        mLayoutTop.setOrientation(LinearLayout.VERTICAL);
        
        TextView text = new TextView(getContext());
        text.setText("title");
        text.setBackgroundColor(Color.RED);
        mLayoutTop.addView(text, new LayoutParams(LayoutParams.MATCH_PARENT, 400));
      
        this.setOnScrollListener(mOnScrollLsner);
        
    }
    
    ListView mListView = null;
    
    OnScrollListener mOnScrollLsner = new OnScrollListener()
    {

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // TODO Auto-generated method stub
            
//            if (mAdapter != null)
//            {
////        	View top = mAdapter.getGroupView(0, true, null, view);
//        	TextView text = getTextView(0);
//                mLayoutTop.removeAllViews();
//                mLayoutTop.addView(text, new LayoutParams(LayoutParams.MATCH_PARENT, 200));
//                mLayoutTop.requestLayout();
//                resetLayoutTop();
//            }
//            View subView = view.getChildAt(firstVisibleItem - getFirstVisiblePosition());
//
//           
//            if (mIsScrollUp)
//            {
//                int groupIndex = isGroupItem(firstVisibleItem);
//                if (groupIndex >= 0)
//                {
//                    
//                  //�滻Ϊ��һ����Ҫ�ö���view
//                    if (mNextTop != null)
//                    {
//                        if (mLayoutTop.indexOfChild(mCurTop) > 0)
//                        {
//                            mLayoutTop.removeView(mCurTop);
//                        }
//                        
//                        mCurTop = mNextTop;
//                        mNextTop = null;
//                        resetLayoutTop();
//                    }
//                }
//                
//                int nextItem = mIsScrollUp ? firstVisibleItem+1 : firstVisibleItem-1;
//                groupIndex = isGroupItem(nextItem);
//                //��һ����groupitem
//                if (groupIndex >= 0)
//                {
//                    
//                    View nextSubView = view.getChildAt(nextItem - getFirstVisiblePosition());
//                    if (mNextTop == null)
//                    {
//                        mNextTop = getTextView(groupIndex);
//                        mNextTop.setTag(groupIndex);
//                        if (mLayoutTop.indexOfChild(mNextTop) < 0)
//                            mLayoutTop.addView(mNextTop);
//                        resetLayoutTop();
//                    }
//                    
//                    //��֮ǰ��top������
//                    if (mCurTop != null)
//                    {
//                        if (nextSubView.getTop() <= mCurTop.getTop() + mCurTop.getHeight())
//                        {                          
//                            MarginLayoutParams params = (MarginLayoutParams) mCurTop.getLayoutParams();
//                            params.topMargin =  nextSubView.getTop() - mCurTop.getHeight();
//                            mCurTop.requestLayout();
//                            resetLayoutTop();
//                        }  
//                        
//                    }
//                }
//            }
//            else
//            {
//                
//                int preGroupIndex = getPreGroupItemIndex(firstVisibleItem);
//                int groupIndex = isGroupItem(firstVisibleItem);
//                
//                if (groupIndex >= 0)
//                    return;
//                //�����Լ������ǵ�һ��
//                if (groupIndex != preGroupIndex)
//                {
//                    
//                    if (mNextTop == null)
//                    {
//                        mNextTop = getTextView(preGroupIndex);
//                        mNextTop.setTag(preGroupIndex);
//                        mLayoutTop.addView(mNextTop, 0);
//                        resetLayoutTop();
//                    }
//                    
//                    View nextSubView = view.getChildAt(firstVisibleItem+1 - getFirstVisiblePosition());
//                    //��ǰtopû����ȫ��ʾ����֮ǰ��top���ƣ�����֮ǰ��top�滻
//                   
//                    {
//                        if (mNextTop.getTop() >= nextSubView.getTop() - nextSubView.getHeight())
//                        {                          
//                            MarginLayoutParams params = 
//                                    (MarginLayoutParams) mNextTop.getLayoutParams();
//                            params.topMargin =  nextSubView.getTop() - mNextTop.getHeight();
//                            mNextTop.requestLayout();
//                            
//                        }  
//                        else
//                        {
//                            mLayoutTop.removeView(mCurTop);
//                            
//                            mCurTop = mNextTop;
//                            mNextTop = null;
//                        }
//                        resetLayoutTop();
//                    }
//                   
//                }
//            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // TODO Auto-generated method stub
//            Log.d("xhb", "state:"+scrollState);
        }
        
    }; 
    
    /**
     * �ж��Ƿ���group��item
     * @param position
     * @return >0 ���,-1��ʾ����
     */
    private int isGroupItem(int position)
    {
        
        if (null != mAdapter)
        {
            int groupCount = mAdapter.getGroupCount();
            
            int totalCount = 0;
            int childCount = 0;
            for (int i = 0; i < groupCount; i++)
            {
                Log.d("xhb", "totalcount:"+totalCount);
                if (totalCount == position)
                {
                    Log.d("xhb", "pos:"+position +" is group : " + i);
                    return i;
                }
                childCount = mAdapter.getChildrenCount(i);
                totalCount += (childCount+1);
                
                if (totalCount > position)
                {
                    break;
                }
            }
        }
        
        Log.d("xhb", "pos:"+position +" isnot group");
        return -1;
    }
    
    /**
     * �����һ��groupitem������
     * @param position
     * @return
     */
    private int getPreGroupItemIndex(int position)
    {
        if (null != mAdapter)
        {
            int groupCount = mAdapter.getGroupCount();
            
            int totalCount = 0;
            int childCount = 0;
            for (int i = 0; i < groupCount; i++)
            {
                childCount = mAdapter.getChildrenCount(i);
                totalCount += (childCount+1);
                
                if (totalCount >= position)
                {
                    return i;
                }
            }
        }
        
        Log.d("xhb", "pos:"+position +" isnot group");
        return -1;
    }
    
    private TextView getTextView(int position)
    {
        
        TextView textView = new TextView(this.getContext());
        textView.setTextSize(100);
        //text.setGravity(Gravity.CENTER);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        
        
        textView.setBackgroundColor(Color.GRAY);
        textView.setText((String)mAdapter.getGroup(position));
        return textView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        if (mLayoutTop != null)
        {
//            mLayoutTop.measure(mLayoutTop.getMeasuredWidth(), 
//                               mLayoutTop.getMeasuredHeight());
            this.measureChild(mLayoutTop, 
                              widthMeasureSpec, 
                              heightMeasureSpec);
            mLayoutWidth = mLayoutTop.getMeasuredWidth();
            mLayoutHeight = mLayoutTop.getMeasuredHeight();
            
            Log.e("xhb", "mesure width:"+mLayoutWidth + ",height:"+mLayoutHeight
        	    + ",child count:" + mLayoutTop.getChildCount());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
        
        if (mLayoutTop != null)
        {
            mLayoutTop.layout(l, 50, mLayoutWidth, 
                              mLayoutHeight);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.dispatchDraw(canvas);
        
        if (mLayoutTop != null)
        {
            this.drawChild(canvas, mLayoutTop, this.getDrawingTime());
        } 
    }
    
    
    private void resetLayoutTop()
    {
       this.requestLayout();
       this.invalidate();
    }

    float mLastDownY = 0.0f;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mLastDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //�ϴ�y������ڵ�ǰy���꣬��ʾ���Ϲ���
                mIsScrollUp = mLastDownY - ev.getY() >= 0;
                mLastDownY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                mLastDownY = 0.0f;
                break;
        }
        
        return super.onTouchEvent(ev);
    }
    
    
    
}
