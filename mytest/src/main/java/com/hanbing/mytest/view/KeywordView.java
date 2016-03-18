/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2015年5月5日
 * Time : 上午10:03:52
 */
package com.hanbing.mytest.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * KeywordView
 * @author hanbing 
 * @date 2015年5月5日
 * @time 上午10:03:52
 */
public class KeywordView extends View {
	
	public interface OnVerifyListener
	{
		public void onSuccess();
		public void onError();
	}
	
	private static final int NORMAL_COLOR = Color.WHITE;
	private static final int SELECT_COLOR = Color.BLUE;
	private static final int ERROR_COLOR = Color.RED;
	private static final int BACKGROUND_COLOR = Color.BLACK;
	
	
	/**
	 * 未选中前的颜色
	 */
	int mNormalColor = NORMAL_COLOR;
	
	/**
	 * 选中后的颜色
	 */
	int mSelectColor = SELECT_COLOR;
	
	/**
	 * 错误的颜色
	 */
	int mErrorColor = ERROR_COLOR;
	
	/**
	 * 背景颜色
	 */
	int mBackgroundColor = BACKGROUND_COLOR;
	
	/**
	 * 行和列数
	 */
	int mColumnSize = 3;
	
	/**
	 * 默认每个按钮的间距
	 */
	float mPadding = 100; 

	/**
	 * 按钮的整体大小
	 */
	float mIconSize = 0;
	
	/**
	 * 按钮圆环的宽度
	 */
	float mIconCircleWidth = 2;
	
	/**
	 *	按钮内圆的半径
	 */
	float mIconCircleRadius = 0;
	
	/**
	 * 线宽度
	 */
	float mLineWidth = 10;
	/**
	 * 默认每个按钮的中心
	 */
	List<PointF> mIconCenterList = new ArrayList<PointF>();
	
	/**
	 * 选中的按钮，按索引顺序
	 */
	List<Integer> mIconSelectedList = new ArrayList<Integer>();
	
	/**
	 * 当前移动的点
	 */
	PointF mMovePoint = null;
	
	
	Paint mPaint = null;
	
	boolean mIsTouch = true;
	boolean mIsMatch = true;
	
	
	String mPasswordString = "0_4_5_8_";
	OnVerifyListener mOnVerifyListener;
	
	public void setPasswordString(String value)
	{
		this.mPasswordString = value;
	}
	
	public void setOnVerifyListener(OnVerifyListener lsner)
	{
		this.mOnVerifyListener = lsner;
	}
	
	/**
	 * @param context
	 */
	public KeywordView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public KeywordView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public KeywordView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		
		mMovePoint = new PointF();
		
