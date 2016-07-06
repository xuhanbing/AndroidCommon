/**
 *  
 */
package com.hanbing.library.android.image;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 图片加载的实现类 各个图片加载工具需要继承它并实现相应的接口
 * 
 * @author hanbing
 * @date 2015-7-13
 */
public abstract class ImageLoaderImpl extends ImageLoaderInterface {

	Context context;

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context) {
		this.context = context;

		/**
		 * 默认使用外部缓存路径 如果没有挂在sd卡，则使用内置存储
		 */
		String path = context.getExternalCacheDir().getAbsolutePath();
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			path = context.getCacheDir().getAbsolutePath();
		}

		path += "/thumbnails";
		init(context, path);
	}

	/**
	 * 初始化，缓存目录绝对路径
	 * 
	 * @param context
	 * @param cacheDir
	 */
	public abstract void init(Context context, String cacheDir);

	/**
	 * 创建默认
	 * 
	 * @return
	 */
	public static ImageLoaderImpl createDefault() {
		return new ImageLoaderImpl() {

			@Override
			public Bitmap getMemeryCache(String uri, int width, int height) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public File getDiskCacheFile(String uri) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void displayImage(ImageView imageView, String uri,
					int width, int height, int defaultResId,
					ImageLoaderListener lsner) {
				// TODO Auto-generated method stub

			}

			@Override
			public String createResource(String uri) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public OnScrollListener createOnScrollListener(
					boolean pauseOnScroll, boolean pauseOnFling,
					OnScrollListener lsner) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String createLocalPath(String uri) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String createContentProviderUrl(String uri) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String createAssetsPath(String uri) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void init(Context context, String cacheDir) {
				// TODO Auto-generated method stub

			}
		};
	}

}
