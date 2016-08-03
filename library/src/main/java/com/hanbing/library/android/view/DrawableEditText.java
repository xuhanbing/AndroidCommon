package com.hanbing.library.android.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by hanbing on 2016/7/27
 */
public class DrawableEditText extends EditText {

    protected int mDrawableSize;

    public DrawableEditText(Context context) {
        super(context);
        init();
    }

    public DrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setDrawableSize();
    }

    private void setDrawableSize() {
        Paint paint = getPaint();
        int size = (int) (paint.getFontMetrics().bottom - paint.getFontMetrics().top);

        Drawable[] compoundDrawables = getCompoundDrawables();

        boolean update = false;
        if (null != compoundDrawables) {
            for (Drawable drawable : compoundDrawables) {
                if (null != drawable) {

                    if (drawable.getBounds().width() != size) {
                        update = true;
                    }
                    drawable.setBounds(0, 0, size, size);
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

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        setDrawableSize();
    }

}