		setBackgroundColor(mBackgroundColor);
	}
	
	private void reset()
	{
		if (null != getHandler())
		{
			getHandler().removeCallbacksAndMessages(null);
		}
		mIsMatch = true;
		mIconSelectedList.clear();
		
		postInvalidate();
	}
	
	private void drawIcons(Canvas canvas, int color)
	{
		for (int i = 0; i < mIconCenterList.size(); i++)
		{
			PointF center = mIconCenterList.get(i);
			
			color = isIconSelected(i) ? (mIsMatch ? mSelectColor : mErrorColor) : mNormalColor;
			
			drawIcon(canvas, center, color);
		}
	}
	
	private void drawIcon(Canvas canvas, PointF center, int color)
	{
		
		mPaint.setColor(mBackgroundColor);
		mPaint.setStyle(Paint.Style.FILL); 
		canvas.drawCircle(center.x, center.y, mIconSize/2, mPaint);

		
		Path path = new Path();
		path.addCircle(center.x, center.y, mIconSize/2 - mIconCircleWidth, Direction.CW);
		
		mPaint.setColor(color);
		mPaint.setStyle(Paint.Style.STROKE); 
		mPaint.setStrokeWidth(mIconCircleWidth); 
		canvas.drawPath(path, mPaint);
		
		mPaint.setStyle(Paint.Style.FILL); 
		canvas.drawCircle(center.x, center.y, mIconCircleRadius, mPaint);
		
	}
	
	
	/**
	 * @param canvas
	 */
	private void drawLines(Canvas canvas, int color) {
		// TODO Auto-generated method stub
		
		if (mIconSelectedList.size() <= 0)
			return;
		
		Path path = new Path();
		
		for (int i = 0; i < mIconSelectedList.size(); i++)
		{
			int index = mIconSelectedList.get(i);
			
			PointF center = mIconCenterList.get(index);
			
			if (0 == i)
			{
				path.moveTo(center.x, center.y);
			}
			else
			{
				path.lineTo(center.x, center.y);
			}
		}
		
		if (mIsTouch)
			path.lineTo(mMovePoint.x, mMovePoint.y);
		
		mLineWidth = mIconCircleWidth;
		
		color = mIsMatch ? color : mErrorColor;
		mPaint.setColor(color);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(mLineWidth); 
		canvas.drawPath(path, mPaint);
	}
	
	
	/**
	 * 判断当前点是否在某个icon中
	 * @param x
	 * @param y
	 * @return <0 表示不再，否则返回那个点的索引
	 */
	private int isInIcon(float x, float y)
	{
		
		for (int i = 0; i < mIconCenterList.size(); i++)
		{
			PointF center = mIconCenterList.get(i);
			
			
			float dx = (center.x - x);
			float dy = (center.y - y);
			
			double distance = Math.sqrt(dx * dx + dy * dy);
			
			if (distance <= mIconSize / 2)
			{
				return i;
			}
		}
		
		
		return -1;
	}
	
	/**
	 * 判断某个按钮是否选中
	 * @param index
	 * @return
	 */
	private boolean isIconSelected(int index)
	{
		return mIconSelectedList.contains(index);
	}
	
	private void calcCirclePoint()
	{
		mIconCenterList.clear();
		
		float size = Math.min(getWidth(), getHeight());
		
		int paddingCount = (mColumnSize + 1);
		
		size = (size - paddingCount * mPadding) / mColumnSize;
		
		mIconSize = size;
		mIconCircleWidth = mIconSize / 20;
		mIconCircleRadius = mIconSize / 4 / 2;
		
		float paddingLeft = (getWidth() - (mColumnSize-1) * mPadding - mColumnSize * size) / 2;
		float paddingTop = (getHeight() - (mColumnSize-1) * mPadding - mColumnSize * size) / 2;
		
		for (int i = 0; i < mColumnSize; i++)
		{
			for (int j = 0; j < mColumnSize; j++)
			{
				PointF center = new PointF();
				
				center.x = paddingLeft + (mPadding + size) * j + size / 2;
				center.y = paddingTop + (mPadding + size) * i + size / 2;
				
				mIconCenterList.add(center);
			}
			
		}
	}
	
	/* (non-Javadoc)
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	/* (non-Javadoc)
	 * @see android.view.View#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		calcCirclePoint();
		super.onLayout(changed, left, top, right, bottom);
	}
	
	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		
//		for (int i = 0; i < mIconSelectedList.size(); i++)
//		{
//			int index = mIconSelectedList.get(i);
//			int indexNext = mi
//			PointF start = mIconCenterList.get(i);
//			PointF end = (i == mIconSelectedList.size()-1)
//					? mMovePoint : mIconCenterList.get(i+1);
//			int color = mSelectColor;
//			
//			drawLine(canvas, start, end, color);
//		}
		
		drawLines(canvas, mSelectColor);
		
		drawIcons(canvas, mSelectColor);
		
		
	}
	
	

	/* (non-Javadoc)
	 * @see android.view.View#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();
		
		PointF point = new PointF(x, y);
		
		int index = isInIcon(x, y);
		
		if (index >= 0 && !isIconSelected(index))
		{
			mIconSelectedList.add(index);
		}
		
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			reset();
			mIsTouch = true;
			
		}
			break; 
		case MotionEvent.ACTION_MOVE:
		{
			mMovePoint.set(point);
		}
			 break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
		{
			mIsTouch = false;
			checkOk();
		}
			break;
		
		}
		
		postInvalidate();
//		return super.dispatchTouchEvent(event);
		return true;
	}

	
	private String getKey()
	{
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < mIconSelectedList.size(); i++)
		{
			sb.append(mIconSelectedList.get(i));
			sb.append("_");
		}
		
		return sb.toString();
	}
	
	private void checkOk()
	{
		
		mIsMatch = getKey().equals(mPasswordString);
		
		if (null != mOnVerifyListener)
		{
			if (mIsMatch)
			{
				mOnVerifyListener.onSuccess();
			}
			else{
				mOnVerifyListener.onError();
			}
		}
		
		getHandler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				reset();
			}
		}, 1000);
	}
}
