package com.hanbing.mytest.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NearListFragment extends BaseFragment {

	ListView listView;
	SimpleAdapter simpleAdapter;
	@Override
	public void setData(List<Object> list) {
		// TODO Auto-generated method stub
		super.setData(list);
		
		//fjaldkfjlajflajfl
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(-1, container, false);
//		return super.onCreateView(inflater, container, savedInstanceState);
		listView.setAdapter(new ListAdapter(list));
		return view;
	}
	
	public class ListAdapter extends BaseAdapter
	{

		public ListAdapter (List<Object > list)
		{
			
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
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
			return null;
		}
		
	}

}
