/**
 * 
 */
package com.hanbing.mytest.activity.view;

import com.pull2refresh.PullToRefreshListView;
import com.hanbing.mytest.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author hanbing
 * @date 2015-7-22
 */
public class TestPullToRefreshView extends Activity {

    PullToRefreshListView rv;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        
        View view = null;
        boolean pull = false;
        
        

        if (pull){
        rv = new PullToRefreshListView(this) ; 
//        rv.setOnRefreshListener(new OnRefreshListener<ListView>() {
//
//	    @Override
//	    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//		    
//		    @Override
//		    public void run() {
//			// TODO Auto-generated method stub
//			try {
//			    Thread.sleep(3000);
//			} catch (InterruptedException e) {
//			    // TODO Auto-generated catch block
//			    e.printStackTrace();
//			}
//			
//			rv.post(new Runnable() {
//			    
//			    @Override
//			    public void run() {
//				// TODO Auto-generated method stub
//				rv.onRefreshComplete();
//			    }
//			});
//			
//		    }
//		}).start();
//	    }
//	});
        
       
        list = rv.getRefreshableView();//new ListView(this);
    }
        else
            list = new ListView(this);
        for (int i = 0; i < 1; i++)
        {
            TextView text = new TextView(this);
            text.setText("this is header " +i );
            text.setTextSize(25);
            text.setBackgroundColor(Color.WHITE);
            text.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            
            list.addHeaderView(text);
        }
        
        list.setDivider(new ColorDrawable(Color.GREEN));
        list.setDividerHeight(100);
        list.setAdapter(new MyAdapter());
        list.setHeaderDividersEnabled(false);
        
        list.setBackgroundColor(Color.RED);
        
        
        if (pull)
        {
            view = rv;
        } else {
            view = list;
        }
        
        setContentView(view);
    }
    
    int count = 10;
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
	    
	    ImageView text = new ImageView(getApplicationContext());
	    
//	    text.setText("Title " + position);
//	    text.setTextSize(25);
//	    text.setTextColor(Color.RED);
	    text.setImageResource(R.drawable.a);
	    text.setBackgroundColor(Color.GRAY);
	    text.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, 400));
	    return text;
	}
	
    }
}
