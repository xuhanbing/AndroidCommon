package com.hanbing.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;
import com.hanbing.library.android.tool.ListViewHelper;
import com.hanbing.library.android.tool.PagingManager;

import java.util.ArrayList;
import java.util.List;

public class TestListHelperActivity extends BaseActivity {


    List<String> list = new ArrayList<>();

    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list_helper);


        ListView listView = (ListView) findViewById(R.id.list);


        ListViewHelper listViewHelper = new ListViewHelper(this) {
            @Override
            public void onLoadData(boolean isRefresh, int pageIndex, int pageSize) {

                for (int i = 0; i < pageSize; i++) {
                    list.add("item " + ((pageIndex - 1) * pageSize + i));
                }

                adapter.notifyDataSetChanged();

                onLoadSuccess();


            }


        };
        listViewHelper.setDataView(listView);
        listViewHelper.setDataAdapter(adapter = new MyAdapter());

        {
            TextView textView = new TextView(this);
            textView.setText("加载跟多");
            textView.setHeight(200);

            listViewHelper.setLoadMoreView(textView);
            listViewHelper.setClickLoadMoreEnabled(true);
        }

        listViewHelper.setPagingManager(new PagingManager(20, 40));
        listViewHelper.init();
        listViewHelper.onRefresh();
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
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
                convertView = LayoutInflater.from(getApplicationContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);

            textView.setText(list.get(position));
            textView.setTextColor(Color.BLACK);
            return convertView;
        }
    }
}
