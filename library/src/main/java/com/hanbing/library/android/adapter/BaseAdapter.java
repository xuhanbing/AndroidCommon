package com.hanbing.library.android.adapter;

import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 * Created by hanbing on 2016/6/13.
 */
public abstract class BaseAdapter<VH extends BaseAdapter.ViewHolder> extends android.widget.BaseAdapter{

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        VH viewHolder;

        if (null == convertView) {

            viewHolder = onCreateViewHolder(parent, position);

            convertView = viewHolder.mItemView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }

        onBindViewHolder(viewHolder, position);


        return convertView;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int position);

    public abstract void onBindViewHolder(VH viewHolder, int position);

    public static abstract class ViewHolder {
        public View mItemView;

        public ViewHolder(View itemView) {
            this.mItemView = itemView;
            if (null != itemView)
            x.view().inject(this, itemView);
        }

    }

}
