package com.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.common.util.LogUtils;
import com.common.view.ClearableEditText;

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
