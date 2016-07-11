package com.hanbing.library.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 * Created by hanbing on 2016/6/13.
 */
public abstract class BaseAdapter<VH extends BaseAdapter.ViewHolder> extends android.widget.BaseAdapter{

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        VH viewHolder = null;
        if (null == convertView) {

            viewHolder = onCreateViewHolder(parent, getItemViewType(position));

            convertView = viewHolder.mItemView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }



        onBindViewHolder(viewHolder, position);

        return convertView;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH viewHolder, int position);

    public static abstract class ViewHolder {
        public View mItemView;

        public ViewHolder(View itemView) {
            this.mItemView = itemView;
        }

    }

}
