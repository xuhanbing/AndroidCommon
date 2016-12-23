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
public class DrawableTextView extends TextView {

    public static class DrawableResizeHelper {
        //Force set width equals height, default is flase
        boolean mForceSquare;
        TextView mTextView;

        public DrawableResizeHelper(TextView textView) {
            mTextView = textView;
        }

        public DrawableResizeHelper(TextView textView, boolean forceSquare) {
            mForceSquare = forceSquare;
            mTextView = textView;
        }

        public void resize() {

            TextView textView = mTextView;
            if (null ==textView)
            return;

            Paint paint = textView.getPaint();
            int size = (int) (paint.getFontMetrics().bottom - paint.getFontMetrics().top);

            Drawable[] compoundDrawables = textView.getCompoundDrawables();

            boolean update = false;
            if (null != compoundDrawables) {
                for (Drawable drawable : compoundDrawables) {
                    if (null != drawable) {

                        if (drawable.getBounds().height() != size) {
                            update = true;
                        }
                        int width = drawable.getIntrinsicWidth();
                        int height = drawable.getIntrinsicHeight();

                        if (!mForceSquare && width > 0 && height > 0) {
                            float ratio = width * 1.0f / height;
                            width = (int) (size * ratio);
                            height = size;
                        } else {
                            width = height = size;
                        }
                        drawable.setBounds(0, 0, width, height);
                    }
                }

                if (update)
                    textView.setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
            }
        }
    }


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
            mDrawableResizeHelper = new DrawableResizeHelper(this, isDrawableForceSquare());

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

}
