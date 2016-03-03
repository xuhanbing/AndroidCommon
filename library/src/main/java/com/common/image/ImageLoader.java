/**
 *  
 */
package com.common.image;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;

/**
 * image loader
 * 
 * @author hanbing
 * @date 2015-7-10
 */
public class ImageLoader extends ImageLoaderInterface {

	static enum ImageLoaderType {
		UNIVERSUALIMAGELOADER
	}

	static ImageLoaderType mImageLoaderType = ImageLoaderType.UNIVERSUALIMAGELOADER;

	static ImageLoader mImageLoader;

	ImageLoaderImpl mImageLoaderImpl;

	public static ImageLoader getInstance(Context context) {
		if (null == mImageLoader) {
			mImageLoader = new ImageLoader(context);
		}

		return mImageLoader;
	}

	public void init(Context context) {

		switch (mImageLoaderType) {
		case UNIVERSUALIMAGELOADER:
			mImageLoaderImpl = new UniversalImageLoader();
			break;
		default:
			mImageLoaderImpl = ImageLoaderImpl.createDefault();
			break;
		}

		mImageLoaderImpl.init(context);
	}

	public ImageLoader(Context context) {

		init(context);

	}

	public void displayImage(ImageView imageView, String uri) {
		displayImage(imageView, uri, 0);
	}

	public void displayImage(ImageView imageView, String uri, int defaultResId) {
		displayImage(imageView, uri, 0, 0, defaultResId);
	}

	public void displayImage(ImageView imageView, String uri,
			ImageLoaderListener lsner) {
		displayImage(imageView, uri, 0, 0, 0, lsner);
	}

	public void displayImage(ImageView imageView, String uri, int width,
			int height, int defaultResId) {
		displayImage(imageView, uri, width, height, defaultResId,
				new SimpleImageLoaderListener());
	}

	public void displayImage(final ImageView imageView, String uri,
			final int width, final int height, int defaultResId,
			final ImageLoaderListener lsner) {

		if (null == imageView)
			return;

		if (defaultResId > 0)
			imageView.setImageResource(defaultResId);

		if (TextUtils.isEmpty(uri))
			return;

		final ImageLoaderListener listener = null != lsner ? lsner
				: new SimpleImageLoaderListener();

		mImageLoaderImpl.displayImage(imageView, uri, width, height,
				defaultResId, listener);
	}

	@Override
	public Bitmap getDiskCache(String uri) {
		// TODO Auto-generated method stub
		return mImageLoaderImpl.getDiskCache(uri);
	}

	@Override
	public Bitmap getMemeryCache(String uri, int width, int height) {
		// TODO Auto-generated method stub
		return mImageLoaderImpl.getMemeryCache(uri, width, height);
	}

	@Override
	public File getDiskCacheFile(String uri) {
		// TODO Auto-generated method stub
		return mImageLoaderImpl.getDiskCacheFile(uri);
	}

	@Override
	public String createAssetsPath(String uri) {
		// TODO Auto-generated method stub
		return mImageLoaderImpl.createAssetsPath(uri);
	}

	@Override
	public String createContentProviderUrl(String uri) {
		// TODO Auto-generated method stub
		return mImageLoaderImpl.createContentProviderUrl(uri);
	}

	@Override
	public String createLocalPath(String uri) {
		// TODO Auto-generated method stub
		return mImageLoaderImpl.createLocalPath(uri);
	}

	@Override
	public String createResource(String uri) {
		// TODO Auto-generated method stub
		return mImageLoaderImpl.createResource(uri);
	}

	@Override
	public OnScrollListener createOnScrollListener(boolean pauseOnScroll,
			boolean pauseOnFling, OnScrollListener lsner) {
		// TODO Auto-generated method stub
		return mImageLoaderImpl.createOnScrollListener(pauseOnScroll,
				pauseOnFling, lsner);
	}
}
