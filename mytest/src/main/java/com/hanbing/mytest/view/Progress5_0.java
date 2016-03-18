/**
 * 
 */
package com.hanbing.mytest.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author hanbing
 * @date 2015-11-19
 */
public class Progress5_0 extends View {

    float minAngle = 0;
    float maxAngle = 360 - minAngle;
    
    int radius = 0;
    int thickNess = 10;
    int thickColor = Color.RED;
    
    float startAngle = 0;
    float endAngle = 0;
    float sweepAngle = minAngle;
    
    final float DEFAULT_SWEEP_SPEED = 6;
    final float DEFAULT_START_SPEED = 3;
    
    float speedStartAngle = DEFAULT_START_SPEED;
    float speedSweepAngle = DEFAULT_SWEEP_SPEED;
    int duration = 5;
    
    static final int MODE_0 = 0; //首尾相接后sweep减，显示效果为两端缩短在展开，如此循环
    static final int MODE_1 = 1; //首尾相接后sweep减，速度=start的速度，
    
    int mode = MODE_1;
   
    
    boolean isRun;
    
    Handler handler = new Handler();
    
    
    /**
     * @param context
     */
    public Progress5_0(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
	init();
    }

    /**
     * @param context
     * @param attrs
     */
    public Progress5_0(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
	init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public Progress5_0(Context context, AttributeSet attrs, int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	// TODO Auto-generated constructor stub
	init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP) public Progress5_0(Context context, AttributeSet attrs, int defStyleAttr,
	    int defStyleRes) {
	super(context, attrs, defStyleAttr, defStyleRes);
	// TODO Auto-generated constructor stub
	init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        
        if (changed)
        {
            radius = Math.min(getWidth(), getHeight()) / 4;
        }
    }
    
    
    /**
     * 
     */
    private void init() {
	// TODO Auto-generated method stub

    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        
        
        if (radius <= 0)
            return;
        
        
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        
        
        RectF rect = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        
        LinearGradient gradient = new LinearGradient(rect.left, rect.top, rect.right, rect.bottom, 0xff00ffff, Color.WHITE, TileMode.REPEAT);
        
        Paint paint = new Paint();
        paint.setShader(gradient);
        paint.setColor(thickColor);
        paint.setStrokeWidth(thickNess);
        paint.setStyle(Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawArc(rect, startAngle, sweepAngle, false, paint);
        
        
       
        
        
        rect.left = centerX;
        rect.bottom = centerY;
        
       
    }
    
    
    /* (non-Javadoc)
     * @see android.view.View#computeScroll()
     */
    @Override
    public void computeScroll() {
    	// TODO Auto-generated method stub
    	
    	
    	
    	if (sweepAngle + speedSweepAngle > 360)
    	{
    		switch (mode)
    		{
    		case MODE_0:
    			speedSweepAngle = -DEFAULT_SWEEP_SPEED;
    		break;
    		case MODE_1:
    			speedSweepAngle = -DEFAULT_START_SPEED;
    			speedStartAngle = DEFAULT_SWEEP_SPEED;
    			break;
    		}
    		
    		
    	} else if (sweepAngle + speedSweepAngle < 0)
    	{
    		switch (mode)
    		{
    		case MODE_0:
    			speedSweepAngle = DEFAULT_SWEEP_SPEED;
    		break;
    		case MODE_1:
    			speedSweepAngle = DEFAULT_SWEEP_SPEED;
    			speedStartAngle = DEFAULT_START_SPEED;
    			break;
    		}
    		
    	}
    	
    	{
    		startAngle += speedStartAngle;
    		sweepAngle += speedSweepAngle;
    	}
    	
    	startAngle %= 360;
    	
    	
//    	LogUtils.e("startAngle=" + startAngle + ",endAngle=" + endAngle + ",sweepAngle=" + sweepAngle);
    	
    	
    	postInvalidateDelayed(duration);
    }
    
    private void start()
    {
    	
    	isRun = true;
	
    }
    
    private void stop()
    {
	isRun = false;
    }
    
    @Override
    protected void onAttachedToWindow() {
        // TODO Auto-generated method stub
        super.onAttachedToWindow();
        start();
    }
    
    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
	stop();
        super.onDetachedFromWindow();
    }
}
