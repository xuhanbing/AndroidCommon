package com.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 正方形布局
 * 如果父容器的宽高长度不确定，可以使用{@link SquareWHLayout}或{@link SquareHWLayout}替代
 * Created by hanbing on 2016/3/23.
 */
public class SquareLayout extends FrameLayout {

    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSize > 0 && 0 == heightSize
                && MeasureSpec.EXACTLY == widthMode
                && MeasureSpec.UNSPECIFIED == heightMode )
        {

            heightMeasureSpec = widthMeasureSpec;
        }
        else if (0 == widthSize && heightSize > 0
                && MeasureSpec.UNSPECIFIED == widthMode
                && MeasureSpec.EXACTLY == heightMeasureSpec)
        {
            widthMeasureSpec = heightMeasureSpec;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
