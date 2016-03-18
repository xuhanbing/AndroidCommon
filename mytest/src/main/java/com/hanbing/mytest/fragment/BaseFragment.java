package com.hanbing.mytest.fragment;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

public abstract class BaseFragment extends Fragment {
	
	List<Object> list;
	BaseAdapter adapter;
	
	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Object obj = parent.getAdapter().getItem(position);
			
			
			Intent intent = null;
			/***
			 * 
			 * 
			 * dfad
			 */
			
			startActivity(intent);
		}
	};

	
//	public void setAdapter(BaseAdapter adapter)
//	{
//		this.adapter = adapter;
//	}
//	
//	public abstract void setData(List<Object> list);
	
	public void setData(List<Object> list)
	{
		this.list = list;
	}
	
//	
//	public void clickItem(Object object)
//	{
//		
//	}
	
	/**
	 * 
	 * @param list
	 */
	public void updateData()
	{
		adapter.notifyDataSetChanged();
	}
	
	public interface ActionInterface
	{
		public abstract void loadMore();
	}
}
	
