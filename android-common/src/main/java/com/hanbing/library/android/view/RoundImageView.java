/**
 *
 */
package com.hanbing.library.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hanbing.library.android.R;

/**
 * A round ImageView
 * Created by hanbing
 */
public class RoundImageView extends ImageView {

    /**
     * type
     */
    protected int mType = TYPE_CIRCLE;
    protected static final int TYPE_CIRCLE = 0;
    protected static final int TYPE_ROUND = 1;

    /**
     * Default radius size
     */
    private static final int BORDER_RADIUS_DEFAULT = 10;

    /**
     * Border size.
     */
    private int mBorderSize = 0;

    /**
     * Border color.
     */
    private int mBorderColor = Color.TRANSPARENT;

    /**
     * Radius size.
     */
    private int mBorderRadius;

    /**
     * Border paint.
     */
    private Paint mBorderPaint = new Paint();

    /**
     * Bitmap paint.
     */
    private Paint mBitmapPaint;
    /**
     * Circle radius
     */
    private int mRadius;
    /**
     * A matrix used for scale
     */
    private Matrix mMatrix;
    /**
     * Bitmap shader
     */
    private BitmapShader mBitmapShader;
    /**
     * View width.
     */
    private int mWidth;
    private RectF mRoundRect;

    private RectF mBorderRoundRect;

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init(context, attrs, defStyle);
    }

    /**
     * @param context
     */
    public RoundImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub

        init(context, null, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs, 0);
    }

    void init(Context context, AttributeSet attrs, int defStyle) {
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView, defStyle, 0);

        mType = a.getInt(R.styleable.RoundImageView_type, TYPE_ROUND);

        mBorderSize = a.getDimensionPixelSize(
                R.styleable.RoundImageView_borderSize, 0);

        mBorderColor = a.getColor(R.styleable.RoundImageView_borderColor,
                mBorderColor);


        mBorderRadius = a.getDimensionPixelSize(
                R.styleable.RoundImageView_radius, BORDER_RADIUS_DEFAULT);

        a.recycle();

        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.FILL);
        mBorderPaint.setStrokeWidth(mBorderSize);

        //center
        setScaleType(ScaleType.CENTER);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mType == TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = mWidth / 2;
        }

    }

    /**
     * Init BitmapShader
     */
    private void setUpShader() {
        mBitmapShader = null;
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        Bitmap bmp = drawableToBitmap(drawable);

        if (null == bmp)
            return;

        mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
        int width = getWidth();
        int height = getHeight();

        int contentWidth = 0;
        int contentHeight = 0;

        float scale = 1.0f;
        if (mType == TYPE_CIRCLE) {
            // a min size of width and height
            int bSize = Math.min(bmp.getWidth(), bmp.getHeight());

            contentWidth = contentHeight  = mWidth -  2 * mBorderSize;
                    scale = contentHeight * 1.0f / bSize;


        } else if (mType == TYPE_ROUND) {
            contentWidth = width - mBorderSize * 2;
            contentHeight = height - mBorderSize * 2;
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            //if width or height of bitmap is not match width or height of view, we will scale it just like CenterCrop scale type.
            scale = Math.max(contentWidth * 1.0f / bmp.getWidth(), contentHeight * 1.0f / bmp.getHeight());

        }
        // scale
        mMatrix.setScale(scale, scale);

        // move
        int tx = (width - contentWidth) / 2;
        int ty = (height - contentHeight) / 2;
        mMatrix.postTranslate(tx, ty);

        mBitmapShader.setLocalMatrix(mMatrix);
        mBitmapPaint.setShader(mBitmapShader);
    }

    /**
     * drawable to bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (null == drawable)
            return null;

        Bitmap bitmap = null;

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            bitmap = bd.getBitmap();

        } else {
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, w, h);
            drawable.draw(canvas);
        }

        //if bitmap is null, return
        if (null == bitmap)
            return null;

        //Check whether need crop or not.
        boolean needCrop = TYPE_CIRCLE == mType;

        switch (getScaleType())
        {
            case CENTER:
            case CENTER_INSIDE:
            case CENTER_CROP:
                needCrop = true;
                break;
        }

        if (needCrop)
        {
            int bitmapWidth = 0;
            int bitmapHeight = 0;
            int x = 0;
            int y = 0;

            int width = getWidth();
            int height = getHeight();


            if (TYPE_CIRCLE == mType)
            {
                bitmapWidth = bitmapHeight = Math.min(w, h);
            }
            else {
                float ratio = width * 1.0f / height;
                float ratioDrawable = w * 1.0f / h;

                if (ratio > ratioDrawable) {
                    bitmapWidth = w;
                    bitmapHeight = (int) (bitmapWidth / ratio);

                } else if (ratio < ratioDrawable){
                    bitmapHeight = h;
                    bitmapWidth = (int) (h * ratio);
                } else {
                    bitmapWidth = w;
                    bitmapHeight = h;
                }
            }


            x = (w - bitmapWidth) / 2;
            y = (h - bitmapHeight) / 2;

            Bitmap output = Bitmap.createBitmap(bitmap, x, y, bitmapWidth, bitmapHeight);

            bitmap = output;
        }
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        setUpShader();

        if (null == mBitmapShader)
            return;

        if (mType == TYPE_ROUND) {

            if (mBorderSize > 0) {
                canvas.drawRoundRect(mBorderRoundRect, mBorderRadius, mBorderRadius, mBorderPaint);
            }

            canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mBitmapPaint);

        } else {
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            if (mBorderSize > 0)
            {
                int offset = 0;
                if (mBorderPaint.getStyle() == Paint.Style.STROKE) {
                    offset = mBorderSize / 2;
                }
                canvas.drawCircle(centerX, centerY, mRadius - offset, mBorderPaint);
            }
            canvas.drawCircle(centerX, centerY, mRadius - mBorderSize, mBitmapPaint);

            // drawSomeThing(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //Reset rect.
        if (mType == TYPE_ROUND) {
            mBorderRoundRect = new RectF(0, 0, getWidth(), getHeight());
            mRoundRect = new RectF(mBorderRoundRect);

            if (mBorderSize > 0) {

                if (mBorderPaint.getStyle() == Paint.Style.STROKE)
                {
                    int offset = mBorderSize / 2;
                    mBorderRoundRect.set(offset, offset, getWidth() - offset, getHeight() - offset);
                }

                mRoundRect.left += mBorderSize;
                mRoundRect.right -= mBorderSize;
                mRoundRect.top += mBorderSize;
                mRoundRect.bottom -= mBorderSize ;
            }
        }
    }

}
