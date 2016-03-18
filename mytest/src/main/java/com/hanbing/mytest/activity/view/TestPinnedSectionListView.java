/**
 * 
 */
package com.hanbing.mytest.activity.view;

import java.util.ArrayList;
import java.util.List;

import com.common.view.PinnedSectionListView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author hanbing
 * @date 2015-8-11
 */
public class TestPinnedSectionListView extends Activity {

    
    PinnedSectionListView listView;
//    PinnedHeaderListView listView;
    MyAdapter adapter;
    
    List<String> titles = new ArrayList<String>();
    List<String> headers = new ArrayList<String>();
    /**
     * 
     */
    public TestPinnedSectionListView() { 
	// TODO Auto-generated constructor stub
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        for (int i = 0; i < 10; i++)
        {
            char c = (char) ('a' + i);
            headers.add(""+c);
        }
     
        for (int i = 0; i < headers.size(); i++)
        {
            for (int j = 0; j < 5; j++)
            {
        	titles.add(headers.get(i) + "" + j);
            }
        }
        
//        listView = new PinnedHeaderListView(this);
        listView = new PinnedSectionListView(this);
        listView.setPinnedView(getHeaderView());
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        setContentView(listView);
    }

    public TextView getHeaderView()
 {
	TextView text = new TextView(getApplicationContext());
	text.setTextColor(Color.BLUE);
	text.setTextSize(20);
	text.setPadding(20, 20, 20, 20);
	text.setBackgroundColor(0x44000000);
	text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

	return text;
    }

    public TextView getTitleView() {
	TextView text = new TextView(getApplicationContext());
	text.setTextColor(Color.BLACK);
	text.setTextSize(25);
	text.setPadding(20, 20, 20, 20);
	text.setBackgroundColor(Color.WHITE);
	text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	
	return text;
    }
    
    class MyAdapter extends PinnedSectionListView.PinnedSectionAdapter
    {

	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return titles.size();
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
	    
	    Holder holder = null;
	    if (null == convertView)
	    {
		holder = new Holder();
		
		holder.title = getTitleView();
		holder.header = getHeaderView();
		
		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		
		layout.addView(holder.header);
		layout.addView(holder.title);
		convertView = layout;
		
		convertView.setTag(holder);
	    }
	    else
	    {
		
		holder = (Holder) convertView.getTag();
	    }
	    
	    holder.header.setVisibility(isPinnedSection(position) ? View.VISIBLE : View.GONE);
		holder.title.setText(titles.get(position));
		holder.header.setText(headers.get(indexOfHeader(position)));
		
	    return convertView;
	}
	
	
	

	@Override
	public View createPinned(int position) {
	    // TODO Auto-generated method stub
	    TextView text = getHeaderView();
	    text.setText(headers.get(indexOfHeader(position)));
	    return text;
	}

	@Override
	public boolean isPinnedSection(int position) {
	    // TODO Auto-generated method stub
	    return position % 5 == 0;
	}
	
	public int indexOfHeader(int position)
	{
	    return position / 5;
	}
	
	class Holder
	{
	    TextView header;
	    TextView title;
	}

	@Override
	public void configurePinned(View pinnedView, int position) {
	    // TODO Auto-generated method stub
	    TextView text = (TextView) pinnedView;
	    text.setText(headers.get(indexOfHeader(position)));
	}
	
    }
}
