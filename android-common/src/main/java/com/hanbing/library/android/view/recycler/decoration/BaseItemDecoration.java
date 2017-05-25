package com.hanbing.library.android.view.recycler.decoration;

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

import com.hanbing.library.android.util.ImageUtils;
import com.hanbing.library.android.util.ReflectUtils;

import java.util.ArrayList;

/**
 * Created by hanbing on 2016/3/11.
 */
public class BaseItemDecoration extends RecyclerView.ItemDecoration {


    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;
    public static final int VERTICAL = OrientationHelper.VERTICAL;


    public static class Builder {

        Builder mBuilder = this;

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
            return mBuilder;
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

            if (size >= 0)
            {
                mMarginRect.set(size, size, size, size);
            }

            return mBuilder;
        }

        protected int getSize()
        {
            return mSize;
        }

        public Builder setColor(int color)
        {
            this.mColor = color;
            return mBuilder;
        }

        protected int getColor()
        {
            return mColor;
        }

        public Builder setDrawable(Drawable drawable)
        {
            this.mDrawable = drawable;
            return mBuilder;
        }

        public Builder setDrawableRes(int resId)
        {
            this.mDrawable = mContext.getResources().getDrawable(resId);
            return mBuilder;
        }

        protected Drawable getDrawable()
        {
            return mDrawable;
        }

        public Builder setOrientation(int orientation)
        {
            this.mOrientation = orientation;

            return mBuilder;
        }

        protected int getOrientation()
        {
            return mOrientation;
        }

        public BaseItemDecoration create()
        {
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

    protected ArrayList<RecyclerView.ItemDecoration> mItemDecorations;

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

            drawDecoration(c, parent, child);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Rect rect = getDecorationRect(parent, view);
        outRect.set(new Rect(rect));
    }

    public Rect getDecorationRect(RecyclerView parent, View child) {
        return mMarginRect;
    }

    private Bitmap mBitmap;


    protected void drawDecoration(Canvas c, RecyclerView parent, View child) {


        int l = child.getLeft();
        int t = child.getTop();
        int r = child.getRight();
        int b = child.getBottom();

        /**
         * find all decorations use reflection
         */
        if (null == mItemDecorations)
            mItemDecorations =  ReflectUtils.getValue(parent, "mItemDecorations", null);

        /**
         * offset item decorations before this
         */
        if (null != mItemDecorations && mItemDecorations.size() > 0) {
            Rect tmp = new Rect();

            for (int i = 0; i < mItemDecorations.size(); i++) {
                RecyclerView.ItemDecoration itemDecoration = mItemDecorations.get(i);
                if (itemDecoration == this) {
                    break;
                } else {
                    itemDecoration.getItemOffsets(tmp, child, parent, null);

                    l -= tmp.left;
                    r += tmp.right;
                    t -= tmp.top;
                    b += tmp.bottom;
                }

            }
        }


        Rect rect = new Rect(getDecorationRect(parent, child));

        int ml = rect.left;
        int mt = rect.top;
        int mr = rect.right;
        int mb = rect.bottom;

        rect.set(l - ml, t - mt, r, t);
        adjustRect(parent, rect);
        draw(c, rect);

        rect.set(r, t - mt, r + mr, b);
        adjustRect(parent, rect);
        draw(c, rect);

        rect.set(l, b, r + mr, b + mb);
        adjustRect(parent, rect);
        draw(c, rect);

        rect.set(l - ml, t, l, b + mb);
        adjustRect(parent, rect);
        draw(c, rect);
    }

    /**
     * make sure decoration's range is in RecyclerView
     * @param parent
     * @param rect
     */
    private void adjustRect(RecyclerView parent, Rect rect) {
        int paddingLeft = parent.getPaddingLeft();
        int paddingTop = parent.getPaddingTop();
        int paddingRight = parent.getPaddingRight();
        int paddingBottom = parent.getPaddingBottom();


        int minLeft = paddingLeft;
        int maxRight = parent.getWidth() - paddingRight;
        int minTop = paddingTop;
        int maxBottom = parent.getHeight() - paddingBottom;


        rect.left = Math.max(minLeft, Math.min(rect.left, maxRight));
        rect.right = Math.max(minLeft, Math.min(rect.right, maxRight));
        rect.top = Math.max(minTop, Math.min(rect.top, maxBottom));
        rect.bottom = Math.max(minTop, Math.min(rect.bottom, maxBottom));
    }

    private void draw(Canvas c, Rect rect)
    {
        if (null == mBitmap && null != mDrawable)
        {
            mBitmap = ImageUtils.drawableToBitmap(mDrawable);
        }

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

