package com.hanbing.mytest.activity.view;

import java.util.ArrayList;
import java.util.List;

import com.hanbing.mytest.view.MyGroupListView;
import com.hanbing.mytest.view.PinnedSectionListView2;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;


/**
 * 锟斤拷TestExpandableList.java锟斤拷实锟斤拷锟斤拷锟斤拷锟斤拷TODO 锟斤拷实锟斤拷锟斤拷锟斤拷 
 * @author Administrator 2014锟斤拷2锟斤拷18锟斤拷 锟斤拷锟斤拷3:22:37
 */
public class TestExpandableList extends Activity {

    Context mContext;
    
//    String [][] mArrays = {
//                      {"car", "car1", "car2", "car3", "car1", "car2", "car3", "car1", "car2", "car3"},
//                      {"man", "man1", "man2", "man3", "man1", "man2", "man3", "man1", "man2", "man3", "man1", "man2", "man3"},
//                      {"money", "money1", "money2", "money3", "money4", "money5", "money6", "money1", "money2", "money3", "money4", "money5", "money6"},
//                      {"aa", "money1", "money2", "money3", "money4", "money5", "money6", "money1", "money2", "money3", "money4", "money5", "money6"},
//                      {"bb", "money1", "money2", "money3", "money4", "money5", "money6", "money1", "money2", "money3", "money4", "money5", "money6"},
//    }; 
    String [][] mArrays = {
                           {"a", "1", "2", "3", "4", "5"},
                           {"b", "1", "2", "3", "4", "5"},
                           {"c", "1", "2", "3", "4", "5"},
                           {"d", "1", "2", "3", "4", "5"},
                           {"e", "1", "2", "3", "4", "5"},
                           {"f", "1", "2", "3", "4", "5"},
                           {"g", "1", "2", "3", "4", "5"},
                           {"h", "1", "2", "3", "4", "5"},
                           {"i", "1", "2", "3", "4", "5"},
                           {"j", "1", "2", "3", "4", "5"},
                           {"k", "1", "2", "3", "4", "5"},
                           {"l", "1", "2", "3", "4", "5"},
                           {"l", "1", "2"}
         };
    MyGroupListView mListView;
    PinnedSectionListView2 mSectionListView;
    List<List<String>> mList = new ArrayList<List<String>>();
    MyExpandableListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
	
        super.onCreate(savedInstanceState);
        initData();
        mContext = this.getApplicationContext();
        
