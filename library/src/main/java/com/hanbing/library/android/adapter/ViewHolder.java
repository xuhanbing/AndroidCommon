package com.hanbing.library.android.adapter;

import android.view.View;

/**
 * Created by hanbing on 2016/8/30
 */
public  abstract class ViewHolder {
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