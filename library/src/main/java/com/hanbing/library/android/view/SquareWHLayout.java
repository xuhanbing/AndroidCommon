package com.hanbing.library.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * width=height
 * Created by hanbing on 2016/3/23.
 */
public class SquareWHLayout extends FrameLayout {

    public SquareWHLayout(Context context) {
        super(context);
    }

    public SquareWHLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareWHLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
