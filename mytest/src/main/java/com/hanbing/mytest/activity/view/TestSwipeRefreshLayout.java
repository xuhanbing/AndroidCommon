/**
 * 
 */
package com.hanbing.mytest.activity.view;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-10-8
 */
public class TestSwipeRefreshLayout extends BaseActivity {

	SwipeRefreshLayout swipeRefreshLayout;
	ListView listView;
	MyAdapter adapter;

	int count = 10;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		listView = new ListView(this);
		listView.setAdapter(adapter = new MyAdapter());

		swipeRefreshLayout = new SwipeRefreshLayout(this);
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_light,
				android.R.color.holo_red_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);
		swipeRefreshLayout.addView(listView);
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub

				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						SystemClock.sleep(3000);

						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								count += 10;
								
								adapter.notifyDataSetChanged();
								
								swipeRefreshLayout.setRefreshing(false);
							}
						});
					}
				}).start();
				
			}
		});

		setContentView(swipeRefreshLayout);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return count;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			TextView text = new TextView(getApplicationContext());

			text.setTextSize(25);
			text.setText("item " + position);

			return text;
		}

	}

}
