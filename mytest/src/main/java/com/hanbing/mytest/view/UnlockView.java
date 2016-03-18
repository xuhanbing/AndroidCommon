package com.hanbing.mytest.view;

import com.hanbing.mytest.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

public class UnlockView extends View {

	
	public interface OnStatusChangedListener
	{
		/**
		 * slide to left
		 */
		public void onSlideLeft();
		
		/**
		 * slide to right
		 */
		public void onSlideRight();
		
		/**
		 * click slider
		 */
		public void onClickSlider();
	}
	
	
	String mLeftText = "+1.5";
	String mRightText = "+1.5";
	
	Bitmap mLeftBm;
	Bitmap mRightBm;
	Bitmap mSliderBm;
	
	int mCircleWidth = 4;
	float mRadius = 100;
	float mTextSize = 25.0f;
	
	Paint mPaint;
	
	PointF mCenterP = new PointF();
	PointF mSliderP = new PointF();
	PointF mLeftP = new PointF();
	PointF mRightP = new PointF();
	
	float mDownX = 0.0f;
	float mDownY = 0.0f;
	float mLastX = 0.0f;
	float mLastY = 0.0f;
	
	Scroller mScroller = null;
	
	boolean mIsTouchInSlider = false;
	
	/**
	 * default scale
	 */
	final float DEFAULT_SCALE = 0.75f;
	
	/**
	 * slider touch scale, max 1.0f
	 */
	final float SLIDER_SCALE = 1.0f;
	
	
	/**
	 * if velocity is bigger than this value when up, 
	 * we think it is moving but not a single click
	 */
	float MAX_CLICK_VELOCITY = 50;
	
	/**
	 * if move distance bigger than this value .
	 * we think it is moving but not a single click
	 */
	float MAX_MISS_DISTANCE = 50;
	
	VelocityTracker mVelociytTracker;
	
	boolean mIsMoving = false;
	
	int count = 0;
//	OnClickListener mClickSliderLisener = new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Log.e("", "on click slider");
//			
//			mSliderBm = count % 2 == 0 ? mLeftBm : mRightBm;
//			count++;
//		}
//	};
	
	
	OnStatusChangedListener mOnStatusChangedListener = new OnStatusChangedListener() {
		
		@Override
		public void onSlideRight() {
			// TODO Auto-generated method stub
			Log.e("", "slide right");
		}
		
		@Override
		public void onSlideLeft() {
			// TODO Auto-generated method stub
			Log.e("", "slide left");
		}
		
		@Override
		public void onClickSlider() {
			// TODO Auto-generated method stub
			Log.e("", "on click slider");
			
			changeSliderImage();
		}
	};
	
	public UnlockView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public UnlockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public UnlockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init()
	{

		mScroller = new Scroller(getContext());
		
		
		mLeftBm = BitmapFactory.decodeResource(getResources(), R.drawable.a);
		mRightBm = BitmapFactory.decodeResource(getResources(), R.drawable.b);
		mSliderBm = BitmapFactory.decodeResource(getResources(), R.drawable.p3);
		
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		initValues();
	}
	
	private void initValues()
	{
		int w = getWidth();
		int h = getHeight();
		mRadius = Math.min(mRadius, Math.min(w, h) / 4);
		
		mCenterP.set(w / 2, h / 2);
		mSliderP.set(mCenterP);
		mLeftP.set(getPaddingLeft() + mRadius, h / 2);
		mRightP.set(w - getPaddingRight() - mRadius, h / 2);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(mCircleWidth);
		
		float scale = 0;
		
		scale = DEFAULT_SCALE;
		mPaint.setColor(Color.BLACK);
		if (isSliderTouchLeft())
		{
			mPaint.setColor(0xff00ffff);
			scale = SLIDER_SCALE;
		}
		canvas.drawCircle(mLeftP.x, mLeftP.y,  mRadius * scale, mPaint);
		drawCircle(canvas, mLeftP, mLeftBm, scale);  
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setTextSize(mTextSize);
		canvas.drawText(mLeftText, mLeftP.x, mLeftP.y + mRadius + mTextSize, mPaint);
		
		scale = DEFAULT_SCALE;
		mPaint.setColor(Color.BLACK);
		if (isSliderTouchRight())
		{
			mPaint.setColor(0xff00ffff);
			scale = SLIDER_SCALE;
		}
		canvas.drawCircle(mRightP.x, mRightP.y, mRadius * scale, mPaint);
		drawCircle(canvas, mRightP, mRightBm, scale);
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setTextSize(mTextSize);
		canvas.drawText(mRightText, mRightP.x, mRightP.y + mRadius + mTextSize, mPaint);
		
		scale = DEFAULT_SCALE;
		mPaint.setColor(Color.BLACK);
		canvas.drawCircle(mSliderP.x, mSliderP.y, mRadius * scale, mPaint);
		drawCircle(canvas, mSliderP, mSliderBm, scale);
		
		
		
		super.onDraw(canvas);
	}
	
	
	private void drawCircle(Canvas canvas, PointF center, Bitmap bm, float scale)
	{
		
		scale = Math.max(0, Math.min(scale, 1.0f));
		float left;
		float top;
		float right;
		float bottom;
		
		Path path = new Path();
		
		
		canvas.save();
		
		float raduis = (mRadius - mCircleWidth) * scale;
		
		path.reset();
		left = center.x - raduis;
		right = center.x + raduis;
		top = center.y - raduis ;
		bottom = center.y + raduis;
		
		
		path.addCircle(center.x , center.y, raduis, Direction.CW);
		canvas.clipPath(path);
		canvas.drawBitmap(bm, null, new RectF(left, top, right, bottom), mPaint);
		
		canvas.restore();
	}
	
	
	private boolean isTouchInSlider(float x, float y)
	{
		
		return calcDistance(mSliderP, new PointF(x, y)) <= mRadius;
	}
	
