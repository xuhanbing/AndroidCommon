/**
 * 
 */
package com.hanbing.library.android.view.pager;

import java.util.List;

import com.hanbing.library.android.image.ImageLoader;
import com.hanbing.library.android.util.LogUtils;

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

	public interface OnItemClickListener {
		void onClick(int position);
	}

	static final int LOOP_DURATION = 3000;

	OnItemClickListener mOnItemClickListener;
	List<String> mUrlList;
	long mDuration = LOOP_DURATION;

	protected class DefaultPageAdapter extends PagerAdapter {

		SparseArray<ImageView> imageViews;

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			// TODO Auto-generated method stub
			if (null == imageViews)
				imageViews = new SparseArray<ImageView>();

			ImageView imageView = imageViews.get(position);

			if (null == imageView) {
				imageView = new ImageView(getContext());
				imageView.setScaleType(ScaleType.CENTER_CROP);
				imageViews.put(position, imageView);
			}

			showPicture(position, imageView);

			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickItem(position);
				}
			});

			container.addView(imageView);

			return imageView;
		}

		protected void showPicture(int position, ImageView imageView) {
			ImageLoader instance = ImageLoader.getInstance(getContext());
			instance.displayImage(imageView, mUrlList.get(position));
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

	};

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
		init();
	}

	/**
	 * @param context
	 */
	public BannerViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {


		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_MOVE:
						mHandler.removeCallbacks(mLoopPlayer);
						break;
					case MotionEvent.ACTION_UP:
						mHandler.removeCallbacks(mLoopPlayer);
						mHandler.postDelayed(mLoopPlayer, LOOP_DURATION);
						break;

				}
				return false;
			}
		});


	}
	
	/**
	 * 
	 */
	protected void setAdapter() {
		setAdapter(new DefaultPageAdapter());
	}



	protected void onClickItem(int position) {
		if (null != mOnItemClickListener) mOnItemClickListener.onClick(position);
	}

	/**
	 * set urls
	 * @param urlList
	 * @return
	 */
	public BannerViewPager setUrlList(List<String> urlList) {
		this.mUrlList = urlList;
		setAdapter();

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

	public List<String> getUrlList() {
		return mUrlList;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}

	/**
	 * start 
	 */
	public void start(){
		stop();
		if (null != getAdapter() && getAdapter().getCount() > 1)
			mHandler.postDelayed(mLoopPlayer, LOOP_DURATION);
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
