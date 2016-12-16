package com.hanbing.library.android.view.recycler;

import android.support.v7.widget.RecyclerView;

/**
 * Created by hanbing on 2016/12/16
 */

public class Utils {

    /**
     * Bind onItemClick listener.
     *
     * @param recyclerView
     * @param onItemClickListener
     */
    public static RecyclerView.OnItemTouchListener bindOnItemClickListener(RecyclerView recyclerView, OnItemClickListener onItemClickListener) {
        return bindListener(recyclerView, onItemClickListener, null);
    }

    /**
     * Bind onItemLongClick listener.
     *
     * @param recyclerView
     * @param onItemLongClickListener
     * @return
     */
    public static RecyclerView.OnItemTouchListener bindOnItemLongClickListener(RecyclerView recyclerView, OnItemLongClickListener onItemLongClickListener) {
        return bindListener(recyclerView, null, onItemLongClickListener);
    }

    /**
     * Bind onItemClick and onItemLongClick
     *
     * @param recyclerView
     * @param onItemClickListener
     * @param onItemLongClickListener
     */
    public static RecyclerView.OnItemTouchListener bindListener(final RecyclerView recyclerView, final OnItemClickListener onItemClickListener, final OnItemLongClickListener onItemLongClickListener) {
        if (null == recyclerView)
            return null;

        if (null == onItemClickListener && null == onItemLongClickListener)
            return null;

        //多次添加后面的会接收不到事件
        SimpleOnItemTouchListener simpleOnItemTouchListener = new SimpleOnItemTouchListener(recyclerView, onItemClickListener, onItemLongClickListener);
        recyclerView.addOnItemTouchListener(simpleOnItemTouchListener);

        return simpleOnItemTouchListener;
    }


    /**
     * Bind onItemClick and onItemLongClick, and will replace old one.
     *
     * @param recyclerView
     * @param onItemClickListener
     * @param onItemLongClickListener
     */
    public static RecyclerView.OnItemTouchListener bindOccupiedListener(final RecyclerView recyclerView, final OnItemClickListener onItemClickListener, final OnItemLongClickListener onItemLongClickListener) {
        if (null == recyclerView)
            return null;

        if (null == onItemClickListener && null == onItemLongClickListener)
            return null;


        //simple use android id
        int key = android.R.id.text1;


        RecyclerView.OnItemTouchListener old = (RecyclerView.OnItemTouchListener) recyclerView.getTag(key);

        //remove first
        if (null != old) {
            recyclerView.removeOnItemTouchListener(old);
            recyclerView.setTag(key, null);
        }

        SimpleOnItemTouchListener simpleOnItemTouchListener = new SimpleOnItemTouchListener(recyclerView, onItemClickListener, onItemLongClickListener);
        recyclerView.addOnItemTouchListener(simpleOnItemTouchListener);

        recyclerView.setTag(key, simpleOnItemTouchListener);

        return simpleOnItemTouchListener;
    }


}
