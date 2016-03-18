/**
 * 
 */
package com.hanbing.mytest.fragment;

import com.pull2refresh.PullToRefreshBase;
import com.pull2refresh.PullToRefreshBase.OnRefreshListener;
import com.pull2refresh.PullToRefreshListView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author hanbing
 * @date 2015-9-14
 */
public class PullToRefreshFragment extends Fragment {

	PullToRefreshListView pullToRefreshListView;
	ListView listView;
	MyAdapter adapter;
	
	int count = 0;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		return super.onCreateView(inflater, container, savedInstanceState);
		pullToRefreshListView = new PullToRefreshListView(getActivity());
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						SystemClock.sleep(2000);
						
						pullToRefreshListView.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								count += 10;
								adapter.notifyDataSetChanged();
								
								pullToRefreshListView.onRefreshComplete();
							}
						});
						
					}
				}).start();
			}
		});
		
		pullToRefreshListView.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				pullToRefreshListView.setRefreshing();
			}
		});
		pullToRefreshListView.setBackgroundColor(Color.WHITE);
		listView = pullToRefreshListView.getRefreshableView();
		adapter = new MyAdapter();
		listView.setAdapter(adapter);
		
		return pullToRefreshListView;
	}
	
	class MyAdapter extends BaseAdapter
	{

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
			TextView text = new TextView(getActivity());
			
			text.setTextColor(Color.BLACK);
			text.setTextSize(25);
			
			text.setText("Position " + position);
			
			return text;
		}
		
	}

}
