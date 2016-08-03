/**
 * 
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.mytest.R;
import com.hanbing.mytest.view.LinearLayout;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author hanbing
 * @date 2015-12-17
 */
public class TestListView2 extends Activity{

    
    int count = 30;
    
    ListView listView;
    
    /**
     * 
     */
    public TestListView2() {
	// TODO Auto-generated constructor stub
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(android.widget.LinearLayout.VERTICAL);

		{
			View view = ViewUtils.inflate(this, R.layout.list_item, layout, false);

			TextView textView  = ViewUtils.findViewById(view, R.id.text2);
			textView.setText("外面的text2");

			layout.addView(view);
		}

        listView = new ListView(this);
        listView.setBackgroundColor(Color.RED);
        listView.setHeaderDividersEnabled(false);
        listView.setDivider(new ColorDrawable(Color.GREEN));
        listView.setDividerHeight(20);
        
        final MyAdapter adapter = new MyAdapter();
        
        for (int i = 0; i < 3; i++)
        {
            TextView text = new TextView(this);
            text.setText("this is header " +i );
            text.setTextSize(25);
            text.setBackgroundColor(Color.YELLOW);
            
            listView.addHeaderView(text);
        }
        
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
       
        
        
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub

//		long[] checkedItemIds = listView.getCheckedItemIds();
//		SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
//		int pos = listView.getCheckedItemPosition();
//
//		Log.e("", "item click " + position + ", select count=" + checkedItemIds.length + "/" + checkedItemPositions.size()
//			+ ",select pos=" + pos);
//		for (int i = 0; i < checkedItemPositions.size(); i++)
//		{
//		    int key = checkedItemPositions.keyAt(i);
//		    Log.e("", "check item " + key + "=" + checkedItemPositions.valueAt(i));
//		}
//
//		for (int i = 0; i < checkedItemIds.length; i++)
//		{
//		    Log.e("", "check id=" + checkedItemIds[i]);
//		}
//
//		adapter.notifyDataSetChanged();
				LogUtils.e("onItemClick pos = " + position);
			}
		});

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				LogUtils.e("onItemLongClick pos = " + position);
				return false;
			}
		});


        layout.addView(listView);
        setContentView(layout);
    }
    
    class MyAdapter extends BaseAdapter {

	@Override
	public boolean hasStableIds() {
	    // TODO Auto-generated method stub
	    return true;
	}
	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return 30;
	}

	@Override
	public Object getItem(int position) {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public long getItemId(int position) {
	    // TODO Auto-generated method stub
	    return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    // TODO Auto-generated method stub
//	    TextView text = new TextView(getApplicationContext());
//	    text.setBackgroundResource(R.drawable.sel_common);
//
//	    text.setText("item " + position);
//	    text.setTextColor(0xff00ffff);
//	    text.setGravity(Gravity.CENTER);
//
//	    int padding = 50;
//	    text.setPadding(padding, padding, padding, padding);
//	    text.setBackgroundColor(Color.GRAY);
//
//
//	    return text;

		if (null == convertView)
			convertView = ViewUtils.inflate(getApplicationContext(), R.layout.list_item, parent, false);

		TextView text2 = ViewUtils.findViewById(convertView, R.id.text2);
		text2.setText("position " + position);
		return convertView;
	}
	
    }

}
