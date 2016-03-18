package com.hanbing.mytest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.Rect;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class CropImageView extends ImageView {
	
	Uri mSrc;
	int mAspectX = 1;
	int mAspectY = 1;
	int mOutputX = 100;
	int mOutputY = 100;
	
	Rect mCropRect = new Rect();
	int mCropRectWidth = 0;
	int mCropRectHeight = 0;
	
	int mRectColor = 0xffff0000/*0xff33b5e5*/;
	int mRectWidth = 2;
	
	Paint mPaint;
	
	float mLastX;
	float mLastY;
	
	Bitmap mSrcBitmap = null;
	
	public CropImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public CropImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public CropImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		
	}
	
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		
		if (changed)
		{
			int w = getWidth();
			int h = getHeight();
			int centerX = w / 2;
			int centerY = h / 2;
			
			int size = Math.min(Math.min(mOutputX, mOutputY), Math.min(w, h));
			int rectWidth = size;
			int rectHeight = (int) (size * mAspectY * 1.0f / mAspectX);
			mCropRectWidth = rectWidth;
			mCropRectHeight = rectHeight;
			
			mCropRect.set(
					centerX - rectWidth / 2, 
					centerY - rectHeight / 2, 
					centerX + rectWidth / 2, 
					centerY + rectHeight / 2);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		drawCropRect(canvas);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
//		return super.dispatchTouchEvent(event);
		
		float x = event.getX();
		float y = event.getY();
		
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			mLastX = x;
			mLastY = y;
		}
			break;
		case MotionEvent.ACTION_MOVE:
		{
			float dx = (x - mLastX);
			float dy = (y - mLastY);
			
			mCropRect.left += dx;
			mCropRect.top += dy;
			
			
			Drawable drawable = getDrawable();
			
			if (null != drawable)
			{
				int w = drawable.getIntrinsicWidth();
				int h = drawable.getIntrinsicHeight();
				
				float [] values = new float[9];
				getImageMatrix().getValues(values);
				
				float scaleX = values[0];
				float scaleY = values[4];
				float startX = values[2];
				float startY = values[5];
				
				mCropRect.left = (int) Math.max(startX, Math.min(mCropRect.left, startX + w * scaleX - mCropRectWidth));
				mCropRect.top = (int) Math.max(startY, Math.min(mCropRect.top, startY + h * scaleY - mCropRectHeight));
				
			}
			
			mCropRect.right = mCropRect.left + mCropRectWidth;
			mCropRect.bottom = mCropRect.top + mCropRectHeight;
			
			postInvalidate();
			
		}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
		{
			
		}
			break;
		}
		
		mLastX = x;
		mLastY = y;
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
	
	private void drawCropRect(Canvas canvas)
	{
		
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(mRectColor);
		mPaint.setStrokeWidth(mRectWidth);
		
		canvas.drawRect(mCropRect, mPaint);
		
	}
	
	public Bitmap cropImage()
	{
		Drawable src = getDrawable();
		
		if (null != src)
		{
			int width = src.getIntrinsicWidth();
			int height = src.getIntrinsicHeight();
			
			if (null == mSrcBitmap)
			{
				
				
				mSrcBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
				Canvas canvas = new Canvas(mSrcBitmap);
				
				src.setBounds(0, 0, width, height);
				src.draw(canvas);
				
			}
			
			Matrix matrix = getImageMatrix();
			
			float[] values = new float[9];
			
			matrix.getValues(values);
			
			float scaleX = values[0];
			float scaleY = values[4];
			float startX = values[2];
			float startY = values[5];
			
			
			Bitmap bitmap = Bitmap.createBitmap(mCropRect.width(), mCropRect.height(), Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			
			Rect srcRect = new Rect(0, 0, width, height);
			int rectWidth = (int) (mCropRect.width() / scaleX);
			int rectHeight = (int) (mCropRect.height() / scaleY);
			
			srcRect.left = (int) ((mCropRect.left - startX) / scaleX);
			srcRect.right = srcRect.left + rectWidth;
			srcRect.top = (int) ((mCropRect.top - startY) / scaleY);
			srcRect.bottom = srcRect.top + rectHeight;
			
			canvas.drawBitmap(mSrcBitmap, srcRect, new Rect(0, 0, mCropRect.width(), mCropRect.height()), null);
			
			return bitmap;
		}
		
		return null;
	}
	
	public void setSrc(Uri uri)
	{
		mSrc = uri;
	}
	
	public void setAspect(int aspectX, int aspectY)
	{
		mAspectX = aspectX;
		mAspectY = aspectY;
	}
	
	public void setOutput(int outputX, int outputY)
	{
		mOutputX = outputX;
		mOutputY = outputY;
	}
}
