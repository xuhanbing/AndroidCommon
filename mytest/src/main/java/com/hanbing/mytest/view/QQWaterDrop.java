package com.hanbing.mytest.view;


import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class QQWaterDrop extends View {
	
	int mOriginSize = 20;
	int mMoveDistance = 100;
	
	Point mOriginPos = new Point();
	

	public QQWaterDrop(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public QQWaterDrop(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public QQWaterDrop(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
	    super.onLayout(changed, left, top, right, bottom);
	    
	    if (mIsFirst)
	    {
		mWaterPoint = new PointF(getWidth() / 2, getHeight() / 2);
		mWaterPointN = new PointF(mWaterPoint.x, mWaterPoint.y);
		mWaterBigRadius = getWidth() / 16;
		mWaterSmallRadius = mWaterBigRadius *3/ 4;
		mIsFirst = false;
	    }
	};
	
	boolean mIsFirst = true;
	PointF mWaterPoint;
	PointF mWaterPointN = null;
	float mWaterBigRadius = 50;
	float mWaterSmallRadius = mWaterBigRadius / 2;
	boolean mIsTouchWater = false;
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
//		test1(canvas);
		
//		test2(canvas);
	    
	    
	    super.onDraw(canvas);
	    drawWater(canvas);
	    
//	    Paint mPaint = new Paint();
//	    mPaint .setColor(Color.parseColor("#ff0000"));
//	    mPaint.setStyle(Style.STROKE);
//	        //为了增强效果对比，我们绘制出三个控制点之间对应的直线
//	        Path mPath = new Path();
//		mPath .lineTo(100,50);
//	        mPath.lineTo(200, 300);
//	        mPath.lineTo(400, 220);
//	        canvas.drawPath(mPath, mPaint);
//	        mPath = new Path();
//	        //绘制贝塞尔曲线
//	        mPath.cubicTo(100, 50, 200, 300, 400, 220);
//	        canvas.drawPath(mPath,mPaint);
	}
	
	
	/**
	 * @param canvas
	 */
	private void drawWater(Canvas canvas) {
	    // TODO Auto-generated method stub
	    
	    Paint paint = new Paint();
	    paint.setAntiAlias(true);
	    paint.setColor(Color.RED);
	    
	    paint.setStyle(Style.FILL);
	    paint.setStrokeWidth(1);
	    
	    Path path = new Path();
	    
	    
	    if (mIsTouchWater)
	    {
		float scale = 1 - calcDistance(mWaterPointN, mWaterPoint) / getWidth() ;
		
		float radius = mWaterSmallRadius * scale;
		
		float tan = (mWaterPointN.y - mWaterPoint.y) / (mWaterPointN.x - mWaterPoint.x);
		float s = (float) (Math.atan(tan) / Math.PI);
		Log.e("", "q = " + s);
		float sin = (float) Math.sin(Math.atan(tan));
		float cos = (float) Math.cos(Math.atan(tan));
		
		
		float bx1 = mWaterPointN.x - ( mWaterBigRadius * sin);
		float bx2 = mWaterPointN.x + (mWaterBigRadius * sin);
		float by1 = mWaterPointN.y + (mWaterBigRadius * cos);
		float by2 = mWaterPointN.y - (mWaterBigRadius * cos);
		
		float hx1 = mWaterPoint.x - (radius * sin);
		float hx2 = mWaterPoint.x + (radius * sin);
		float hy1 = mWaterPoint.y + (radius * cos);
		float hy2 = mWaterPoint.y - (radius * cos);
		
		
		float cx = (mWaterPointN.x + mWaterPoint.x) * 1 / 2;
		float cy = (mWaterPointN.y + mWaterPoint.y) * 1 / 2;
		
		path.moveTo(bx1, by1);
		path.quadTo(cx, cy, hx1, hy1);
		path.lineTo(hx2, hy2);
		path.quadTo(cx, cy, bx2, by2);
		path.lineTo(bx1, by1);
		
		
		canvas.drawPath(path, paint);
		
		path.reset();
		
		path.addCircle(mWaterPointN.x, mWaterPointN.y, mWaterBigRadius, Direction.CW);
		path.addCircle(mWaterPoint.x, mWaterPoint.y, radius, Direction.CW);
		canvas.drawPath(path, paint);
		
	    } else {
		path.addCircle(mWaterPoint.x, mWaterPoint.y, mWaterBigRadius, Direction.CW);
		canvas.drawPath(path, paint);
	    }
	    
	    
	}

	private void reset(){
	    mIsTouchWater = false;
	}
	
	
	ValueAnimator mValueAnimator = null;
	PointF mReleasePoint = null;
	private void startAnimation()
	{
	    if (mIsTouchWater)
	    {
		mReleasePoint = new PointF(mWaterPointN.x, mWaterPointN.y);
		
		mValueAnimator = new ValueAnimator();
		mValueAnimator.setDuration((long) calcDistance(mWaterPoint, mReleasePoint));
		mValueAnimator.setFloatValues(mWaterPointN.x, mWaterPoint.x);
		mValueAnimator.addListener(new AnimatorListener() {
		    
		    @Override
		    public void onAnimationStart(Animator animation) {
			// TODO Auto-generated method stub
			
		    }
		    
		    @Override
		    public void onAnimationRepeat(Animator animation) {
			// TODO Auto-generated method stub
			
		    }
		    
		    @Override
		    public void onAnimationEnd(Animator animation) {
			// TODO Auto-generated method stub
			
		    }
		    
		    @Override
		    public void onAnimationCancel(Animator animation) {
			// TODO Auto-generated method stub
			
		    }
		});
		
		mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {
		    
		    @Override
		    public void onAnimationUpdate(ValueAnimator animation) {
			// TODO Auto-generated method stub
			float x = (Float) animation.getAnimatedValue();
			
			float tan = (mReleasePoint.y - mWaterPoint.y ) / (mReleasePoint.x - mWaterPoint.x);
			
			float y = tan * (x - mWaterPoint.x) + mWaterPoint.y;
			
			mWaterPointN = new PointF(x, y);
			postInvalidate();
		    }
		});
		
		mValueAnimator.start();
	    }
	}
	
	
	private void test1(Canvas canvas) {
		// TODO Auto-generated method stub
		int w = getWidth();
		int h = getHeight();
		
		
		Paint paint = new Paint();
		mOriginPos.x = w / 2;
		mOriginPos.y = h / 2;
		
		int centerX = w / 2;
		int centerY = h / 2;
		
//		canvas.drawCircle(w/2, h/2, mOriginSize, paint);
		canvas.drawLine(0, centerY, w, centerY, paint);
		
		paint.setTextSize(50);
		paint.setTextAlign(Align.CENTER);
		FontMetrics fm = paint.getFontMetrics();
		
		float baseLine = centerY;
		
		
		float ascent = fm.ascent;
		float top = fm.top;
		float bottom = fm.bottom;
		float descent = fm.descent;
		float x = 0;
		float y = 0;      
		float width = w/4;
		baseLine = baseLine - ((bottom - top) / 2 + top);
//		baseLine + ((baseLine+bottom) - (baseLine + top))/2;
//		baseLine + (bottom - top)/2;
		
		paint.setTextSize(15);
		
		paint.setColor(Color.RED);
		x = 0;
		y = baseLine  + ascent;
		canvas.drawLine(x, y, x + width, y, paint);
		canvas.drawText("ascent", x, y, paint);
		
		paint.setColor(Color.GREEN);
		x +=width;
		y = baseLine  + top;
		canvas.drawLine(x, y, x + width, y, paint);
		canvas.drawText("top", x, y, paint);
		
		paint.setColor(Color.BLUE);
		x +=width;
		y = baseLine  + bottom;
		canvas.drawLine(x, y, x + width, y, paint);
		canvas.drawText("bottom", x, y, paint);
		
		paint.setColor(0xff00ffff);
		x +=width;
		y = baseLine  + descent;
		canvas.drawLine(x, y, x + width, y, paint);
		canvas.drawText("descent", x, y, paint);
		
		paint.setTextSize(50);
		paint.setColor(Color.BLACK);
		canvas.drawText("Hello world! Go go go!", centerX, baseLine, paint);
		
	}

	private void test2(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);  
	    paint.setStrokeWidth(3);  
	    paint.setTextSize(80);  
	    FontMetricsInt fmi = paint.getFontMetricsInt();  
	    String testString = "测试：ijkJQKA:1234";  
	    Rect bounds1 = new Rect();  
	    paint.getTextBounds("测", 0, 1, bounds1);  
	    Rect bounds2 = new Rect();  
	    paint.getTextBounds("测试：ijk", 0, 6, bounds2);  
	    // 随意设一个位置作为baseline  
	    int x = 0;  
	    int y = getHeight()/2;  
	    // 把testString画在baseline上  
	    canvas.drawText(testString, x, y, paint);  
	    // bounds1  
	    paint.setStyle(Style.STROKE);  // 画空心矩形  
	    canvas.save();  
	    canvas.translate(x, y);  // 注意这里有translate。getTextBounds得到的矩形也是以baseline为基准的  
	    paint.setColor(Color.GREEN);          
	    canvas.drawRect(bounds1, paint);  
	    canvas.restore();  
	    // bounds2  
	    canvas.save();  
	    paint.setColor(Color.MAGENTA);  
	    canvas.translate(x, y);  
	    canvas.drawRect(bounds2, paint);  
	    canvas.restore();  
	    // baseline  
	    paint.setColor(Color.RED);  
	    canvas.drawLine(x, y, 1024, y, paint);  
	    // ascent  
	    paint.setColor(Color.YELLOW);  
	    canvas.drawLine(x, y+fmi.ascent, 1024, y+fmi.ascent, paint);  
	    // descent  
	    paint.setColor(Color.BLUE);  
	    canvas.drawLine(x, y+fmi.descent, 1024, y+fmi.descent, paint);  
	    // top  
	    paint.setColor(Color.DKGRAY);  
	    canvas.drawLine(x, y+fmi.top, 1024, y+fmi.top, paint);  
	    // bottom  
	    paint.setColor(Color.GREEN);  
	    canvas.drawLine(x, y+fmi.bottom, 1024, y+fmi.bottom, paint);  
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
	    
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
	// TODO Auto-generated method stub
	    doTouch(event);
	return true;
	}

	/**
	 * @param event
	 */
	protected void doTouch(MotionEvent event) {
	   
	    mWaterPointN = new PointF(event.getX(), event.getY());
	    if (MotionEvent.ACTION_DOWN == event.getAction())
	    {
		if (null != mValueAnimator && mValueAnimator.isRunning())
		{
		    mValueAnimator.end();
		    mValueAnimator = null;
		}
		
		if (calcDistance(mWaterPoint, mWaterPointN) < mWaterBigRadius)
		{
		    Log.e("", "touch in");
		    mIsTouchWater = true;
		} else {
		    Log.e("", "touch out");
		    mIsTouchWater = false;
		}
		
	    } else if (MotionEvent.ACTION_UP == event.getAction())
	    {
		startAnimation();
		mWaterPointN = new PointF(mWaterPoint.x, mWaterPoint.y);
	    }
	    
	    postInvalidate();
	}
	
	private float calcDistance(PointF p1, PointF p2)
	{
	    float dx = p1.x - p2.x;
	    float dy = p1.y - p2.y;
	    
	    return (float)Math.sqrt(dx * dx + dy * dy);
	}
	

}
