/**
 * 
 */
package com.common.image;

import java.io.File;
import com.common.util.FileUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.imageaware.NonViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;

/**
 * universalimageloader
 * 
 * @author hanbing
 * @date 2015-7-13
 */
public class UniversalImageLoader extends ImageLoaderImpl {

	ImageLoader imageLoader;

	/**
	 * 缓存的最大图片大小
	 */
	ImageSize maxImageSize;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dy.lock.screen.image.ImageLoaderImpl#init(android.content.Context,
	 * java.lang.String)
	 */
	@Override
	public void init(Context context, String path) {
		// TODO Auto-generated method stub

		/**
		 * 最大图片尺寸，使用屏幕大小
		 */
		float aspect = 3.0f / 4;
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		maxImageSize = new ImageSize((int) (dm.widthPixels * aspect),
				(int) (dm.heightPixels * aspect));
		/**
		 * 缓存文件的目录
		 */
		File cacheDir = new File(FileUtils.addRootIfNeed(context, path));
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(maxImageSize.getWidth(),
						maxImageSize.getHeight())
				// max width, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(5)
				// 线程池内线程的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
				.diskCacheSize(50 * 1024 * 1024) // SD卡缓存的最大值
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// 由原先的discCache -> diskCache
				.diskCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
				// (5
				// s),
				// readTimeout
				// (30
				// s)超时时间
				// .writeDebugLogs() // Remove for release app
				.build();
		// 全局初始化此配置
		imageLoader = com.nostra13.universalimageloader.core.ImageLoader
				.getInstance();
		imageLoader.init(config);
	}

	public void displayImage(ImageView imageView, String uri, final int width,
			final int height, int defaultResId, final ImageLoaderListener lsner) {
		// TODO Auto-generated method stub

		if (null == uri)
			return;

		// String imageUri = "http://site.com/image.png"; // from Web
		// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
		// String imageUri = "content://media/external/audio/albumart/13"; //
		// from content provider
		// String imageUri = "assets://image.png"; // from assets
		// String imageUri = "drawable://" + R.drawable.image; // from drawables
		// (only images, non-9patch)

		if (FileUtils.isExist(uri)) {
			uri = createLocalPath(uri);
		}

		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();

		builder.bitmapConfig(Config.RGB_565).cacheInMemory(true)
				.cacheOnDisk(true).showImageOnLoading(defaultResId)
				.showImageOnFail(defaultResId);
		/**
		 * 结果回调
		 */
		ImageLoadingListener loadingListener = new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub
				lsner.onLoadStarted(view, imageUri);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				// TODO Auto-generated method stub
				lsner.onLoadFailed(view, imageUri);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				// TODO Auto-generated method stub
				lsner.onLoadCompleted(view, imageUri, loadedImage);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub
				lsner.onLoadCancelled(view, imageUri);
			}
		};

		/**
		 * 进度回调
		 */
		ImageLoadingProgressListener loadingProgressListener = new ImageLoadingProgressListener() {

			@Override
			public void onProgressUpdate(String imageUri, View view,
					int current, int total) {
				// TODO Auto-generated method stub
				lsner.onLoading(view, imageUri, total, current);
			}
		};

		/**
		 * 是否使用自定义大小 默认大小根据imageview适配
		 */
		final boolean useCustomSize = (width > 0 && height > 0);

		ImageViewAware imageViewAware = new ImageViewAware(imageView) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.nostra13.universalimageloader.core.imageaware.ImageViewAware
			 * #getWidth()
			 */
			@Override
			public int getWidth() {
				// TODO Auto-generated method stub
				return useCustomSize ? width : super.getWidth();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.nostra13.universalimageloader.core.imageaware.ImageViewAware
			 * #getHeight()
			 */
			@Override
			public int getHeight() {
				// TODO Auto-generated method stub
				return useCustomSize ? height : super.getHeight();
			}
		};

		imageLoader.displayImage(uri, imageViewAware, builder.build(),
				loadingListener, loadingProgressListener);
	}

	@Override
	public Bitmap getMemeryCache(String uri, int width, int height) {
		// TODO Auto-generated method stub

		String key = uri;

		ImageSize targetSize = ImageSizeUtils.defineTargetSizeForView(
				new NonViewAware(new ImageSize(width, height),
						ViewScaleType.CROP), maxImageSize);
		key = MemoryCacheUtils.generateKey(uri, targetSize);

		return imageLoader.getMemoryCache().get(key);
	}

	@Override
	public File getDiskCacheFile(String uri) {
		// TODO Auto-generated method stub
		return imageLoader.getDiskCache().get(uri);
	}

	@Override
	public String createAssetsPath(String uri) {
		// TODO Auto-generated method stub
		return Scheme.ASSETS.wrap(uri);
	}

	@Override
	public String createLocalPath(String uri) {
		// TODO Auto-generated method stub
		return Scheme.FILE.wrap(uri);
	}

	@Override
	public String createResource(String uri) {
		// TODO Auto-generated method stub
		return Scheme.DRAWABLE.wrap(uri);
	}

	@Override
	public String createContentProviderUrl(String uri) {
		// TODO Auto-generated method stub
		return uri;
	}

	@Override
	public OnScrollListener createOnScrollListener(boolean pauseOnScroll,
			boolean pauseOnFling, OnScrollListener lsner) {
		// TODO Auto-generated method stub
		return new PauseOnScrollListener(imageLoader, pauseOnScroll,
				pauseOnFling, lsner);
	}

}
