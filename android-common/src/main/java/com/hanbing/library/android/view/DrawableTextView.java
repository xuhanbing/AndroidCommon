package com.hanbing.library.android.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A TextView that will resize drawable base on height.
 * Created by hanbing on 2016/7/27
 */
public class DrawableTextView extends TextView implements DrawableResizeHelper.Fetcher{

    DrawableResizeHelper mDrawableResizeHelper;

    public DrawableTextView(Context context) {
        super(context);
        init();
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setDrawableSize();
    }

    private void setDrawableSize() {

        if (null == mDrawableResizeHelper)
            mDrawableResizeHelper = new DrawableResizeHelper(this, this, isDrawableForceSquare());

        mDrawableResizeHelper.resize();
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        setDrawableSize();
    }

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        setDrawableSize();
    }


    protected boolean isDrawableForceSquare() {
        return true;
    }

    @Override
    public int getDrawableSize(Drawable drawable) {
        return 0;
    }
}
