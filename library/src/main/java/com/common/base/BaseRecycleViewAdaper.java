package com.common.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by hanbing on 2016/3/10.
 */
public abstract class  BaseRecycleViewAdaper<T extends  RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {



    @Override
    public final void onBindViewHolder(T holder, final int position) {

        onBindViewHolderImpl(holder, position);

    }

    public abstract  T onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolderImpl(T holder, int position);

}
