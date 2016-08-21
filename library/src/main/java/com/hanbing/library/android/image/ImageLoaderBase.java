/**
 *  
 */
package com.hanbing.library.android.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;


/**
 * image loader
 * 
 * @author hanbing
 * @date 2015-7-10
 */
public abstract class ImageLoaderBase extends IImageLoader {

	Context mContext;

	/**
	 * 初始化
	 *
	 * @param context
	 */
	public ImageLoaderBase(Context context) {
		this.mContext = context;
	}

	/**
	 * 缓存文件的目录
	 * @param context
	 * @return
	 */
	public static String createDefaultCachePath(Context context) {
		String path = context.getExternalCacheDir().getAbsolutePath();
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			path = context.getCacheDir().getAbsolutePath();
		}

		path = path + "/thumbnails";

		return path;
	}

	ImageLoaderListener mDefaultImageLoaderListener = new ImageLoaderListener() {
		@Override
		public void onLoadStarted(View view, String uri) {

		}

		@Override
		public void onLoading(View view, String uri, long total, long current) {

		}

		@Override
		public void onLoadCompleted(final View view, final String uri, final Bitmap bm) {
			view.post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (view instanceof ImageView) {
						((ImageView) view).setImageBitmap(bm);
					} else {
						view.setBackgroundDrawable(new BitmapDrawable(view
								.getResources(), bm));
					}
				}
			});
		}

		@Override
		public void onLoadFailed(View view, String uri) {

		}

		@Override
		public void onLoadCancelled(View view, String uri) {

		}
	};

	public void displayImage(View view, String uri) {
		displayImage(view, uri, 0);
	}

	public void displayImage(View view, String uri, int defaultResId) {
		displayImage(view, uri, 0, 0, defaultResId);
	}

	public void displayImage(View view, String uri,
			ImageLoaderListener lsner) {
		displayImage(view, uri, 0, 0, 0, lsner);
	}

	public void displayImage(View view, String uri, int width,
			int height, int defaultResId) {
		displayImage(view, uri, width, height, defaultResId,
				mDefaultImageLoaderListener);
	}

	public abstract void displayImage(final View view, String uri,
			final int width, final int height, int defaultResId,
			final ImageLoaderListener lsner) ;

	public Bitmap loadImageSync(String uri) {
		return loadImageSync(uri, 0, 0);
	}

}
