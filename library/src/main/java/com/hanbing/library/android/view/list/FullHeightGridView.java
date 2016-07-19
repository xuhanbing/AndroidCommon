/**
 * 
 */
package com.hanbing.library.android.view.list;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListView;

/**
 *
 */
public class FullHeightGridView extends GridView {


	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public FullHeightGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public FullHeightGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	/**
	 *
	 * @param context
	 * @param attrs
	 */
	public FullHeightGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public FullHeightGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE / 2, MeasureSpec.AT_MOST));
	}
	
}
