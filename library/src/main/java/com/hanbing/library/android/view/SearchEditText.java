package com.hanbing.library.android.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * Created by hanbing on 2016/6/15.
 */
public class SearchEditText extends ClearableEditText {
    public SearchEditText(Context context) {
        super(context);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int contentHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        Drawable left = getCompoundDrawables()[0];

    }


}
