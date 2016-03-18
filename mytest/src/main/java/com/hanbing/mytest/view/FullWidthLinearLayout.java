/**
 * 
 */
package com.hanbing.mytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author hanbing
 * @date 2015-9-16
 */
public class FullWidthLinearLayout extends LinearLayout
{

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public FullWidthLinearLayout(Context context, AttributeSet attrs,
	    int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public FullWidthLinearLayout(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param context
     */
    public FullWidthLinearLayout(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
    }
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
	widthMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE / 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
