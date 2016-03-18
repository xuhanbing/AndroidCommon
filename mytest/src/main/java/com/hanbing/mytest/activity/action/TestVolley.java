package com.hanbing.mytest.activity.action;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

public class TestVolley extends Activity {

	ImageView image;
	ImageLoader loader;
	RequestQueue queue ;
	
	public class BitmapImageCache implements ImageCache
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

		@Override
		public Bitmap getBitmap(String url) {
			// TODO Auto-generated method stub
			return mCache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			// TODO Auto-generated method stub
			mCache.put(url, bitmap);
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		image = new ImageView(this);
		
		queue = Volley.newRequestQueue(this);
		
		String url = "http://www.baidu.com/";
		
		
		queue.add(new StringRequest(Method.GET, url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("response=" + response);
			}
		}, null)); 
		
		loader = new ImageLoader(queue, new BitmapImageCache());
		
		ImageListener listener = ImageLoader.getImageListener(image, R.drawable.alert_dark_frame, R.drawable.ic_delete);
		
		String requestUrl = "http://img.tupianzj.com/uploads/allimg/140911/1-1409111054140-L.jpg";
		loader.get(requestUrl , listener);
		
		setContentView(image);
	}
}
