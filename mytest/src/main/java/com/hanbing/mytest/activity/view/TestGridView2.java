package com.hanbing.mytest.activity.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class TestGridView2 extends Activity {

	Handler handler = new Handler();
	
	List<Data> mList = new ArrayList<Data>();
	
	MyAdapter adapter ;
	
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		
		adapter = new MyAdapter();
		
		GridView grid = new GridView(this);
		
		grid.setNumColumns(3);
		int padding = 20;
		grid.setPadding(padding, padding, padding, padding);
		grid.setHorizontalSpacing(padding);
		grid.setVerticalSpacing(padding);
		grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String path = mList.get(position).path;
				Toast.makeText(mContext, path, Toast.LENGTH_SHORT).show();
			}
		});
		
		setContentView(grid);
		
		loadImage();
	}
	
	public class Data
	{
		public String path;
		public String name;
		public String time;
		
		public String toString()
		{
			return "[ path=" + path + ",name=" + name + ",time=" + time + " ]";
		}
	}
	
	private void loadImage() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int MAX_COUNT = 10;
				
				Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				
				String[] projection = new String[] {
						MediaStore.Images.Media.DATA,
						MediaStore.Images.Media.DISPLAY_NAME,
						MediaStore.Images.Media.DATE_ADDED,};
				String sortOrder = MediaStore.Images.Media.DATE_ADDED + " desc limit " + MAX_COUNT;
				
				String selection = MediaStore.Images.Media.MIME_TYPE + " in ('image/jpeg', 'image/jpg')";
				String [] selectionArgs = null;
				
				
				
				Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
				
				int count = 0;
				
				if (cursor.moveToFirst())
				{
					mList.clear();
					do
					{
						Data data = new Data();
						data.path = cursor.getString(cursor.getColumnIndex(Media.DATA));
						data.name = cursor.getString(cursor.getColumnIndex(Media.DISPLAY_NAME));
						data.time = cursor.getString(cursor.getColumnIndex(Media.DATE_ADDED));
						
						mList.add(data);
						
						System.out.println("count=" + count + "," + data.toString());
						
						count++;
					}while (count < MAX_COUNT && cursor.moveToNext());
					
					
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							update();
						}

						
					});
				}
				
				
				
				
				
			}
		}).start();
	}
	
	private void update() {
		// TODO Auto-generated method stub
		adapter.notifyDataSetChanged();
	}

	public class MyAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView image = null;
			if (null == convertView)
			{
				image = new ImageView(mContext);
				
//				int size = 100;
				image.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				
				convertView = image;
			}
			else
			{
				image = (ImageView) convertView;
			}
			
			image.setBackgroundColor(Color.RED);
			Data data = (Data) getItem(position);
			
			image.setImageResource(R.drawable.ic_launcher);
			showImage(image, data.path);
			
			return convertView;
		}
		
	}
	
	public void showImage(final ImageView image, final String path) {
		// TODO Auto-generated method stub
		new Thread (new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					FileInputStream is = new FileInputStream(path);
					
					BitmapFactory.Options opts = new BitmapFactory.Options();
					
					opts.inJustDecodeBounds = true;
					
					BitmapFactory.decodeStream(is, null, opts);
					
					is.close();
					is = null;
					
					is = new FileInputStream(path);
					int maxSize = 50 * 1024;
					if (is.available() > maxSize)
					{
						opts.inSampleSize = is.available() / maxSize;
					}
					
					opts.inJustDecodeBounds = false;
					
					final Bitmap bm = BitmapFactory.decodeStream(is, null, opts);
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							image.setImageBitmap(bm);
						}
					});
					
					is.close();
					is = null;
				} catch (OutOfMemoryError e) {
					// TODO: handle exception
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
}
