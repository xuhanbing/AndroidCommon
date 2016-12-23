package com.hanbing.demo.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hanbing.demo.EmptyActivity;
import com.hanbing.demo.R;
import com.hanbing.library.android.fragment.list.ListFragment;

/**
 * Created by hanbing on 2016/10/14
 */

public class TestRestoreFragment extends ListFragment {


    ListView mListView = null;
    MyAdapter mMyAdapter;

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mListView = new ListView(getContext());


        return mListView;
    }

    @Override
    public ListView createListView() {
        return mListView;
    }

    @Override
    public BaseAdapter createAdapter() {
        return null == mMyAdapter ? mMyAdapter = new MyAdapter(getContext()) : mMyAdapter;
    }

    @Override
    public void initListView(ListView listView) {
        mListView.setAdapter(mMyAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(), EmptyActivity.class));
            }
        });
    }

    @Override
    protected void bindViews(View view, Bundle savedInstanceState) {
        super.bindViews(view, savedInstanceState);
    }

    class MyAdapter extends BaseAdapter {

            Context mContext ;

        public MyAdapter(Context context) {
            mContext = context;
        }


            @Override
            public int getCount() {
                return 40;
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


                TextView textView = new TextView(getContext());
                if (null != convertView)
                    textView = (TextView) convertView;

                textView.setHeight(100);
                textView.setText(mContext.getString(R.string.action_settings) + " item = " + position);
                return textView;
            }
    }
}
