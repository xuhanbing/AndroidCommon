/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��6��3�� 
 * Time : ����1:50:37
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.mytest.activity.BaseActivity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;


/**
 * TestDrawerLayout.java 
 * @author hanbing 
 * @date 2014��6��3�� 
 * @time ����1:50:37
 */
public class TestDrawerLayout extends BaseActivity {

    
    ListView contentList;
    FrameLayout content;
    DrawerLayout drawerLayout;
    
    String [] list = new String[]
            {
             "A","B","C","D","E",
             "F","G","H","I","J"
            };
    @Override
    protected void onCreate(Bundle bundle) {
        // TODO Auto-generated method stub
        super.onCreate(bundle);
        
        setContentView(R.layout.activity_drawer);
        
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);

        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // ��ť���£��������
            	drawerLayout.openDrawer(Gravity.RIGHT);

            }
        });
        
        contentList = (ListView) findViewById(R.id.content_list);
        content = (FrameLayout) findViewById(R.id.content);
        
        MyAdapter adapter = new MyAdapter();
        
        contentList.setAdapter(adapter);
        contentList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String text = (String) arg0.getAdapter().getItem(arg2);
                
                
                content.removeAllViews();
                
                content.addView(getView(text));
                
            }
        });
    }
    
    private View getView(String text)
    {
        TextView view = new TextView(this);
        
        view.setText("you select " + text);
        
        return view;
    }

    
    class MyAdapter extends BaseAdapter
    {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.length;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list[position];
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            
            TextView text = null;
            if (null == convertView)
            {
                text = new TextView(TestDrawerLayout.this);
                convertView = text;
            }
            else
            {
                text = (TextView) convertView;
            }
            
            text.setText((String)getItem(position));
            text.setTextSize(50);
            
            return convertView;
        }

        
    }
    
}
