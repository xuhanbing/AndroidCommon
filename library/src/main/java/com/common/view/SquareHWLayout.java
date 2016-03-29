package com.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * height=width
 * Created by hanbing on 2016/3/23.
 */
public class SquareHWLayout extends FrameLayout {

    public SquareHWLayout(Context context) {
        super(context);
    }

    public SquareHWLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareHWLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
