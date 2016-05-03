package com.common.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanbing on 2016/3/11.
 */
public abstract class OnItemClickListener {
    public  abstract void onItemClick(RecyclerView recyclerView, View view, int position);

    public  boolean onItemLongClick(RecyclerView recyclerView, View view, int position) {
        return false;
    }
}
