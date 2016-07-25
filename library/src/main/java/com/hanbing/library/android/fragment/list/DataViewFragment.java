package com.hanbing.library.android.fragment.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hanbing.library.android.fragment.BaseFragment;
import com.hanbing.library.android.listener.OnLoadListener;
import com.hanbing.library.android.tool.PagingManager;
import com.hanbing.library.android.view.recycler.HeaderRecyclerView;

/**
 * Created by hanbing on 2016/3/21.
 */
public abstract class DataViewFragment<DataView extends View, DataAdapter> extends BaseFragment implements IDataViewFragment<DataView, DataAdapter>, OnLoadListener {

    /**
     * data view
     */
    DataView mDataView;

    /**
     * data adapter
     */
    DataAdapter mDataAdapter;

    /**
     * 没有数据时，覆盖ListView展示的View
     * 可以自己定义布局，然后返回
     * 如果没有指定view，将自动创建一个透明的view
     * 如果不想使用，不设置mParent即可
     * 该view只会在listview没有数据时才会展示
     */
    View mEmptyView;

    /**
     * 当加载数据时，覆盖ListView展示的view
     */
    View mLoadingView;

    /**
     * 加载更多的view
     */
    View mLoadMoreView;

    /**
     * LoadMore的父容器
     */
    ViewGroup mLoadMoreContainer;

    /**
     * 是否强制显示loadingview
     * 如果为true，只要开始加载数据，就显示
     * 否则，只有当getItemCount返回0时才显示
     */
    boolean mForceShowLoadingView = false;

    /**
     * 是否一直显示加载更多
     * 默认false，只在有数据是显示
     */
    boolean mAlwaysShowLoadMoreView = false;

    /**
     * 是否支持加载更多
     */
    boolean mLoadMoreEnabled = true;

    /**
     * 是否支持滑动到底部加载更多
     */
    boolean mScrollLoadMoreEnabled = true;

    /**
     * 是否支持点击加载更多
     */
    boolean mClickLoadMoreEnabled = true;

    /**
     * 如果为true，刷新时请求当前数据条数，如果不足一页则请求第一页，需要支持自定义分页数mIsSupportCustomPageSize=true
     * 如果为false，请求第一页
     */
    boolean mIsRefreshAll = true;

    /**
     * 是否支持自定义分页数
     * 默认false
     */
    boolean mIsSupportCustomPageSize = false;

    /**
     * 分页索引是否从0开始
     * 默认false
     */
    boolean mIsPageIndexStartFromZero = false;


    /**
     * 请求和分页管理
     */
    PagingManager mPagingManager = new PagingManager();