	private boolean isSliderTouchLeft()
	{
		return calcDistance(mSliderP, mLeftP) <= 2 * mRadius;
	}
	
	private boolean isSliderTouchRight()
	{
		return calcDistance(mSliderP, mRightP) <= 2 * mRadius;
	}
	
	private boolean isSliderMatchLeft()
	{
		return calcDistance(mSliderP, mLeftP) <= 0;
	}
	
	private boolean isSliderMatchRight()
	{
		return calcDistance(mSliderP, mRightP) <= 0;
	}
	
	private double calcDistance(PointF p1, PointF p2)
	{
		float disX = p1.x - p2.x;
		float disY = p1.y - p2.y;
		
		return Math.sqrt(disX * disX + disY * disY);
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			
			mIsMoving = false;
			mIsTouchInSlider = false;
			
			mDownX = event.getX();
			mDownY = event.getY();
			
			if (null == mVelociytTracker)
			{
				mVelociytTracker = VelocityTracker.obtain();
			}
			
			mVelociytTracker.addMovement(event);
			
			mIsTouchInSlider = isTouchInSlider(event.getX(), event.getY());
			
			if (mIsTouchInSlider)
			{
				mLastX = event.getX();
				mLastY = event.getY();
				
				if (!mScroller.isFinished())
				{
					mScroller.abortAnimation();
				}
				
				mSliderP.set(mCenterP);
			}
			
			return true;
		}
		
		return super.dispatchTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		mVelociytTracker.addMovement(event);
		
		float x = event.getX();
		float y = event.getY();
		
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			
		}
			break;
		case MotionEvent.ACTION_MOVE:
		{
			
			if (calcDistance(new PointF(mDownX, mDownY), new PointF(x, y)) > MAX_MISS_DISTANCE)
			{
				mIsMoving = true;
			}
			
			if (mIsTouchInSlider)
			{
				float dx = x - mLastX;
				
				mSliderP.set(Math.max(mLeftP.x, Math.min(mSliderP.x + dx, mRightP.x)), mSliderP.y);
				postInvalidate();
			}
			
		}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
		{
			
			if (mIsTouchInSlider
					&& !mIsMoving)
			{
				mVelociytTracker.computeCurrentVelocity(1000);
				
				//click slider
				if (Math.abs(mVelociytTracker.getXVelocity()) < MAX_CLICK_VELOCITY
						&& Math.abs(mVelociytTracker.getYVelocity()) <MAX_CLICK_VELOCITY)
				{
					if (null != mOnStatusChangedListener)
					{
						mOnStatusChangedListener.onClickSlider();
					}
				}
			}
			
			else if (mIsTouchInSlider && mIsMoving)
			{
				if (null != mOnStatusChangedListener)
				{
					if (isSliderMatchLeft())
					{
						mOnStatusChangedListener.onSlideLeft();
					}
					else if (isSliderMatchRight())
					{
						mOnStatusChangedListener.onSlideRight();
					}
				}
			}
			
			mVelociytTracker.clear();
			
			ainmToOrigin();
		}
			break;
		}
		
		mLastX = x;
		mLastY = y;
		
		return super.onTouchEvent(event);
	}
	
	private void ainmToOrigin()
	{
		mScroller.startScroll((int)mSliderP.x, 0, (int)(mCenterP.x - mSliderP.x), 0, 1000);
	}
	
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		
		if (mScroller.computeScrollOffset())
		{
			mSliderP.set(mScroller.getCurrX(), mSliderP.y);
			
			postInvalidate();
		}
	}
	
	
	public void setSliderIcon(int resId)
	{
		
		Bitmap bm = BitmapFactory.decodeResource(getResources(), resId);
		
		setSliderIcon(bm);
	}
	
	public void setSliderIcon(Bitmap bm)
	{
		mSliderBm = bm;
	}
	
	public void setLeftIcon(int resId)
	{
		Bitmap bm = BitmapFactory.decodeResource(getResources(), resId);
		
		setLeftIcon(bm);
	}
	
	public void setLeftIcon(Bitmap bm)
	{
		mLeftBm = bm;
	}
	
	public void setRightIcon(int resId)
	{
		Bitmap bm = BitmapFactory.decodeResource(getResources(), resId);
		
		setRightIcon(bm);
	}
	
	public void setRightIcon(Bitmap bm)
	{
		mRightBm = bm;
	}
	
	public void setOnStatusChangedListener(OnStatusChangedListener lsner)
	{
		mOnStatusChangedListener = lsner;
	}

	
	public void setPrice(String left, String right)
	{
		this.mLeftText = left;
		this.mRightText = right;
	}
	
	public void changeSliderImage()
	{
		mSliderBm = count % 2 == 0 ? mLeftBm : mRightBm;
		count++;
		
		postInvalidate();
	}
}
