/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��7��17�� 
 * Time : ����11:55:25
 */
package com.hanbing.mytest.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

import com.hanbing.mytest.listener.OnPageChangedListener;

import java.util.HashMap;

/**
 * aaa.java 
 * @author hanbing 
 * @date 2014��7��17�� 
 * @time ����11:55:25
 */
public class PageTextView extends TextView {
    
	
	static final int STEP_BEGIN = 0;
	static final int STEP_CALC = 1;
	static final int STEP_END = 2;
	
	long mStartTime = 0;
	
	Handler mHandler = new Handler()
	{
    	
		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what)
			{
			case STEP_BEGIN:
				mStartTime = System.currentTimeMillis();
				sendEmptyMessage(STEP_CALC);
				break;
			case STEP_CALC:
			{
				int count = getCharNum();
				int curPage = mTotalPage;
				PositionInfo lastInfo  = mPageHashMap.get(curPage-1);
		    	PositionInfo curInfo  = mPageHashMap.get(curPage);
		    	
		    	
		    	if (curInfo == null)
		    	{
		    		curInfo = new PositionInfo();
		    		if (curPage == 0)
		    		{
		    			curInfo.start = 0;
		    		}
		    		else
		    		{
		    			curInfo.start = lastInfo.getNextStart();
		    		}
		    		
		    		curInfo.count = count;
		    		mPageHashMap.put(curPage, curInfo);
		    		
		    	}
		    	
		    	PositionInfo nextInfo  = mPageHashMap.get(curPage +1);
				
				if (nextInfo == null)
				{
					nextInfo = new PositionInfo();
					nextInfo.start = curInfo.getNextStart();
				}
		    	
				//���һҳ
				if (nextInfo.start >= mContent.length())
				{
					sendEmptyMessage(STEP_END);
				}
				else
				{
					mTotalPage++;
					
					int start = nextInfo.start;
					int len = mColCount * mLines;
					int end = start + Math.min(len, mContent.length() - start);
					
					String string = mContent.substring(start, end);
					setText(string);
					
					nextInfo.count = getCharNum();
					mPageHashMap.put(mTotalPage, nextInfo);
					
					Message message = mHandler.obtainMessage(STEP_CALC, nextInfo);
					sendMessage(message);
				}
				
			}
				break;
			case STEP_END:
			{
				mTotalPage++;
				long cost = System.currentTimeMillis() - mStartTime;
				System.out.println("calc finish totalpage=" + mTotalPage + ",cost=" + cost);
				
				if (onCalculateFinishListener != null)
				{
					onCalculateFinishListener.onCalculateFinish();
				}
			}
				break;
			}
		}
		
	};
	
    int mLines = 20;
    public float mTextSize = 0;
    int mColCount = 50;
    
    String filePath;
    
    /**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	String mContent = "";
    
    public void setContent(String content)
    {
    	mContent = content;
    }
    
    OnPageChangedListener onPageChangedListener;
    OnCalculateFinishListener onCalculateFinishListener;
    
    
    /**
	 * @return the onCalculateFinishListener
	 */
	public OnCalculateFinishListener getOnCalculateFinishListener() {
		return onCalculateFinishListener;
	}

	/**
	 * @param onCalculateFinishListener the onCalculateFinishListener to set
	 */
	public void setOnCalculateFinishListener(
			OnCalculateFinishListener onCalculateFinishListener) {
		this.onCalculateFinishListener = onCalculateFinishListener;
	}

	/**
	 * @return the onPageChangedListener
	 */
	public OnPageChangedListener getOnPageChangedListener() {
		return onPageChangedListener;
	}

	/**
	 * @param onPageChangedListener the onPageChangedListener to set
	 */
	public void setOnPageChangedListener(OnPageChangedListener onPageChangedListener) {
		this.onPageChangedListener = onPageChangedListener;
	}

	HashMap<Integer, PositionInfo> mPageHashMap = new HashMap<Integer, PositionInfo>();
    int mCurPage = 0;
    int mTotalPage = 0;
    
    public void setTotalPage(int totalPage)
    {
    	mTotalPage = totalPage;
    	
    	pageChanged();
    }
    
    public int getTotalPage()
    {
    	return mTotalPage;
    }
    
    class PositionInfo
    {
    	int start;
    	int count;
    	
    	public int getNextStart()
    	{
    		return start + count;
    	}
    	
    	public PositionInfo()
    	{
    	}
    	
    	public PositionInfo(int start, int count)
    	{
    		this.start = start;
    		this.count = count;
    	}
    }
    
    
    /**
     * @param context
     */
    public PageTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        
        init();
    }
    
    /**
     * @param context
     * @param attrs
     */
    public PageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public PageTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }
    
    
 
    private void init() {
		// TODO Auto-generated method stub
    	Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/hyzsf.TTF");
		setTypeface(tf);
	}

	@Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        
        if (changed)
        {
            
            int w = right - left - getPaddingLeft() - getPaddingRight();
            int h = bottom - top - getPaddingTop() - getPaddingBottom();
            
            float size = h * 0.8f / mLines ;
            
            int count = (int) (w / size);
            
            System.out.println("count=" + count);
            
            
            DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
            
            size /= dm.scaledDensity ;
            
            
            mTextSize = size;
            
            setLines(mLines);
            setTextSize(size);
            
            System.out.println("onlayout, changed=" + changed + " " + left + "," + top + "-" + right + "," + bottom);
            
        }
        
    }
 
    /**
     * ȥ����ǰҳ�޷���ʾ����
     * @return ȥ��������
     */
    public int resize() {
        CharSequence oldContent = getText();
        CharSequence newContent = oldContent.subSequence(0, getCharNum());
        setText(newContent);
        return oldContent.length() - newContent.length();
    }
 
    /**
     * ��ȡ��ǰҳ������
     */
    public int getCharNum() {
    	
    	getAllLineCount();
        return getLayout().getLineEnd(getLineNum());
    }
    
    public void getAllLineCount()
    {
    	for (int i = 0; i <= getLineNum(); i ++)
    	{
    		System.out.println("line " + i + ",count=" + getLayout().getLineEnd(i));
    	}
    }
    
    
    /**
     * ��ȡ��ǰҳ������
     */
    public int getLineNum() {
        Layout layout = getLayout();
        int topOfLastLine = getHeight() - getPaddingTop() - getPaddingBottom() - getLineHeight();
        return layout.getLineForVertical(topOfLastLine);
    }
    
    
    public void prev()
    {
    	PositionInfo curInfo = mPageHashMap.get(mCurPage);
    	
    	
    	
    	if (null == curInfo)
    	{
    		curInfo = new PositionInfo();
    		curInfo.start = 0;
    		curInfo.count = getCharNum();
    	}
    	
    	if (mCurPage == 0)
    	{
    		Toast.makeText(getContext(), "FirstPage", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	
    	mCurPage--;
    	
    	PositionInfo lastInfo = mPageHashMap.get(mCurPage);
    	
    	setText(mContent.substring(lastInfo.start));
    	
    	System.out.println("page=" + mCurPage + ",count=" + getCharNum());
    	
    	pageChanged();
    }
    
    public void next()
    {

//    	calculatePages();
    	
    	if (!goNextPage(mCurPage))
    	{
    		Toast.makeText(getContext(), "LastPage", Toast.LENGTH_SHORT).show();
    		
    		return;
    	}
    	
    	mCurPage++;
    	
    	System.out.println("page=" + mCurPage + ",count=" + getCharNum());
    	
    	pageChanged();
    }
    
    
    private boolean goNextPage(int curPage)
    {
    	int count = getCharNum();
    	
    	PositionInfo lastInfo  = mPageHashMap.get(curPage-1);
    	PositionInfo curInfo  = mPageHashMap.get(curPage);
    	
    	
    	if (curInfo == null)
    	{
    		curInfo = new PositionInfo();
    		if (curPage == 0)
    		{
    			curInfo.start = 0;
    		}
    		else
    		{
    			curInfo.start = lastInfo.getNextStart();
    		}
    		
    		curInfo.count =count;
    		mPageHashMap.put(curPage, curInfo);
    		
    	}
		
    	
    	PositionInfo nextInfo  = mPageHashMap.get(curPage +1);
		
		if (nextInfo == null)
		{
			nextInfo = new PositionInfo();
			nextInfo.start = curInfo.getNextStart();
		}
    	
		//���һҳ
		if (nextInfo.start >= mContent.length())
		{
//			Toast.makeText(getContext(), "LastPage", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		curPage++;
    	
		setText(mContent.substring(nextInfo.start));
    	
    	nextInfo.count = getCharNum();
    	
    	mPageHashMap.put(curPage, nextInfo);
    	
    	return true;
    }
    
    
    
	private void pageChanged() {
		// TODO Auto-generated method stub
		
		if (null != onPageChangedListener)
		{
			onPageChangedListener.onPageChanged(mCurPage+1, mTotalPage);
		}
	}
	
	Handler handler = new Handler();
	
	/**
	 * ����ҳ��
	 * ֻ����һ��
	 */
	public void calculatePages()
	{
		if (mTotalPage > 0)
		{
			return;
		}
		
		
		int index = 0;
		
		while (goNextPage(index))
		{
			index++;
		}
		
		mTotalPage = index + 1;
		
		System.out.println("total page=" + mTotalPage);
		
		setText(mContent);
		
		pageChanged();
	}
    
	
    public void calcPages()
    {
    	mHandler.sendEmptyMessage(STEP_BEGIN);
    }
    
    public interface OnCalculateFinishListener
    {
    	public abstract void onCalculateFinish();
    }
    
}
