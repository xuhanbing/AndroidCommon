package com.hanbing.library.android.tool;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.hanbing.library.android.view.recycler.BaseRecyclerView;
import com.hanbing.library.android.view.recycler.HeaderRecyclerView;

/**
 * Created by hanbing on 2016/11/23
 */
public abstract class RecyclerViewHelper<DataView extends RecyclerView, DataAdapter extends RecyclerView.Adapter> extends DataViewHelper<DataView, DataAdapter>  {
    public RecyclerViewHelper(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return (null == getDataAdapter()) ? 0 : getDataAdapter().getItemCount();
    }

    /**
     * man scroll
     */
    boolean mIsManScroll = false;

    class OnScrollListener extends RecyclerView.OnScrollListener{

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();


            boolean vertical  = false;

            if (layoutManager instanceof LinearLayoutManager) {
                vertical = ((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                vertical = ((StaggeredGridLayoutManager) layoutManager).getOrientation() == StaggeredGridLayoutManager.VERTICAL;
            }

            if (mIsManScroll
                    && ViewChecker.isLastItemVisible(recyclerView, vertical)) {

                if (LOAD_MORE_MODE_LAST_VISIBLE == getLoadMoreMode()) {
                    if (ViewChecker.arriveEnd(recyclerView, true)) {
                        mIsManScroll = false;
                        onLastItemVisible();
                    }
                } else {
                    mIsManScroll = false;
                    onLastItemVisible();
                }
            }


        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            if (RecyclerView.SCROLL_STATE_DRAGGING == newState
                    || RecyclerView.SCROLL_STATE_SETTLING == newState )
                mIsManScroll = true;
            else
                mIsManScroll = false;

        }
    }

    @Override
    public void initDataView(RecyclerView view) {
        view.addOnScrollListener(new OnScrollListener());
    }

    @Override
    public void notifyDataSetChanged() {
        if (null != getDataAdapter()) getDataAdapter().notifyDataSetChanged();
    }

    @Override
    public void addLoadMoreIfNeed() {
        if (null != mLoadMoreContainer && mDataView instanceof HeaderRecyclerView)
            ((HeaderRecyclerView) mDataView).addFooterView(mLoadMoreContainer);
    }

    @Override
    public void setEmptyViewIfNeed() {
        if (mDataView instanceof BaseRecyclerView)
            ((BaseRecyclerView) mDataView).setEmptyView(mEmptyView);
    }
}
