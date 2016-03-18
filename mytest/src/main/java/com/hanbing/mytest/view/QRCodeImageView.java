package com.hanbing.mytest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class QRCodeImageView extends ImageView {
	
	private final String TAG = getClass().getName();
	
	private final float DEFAULT_CENTER_RATIO = 1.0f/6;
	private final float DEFAULT_ROUND_RATIO = 1.0f/8;
	
	/**�м�С��ͼƬ*/
	Bitmap mCenterImage;
	boolean mUseCenterImage = true;
	Paint mPaint;
	Path mPath;

	RectF mCenterImageRect;
	int mCenterImageSize;
	
	
	public void setCenterImage(Bitmap bm)
	{
		this.mCenterImage = bm;
		
		postInvalidate();
	}
	
	public QRCodeImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public QRCodeImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public QRCodeImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}
	

	private void init()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPath = new Path();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		RectF rect = new RectF();
		
		int width = getWidth();
		int height = getHeight();
		
		
		float ratio = Math.max(0, Math.min(DEFAULT_CENTER_RATIO, 1.0f));
		
		int size = (int) (ratio * Math.min(width, height));
		
		
		int l = (width - size) / 2;
		int t = (height - size) / 2;
		
		rect.set(l, t, l + size, t + size);
		
		mCenterImageRect = rect;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		if (mUseCenterImage
				&& null != mCenterImage)
		{
			
			RectF rect = mCenterImageRect;
			float size = rect.width();
			

			float rx = DEFAULT_ROUND_RATIO * size;
			mPath.addRoundRect(rect, rx, rx, Direction.CW);
			canvas.clipPath(mPath);
			
			canvas.drawBitmap(mCenterImage, null, rect, mPaint);
		}
	}
	
	
}
