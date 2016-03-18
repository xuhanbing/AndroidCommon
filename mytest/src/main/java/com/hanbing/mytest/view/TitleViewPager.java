/**
 * 
 */
package com.hanbing.mytest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author hanbing
 * @date 2015-7-7
 */
public class TitleViewPager extends ViewPager {

    LinearLayout titles = null;
    /**
     * @param context
     * @param attrs
     */
    public TitleViewPager(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
	init();
    }

    /**
     * @param context
     */
    public TitleViewPager(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
	init();
    }

    void init()
    {
    }
    
    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas arg0) {
        // TODO Auto-generated method stub
        super.onDraw(arg0);
        
    }
    
}
