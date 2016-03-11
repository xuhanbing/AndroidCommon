package com.common.widget.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.common.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing on 2016/3/11.
 */
public class RecyclerViewWithHeaderOrFooter extends RecyclerView {


    class HeaderViewAdapter extends Adapter{

        private static final int ITEM_VIEW_TYPE_HEADER = -1;
        private static final int ITEM_VIEW_TYPE_FOOTER = -2;

        @Override
        public int getItemViewType(int position) {
            if (position < getHeaderViewsCount())
            {
                return ITEM_VIEW_TYPE_HEADER;

            } else if (position >= getHeaderViewsCount() + getRealItemCount()){

                return ITEM_VIEW_TYPE_FOOTER;
            }

            if (null != mAdapter)
            {
                return mAdapter.getItemViewType(position - getHeaderViewsCount());
            }

            return 0;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (ITEM_VIEW_TYPE_HEADER == viewType
                    || ITEM_VIEW_TYPE_FOOTER == viewType)
            {

                FrameLayout frameLayout = new FrameLayout(parent.getContext());

                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
                frameLayout.setBackgroundColor(Color.GREEN);
                return new ViewHolder(frameLayout) {

                };
            }

            if (null != mAdapter)
            {
                return mAdapter.onCreateViewHolder(parent, viewType);
            }

            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final int headerCount = getHeaderViewsCount();
            final int footerCount = getFooterViewsCount();
            final int itemCount = getRealItemCount();



            if (position < getHeaderViewsCount())
            {
                int index = position;
                ViewGroup viewGroup = (ViewGroup) holder.itemView;

                viewGroup.removeAllViews();

                View view = mHeaders.get(index);

                viewGroup.addView(ViewUtils.removeFromParent(view));



            } else if (position >= getHeaderViewsCount() + getRealItemCount())
            {

                int index = position - (getHeaderViewsCount() + getRealItemCount());

                ViewGroup viewGroup = (ViewGroup) holder.itemView;
                viewGroup.removeAllViews();;
                viewGroup.addView(ViewUtils.removeFromParent(mFooters.get(index)));

            } else {
                if (null != mAdapter)
                {
                    mAdapter.onBindViewHolder(holder, position - getHeaderViewsCount());
                }
            }




        }

        @Override
        public int getItemCount() {
            int count = null == mAdapter ? 0 : mAdapter.getItemCount();

            return getHeaderViewsCount() + getFooterViewsCount() + count;
        }


    }

    List<View> mHeaders;

    List<View> mFooters;

    RecyclerView.Adapter mAdapter;

    HeaderViewAdapter mHeaderViewAdapter;

    public RecyclerViewWithHeaderOrFooter(Context context) {
        super(context);
    }

    public RecyclerViewWithHeaderOrFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewWithHeaderOrFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setAdapter(Adapter adapter) {

        mAdapter = adapter;

        if (null == adapter)
        {
            mHeaderViewAdapter = null;
        } else {
            if (null == mHeaderViewAdapter)
            {
                mHeaderViewAdapter = new HeaderViewAdapter();
            }
        }


        super.setAdapter(mHeaderViewAdapter);

    }

    @Override
    public Adapter getAdapter() {
        return mHeaderViewAdapter;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        LayoutManager layoutManager = layout;
        if (layoutManager instanceof GridLayoutManager)
        {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;

            final int spanCount = gridLayoutManager.getSpanCount();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position < getHeaderViewsCount() || position >= getHeaderViewsCount() + getRealItemCount()) {
                        return spanCount;
                    } else {
                        return 1;
                    }
                }
            });
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            StaggeredGridLayoutManager gridLayoutManager = (StaggeredGridLayoutManager) layoutManager;


        }

        super.setLayoutManager(layout);
    }

    public void addHeaderView(View view)
    {
        if (null == mHeaders)
            mHeaders = new ArrayList<>();

        mHeaders.add(view);
    }

    public void removeHeaderView(View view)
    {
        if (null == mHeaders)
            return;;

        mHeaders.remove(view);
    }

    public void addFooterView(View view)
    {
        if (null == mFooters)
            mFooters = new ArrayList<>();

        mFooters.add(view);
    }

    public void removeFooterView(View view)
    {
        if (null == mFooters)
            return;;

        mFooters.remove(view);
    }

    public int getHeaderViewsCount()
    {
        if (null == mHeaders)
            return 0;

        return mHeaders.size();
    }

    public int getFooterViewsCount()
    {
        if (null == mFooters)
            return 0;

        return mFooters.size();
    }

    protected int getRealItemCount()
    {
        if (null == mAdapter)
            return 0;

        return mAdapter.getItemCount();
    }


    public boolean isHeaderOrFooter(int position)
    {
        return position < getHeaderViewsCount() || position >= getHeaderViewsCount() + getRealItemCount();
    }

    public int getRealItemPosition(int position)
    {
        if (isHeaderOrFooter(position))
            return -1;

        return position - getHeaderViewsCount();
    }

}
