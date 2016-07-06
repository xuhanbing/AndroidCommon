package com.common.view.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hanbing on 2016/6/12.
 */
public  class WrapContentViewPager extends DisallowInterruptViewPager {

    public WrapContentViewPager(Context context) {
        super(context);
    }

    public WrapContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int childCount = getChildCount();

        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE/2, MeasureSpec.AT_MOST));

            height = Math.max(height, child.getMeasuredHeight());
        }

        height += (getPaddingTop() + getPaddingBottom());

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}