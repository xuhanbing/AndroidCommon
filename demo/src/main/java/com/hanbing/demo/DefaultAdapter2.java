package com.hanbing.demo;

import android.graphics.Color;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hanbing.library.android.adapter.BaseRecyclerViewAdapter;
import com.hanbing.library.android.adapter.ViewHolder;
import com.hanbing.library.android.util.ViewUtils;

/**
 * Created by hanbing on 2016/10/14
 */

public class DefaultAdapter2 extends BaseRecyclerViewAdapter<DefaultAdapter2.ViewHolder> {

    int count = 20;

    public DefaultAdapter2() {

    }

    public DefaultAdapter2(int count) {
        this.count = count;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = ViewUtils.inflate(parent.getContext(), android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView title = ViewUtils.findViewById(holder.itemView, android.R.id.text1);
        title.setTextColor(Color.BLACK);
        title.setBackgroundColor(position % 2 == 0 ? Color.GRAY : Color.LTGRAY);
        title.setText("Item " + position);

    }


    @Override
    public int getItemCount() {
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


    class ViewHolder extends com.hanbing.library.android.adapter.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}