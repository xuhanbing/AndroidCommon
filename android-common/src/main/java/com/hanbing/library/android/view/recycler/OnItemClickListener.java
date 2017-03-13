package com.hanbing.library.android.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanbing on 2016/3/11.
 */
public interface OnItemClickListener {
    public  abstract void onItemClick(RecyclerView recyclerView, View view, int position);
}