        initListView();
        
        
    }

    private void initListView()
    {
        mListView = new MyGroupListView(this);
        mAdapter = new MyExpandableListAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOverscrollFooter(getWallpaper());
        mListView.setOverscrollHeader(getWallpaper());
        mListView.setOverScrollMode(ListView.OVER_SCROLL_ALWAYS);
        mListView.setOnChildClickListener(new OnChildClickListener()
         {

            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2, int arg3, long arg4) {
                // TODO Auto-generated method stub
                String msg = (String) mAdapter.getChild(arg2, arg3);//mArrays[arg2][arg3+1];
                
                
                return false;
            }
    
         });
        
        mListView.setOnGroupClickListener(new OnGroupClickListener()
        {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // TODO Auto-generated method stub
                String msg = (String) mAdapter.getGroup(groupPosition);//mArrays[groupPosition][0];
                
                return true;
            }
            
        });
        
        
        mListView.setLongClickable(true);
        mListView.setGroupIndicator(null);
        this.registerForContextMenu(mListView);
        
        for (int i = 0; i < mList.size(); i++)
        {
            mListView.expandGroup(i);
        }
        this.setContentView(mListView);
    }
    
    private void initListView2()
    {
        
        
        mSectionListView = new PinnedSectionListView2(mContext);
        
        mSectionListView.setAdapter(new SectionListAdapter());
        mSectionListView.initShadow(false);
        this.setContentView(mSectionListView);
    }
    
    private class SectionListAdapter extends BaseAdapter implements PinnedSectionListView2.PinnedSectionListAdapter
    {


		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			// TODO Auto-generated method stub
			super.registerDataSetObserver(observer);
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			// TODO Auto-generated method stub
			super.unregisterDataSetObserver(observer);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			
			int count = 0;
			
			for (int i = 0; i < mArrays.length; i++)
			{
				count += mArrays[i].length;
				
			}
			return count;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			
			int count = 0;
			
			for (int i = 0; i < mArrays.length; i++)
			{
				int index = position - count;
				if (index >= 0 && index < mArrays[i].length)
				{
					return mArrays[i][index];
				}
				count += mArrays[i].length;
				
			}
			
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			TextView textView = new TextView(mContext);
            textView.setTextSize(100);
            //text.setGravity(Gravity.CENTER);
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            textView.setText(getItem(position)+"");
            
            if (isItemViewTypePinned(getItemViewType(position)))
            {
            	textView.setBackgroundColor(Color.YELLOW);
            }
            return textView;
		}

		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			
			int type = 0;
			int count = 0;
			
			for (int i = 0; i < mArrays.length; i++)
			{
				if (position == count)
				{
					type = 1;
				}
				count += mArrays[i].length;
			}
			
			return type;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isItemViewTypePinned(int viewType) {
			// TODO Auto-generated method stub
			return viewType == 1;
		}
    	
    }
    
    
    /**
     * 
     */
    private void initData() {
        // TODO Auto-generated method stub
        mList.clear();
        for (String[] arr : mArrays)
        {
            List<String> list = new ArrayList<String>();

            for (String str : arr)
            {
                list.add(str);
                
            }
            
            mList.add(list);
        }
    }

    int mAddCount = 0;
    public boolean onContextItemSelected(MenuItem item) {  
        // TODO Auto-generated method stub  
        //锟截硷拷锟斤拷锟斤拷  
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();  
        int type = ExpandableListView.getPackedPositionType(info.packedPosition);  
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {//锟斤拷锟斤拷锟絫ype锟借定锟斤拷锟斤拷锟斤拷锟酵碉拷锟叫讹拷锟斤拷锟斤拷锟斤拷锟斤拷child锟叫讹拷锟斤拷  
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); //锟斤拷child锟叫讹拷锟斤拷锟芥，锟斤拷取锟斤拷child锟斤拷锟斤拷group锟斤拷  
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition); //锟斤拷child锟叫讹拷锟斤拷锟芥，锟斤拷取锟斤拷child锟斤拷锟斤拷position锟斤拷  
            String title = (String) item.getTitle();
            showToast(title); 
            switch (item.getItemId()) {  
            case 0:  
                
                initData();
                mAdapter.notifyDataSetChanged();
                break;  
            case 1:  
                mList.get(groupPos).add("add" + mAddCount++);
                mAdapter.notifyDataSetChanged();
                break;  
            case 2:
                showToast(mList.get(groupPos).remove(childPos+1));
                mAdapter.notifyDataSetChanged();
            default:  
                break;  
            }  
             return true;  
        }
        return false;  
    }
    
    class MyExpandableListAdapter extends BaseExpandableListAdapter
    {

        /* (non-Javadoc)
         * @see android.widget.ExpandableListAdapter#getChild(int, int)
         */
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
//            return mArrays[groupPosition][childPosition+1];
            return mList.get(groupPosition).get(childPosition+1);
        }

        /* (non-Javadoc)
         * @see android.widget.ExpandableListAdapter#getChildId(int, int)
         */
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return childPosition;
        }

        /* (non-Javadoc)
         * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                                 ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView text = getTextView(false);
           
            
            text.setText((String)getChild(groupPosition, childPosition));
            return text;
        }

        /* (non-Javadoc)
         * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
         */
        @Override
        public int getChildrenCount(int groupPosition) {
            // TODO Auto-generated method stub
//            return mArrays[groupPosition].length-1;
            return mList.get(groupPosition).size()-1;
        }

        /* (non-Javadoc)
         * @see android.widget.ExpandableListAdapter#getGroup(int)
         */
        @Override
        public Object getGroup(int groupPosition) {
            // TODO Auto-generated method stub
//            return mArrays[groupPosition][0];
            return mList.get(groupPosition).get(0);
        }

        /* (non-Javadoc)
         * @see android.widget.ExpandableListAdapter#getGroupCount()
         */
        @Override
        public int getGroupCount() {
            // TODO Auto-generated method stub
//            return mArrays.length;
            return mList.size();
        }

        /* (non-Javadoc)
         * @see android.widget.ExpandableListAdapter#getGroupId(int)
         */
        @Override
        public long getGroupId(int groupPosition) {
            // TODO Auto-generated method stub
            return groupPosition;
        }

        /* (non-Javadoc)
         * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView text = getTextView(true);
            text.setText((String)getGroup(groupPosition));
            
            return text;
        }

        /* (non-Javadoc)
         * @see android.widget.ExpandableListAdapter#hasStableIds()
         */
        @Override
        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return false;
        }

        /* (non-Javadoc)
         * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
         */
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return true;
        }
        
        private TextView getTextView(boolean isGroup)
        {
            TextView textView = new TextView(mContext);
            textView.setTextSize(100);
            //text.setGravity(Gravity.CENTER);
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            
            
            if (isGroup)
            {
                textView.setBackgroundColor(Color.GRAY);
            }
            else
            {
             // Set the text starting position
                textView.setPadding(36, 0, 0, 0);
            }
            
            return textView;
        }

       

        
    }
    
    
    private void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        menu.setHeaderTitle("选锟斤拷锟斤拷锟�");  
        menu.add(0, 0, 0, "锟斤拷锟斤拷");  
        menu.add(0, 1, 0, "锟斤拷锟�");  
        menu.add(0, 2, 0, "删锟斤拷"); 
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    
    
}
