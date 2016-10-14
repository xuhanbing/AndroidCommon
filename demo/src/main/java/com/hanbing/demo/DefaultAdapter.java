package com.hanbing.demo;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hanbing.library.android.util.ViewUtils;

/**
 * Created by hanbing on 2016/10/14
 */

public class DefaultAdapter extends BaseAdapter {

    int count = 20;

    public DefaultAdapter() {

    }

    public DefaultAdapter(int count) {
        this.count = count;
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