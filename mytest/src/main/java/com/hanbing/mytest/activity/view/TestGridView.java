package com.hanbing.mytest.activity.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.hanbing.mytest.view.IssueHistoryGridView;

import java.util.List;
import java.util.zip.ZipFile;


/**
 * ��TestGridView.java��ʵ��������TODO ��ʵ������ 
 * @author Administrator 2014��2��18�� ����4:36:51
 */
public class TestGridView extends Activity {

    Context mContext;
    List<ResolveInfo> mList;
    GridView mGrid;
    IssueHistoryGridView gridView;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
//        initValues();
//        initViews();
        
        gridView = new IssueHistoryGridView(this);
        gridView.initValues(1, 100);

        setContentView(gridView);
       
    }

    
    /**
     * 
     */

    @SuppressLint("NewApi")
    private void initViews() {
        // TODO Auto-generated method stub
        mGrid = new GridView(mContext);
        mGrid.setAdapter(new MyAdapter());
        mGrid.setColumnWidth(0);
        mGrid.setNumColumns(2);
        mGrid.setFadingEdgeLength(0);
        mGrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        mGrid.setMultiChoiceModeListener(new MultiChoiceModeListener()
        {

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                mode.setTitle("Select Items");
                mode.setSubtitle("One item selected");
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode arg0, int arg1, long arg2, boolean arg3) {
                // TODO Auto-generated method stub
                arg0.setSubtitle("count " + mGrid.getCheckedItemCount());
            }
            
        });
        
        mGrid.setOnItemSelectedListener(new OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                
            }
            
        });
        
 
    }

    private View getView(int index)
    {
        TextView tv = new TextView(this);
        
        tv.setText(""+index);
        tv.setTextColor(0xff0000);
        tv.setGravity(Gravity.CENTER);
        
        return tv;
        
    }
    
    

    /**
     * 
     */
    private void initValues() {
        // TODO Auto-generated method stub
        mContext = this.getApplicationContext();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        
        mList = this.getPackageManager().queryIntentActivities(intent, 0);
    }


    class MyAdapter extends BaseAdapter
    {
    	
    	int COL_COUNT = 2;
    	int ROW_COUNT = 4;

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mList.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mList.get(position);
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
//            ImageView image = new ImageView(mContext);
//            ResolveInfo info = (ResolveInfo) getItem(position);
//            
//            image.setImageDrawable(info.activityInfo.loadIcon(mContext.getPackageManager()));
//            return image;
        	
        	int gridWidth = mGrid.getWidth();
        	int gridHeight = mGrid.getHeight();
        	
        	int itemWidth = gridWidth / COL_COUNT;
        	int itemHeight = gridHeight / ROW_COUNT;
        	
        	
        	AbsListView.LayoutParams lp = new LayoutParams(itemWidth, itemHeight);
        	
        	ResolveInfo info = (ResolveInfo) getItem(position);
        	
        	TextView text = new TextView(mContext);
        	CharSequence title = ""+position;//info.activityInfo.loadLabel(mContext.getPackageManager());
        	
        	text.setTextColor(Color.BLACK);
        	text.setGravity(Gravity.CENTER);
        	text.setText(title);
        	text.setTextSize(20);
        	text.setLayoutParams(lp);
        	
        	int color = Color.YELLOW;
        	
        	switch (position % 4)
        	{
        	case 0:
        	case 3:
        		color = Color.WHITE;
        		break;
        	case 1:
        	case 2:
        		color = Color.YELLOW;
        		break;
        	
        	}
        	
        	text.setBackgroundColor(color);
        	
        	return text;
        	
        }
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        menu.add(0, 1, 0, "prev");
        menu.add(0, 2, 0, "next");
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	// TODO Auto-generated method stub
    	
    	switch (item.getItemId())
    	{
    	case 1:
    		gridView.prev();
    		break;
    	case 2:
    		gridView.next();
    		break;
    	}
    	
    	ZipFile zf;
    	
    	return super.onMenuItemSelected(featureId, item);
    }
    
    
}
