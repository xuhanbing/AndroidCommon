package com.hanbing.library.android.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by hanbing on 2016/10/27
 */

public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public Object getItem(int position) {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }
}
