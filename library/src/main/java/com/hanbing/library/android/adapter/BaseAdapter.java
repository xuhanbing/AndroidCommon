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
        int itemViewType = getItemViewType(position);

        if (null != convertView){
            viewHolder = (VH) convertView.getTag();

            if (itemViewType != viewHolder.mItemViewType) {
                convertView = null;
                viewHolder = null;
            }
        }

        if (null == convertView) {

            viewHolder = onCreateViewHolder(parent, itemViewType);
            viewHolder.mItemViewType = itemViewType;
            convertView = viewHolder.mItemView;
            convertView.setTag(viewHolder);
        }


        onBindViewHolder(viewHolder, position);

        return convertView;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH holder, int position);

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getItemCount(){
        return getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public static abstract class ViewHolder {
        public View mItemView;
        public int mItemViewType;

        public ViewHolder(View itemView) {
            this.mItemView = itemView;
        }
        public ViewHolder(View itemView, int itemViewType) {
            this.mItemView = itemView;
            mItemViewType = itemViewType;
        }
    }

}
