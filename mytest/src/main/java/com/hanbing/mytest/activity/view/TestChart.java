/**
 * 
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.mytest.activity.BaseActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * @author hanbing
 * 
 */
public class TestChart extends BaseActivity {

	/* (non-Javadoc)
	 * @see com.xhb.mytest.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		final Chart chart = new Chart(this);
		
		chart.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				chart.checkTouchEvent(event);
				return false;
			}
		});
		setContentView(chart);
	}
	
	class Chart extends View {

		/**
		 * @param context
		 * @param attrs
		 * @param defStyleAttr
		 */
		public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param context
		 * @param attrs
		 */
		public Chart(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param context
		 */
		public Chart(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
		float[] values = {0.1f, 0.3f, 0.1f, 0.2f, 0.3f};
		
		int selectIndex = 0;
		PointF center = null;
		float radius = 0;
		
		RectF oval;
		
		/* (non-Javadoc)
		 * @see android.view.View#onMeasure(int, int)
		 */
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			// TODO Auto-generated method stub
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			
			center = new PointF(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
			
			radius = Math.min(getMeasuredWidth() / 2, getMeasuredHeight() / 2) / 4;
			
			
			oval = new RectF(center.x - radius, center.y - radius, center.x + radius, center.y + radius);
			
		}
		
		/* (non-Javadoc)
		 * @see android.view.View#onDraw(android.graphics.Canvas)
		 */
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
//			super.onDraw(canvas);
			
			
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			paint.setStyle(Style.FILL_AND_STROKE);
//			paint.setStrokeWidth(1);
			paint.setAntiAlias(true);
			
			
//			oval.set(0, 0, 200, 200);
			
			FontMetrics fm = paint.getFontMetrics();
			float ascent = fm.ascent;
			float bottom = fm.bottom;
			float descent = fm.descent;
			float top = fm.top;
			
			float dy = (bottom - ascent) / 2 - descent;
			
			float count = 0;
			
			
			RectF rect = new RectF();
			
			int index = 0;
			float move = radius / 4; //沿轴线移动距离
			float moveX = 0;
			float moveY = 0;
			for (float value : values)
			{
				int color = (int) (0xffffff * Math.random());
				
				color |= 0xff000000;
				
				paint.setColor(color);
				
				float startAngle = (float) (count * 360);
				float sweepAngle = (float) (value * 360);
				
				float angle = startAngle + sweepAngle / 2;
				
				angle = (float) ( (Math.PI * 1) * angle / 180);
				
				rect.set(oval);
				
				
				moveX = (float) (move * Math.cos(angle));
				moveY = (float) (move* Math.sin(angle));
				if (index == selectIndex)
				{
					rect.left += moveX;
					rect.right += moveX;
					rect.top += moveY;
					rect.bottom += moveY;
				}
				
				
				float cx = (rect.left + rect.right)/2;
				float cy = (rect.top + rect.bottom)/2;
				float x = (float) (cx + radius* Math.cos(angle));
				float y = (float) (cy + radius* Math.sin(angle));
				
				canvas.drawArc(rect, startAngle, sweepAngle, true, paint);
				
				paint.setColor(Color.BLACK);
				paint.setTextAlign(Align.CENTER);
				
				/**
				 * 与圆心的中点
				 */
				x = (x + cx) / 2;
				y = (y + dy + cy) / 2;
				
				canvas.drawText(Math.round(100 * value) + "%", x, y, paint);
				
				index++;
				count+= value;
			}
		}
		
		
		/* (non-Javadoc)
		 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
		 */
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			
			return super.onTouchEvent(event);
		}
		
		public void checkTouchEvent(MotionEvent event)
		{
//			if (MotionEvent.ACTION_UP == event.getAction())
			{
				/**
				 * 判断在哪一块
				 */
				
				float x = event.getX();
				float y = event.getY();
				
				float dx = x - center.x;
				float dy = y - center.y;
				
				/**
				 * 在圆内
				 */
				float r = (float) Math.sqrt(dx * dx + dy * dy);
				
				if (r <= radius)
				{
					float angle = 0.0f;
					if (Math.abs(dx) <= 0.00001)
					{
						if (dy >= 0)
						{
							angle = 90;
						} else {
							angle = 270;
						}
					} else {
						
						float tan = dy / dx;
						
						angle = (float) Math.atan(tan);
						angle = (float) (angle / Math.PI * 180);
						
						if (angle < 0)
						{
							angle += 180;
						}
						
						if (dy < 0)
						{
							angle += 180;
						}
						
						angle += 360;
						angle %= 360;
					}
					
					float ratio = angle / 360;
					
					float count = 0;
					int index = 0;
					for (float value : values) 
					{
						if (ratio >= count && ratio <= value + count)
						{
							break;
						}
						
						count += value;
						index++;
					}
					
					selectIndex = index;
					
					postInvalidate();
					
				}
				
			}
		}
	}
}
