package com.hanbing.library.android.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by hanbing on 2016/7/27
 */
public class DrawableTextView extends TextView {

    protected int mDrawableSize;

    public DrawableTextView(Context context) {
        super(context);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setDrawableSize();
    }

    private void setDrawableSize() {

        Paint paint = getPaint();
        int size = (int) (paint.getFontMetrics().bottom - paint.getFontMetrics().top);

        mDrawableSize = size;

        Drawable[] compoundDrawables = getCompoundDrawables();

        boolean update = false;
        if (null != compoundDrawables) {
            for (Drawable drawable : compoundDrawables) {
                if (null != drawable) {

                    if (drawable.getBounds().width() != mDrawableSize) {
                        update = true;
                    }
                    drawable.setBounds(0, 0, mDrawableSize, mDrawableSize);
                }
            }

            if (update)
                setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
        }

    }


    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        setDrawableSize();
    }
}
