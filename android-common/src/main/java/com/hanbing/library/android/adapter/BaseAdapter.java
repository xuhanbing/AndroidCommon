package com.hanbing.library.android.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hanbing on 2016/6/13.
 */
public abstract class BaseAdapter<VH extends ViewHolder> extends android.widget.BaseAdapter{

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

    public abstract int getItemCount();

    public int getCount(){
        return getItemCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }
}
