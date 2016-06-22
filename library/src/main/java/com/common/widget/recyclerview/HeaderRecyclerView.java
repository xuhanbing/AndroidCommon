package com.common.widget.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
public class HeaderRecyclerView extends BaseRecyclerView {


    class HeaderViewAdapter extends Adapter{

        private static final int ITEM_VIEW_TYPE_HEADER = -1;
        private static final int ITEM_VIEW_TYPE_FOOTER = -2;

        @Override
        public int getItemViewType(int position) {
            if (isHeader(position))
            {
                return ITEM_VIEW_TYPE_HEADER;

            } else if (isFooter(position)){

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
                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
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


            LayoutManager layoutManager = getLayoutManager();
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (isHeader(position))
            {
                int index = position;
                ViewGroup viewGroup = (ViewGroup) holder.itemView;

                viewGroup.removeAllViews();

                View view = mHeaders.get(index);

                viewGroup.addView(ViewUtils.removeFromParent(view));

                if (layoutManager instanceof StaggeredGridLayoutManager) {
                    viewGroup.setLayoutParams(generateFullSpanLayoutParams( layoutParams));
                }

            } else if (isFooter(position))
            {

                int index = position - (getHeaderViewsCount() + getRealItemCount());

                ViewGroup viewGroup = (ViewGroup) holder.itemView;
                viewGroup.removeAllViews();
                viewGroup.addView(ViewUtils.removeFromParent(mFooters.get(index)));

                if (layoutManager instanceof StaggeredGridLayoutManager) {
                    viewGroup.setLayoutParams(generateFullSpanLayoutParams(layoutParams));
                }

            } else {
                if (null != mAdapter)
                {
                    mAdapter.onBindViewHolder(holder, position - getHeaderViewsCount());
                }
            }

        }

        @Override
        public int getItemCount() {
            return getHeaderViewsCount() + getFooterViewsCount() + getRealItemCount();
        }


    }

    List<View> mHeaders;

    List<View> mFooters;

    RecyclerView.Adapter mAdapter;

    HeaderViewAdapter mHeaderViewAdapter;

    boolean mShowHeaderAndFooterDivider = false;

    public HeaderRecyclerView(Context context) {
        super(context);
        setLayoutManager(new LinearLayoutManager(context));
    }

    public HeaderRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutManager(new LinearLayoutManager(context));
    }

    public HeaderRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setLayoutManager(new LinearLayoutManager(context));
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
                    if (isHeaderOrFooter(position)) {
                        return spanCount;
                    } else {
                        return 1;
                    }
                }
            });
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {


        }

        super.setLayoutManager(layout);
    }

    StaggeredGridLayoutManager.LayoutParams generateFullSpanLayoutParams(ViewGroup.LayoutParams layoutParams) {


        StaggeredGridLayoutManager.LayoutParams params = null;


        if (null == layoutParams )
        {
            params = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        else if (!(layoutParams instanceof StaggeredGridLayoutManager.LayoutParams)) {
            params = new StaggeredGridLayoutManager.LayoutParams(layoutParams.width, layoutParams.height);
        }
        else {
            params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
        }

        params.setFullSpan(true);

        return params;
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
            return;

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

    public int getItemCount(){
        return getHeaderViewsCount() + getFooterViewsCount() + getRealItemCount();
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

    public boolean isHeader(int position) {
        return position < getHeaderViewsCount();
    }

    public boolean isFooter(int position) {
        return position >= (getHeaderViewsCount() + getRealItemCount());
    }

    public boolean isHeaderOrFooter(int position)
    {
        return isHeader(position) || isFooter(position);
    }


    public void setShowDividerHeaderAndFooter(boolean show) {
        this.mShowHeaderAndFooterDivider = show;
    }

    public boolean showHeaderAndFooterDivider() {
        return mShowHeaderAndFooterDivider;
    }

    public boolean showDivider(int position)
    {
        if (isHeaderOrFooter(position))
        {
            if (showHeaderAndFooterDivider())
                return true;
            else
                return false;
        }
        return true;
    }

    public boolean isClickable(int position)
    {
        if (isHeaderOrFooter(position))
            return false;

        return true;
    }

    public int getRealItemPosition(int position)
    {
        if (isHeaderOrFooter(position))
            return -1;

        return position - getHeaderViewsCount();
    }

    @Override
    public boolean drawItemDecoration(int position) {

        if (!showDivider(position))
            return false;

        return  true;
    }
}
