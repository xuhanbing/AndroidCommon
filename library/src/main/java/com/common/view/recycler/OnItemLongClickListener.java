package com.common.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanbing
 */
public interface OnItemLongClickListener {
    public abstract boolean onItemLongClick(RecyclerView recyclerView, View view, int position);
}
