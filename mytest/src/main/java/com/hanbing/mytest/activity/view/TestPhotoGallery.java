package com.hanbing.mytest.activity.view;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import com.lidroid.xutils.BitmapUtils;
//import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
//import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
//import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.hanbing.mytest.R;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class TestPhotoGallery extends Activity {

	
	GridView grid;
	PhotoAdapter adapter;
	
//	BitmapUtils bitmapUtils ;

	ImageView image;
	int numOfColumns = 1;
	public TestPhotoGallery() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		File file = new File("/sdcard/1.txt");
		file.renameTo(new File("/sdcard/11.txt"));
		
//		bitmapUtils = new BitmapUtils(this);
		
		grid = new GridView(this);
		
		grid.setNumColumns(numOfColumns);
		grid.setVerticalSpacing(10);
		grid.setHorizontalSpacing(20);
		
		adapter = new PhotoAdapter(this, null);
		
		grid.setAdapter(adapter);
		
				
		
		setContentView(grid);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				long start = System.nanoTime();
				
				final List<PhotoEntity> list = listPhotos();
				
				start = (System.nanoTime() - start) / (100 * 1000 * 1000);
				
				
				runOnUiThread(new Runnable() {
					public void run() {
						adapter.update(list);
					}
				});
			}
		}).start();
	}
	
	
	public List<PhotoEntity> listPhotos()
	{
		String[] projection = null;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;
		List<PhotoEntity> list = new ArrayList<TestPhotoGallery.PhotoEntity>();
		
		
		Cursor cursor = getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
				projection, selection, selectionArgs, sortOrder);
		
		while (cursor.moveToNext())
		{	
			
			String type = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
			
//			if (type.contains("gif"))
			{
				PhotoEntity entity = new PhotoEntity();
				
				entity.title = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.TITLE));
				entity.path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
				
				File file = new File(entity.path);
				entity.folder = file.getParentFile().getName();
				
				list.add(entity);
			}
		}
		
		return list;
	}
	
	class PhotoEntity implements Serializable
	{

		private static final long serialVersionUID = -2969979081122333970L;
		
		
		String path;
		String folder;
		String title;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "[title=" + title + ",path=" + path + ",folder=" + folder+"]";
		}
	}
	
	
	class PhotoAdapter extends BaseAdapter
	{
		
		ImageCache mImageCache = new ImageCache();
		
		public class ImageCache
		{

			int MAX_SIZE = 10 * 1024 * 1024;
			LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(MAX_SIZE)
					{
				@Override
				protected int sizeOf(String key, Bitmap value) {
					// TODO Auto-generated method stub
					return super.sizeOf(key, value);
				}
					};

			public Bitmap getBitmap(String url) {
				// TODO Auto-generated method stub
				return mCache.get(url);
			}

			public void putBitmap(String url, Bitmap bitmap) {
				// TODO Auto-generated method stub
				mCache.put(url, bitmap);
			}
			
		}
		
		Context context;
		List<PhotoEntity> list;
		
		Handler handler = new Handler();
		
		public PhotoAdapter(Context context, List<PhotoEntity> list)
		{
			this.context = context;
			this.list = list;
		}

		public void update(List<PhotoEntity> list)
		{
			this.list = list;
			
			notifyDataSetChanged();
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return null == list ? 0 : list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null == list ? null : list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			Holder holder = null;
			
			if (null == convertView)
			{
				holder = new Holder();
				
				ImageView image = new ImageView(context);
				
				DisplayMetrics dm = new DisplayMetrics();
				
				dm = getResources().getDisplayMetrics();
				
				int size = dm.widthPixels / numOfColumns;
				image.setLayoutParams(new AbsListView.LayoutParams(size, size));
				image.setBackgroundColor(Color.YELLOW);
				image.setScaleType(ScaleType.CENTER_CROP);
				convertView = image;
				
				holder.image = image;
				
				convertView.setTag(holder);
			}
			else
			{
				holder = (Holder) convertView.getTag();
			}
			
//			holder.image.setImageBitmap(getBitmap(list.get(position).path));
			
			showImage(holder.image, list.get(position).path);
			
			return convertView;
		}
		
		
		public void showImage(final ImageView image, final String path)
		{
			
			
//			// ��������ͼƬ
//			bitmapUtils.display(image, "http://bbs.lidroid.com/static/image/common/logo.png");
//
//			// ���ر���ͼƬ(·����/��ͷ�� ���·��)
//			bitmapUtils.display(image, "/sdcard/test.jpg");
//
//			// ����assets�е�ͼƬ(·����assets��ͷ)
//			bitmapUtils.display(image, "assets/img/wallpaper.jpg");
			
			boolean useUtil = true;
			
			if (useUtil)
			{
				
				
				final String url = path;//"http://pica.nipic.com/2007-12-18/200712189215503_2.jpg";
				
				
				
//				BitmapDisplayConfig displayConfig = new BitmapDisplayConfig();
////				displayConfig.setOnlyStream(true);
////				displayConfig.setUseMaxExpiredTime(true);
//				bitmapUtils.display(image, url, displayConfig, new BitmapLoadCallBack<ImageView>() {
//					
//					@Override
//					public void onLoadFailed(ImageView arg0, String arg1, Drawable arg2) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void onLoadCompleted(ImageView arg0, String arg1, Bitmap arg2,
//							BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
//						// TODO Auto-generated method stub
//						image.setImageBitmap(arg2);
//					}
//					
//				});
			}
			else
			{
				image.setImageResource(R.drawable.dft_image);
				mThreadPool.execute(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						Bitmap bitmap = mImageCache.getBitmap(path);
						
						if (bitmap != null)
						{
							
						}
						else
						{
							bitmap = getBitmap(path);
							
							if (bitmap != null)
							{
//								mImageCache.putBitmap(path, bitmap);
							}
						}
						
						final Bitmap bm = bitmap;
						
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								image.setImageBitmap(bm);
							}
						});
						
					}
				});
			}
			

						
			
		}
		
		
//		ExecutorService mThreadPool = new ThreadPoolExecutor(1, 
//				100, 
//				1, 
//				TimeUnit.SECONDS, 
//				new SynchronousQueue<Runnable>());
		ExecutorService mThreadPool  = Executors.newSingleThreadExecutor();
		
		
		private Bitmap getBitmap(String path)
		{
			Bitmap bm = null;
			
			
			try {
				
				BitmapFactory.Options opts = new Options();
				opts.inJustDecodeBounds  = true;
				
				BitmapFactory.decodeFile(path, opts);
				
				int w = opts.outWidth;
				int h = opts.outHeight;
				
				int size = w * h * 2;
				int maxSize = 500 * 1024;
				
				if (size > maxSize)
				{
					opts.inSampleSize = size / maxSize;
				}
				
				System.out.println("size " + w + " " + h + " " + opts.inSampleSize);
				
				opts.inInputShareable = true;
				opts.inJustDecodeBounds = false;
				opts.inPreferredConfig = Config.RGB_565;
				
				BitmapFactory.decodeFile(path, opts);
				
				bm = BitmapFactory.decodeFile(path, opts);
				
			} catch (OutOfMemoryError e) {
				// TODO: handle exception
			}
			
			return bm;
		}
		
		class Holder 
		{
			ImageView image;
		}
		
	}
	
	

}
