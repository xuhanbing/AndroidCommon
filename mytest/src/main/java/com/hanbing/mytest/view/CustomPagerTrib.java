/**
 * 
 */
package com.hanbing.mytest.view;

import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author hanbing
 * @date 2015-7-8
 */
public class CustomPagerTrib extends RelativeLayout implements OnPageChangeListener{

    
    LinearLayout mTabs;
    View mLine;
    
    boolean mShowLine = true;
    int mLineHeight = 100;
    
    LayoutParams mParams;
    
    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomPagerTrib(Context context, AttributeSet attrs, int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	// TODO Auto-generated constructor stub
	init();
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomPagerTrib(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
	init();
    }

    /**
     * @param context
     */
    public CustomPagerTrib(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
	init();
    }

    void init()
    {
	View v = inflate(getContext(), R.layout.layout_viewpager_trib, null);
	
	mTabs = (LinearLayout) v.findViewById(R.id.tabs);
	mLine = v.findViewById(R.id.line);
//	mLine.setVisibility(GONE);
//	mParams = new LayoutParams(LayoutParams.WRAP_CONTENT, mLineHeight);
	
	addView(v);
    }
    
    /* (non-Javadoc)
     * @see android.widget.LinearLayout#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    /* (non-Javadoc)
     * @see android.widget.RelativeLayout#onLayout(boolean, int, int, int, int)
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
        
        if (changed)
        {
            
        }
    }
    
    public void showLine(boolean show)
    {
	mShowLine = show;
	
	mLine.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    
    public void addTab(View child)
    {
	addTab(child, null);
    }
    
    public void addTab(View child, LayoutParams params)
    {
	if (null == mTabs)
	    return;
	
	if (null == params)
	{
	    params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}
	
	android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(params);
	lp.width = 0;
	lp.weight = 1;
	
	mTabs.addView(child, lp);
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
     */
    @Override
    public void onPageScrollStateChanged(int arg0) {
	// TODO Auto-generated method stub
	
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
     */
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
	// TODO Auto-generated method stub
	
	Log.e("", "onPageScrolled arg0=" + arg0 + ",arg1=" + arg1 + ",arg2=" + arg2);
	
//	mLine.setVisibility(View.VISIBLE);
//	mParams.width = mTabs.getChildAt(0).getWidth();
//	mLine.setLayoutParams(mParams);
//	
//	mLine.layout(mParams.width * arg0, 0, mParams.width, mParams.height);
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
     */
    @Override
    public void onPageSelected(int arg0) {
	// TODO Auto-generated method stub
	
    }


}
