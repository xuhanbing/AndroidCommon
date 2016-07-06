package com.common.view.plugin;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.common.view.recycler.PinnedSectionRecyclerView;

/**
 * Created by hanbing
 */
public class RecyclerViewPinnedSectionWrapper extends PinnedSectionWrapper<RecyclerView> {


    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            int firstVisibleItemPosition = getFirstVisibleItemPosition();
            int itemCount = recyclerView.getLayoutManager().getItemCount();

            onScroll(recyclerView, firstVisibleItemPosition, itemCount);

        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        }
    };

    public RecyclerViewPinnedSectionWrapper(RecyclerView parent) {
        super(parent);

        if (null != parent)
        parent.addOnScrollListener(mOnScrollListener);
    }

    @Override
    protected int getFirstVisibleItemPosition() {
        if (null == mParent)
            return -1;

        RecyclerView.LayoutManager layoutManager = mParent.getLayoutManager();

        int firstVisibleItemPosition = -1;
        //current only support LinearLayoutManager
        if (layoutManager instanceof LinearLayoutManager) {
            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
        }

        return firstVisibleItemPosition;
    }
}
