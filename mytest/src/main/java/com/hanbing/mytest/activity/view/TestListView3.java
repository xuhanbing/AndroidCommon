package com.hanbing.mytest.activity.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.mytest.R;
import com.hanbing.mytest.adapter.DefaultAdapter;

public class TestListView3 extends AppCompatActivity {


    ListView mListView;
    View mEmptyView;
    BaseAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list_view3);

        mListView = ViewUtils.findViewById(this, R.id.listView);
        mEmptyView = ViewUtils.findViewById(this, R.id.emptyView);

        mListView.setEmptyView(mEmptyView);
        mListView.setAdapter(mAdapter = new DefaultAdapter());
    }


    public void showData(View view) {
        count = 20;
        mAdapter.notifyDataSetChanged();
    }

    public void clearData(View view) {
        count = 0 ;
        mAdapter.notifyDataSetChanged();
    }

    int count = 20;
    public class DefaultAdapter extends BaseAdapter {



        public DefaultAdapter() {

        }


        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = ViewUtils.inflate(parent.getContext(), android.R.layout.simple_list_item_1, parent, false);
            }

            TextView title = ViewUtils.findViewById(convertView, android.R.id.text1);
            title.setTextColor(Color.BLACK);
            title.setBackgroundColor(Color.WHITE);
            title.setText("Item " + position);

            return convertView;
        }
    }

}
