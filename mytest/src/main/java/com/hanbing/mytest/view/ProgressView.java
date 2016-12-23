package com.hanbing.mytest.view;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class ProgressView extends TextView {
	
	
	/**
	 * Բ��
	 */
	public static final int STYLE_CIRCLE = 0x1000;
	/**
	 * Բ��
	 */
	public static final int STYLE_RING = STYLE_CIRCLE | 0x0100;
	
	/**
	 * ɨ��
	 */
	public static final int STYLE_SWEEP = STYLE_CIRCLE | 0x0200;
	
	/**
	 * ˮƽ��
	 */
	public static final int STYLE_WATER = STYLE_CIRCLE | 0x0400;
	
	
	/**
	 * ������
	 */
	public static final int STYLE_RECT = 0x2000;
	public static final int STYLE_RECT_PROGRESS_IN = STYLE_RECT | 0x0100;
	public static final int STYLE_RECT_PROGRESS_OUT = STYLE_RECT | 0x0200;
	public static final int STYLE_RECT_PROGRESS_OUT_LEFT = STYLE_RECT_PROGRESS_OUT | 0x0010;
	public static final int STYLE_RECT_PROGRESS_OUT_RIGHT = STYLE_RECT_PROGRESS_OUT | 0x0020;
	public static final int STYLE_RECT_PROGRESS_OUT_TOP = STYLE_RECT_PROGRESS_OUT | 0x0040;
	public static final int STYLE_RECT_PROGRESS_OUT_BOTTOM = STYLE_RECT_PROGRESS_OUT | 0x0080;
	
	public static final int STYLE_MASK = 0xF000;
	public static final int STYLE_MASK_PROGRESS = 0xFF00;
	
	public static final int DEGREE_360 = 360;
	public static final int DEGREE_180 = 180;
	public static final int DEGREE_90 = 90;
	
	//Բ���Ŀ�ȣ�Ĭ��������ȵı���
	public static final float RING_WIDTH_RATIO = 1.0f / 8;
	float mRingWidth = 4;
	
	/**
	 * ��ǰ����
	 */
	int mCurProgress = 0;
	
	/**
	 * Բ����ʼ�ĽǶȣ�0Ĭ��Ϊ3���ӣ�90Ϊ6����
	 */
	int mStartAngle = -90;
	Paint mPaint;
	
	/**
	 * Ĭ�������ɫ
	 */
	int mFillColor = Color.WHITE;
	
	/**
	 * Բ����ɫ
	 */
	int mRingColor = Color.BLUE;
	
	/**
	 * ������ɫ
	 */
	int mProgressColor = Color.GREEN;
	
	/**
	 * �Ƿ�ʹ�û�
	 */
	public boolean mUseRing = true;
	
	/**
	 * �Ƿ���ʾ���ֽ���
	 */
	public boolean mShowProgress = true;
	
	/**
	 * ��������
	 */
	int mProgressStyle = STYLE_SWEEP;

	public ProgressView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public ProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		parseParams(context, attrs, android.R.attr.textViewStyle);
		init();
	}

	public ProgressView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		parseParams(context, attrs, defStyle);
		
		init();
		
	}
	
	public ProgressView(Context context, int type) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
		this.mProgressStyle = type;
	}
	
	/**
	 * ��������
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	private void parseParams(Context context, AttributeSet attrs, int defStyle)
	{
		Theme theme = context.getTheme();
		
		TypedArray a = theme .obtainStyledAttributes(
                attrs, R.styleable.ProgressView, defStyle, 0);
		
		if (null != a)
		{
			int indexCount = a.getIndexCount();
			
			for (int i = 0; i < indexCount; i++)
			{
				int index = a.getIndex(i);
				
				switch (index)
				{
				case R.styleable.ProgressView_fillColor:
					mFillColor = a.getColor(index, mFillColor);
					break;
				case R.styleable.ProgressView_ringColor:
					mRingColor = a.getColor(index, mRingColor);
					break;
				case R.styleable.ProgressView_progressColor:
					mProgressColor = a.getColor(index, mProgressColor);
					break;
				case R.styleable.ProgressView_useRing:
					mUseRing = a.getBoolean(index, true);
					break;
				case R.styleable.ProgressView_ringWidth:
					mRingWidth = a.getDimensionPixelSize(index, (int)mRingWidth);
					break;
				case R.styleable.ProgressView_startAngle:
					mStartAngle = a.getInteger(index, mStartAngle);
					break;
				case R.styleable.ProgressView_progressStyle:
					mProgressStyle = a.getInteger(index, mProgressStyle);
					break;
				case R.styleable.ProgressView_progress:
					mCurProgress = a.getInteger(index, mCurProgress);
					break;
				}
			}
			
			a.recycle();
		}
		
	}
	
	private void init()
	{
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		setGravity(Gravity.CENTER);
		setProgress(mCurProgress);
		setTextSize(12);
		setBackgroundColor(Color.YELLOW);
		
		test();
	}
	
	public void setProgress(int progress)
	{
		if (mShowProgress)
		{
			progress = Math.min(progress, 100);
			progress = Math.max(0, progress);
			
			setText(progress + "%");
		}
		else
		{
			setText("");
		}
		mCurProgress = progress;
		invalidate();
		
	}
	
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	};
	
	RectF oval = new RectF();
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		
		if (STYLE_RECT == (mProgressStyle & STYLE_MASK))
		{
			drawRect(canvas);
		}
		else
		{
			drawCircle(canvas);
		}
		
		
		
		super.onDraw(canvas);
	}

	/**
	 * ���εĽ�����
	 * @param canvas
	 */
	private void drawRect(Canvas canvas) {
		// TODO Auto-generated method stub
		int w = getWidth();
		int h = getHeight();
		float l = 0;
		float t = 0;
		float space = 4;
		//�õ����ֿ��
		float textWidth = getPaint().measureText("100%");
		float textHeight = getPaint().measureText("100%");
		
		switch (mProgressStyle & STYLE_MASK_PROGRESS)
		{
		case STYLE_RECT_PROGRESS_OUT:
		{
			switch (mProgressStyle)
			{
			
			case STYLE_RECT_PROGRESS_OUT_LEFT:
			{
				setGravity(Gravity.LEFT | getGravity());
				
				//���ȴ�0~100 ʹ����󳤶ȣ�Ԥ��һ�㳤��
				w -=  (textWidth + space);
				
				l = l + textWidth;
				
			}
				break;
			case STYLE_RECT_PROGRESS_OUT_RIGHT:
			{
				setGravity(Gravity.RIGHT | getGravity());
				
				//���ȴ�0~100 ʹ����󳤶ȣ�Ԥ��һ�㳤��
				w -=  (textWidth + space);
			}
				break;
			case STYLE_RECT_PROGRESS_OUT_TOP:
			{
				setGravity(Gravity.TOP | getGravity());
				
				h -=  (textHeight + space);
				
				t += textHeight;
			}
				break;
			case STYLE_RECT_PROGRESS_OUT_BOTTOM:
			{
				setGravity(Gravity.BOTTOM | getGravity());
				
				h -=  (textHeight + space);
				
			}
				break;
			}
			
		}
		case STYLE_RECT_PROGRESS_IN:
		{
			
			float cw =  Math.min(w, h) * RING_WIDTH_RATIO;
//			mRingWidth = mUseRing ? mRingWidth : 0;
			
			RectF outRect = new RectF(l, t, l+w, t+h);
			float inLeft = l + mRingWidth;
			float inTop = t + mRingWidth;
			float inW = w - 2 * mRingWidth;
			float inH = h - 2 * mRingWidth;
			
			RectF inRect = new RectF(inLeft, inTop, 
					inLeft + inW , inTop + inH);
			
			float progress = mCurProgress / 100.0f;
			
			RectF progressRect = new RectF(inLeft, inTop,
					inW * progress + inLeft, inTop + inH);
			float rx = cw;
			float ry = rx;
			
			//���
			mPaint.setColor(mProgressColor);
			canvas.drawRoundRect(outRect, rx, ry, mPaint);
			
			//�ڲ�
			mPaint.setColor(mFillColor);
			canvas.drawRoundRect(inRect, rx, ry, mPaint);
			//����
			mPaint.setColor(mProgressColor);
			canvas.drawRoundRect(progressRect, 0, 0, mPaint);
		}
			break;
		}
	}

	/**
	 * Բ�εĽ�����
	 * @param canvas
	 */
	private void drawCircle(Canvas canvas) {
		// TODO Auto-generated method stub
		int w = getWidth();
		int h = getHeight();
		
		int size = Math.min(w, h);
		int outerRadius = size / 2;
		int innerRadius = outerRadius;
		
		float cx = w / 2;
		float cy = h / 2;
		
		float startAngle = mStartAngle;
		float sweepAngle = (float) (DEGREE_360 * mCurProgress / 100.0);
		boolean useCenter = true;
		
		float l = cx - outerRadius;
		float t = cy - outerRadius;
		
		if (mUseRing || mProgressStyle == STYLE_RING)
		{
			mRingWidth = (int) (outerRadius * RING_WIDTH_RATIO);
			
			mPaint.setColor(mRingColor);
			canvas.drawCircle(cx, cy, outerRadius, mPaint);
			
			innerRadius = (int) (outerRadius - mRingWidth);
			
			if (mProgressStyle != STYLE_RING)
			{
				size -= 2 * mRingWidth;
				l = cx - innerRadius;
				t = cy - innerRadius;
			}
		}
		
		
		oval.set(l, t, l + size, t + size);
		
		switch (mProgressStyle)
		{
		case  STYLE_RING:
		{
			mPaint.setColor(mProgressColor);
			canvas.drawArc(oval, startAngle, sweepAngle, useCenter, mPaint);
			
			mPaint.setColor(mFillColor);
			canvas.drawCircle(cx, cy, innerRadius, mPaint);
		}
			break;
			
		case STYLE_WATER:
			startAngle = DEGREE_90 - sweepAngle / 2;
			useCenter = false;
		case STYLE_SWEEP:
		{
			mPaint.setColor(mFillColor);
			canvas.drawCircle(cx, cy, innerRadius, mPaint);
			
			mPaint.setColor(mProgressColor);
			canvas.drawArc(oval, startAngle, sweepAngle, useCenter, mPaint);
		}
			break;
		}
	}

	public void test()
	{
		new AsyncTask<String, Integer, Integer>()
        {

			@Override
			protected Integer doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				int progress = 0;
				
				while (progress <= 100)
				{
					Random r = new Random(System.currentTimeMillis());
					
					progress+= r.nextInt(5);
					publishProgress(progress);
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				return 0;
			}
			
			@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
//				super.onProgressUpdate(values);
				setProgress(values[0]);
			}
        	
        }.execute("");
	}
}
