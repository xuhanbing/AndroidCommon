package com.hanbing.mytest.activity.view;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.list.DrawerListView;
import com.hanbing.library.android.view.plugin.DrawerItemWrapper;
import com.hanbing.mytest.R;
import com.hanbing.mytest.view.SlideListView;
import com.hanbing.mytest.view.SlideListView.SlideListAdapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TestSlideListView extends Activity {


	ListView listView;
	int count = 20;
	public TestSlideListView() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


		{
//			SlideListView listView = new SlideListView(this);
			final DrawerListView listView = new DrawerListView(this);
			listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			listView.setAdapter(new MyAdapter());
			listView.setBackgroundColor(Color.GREEN);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
					// TODO Auto-generated method stub
					LogUtils.e("onItemClick " + position);


				}
			});
			listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					LogUtils.e("onItemLongClick " + position);
					return true;
				}
			});
			listView.setFastScrollEnabled(true);

			this.listView = listView;
		}

		setContentView(listView);
	}
	
	public class MyAdapter extends BaseAdapter implements SlideListAdapter, DrawerItemWrapper.Adapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return count;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return count;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
		    
		    convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_slidelistview, parent, false);
		    
			
			TextView text = (TextView) convertView.findViewById(R.id.tv_title);
			text.setText("Position " + position);
			text.setTextSize(40);
			text.setTextColor(Color.BLACK);
			text.setBackgroundColor(Color.YELLOW);
			
			TextView textExtra = (TextView) convertView.findViewById(R.id.tv_menu1);
			textExtra.setText("Option " + position);
			textExtra.setTextSize(40);
			textExtra.setBackgroundColor(Color.RED);
			textExtra.setTextColor(Color.GREEN);
			textExtra.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					LogUtils.e("click option " + position);

					if (listView instanceof SlideListView) {
						((SlideListView)listView).closeMenu();
					} else if (listView instanceof DrawerListView){
						((DrawerListView)listView).closeDrawer();
					}

				}
			});
			textExtra.setOnTouchListener(new OnTouchListener() {
			    
			    @Override
			    public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.e("", "touch " + position + ",action=" +event.getAction());
				return false;
			    }
			});
			
			return convertView;
		}
		
		@Override
		public int getMaxScrollX(View child) {
		    // TODO Auto-generated method stub
		    if (child instanceof ViewGroup)
		    {
			ViewGroup vg = (ViewGroup) child;
			
			int contentWidth = 0;
			for (int i = 0; i < vg.getChildCount(); i++)
			{
			    contentWidth += vg.getChildAt(i).getMeasuredWidth();
			}
			
			return Math.max(200, contentWidth - child.getWidth());
		    }
		    return child.getMeasuredWidth() - child.getWidth();
		}

		@Override
		public int getMaxScroll(int position) {
			View child = listView.getChildAt(position - listView.getFirstVisiblePosition());
			if (null != child)
			{
				View menu = ViewUtils.findViewById(child, R.id.layout_menu);
				return menu.getMeasuredWidth();
			}

			return 0;
		}

		class ViewHolder
		{
		}
	}
	
	

}
