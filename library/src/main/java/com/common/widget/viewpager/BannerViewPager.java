/**
 * 
 */
package com.common.widget.viewpager;

import java.util.List;

import com.common.image.ImageLoader;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * @author hanbing
 * @date 2016年1月15日
 */
public class BannerViewPager extends ViewPager {

	static final int LOOP_DURATION = 3000;

	List<String> mUrlList;
	long mDuration = LOOP_DURATION;

	Handler mHandler = new Handler();

	Runnable mLoopPlayer = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int position = getCurrentItem();

			position++;

			if (position >= getAdapter().getCount()) {
				position = 0;
			}

			setCurrentItem(position);

			mHandler.postDelayed(this, mDuration);
		}
	};

	/**
	 * @param context
	 * @param attrs
	 */
	public BannerViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public BannerViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	void init() {
		setAdapter();

		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mHandler.removeCallbacks(mLoopPlayer);
					break;
				case MotionEvent.ACTION_UP:
					mHandler.postDelayed(mLoopPlayer, LOOP_DURATION);
					break;

				}
				return false;
			}
		});

		mHandler.postDelayed(mLoopPlayer, LOOP_DURATION);
	}
	
	/**
	 * 
	 */
	protected void setAdapter() {
		setAdapter(new PagerAdapter() {

			SparseArray<ImageView> imageViews;

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				// TODO Auto-generated method stub
				container.removeView((View) object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				if (null == imageViews)
					imageViews = new SparseArray<ImageView>();

				ImageView imageView = imageViews.get(position);

				if (null == imageView) {
					imageView = new ImageView(getContext());
					imageView.setScaleType(ScaleType.CENTER_CROP);
					imageViews.put(position, imageView);
				}

				ImageLoader instance = ImageLoader.getInstance(getContext());
				instance.displayImage(imageView, mUrlList.get(position));

				container.addView(imageView);

				return imageView;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return null == mUrlList ? 0 : mUrlList.size();
			}
		});
	}

	/**
	 * set urls
	 * @param urlList
	 * @return
	 */
	public BannerViewPager setUrlList(List<String> urlList) {

		this.mUrlList = urlList;
		
		return this;
	}

	/**
	 * set duration 
	 * @param duration
	 * @return
	 */
	public BannerViewPager setDuration(long duration) {
		this.mDuration = duration;
		
		return this;
	}

	/**
	 * start 
	 */
	public void start(){
		stop();
		
		init();
	}
	
	/**
	 * stop
	 */
	public void stop() {
		mHandler.removeCallbacks(mLoopPlayer);
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager#onDetachedFromWindow()
	 */
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		stop();
		super.onDetachedFromWindow();
	}
}
