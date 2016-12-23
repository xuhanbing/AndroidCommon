package com.hanbing.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanbing.library.android.fragment.list.ListFragment;
import com.hanbing.library.android.util.ViewUtils;

/**
 * Created by hanbing on 2016/10/26
 */

public class NumListFragment extends ListFragment<Object> {


    int count;


    ListView mListView;

    View mEmptyView;
    View mLoadingView;

    Handler mHandler = new Handler();

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout relativeLayout = new RelativeLayout(getContext());


        ListView listView = new ListView(getContext());

        for (int i = 0; i < 3; i++)
        {
            TextView header = new TextView(getContext());
            header.setText("header " + i);

            TextView footer = new TextView(getContext());
            footer.setText("footer " + i);

            listView.addHeaderView(header);
            listView.addFooterView(footer);
        }


        TextView textView = new TextView(getContext());

        textView.setBackgroundColor(Color.LTGRAY);
        textView.setText("当前没有数据");
        textView.setGravity(Gravity.CENTER);

        mEmptyView = textView;
        mListView = listView;

        relativeLayout.addView(listView);
        relativeLayout.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        return relativeLayout;
    }

    @Override
    public ListView createListView() {
        return mListView;
    }

    @Override
    public BaseAdapter createAdapter() {
        return new Adapter();
    }


    @Override
    public View createEmptyView() {
        return mEmptyView;
    }

    @Override
    public void onLoadData(boolean isRefresh, int pageIndex, final int pageSize) {


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                count += pageSize;

                getDataAdapter().notifyDataSetChanged();
                onLoadSuccess();
            }
        }, 3000);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return super.onItemLongClick(parent, view, position, id);
    }

    class Adapter extends android.widget.BaseAdapter {

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
