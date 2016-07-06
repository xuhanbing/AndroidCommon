/**
 * 
 */
package com.common.view.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * @author hanbing
 * @date 2016年1月14日
 */
public class FullHeightExpandableListView extends ExpandableListView {


	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public FullHeightExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public FullHeightExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public FullHeightExpandableListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
	 * @see android.widget.ListView#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE / 2, MeasureSpec.AT_MOST));
	}
	
}
