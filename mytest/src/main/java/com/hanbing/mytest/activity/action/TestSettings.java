package com.hanbing.mytest.activity.action;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.hanbing.mytest.activity.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TestSettings extends BaseActivity {

	
	ListView listView;
	
	List<String> actions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_settings);
		
		listView = (ListView) findViewById(R.id.list);
		
		actions = new ArrayList<String>();
		
		Field[] fields = Settings.class.getDeclaredFields();
		
		for (Field field : fields)
		{
			Annotation[] annos = field.getAnnotations();
			
			if (field.getName().toUpperCase().contains("ACTION"))
			{
//				actions.add(field.getName());
				
				field.setAccessible(true);
				try {
					actions.add(field.get(Settings.class).toString());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				field.setAccessible(false);
			}
			
		}
		
		
		
		listView.setAdapter(new MyAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					// TODO Auto-generated method stub
					startActivity(new Intent(actions.get(position)));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		
//		setContentView(listView);
		
	}
	
	class MyAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return actions.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return actions.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			TextView text = new TextView(getApplication());
			
			text.setTextSize(25);
			text.setText(actions.get(position));
			
			return text;
		}
		
	}
}
