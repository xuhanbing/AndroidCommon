package com.hanbing.mytest.view;

/**
 * 
 */

import android.content.Context;
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

/**
 * @author hanbing
 * @date 2015-7-1
 */
public class RoundImageView2 extends ImageView {

    /**
     * 图片的类型，圆形or圆角
     */
    protected int mType = TYPE_ROUND;
    protected static final int TYPE_CIRCLE = 0;
    protected static final int TYPE_ROUND = 1;

    /**
     * 圆角大小的默认值
     */
    protected static final int BODER_RADIUS_DEFAULT = 10;

    /**
     * 边界大小
     */
    protected int mBorderWidth = 0;

    /**
     * 边界颜色
     */
    protected int mBorderColor = Color.TRANSPARENT;

    /**
     * 
     */
    protected Paint mBorderPaint = new Paint();

    /**
     * 圆角的大小
     */
    protected int mBorderRadius = BODER_RADIUS_DEFAULT;

    /**
     * 绘图的Paint
     */
    protected Paint mBitmapPaint;
    /**
     * 圆角的半径
     */
    protected int mRadius;
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    protected Matrix mMatrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    protected BitmapShader mBitmapShader;
    /**
     * view的宽度
     */
    protected int mWidth;
    protected RectF mRoundRect;

    protected RectF mBorderRoundRect;

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public RoundImageView2(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	// TODO Auto-generated constructor stub
	init(context, attrs, defStyle);
    }

    /**
     * @param context
     */
    public RoundImageView2(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
	
	init(context, null, 0);
    }

    public RoundImageView2(Context context, AttributeSet attrs) {
	super(context, attrs);

	init(context, attrs, 0);
    }

    void init(Context context, AttributeSet attrs, int defStyle) {
	
	mMatrix = new Matrix();
	mBitmapPaint = new Paint();
	mBitmapPaint.setAntiAlias(true);
//	TypedArray a = context.obtainStyledAttributes(attrs,
//		R.styleable.RoundImageView, defStyle, 0);
//
//	mType = a.getInt(R.styleable.RoundImageView_type, TYPE_ROUND);// 默认为round
//
//	mBorderWidth = a.getDimensionPixelSize(
//		R.styleable.RoundImageView_borderWidth, 0);
//
//	mBorderColor = a.getColor(R.styleable.RoundImageView_borderColor,
//		mBorderColor);
//
//	mBorderPaint.setColor(mBorderColor);
//	mBorderPaint.setAntiAlias(true);
//	
//	mBorderRadius = a.getDimensionPixelSize(
//		    R.styleable.RoundImageView_radius, BODER_RADIUS_DEFAULT);

//	a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	/**
	 * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
	 */
	if (mType == TYPE_CIRCLE) {
	    mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
	    mRadius = mWidth / 2;
	    setMeasuredDimension(mWidth, mWidth);
	}

    }

    /**
     * 初始化BitmapShader
     */
    protected void setUpShader() {
	Drawable drawable = getDrawable();
	if (drawable == null) {
	    return;
	}

	Bitmap bmp = drawableToBitamp(drawable);
	// 将bmp作为着色器，就是在指定区域内绘制bmp
	mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
	
	float scale = 1.0f;
	if (mType == TYPE_CIRCLE) {
	    // 拿到bitmap宽或高的小值
	    int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
	    scale = mWidth * 1.0f / bSize;

	} else if (mType == TYPE_ROUND) {
	    // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
	    scale = Math.max(getWidth() * 1.0f / bmp.getWidth(), getHeight()
		    * 1.0f / bmp.getHeight());
	}
	// shader的变换矩阵，我们这里主要用于放大或者缩小
	mMatrix.setScale(scale, scale);
	// 设置变换矩阵
	mBitmapShader.setLocalMatrix(mMatrix);
	// 设置shader
	mBitmapPaint.setShader(mBitmapShader);
    }

    /**
     * drawable转bitmap
     * 
     * @param drawable
     * @return
     */
    protected Bitmap drawableToBitamp(Drawable drawable) {
	if (drawable instanceof BitmapDrawable) {
	    BitmapDrawable bd = (BitmapDrawable) drawable;
	    return bd.getBitmap();
	}
	int w = drawable.getIntrinsicWidth();
	int h = drawable.getIntrinsicHeight();
	Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
	Canvas canvas = new Canvas(bitmap);
	drawable.setBounds(0, 0, w, h);
	drawable.draw(canvas);
	return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
	if (getDrawable() == null) {
	    return;
	}
	setUpShader();

	if (mType == TYPE_ROUND) {

	    if (mBorderWidth > 0) {
		canvas.drawRoundRect(mBorderRoundRect, mBorderRadius, mBorderRadius,
			mBorderPaint);
		
	    } 
	    
	    canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
			mBitmapPaint);

	} else {
	    if (mBorderWidth > 0) {
		canvas.drawCircle(mRadius, mRadius, mRadius, mBorderPaint);
		canvas.drawCircle(mRadius, mRadius, mRadius - mBorderWidth,
			    mBitmapPaint);
	    } else {
		canvas.drawCircle(mRadius, mRadius, mRadius,
			    mBitmapPaint);
	    }
	    
	    // drawSomeThing(canvas);
	}
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	super.onSizeChanged(w, h, oldw, oldh);
	// 圆角图片的范围
	if (mType == TYPE_ROUND) {
	    mBorderRoundRect = new RectF(0, 0, getWidth(), getHeight());
	    mRoundRect = new RectF(mBorderRoundRect);
	    
	    if (mBorderWidth > 0) {
		mRoundRect.left += mBorderWidth;
		mRoundRect.right -= mBorderWidth;
		mRoundRect.top += mBorderWidth;
		mRoundRect.bottom -= mBorderWidth;
	    }
	}
    }

}
