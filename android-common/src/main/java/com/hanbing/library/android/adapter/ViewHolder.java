package com.hanbing.library.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanbing on 2016/8/30
 */
public  abstract class ViewHolder extends RecyclerView.ViewHolder{
    public View mItemView;
    public int mItemViewType;

    public ViewHolder(View itemView) {
        super(itemView);
        this.mItemView = itemView;
    }
    public ViewHolder(View itemView, int itemViewType) {
        super(itemView);

        this.mItemView = itemView;
        this.mItemViewType = itemViewType;
    }
}