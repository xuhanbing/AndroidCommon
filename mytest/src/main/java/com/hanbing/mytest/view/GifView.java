package com.hanbing.mytest.view;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.hanbing.mytest.view.gif.AnimatedGifDrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.ImageView;

public class GifView extends ImageView {
	
	Movie mMovie;
	int mWidth = 0;
	int mHeight = 0;
	private float mScale;
	private int mMeasuredMovieWidth;
	private int mMeasuredMovieHeight;
	private float mLeft;
	private float mTop;
	
	Handler mHandler = new Handler();

	public GifView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public GifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public GifView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init()
	{
//		String path = Environment.getExternalStorageDirectory().getAbsolutePath()
//				+ "/00.gif";
//		
//		try {
//			
//			setGifInputStream(new FileInputStream(path));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		setGifInputStream(getResources().openRawResource(R.raw.a00));
//		setBackgroundColor(Color.YELLOW);
//		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String address = "http://ww3.sinaimg.cn/bmiddle/dc66b248gw1emr1kcc3eqg209g05b4qq.gif";//"http://s1.dwstatic.com/group1/M00/5C/E1/428aab0b55f2a0f2af91eee76251b51e.gif";
				URL url;
				InputStream is = null;
				try {
					url = new URL(address);
					is = url.openStream();
					
					setGifInputStream(is);
					
				}  catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//getResources().openRawResource(R.raw.test);
				
			}
		}).start();
	}
	
	public void setGifInputStream(InputStream is)
	{
		int size = 2048;
		InputStream bis = new BufferedInputStream(is, size);
		bis.mark(size);
		boolean useDft = false;
		
		if (useDft)
		{
			//��Ӵ��������޷���ʾ����gif
			
			
			mMovie = Movie.decodeStream(bis);
			
			if (null != mMovie)
			{
				mWidth = mMovie.width();
				mHeight = mMovie.height();
				
	//			System.out.println("duration=" + mMovie.duration());
	//			System.out.println("[" + mWidth + "," + mHeight + "]");
				
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						requestLayout();
						invalidate();
					}
				});
			}
		}
		else
		{
			final AnimatedGifDrawable gif = new AnimatedGifDrawable(bis, new AnimatedGifDrawable.UpdateListener() {
			
				@Override
				public void update() {
					// TODO Auto-generated method stub
					postInvalidate();
				}
			});
			
	        mHandler.post(new Runnable() {
	            public void run() {
	            	gif.nextFrame();
	                // Set next with a delay depending on the duration for this frame 
	                mHandler.postDelayed(this, gif.getFrameDuration());
	            }
	        });
			setImageDrawable(gif);
//			gif.start();
		}
		
	}
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
        
		if (null != mMovie)
		{
			int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
			int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
			
			System.out.println("mesure 1: [" + maxWidth + "," + maxHeight + "]");
			 
	        float scaleW = mWidth * 1.0f / maxWidth;
	        float scaleH = mHeight * 1.0f / maxHeight;
	        mScale = Math.min(1f / scaleW, 1f / scaleH);
	        mMeasuredMovieWidth = (int) (mWidth * mScale);
	        mMeasuredMovieHeight = (int) (mHeight * mScale);
//	        
//	        setMeasuredDimension(mMeasuredMovieWidth, mMeasuredMovieHeight);
	        
	        System.out.println("mesure : [" + mMeasuredMovieWidth + "," + mMeasuredMovieHeight + "]");
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
		else
		{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	
	@Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        mLeft = (getWidth() - mMeasuredMovieWidth) / 2f;
//        mTop = (getHeight() - mMeasuredMovieHeight) / 2f;
//        mVisible = getVisibility() == View.VISIBLE;
        
        System.out.println("onLayout 1: [" + getWidth() + "," + getHeight() + "]");
    }
	
	private long mStartTime = 0;
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		super.onDraw(canvas);
		
		if (null != mMovie)
		{
			int duration = mMovie.duration();
			if (0 == duration)
			{
				duration = 1000;
			}
			
			{
				long time = SystemClock.uptimeMillis();
				if (mStartTime == 0)
				{
					mStartTime = time;
				}
				
				int relTime = (int) ((time-mStartTime)% duration);
				
				mMovie.setTime(relTime);
				
				float x = 0;
				float y = 0;
				
				int w = getWidth();
				int h = getHeight();
				
				x = (w - mMeasuredMovieWidth)/2 / mScale;
				y = (h - mMeasuredMovieHeight)/2 / mScale;
				
//				System.out.println("x,y=[" + x + "," + y + "],mScale=" + mScale);
				
				canvas.save(Canvas.MATRIX_SAVE_FLAG);
		        canvas.scale(mScale, mScale);
				
				mMovie.draw(canvas, x, y);
				canvas.restore();
				
				invalidate();
			}
			
		}
	}
}
