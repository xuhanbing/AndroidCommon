/**
 * 
 */
package com.hanbing.mytest.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author hanbing
 * @date 2015-8-20
 */
public class TabViewPager2 extends RelativeLayout implements ViewPager.OnPageChangeListener{

    
    ViewPager mViewPager;
    
    LinearLayout mTabLayout;
    RelativeLayout mIndicatorLayout;
    
    View mIndicator = null;
    int mIndicatorHeight = 2;
    int mIndicatorWidth = 10;
    int mIndicatorColor = Color.WHITE;
    
    int mTabGravity = Gravity.TOP;
    boolean mShowIndicator = false;
    boolean mScrollIndicator = false;
    
    ViewPager.OnPageChangeListener mOnPageChangeListener = null;
    /**
     * @param context
     */
    public TabViewPager2(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
	init();
    }

    /**
     * @param context
     * @param attrs
     */
    public TabViewPager2(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
	init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TabViewPager2(Context context, AttributeSet attrs, int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	// TODO Auto-generated constructor stub
	init();
    }
    
    
    public ViewPager getViewPager()
    {
	return mViewPager;
    }
    
    void init()
    {
	
	inflate(getContext(), R.layout.layout_tabviewpager, this);
	
	mViewPager = (ViewPager) findViewById(R.id.viewpager);
	mTabLayout = (LinearLayout) findViewById(R.id.layout_tab);
	mIndicatorLayout = (RelativeLayout) findViewById(R.id.layout_indicator);
	mIndicator = findViewById(R.id.indicator);
	
	mViewPager.setBackgroundColor(Color.RED);
	mTabLayout.setBackgroundColor(Color.YELLOW);
	mIndicatorLayout.setBackgroundColor(Color.BLUE);
	
	
//	RelativeLayout.LayoutParams params0 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
//		LayoutParams.MATCH_PARENT);
//	RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
//		LayoutParams.WRAP_CONTENT);
//	RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
//		LayoutParams.WRAP_CONTENT);
//	
//	
//	int align = 0;
//	if (mTabGravity == Gravity.TOP)
//	{
//	    params1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//	    
//	    align = RelativeLayout.ALIGN_BOTTOM;
//	    
//	}
//	else
//	{
//	    params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//	    
//	    align = RelativeLayout.ALIGN_TOP;
//	}
//	
//	/**
//	 * չʾָʾ��
//	 */
//	if (mShowIndicator) {
//	    params2.addRule(align, mTabLayout.getId());
//	    params0.addRule(align, mIndicatorLayout.getId());
//	    addView(mIndicatorLayout, params2);
//	    
//	    mIndicator = new View(getContext());
//	    mIndicator.setBackgroundColor(mIndicatorColor);
//	    mIndicator.setLayoutParams(new LayoutParams(100, mIndicatorHeight));
//	    mIndicatorLayout.addView(mIndicator);
//	} else {
//	    params0.addRule(align, mTabLayout.getId());
//	}
	    
	
	TextView text = new TextView(getContext());
	text.setText("hhhh");
	mTabLayout.addView(text);
	
    }
    
    public void addTab(View tab)
    {
	
	if (null == mTabLayout)
	    return;
	
	if (null != tab)
	{
	    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
	    params.weight = 1;
	    mTabLayout.addView(tab, params);
	}
	else
	{
	    throw new NullPointerException("null == tab");
	}
    }
    
    
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener)
    {
	this.mOnPageChangeListener = onPageChangeListener;
	
	if (null != mViewPager)
	{
	    mViewPager.setOnPageChangeListener(this);
	}
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
	// TODO Auto-generated method stub
	if (null != mOnPageChangeListener)
	{
	    mOnPageChangeListener.onPageScrollStateChanged(arg0);
	}
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
	// TODO Auto-generated method stub
	if (null != mOnPageChangeListener)
	{
	    mOnPageChangeListener.onPageScrolled(arg0, arg1, arg2);
	}
    }

    @Override
    public void onPageSelected(int arg0) {
	// TODO Auto-generated method stub
	
	if (null != mTabLayout)
	{
	    for (int i = 0; i < mTabLayout.getChildCount(); i++)
	    {
		mTabLayout.getChildAt(arg0).setSelected(i == arg0);
	    }
	}
	
	if (null != mOnPageChangeListener)
	{
	    mOnPageChangeListener.onPageSelected(arg0);
	}
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        if (null != mTabLayout && mShowIndicator)
        {
            
            int count = mTabLayout.getChildCount();
            if (count > 0)
            {
        	mIndicatorWidth =  mTabLayout.getMeasuredWidth() / count;
                
                RelativeLayout.LayoutParams params = (LayoutParams) mIndicator.getLayoutParams();
                params.width = mIndicatorWidth;
                params.height = mIndicatorHeight;
                mIndicator.setLayoutParams(params);
                
                mIndicator.requestLayout();
            }
            
        }
    }
}
