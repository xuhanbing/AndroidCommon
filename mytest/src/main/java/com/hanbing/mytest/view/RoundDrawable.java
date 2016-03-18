package com.hanbing.mytest.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public class RoundDrawable extends Drawable {

	
	Paint mPaint = new Paint();
	
	Bitmap mBitmap = null;
	BitmapShader mBitmapShader = null;
	RectF mRectF = null;
	
	public RoundDrawable() {
		// TODO Auto-generated constructor stub
	}
	
	public RoundDrawable(Bitmap bitmap)
	{
		mBitmap = bitmap;
		mBitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
		
//		mRectF = new RectF(0, 0, getIntrinsicWidth(), getIntrinsicHeight());
		
		mPaint.setShader(mBitmapShader);
	}
	
	@Override
	public void setBounds(int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub
		
		right = left + 200;
		bottom = top + 200;
		
		super.setBounds(left, top, right, bottom);
		mRectF = new RectF(left, top, right, bottom); 
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawRoundRect(mRectF, 10, 10, mPaint);
	}
	
	@Override
	public int getIntrinsicWidth() {
		// TODO Auto-generated method stub
		return 200;//mBitmap.getWidth();
	}
	
	@Override
	public int getIntrinsicHeight() {
		// TODO Auto-generated method stub
		return 200;//mBitmap.getHeight();
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		mPaint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return PixelFormat.TRANSLUCENT;
	}

}
