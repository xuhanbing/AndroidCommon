package com.common.widget.recyclerview.decoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.util.ImageUtils;

/**
 * Created by hanbing on 2016/3/11.
 */
public class BaseItemDecoration extends RecyclerView.ItemDecoration {


    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;
    public static final int VERTICAL = OrientationHelper.VERTICAL;


    public static class Builder {

        protected Context mContext;

        /**
         * offset rect
         */
        protected  Rect mMarginRect;

        /**
         * decoration size
         */
        protected int mSize;

        /**
         * decoration color
         */
        protected int mColor = Color.TRANSPARENT;

        /**
         * decoration drawable
         */
        protected Drawable mDrawable = null;

        protected int mOrientation = VERTICAL;

        public Builder(Context context) {
            this.mContext = context;

            mMarginRect = new Rect(0, 0, 0, 0);
        }

        /**
         *
         * @param l
         * @param t
         * @param r
         * @param b
         * @return
         */
        public Builder setMargin(int l, int t, int r, int b)
        {
            mMarginRect.set(l, t, r, b);
            return this;
        }

        protected Rect getMarginRect()
        {
            return mMarginRect;
        }

        /**
         * used only for single edge according to layoutmanager
         * @param size
         * @return
         */
        public Builder setSize(int size)
        {
            this.mSize = size;
            return this;
        }

        protected int getSize()
        {
            return mSize;
        }

        public Builder setColor(int color)
        {
            this.mColor = color;
            return this;
        }

        protected int getColor()
        {
            return mColor;
        }

        public Builder setDrawable(Drawable drawable)
        {
            this.mDrawable = drawable;
            return this;
        }

        public Builder setDrawableRes(int resId)
        {
            this.mDrawable = mContext.getResources().getDrawable(resId);
            return this;
        }

        protected Drawable getDrawable()
        {
            return mDrawable;
        }

        public Builder setOrientation(int orientation)
        {
            this.mOrientation = orientation;

            return this;
        }

        protected int getOrientation()
        {
            return mOrientation;
        }

        public BaseItemDecoration create()
        {
            System.out.println("creat base");
            BaseItemDecoration baseItemDecoration = new BaseItemDecoration(this);
            return baseItemDecoration;
        }
    }



    /**
     * offset rect
     */
    protected  Rect mMarginRect;

    /**
     * decoration size
     */
    protected int mSize;

    /**
     * decoration color
     */
    protected int mColor = Color.TRANSPARENT;

    /**
     * decoration drawable
     */
    protected Drawable mDrawable = null;

    protected int mOrientation = VERTICAL;

    protected Paint mPaint;


    public BaseItemDecoration() {
        initPaint();
    }

    public BaseItemDecoration(Builder builder)
    {

        if (null != builder)
        {
            this.mColor = builder.getColor();
            this.mDrawable = builder.getDrawable();
            this.mSize = builder.getSize();
            this.mOrientation = builder.getOrientation();
            this.mMarginRect = new Rect(builder.getMarginRect());
        }

        initPaint();
    }


    private void initPaint() {
        int color = mColor;
        int size = mSize;
        Drawable drawable = mDrawable;


        if (null == mPaint)
        {
            mPaint = new Paint();
        }

        mPaint.setColor(color);
        if (null != drawable)
        {
            Bitmap bitmap= ImageUtils.drawableToBitmap(drawable);
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            mPaint.setShader(shader);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++)
        {
            View child = parent.getChildAt(i);

            drawDecoration(c, child);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mSize > 0)
            mMarginRect.set(mSize, mSize, mSize, mSize);
        outRect.set(mMarginRect);
    }

    private Bitmap mBitmap;

    protected void drawDecoration(Canvas c, View child) {

        int l = child.getLeft();
        int t = child.getTop();
        int r = child.getRight();
        int b = child.getBottom();

        int ml = mMarginRect.left;
        int mt = mMarginRect.top;
        int mr = mMarginRect.right;
        int mb = mMarginRect.bottom;

        if (null != mDrawable)
        {
            if (null == mBitmap)
            {
                mBitmap = ImageUtils.drawableToBitmap(mDrawable);

            }
        }

        Rect rect = new Rect();

        rect.set(l - ml, t - mt, r, t);
        draw(c, rect);;

        rect.set(r, t - mt, r + mr, b);
        draw(c, rect);

        rect.set(l, b, r + mr, b + mb);
        draw(c, rect);

        rect.set(l - ml, t, l, b + mb);
        draw(c, rect);
    }

    private void draw(Canvas c, Rect rect)
    {
        if (null != mBitmap)
            c.drawBitmap(mBitmap, null, rect, null);
        else
            c.drawRect(rect, mPaint);
    }

    protected boolean isVertical()
    {
        return mOrientation == VERTICAL;
    }

    /**
     *
     * @param l
     * @param t
     * @param r
     * @param b
     * @return
     */
    public void setMargin(int l, int t, int r, int b)
    {
        mMarginRect.set(l, t, r, b);
    }

    public Rect getMarginRect()
    {
        return mMarginRect;
    }

    /**
     * used only for single edge according to layoutmanager
     * @param size
     * @return
     */
    public void setSize(int size)
    {
        this.mSize = size;
    }

    public int getSize()
    {
        return mSize;
    }

    public void setColor(int color)
    {
        this.mColor = color;
    }

    public int getColor()
    {
        return mColor;
    }

    public void setDrawable(Drawable drawable)
    {
        this.mDrawable = drawable;
    }

    public Drawable getDrawable()
    {
        return mDrawable;
    }

    public void setOrientation(int orientation)
    {
        this.mOrientation = orientation;

    }

    public int getOrientation()
    {
        return mOrientation;
    }
}

