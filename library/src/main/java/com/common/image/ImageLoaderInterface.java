/**
 *  
 */
package com.common.image;

import java.io.File;
import java.lang.reflect.Field;

import com.common.util.ImageUtils;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;

/**
 * 显示图片的接口
 * 
 * @author hanbing
 * @date 2015-7-13
 */
public abstract class ImageLoaderInterface {

	/**
	 * 获取字段属性
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	protected static int getImageViewFieldValue(Object object, String fieldName) {
		int value = 0;
		if (object instanceof ImageView) {
			try {
				Field field = ImageView.class.getDeclaredField(fieldName);
				field.setAccessible(true);
				int fieldValue = (Integer) field.get(object);
				if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
					value = fieldValue;
				}
			} catch (Throwable e) {
			}
		}
		return value;
	}

	/**
	 * @param imageView
	 *            imageView
	 * @param uri
	 *            图片url
	 * @param width
	 *            指定图片宽（宽和高都大于0才有效，否则使用默认）
	 * @param height
	 *            指定图片高（宽和高都大于0才有效，否则使用默认）
	 * @param defaultResId
	 *            默认图片
	 * @param lsner
	 *            回调
	 */
	public abstract void displayImage(final ImageView imageView, String uri,
			final int width, final int height, int defaultResId,
			final ImageLoaderListener lsner);

	/**
	 * 获取缓存的图片，内存+sd卡
	 * 
	 * @param uri
	 * @return
	 */
	public Bitmap getCache(String uri) {
		Bitmap bm = getMemeryCache(uri);

		if (null == bm) {
			bm = getDiskCache(uri);
		}

		return bm;
	}

	/**
	 * 获取内存中的缓存图片
	 * 
	 * @param uri
	 * @return
	 */
	public Bitmap getMemeryCache(String uri) {
		return getMemeryCache(uri, 0, 0);
	}

	/**
	 * 通过imageView获取大小，再获取缓存
	 * 
	 * @param uri
	 * @param imageView
	 * @return
	 */
	public Bitmap getMemeryCache(String uri, ImageView imageView) {
		int width = 0;
		int height = 0;
		if (null != imageView) {
			width = imageView.getWidth();
			height = imageView.getHeight();
		}

		if (width <= 0) {
			width = getImageViewFieldValue(imageView, "mMaxWidth");
		}

		if (height <= 0) {
			height = getImageViewFieldValue(imageView, "mMaxHeight");
		}

		DisplayMetrics dm = imageView.getResources().getDisplayMetrics();

		if (width <= 0) {
			width = dm.widthPixels;
		}

		if (height <= 0) {
			height = dm.heightPixels;
		}

		return getMemeryCache(uri, width, height);
	}

	/**
	 * 获取内存中的缓存图片
	 * 
	 * @param uri
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return
	 */
	public abstract Bitmap getMemeryCache(String uri, int width, int height);

	/**
	 * 获取sd中的缓存图片
	 * 
	 * @param uri
	 * @return
	 */
	public Bitmap getDiskCache(String uri) {
		return ImageUtils.getBitmap(getDiskCacheFile(uri));
	}

	/**
	 * 获取缓存的文件
	 * 
	 * @param uri
	 * @return
	 */
	public abstract File getDiskCacheFile(String uri);

	/**
	 * 生成assets文件完整的名称
	 * 
	 * @param uri
	 * @return
	 */
	public abstract String createAssetsPath(String uri);

	/**
	 * 生成contentprovider文件
	 * 
	 * @param uri
	 * @return
	 */
	public abstract String createContentProviderUrl(String uri);

	/**
	 * 生成本地文件完成的名称
	 * 
	 * @param uri
	 * @return
	 */
	public abstract String createLocalPath(String uri);

	/**
	 * 生成资源文件完成的名称
	 * 
	 * @param uri
	 * @return
	 */
	public abstract String createResource(String uri);

	/**
	 * 支持滑动暂停加载的scroll
	 * 
	 * @param pauseOnScroll
	 *            滑动时是否暂停
	 * @param pauseOnFling
	 *            快速滑动时是否暂停
	 * @param lsner
	 *            自定义的onScrollListener
	 * @return
	 */
	public abstract OnScrollListener createOnScrollListener(
			boolean pauseOnScroll, boolean pauseOnFling, OnScrollListener lsner);
}