    View.OnClickListener mOnLoadMoreClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mClickLoadMoreEnabled) onLoadMore();
        }
    };

    @Override
    protected void initViews(View view) {
        super.initViews(view);

        mPagingManager = createPagingManager();
        mDataView = createDataView();
        mEmptyView = createEmptyView();
        mLoadingView = createLoadingView();
        mLoadMoreView = createLoadMoreView();

        /**
         * 如果有加载更多view，添加到最末尾
         */
        if (null != mLoadMoreView) {
            if (mClickLoadMoreEnabled) mLoadMoreView.setOnClickListener(mOnLoadMoreClick);

            mLoadMoreContainer = new LinearLayout(getContext());
            mLoadMoreContainer.setMinimumHeight(1);
            mLoadMoreContainer.addView(mLoadMoreView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (mAlwaysShowLoadMoreView) {
                showLoadMoreView();
            } else {
                hideLoadMoreView();
            }
        }
        initDataView(mDataView);

        hideLoadingView();
        hideEmptyView();
    }

    public DataView getDataView() {
        return mDataView;
    }

    public void setDataView(DataView dataView) {
        this.mDataView = dataView;
    }

    public DataAdapter getDataAdapter() {
        if (null == mDataAdapter)
            mDataAdapter = createAdapter();
        return mDataAdapter;
    }

    public void setDataAdapter(DataAdapter dataAdapter) {
        this.mDataAdapter = dataAdapter;
    }


    public void setPagingManager(PagingManager pagingManager) {
        mPagingManager = pagingManager;
    }

    public PagingManager getPagingManager() {
        return mPagingManager;
    }



    public void addLoadMoreIfNeed() {
        if (null == mLoadMoreContainer)
            return;

        if (mDataView instanceof ListView) ((ListView) mDataView).addFooterView(mLoadMoreContainer);
        else if (mDataView instanceof HeaderRecyclerView)
            ((HeaderRecyclerView) mDataView).addFooterView(mLoadMoreContainer);
    }

    public void setEmptyViewIfNeed() {
        if (null == mEmptyView)
            return;
        if (mDataView instanceof AbsListView) ((AbsListView) mDataView).setEmptyView(mEmptyView);
    }

    @Override
    protected void onVisible(boolean isFirstVisibleToUser) {
        if (isFirstVisibleToUser)
            onRefresh();
    }

    /**
     * 最后一个item展示（滑动时）
     */
    protected void onLastItemVisible() {
        if (isLoadMoreEnabled() && isScrollLoadMoreEnabled())
            onLoadMore();
    }


    @Override
    public final void onLoadMore() {

        if (!mLoadMoreEnabled)
            return;

        //没有更多数据了
        if (mPagingManager.isLastPage()) {
            return;
        }

        if (mPagingManager.lock()) {
            mPagingManager.loadMore();
            onLoadStart();
            onLoadData(false, mPagingManager.getNextPageIndex(), mPagingManager.getPageSize());
        }
    }

    @Override
    public final void onRefresh() {
        if (mPagingManager.lock()) {
            /**
             * 获取当前listview的数据总量
             */

            int itemCount = getItemCount();
            int pageIndex = 0;
            int pageSize = mPagingManager.getPageSize();
            if (mIsSupportCustomPageSize && mIsRefreshAll) {

                if (itemCount < mPagingManager.getPageSize()) {
                    pageIndex = mPagingManager.getPageIndexByTotalCount(itemCount) + 1;
                } else {
                    //刷新一页，数量为当前个数
                    pageIndex = mPagingManager.getFirstPageIndex();
                    pageSize = itemCount;
                }

            } else {
                //只刷新第一页
                pageIndex = mPagingManager.getFirstPageIndex();
            }
            mPagingManager.refresh();

            onLoadStart();
            onLoadData(true, pageIndex, pageSize);

        }
    }

    public PagingManager createPagingManager() {
        return new PagingManager();
    }


    /**
     * 加载数据
     *
     * @param pageIndex 分页索引（如果支持）
     * @param pageSize  分页数量（如果支持）
     */
    public void onLoadData(boolean isRefresh, int pageIndex, int pageSize) {

    }


    @Override
    public void setShowLoadingViewForced(boolean forced) {
        mForceShowLoadingView = forced;
    }

    @Override
    public void setLoadMoreAlwaysShow(boolean alwaysShow) {
        mAlwaysShowLoadMoreView = alwaysShow;

        if (!mAlwaysShowLoadMoreView) {
            hideLoadMoreView();
        } else {
            showLoadMoreView();
        }
    }

    protected void showLoadMoreView() {
        if (null != mLoadMoreView) mLoadMoreView.setVisibility(View.VISIBLE);
    }

    protected void hideLoadMoreView() {
        if (null != mLoadMoreView) mLoadMoreView.setVisibility(View.GONE);
    }

    @Override
    public void setLoadMoreEnabled(boolean enabled) {
        mLoadMoreEnabled = enabled;

    }

    public boolean isLoadMoreEnabled() {
        return mLoadMoreEnabled;
    }

    @Override
    public void setScrollLoadMoreEnabled(boolean enabled) {
        mScrollLoadMoreEnabled = enabled;
    }

    public boolean isScrollLoadMoreEnabled() {
        return mScrollLoadMoreEnabled;
    }

    @Override
    public void setClickLoadMoreEnabled(boolean enabled) {
        mClickLoadMoreEnabled = enabled;
        if (null != mLoadMoreView) {
            if (enabled)
                mLoadMoreView.setOnClickListener(mOnLoadMoreClick);
            else
                mLoadMoreView.setOnClickListener(null);
        }

    }

    public boolean isClickLoadMoreEnabled() {
        return mClickLoadMoreEnabled;
    }

    /**
     * 设置刷新时是否刷新当前数据条数
     * 默认为true，同时需要mIsSupportCustomPageSize=true
     *
     * @param isRefreshAll
     */
    public void setRefreshAll(boolean isRefreshAll) {
        this.mIsRefreshAll = isRefreshAll;
    }

    /**
     * 设置分页是否支持自定义每页数量
     * 根据服务器接口实现设置
     *
     * @param isSupportCustomPageSize
     */
    public void setSupportCustomPageSize(boolean isSupportCustomPageSize) {
        this.mIsSupportCustomPageSize = isSupportCustomPageSize;
    }

    /**
     * 分页是否从0开始
     * 默认false，即从1开始
     *
     * @param isPageIndexStartFromZero
     */
    public void setPageIndexStartFromZero(boolean isPageIndexStartFromZero) {
        this.mIsPageIndexStartFromZero = isPageIndexStartFromZero;
    }

    public void setEmptyView(View view) {
        mEmptyView = view;
    }


    protected void showLoadingView() {

        if (null == mLoadingView)
            return;

        if (mForceShowLoadingView) {
            mLoadingView.setVisibility(View.VISIBLE);
        } else {
            if (getItemCount() <= 0) {
                mLoadingView.setVisibility(View.VISIBLE);
            } else {
                mLoadingView.setVisibility(View.GONE);
            }
        }

    }

    protected void hideLoadingView() {
        if (null != mLoadingView) mLoadingView.setVisibility(View.GONE);
    }

    protected void showEmptyView() {
        if (null != mEmptyView) mEmptyView.setVisibility(View.VISIBLE);
    }

    protected void hideEmptyView() {
        if (null != mEmptyView) mEmptyView.setVisibility(View.GONE);
    }

    protected boolean isRefresh() {
        return mPagingManager.isRefresh();
    }

    @Override
    public void onLoadCompleted() {
        mPagingManager.unlock();
        hideLoadingView();

        //有数据，显示加载更多
        if (getItemCount() > 0) {
            showLoadMoreView();
            hideEmptyView();
        } else {
            hideLoadMoreView();
            showEmptyView();
        }


        if (mLoadMoreView instanceof OnLoadListener) {
            ((OnLoadListener) mLoadMoreView).onLoadCompleted();
        }

    }

    @Override
    public void onLoadFailure(String msg) {

        if (mLoadMoreView instanceof OnLoadListener) {
            ((OnLoadListener) mLoadMoreView).onLoadFailure(msg);
        }
        onLoadCompleted();
    }

    @Override
    public void onLoadStart() {
        showLoadingView();
        if (mLoadMoreView instanceof OnLoadListener) {
            ((OnLoadListener) mLoadMoreView).onLoadStart();
        }
    }

    @Override
    public void onLoadSuccess() {
        if (mPagingManager.isRefresh()) {
            mPagingManager.setIndexAfterRefresh(getItemCount());
        } else {
            mPagingManager.addPageIndex();
            mPagingManager.setTotalCount(getItemCount());
        }

        if (mLoadMoreView instanceof OnLoadListener) {
            ((OnLoadListener) mLoadMoreView).onLoadSuccess();
        }
        onLoadCompleted();
    }

    @Override
    public void onLoadSuccessNoData() {

        mPagingManager.setNoMore();

        if (mLoadMoreView instanceof OnLoadListener) {
            ((OnLoadListener) mLoadMoreView).onLoadSuccessNoData();
        }
        onLoadCompleted();
    }

}
