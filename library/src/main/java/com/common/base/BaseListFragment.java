package com.common.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.common.listener.OnLoadListener;
import com.common.tool.PagingManager;

/**
 * Created by hanbing on 2016/3/21.
 */
public abstract class BaseListFragment extends BaseFragment implements IBaseListFragment,AdapterView.OnItemClickListener, OnLoadListener, AbsListView.OnScrollListener {


    /**
     * ListView的父容器
     *
     */
    ViewGroup mParent;

    /**
     * ListView
     */
    ListView mListView;

    /**
     *
     */
    BaseAdapter mListAdapter;

    /**
     * 没有数据时，覆盖ListView展示的View
     * 可以自己定义布局，然后返回
     * 如果没有指定view，将自动创建一个透明的view
     * 如果不想使用，不设置mParent即可
     * 该view只会在listview没有数据时才会展示
     */
    View mEmptyView;

    /**
     *
     */
    View mDefaultEmptyView;

    /**
     * 当加载数据时，覆盖ListView展示的view
     *
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
    boolean mShowLoadingViewForeced = false;

    /**
     * 是否一直显示加载更多
     * 默认false，只在有数据是显示
     */
    boolean mLoadMoreAlwaysShow = false;

    /**
     * 是否支持滑动到底部加载更多
     */
    boolean mLoadMoreEnabled = true;

    /**
     * 是否支持下拉刷新
     */
    boolean mPullToRefreshEnabled = true;

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


    /**
     * 手动滚动
     */
    boolean mIsManScroll = false;

    @Override
    protected void initViews(View view) {
        super.initViews(view);

        mPagingManager = createPagingManager();
        mListView = createListView();
        mListAdapter = createListAdapter();
        mEmptyView = createEmptyView();
        mLoadingView = createLoadingView();
        mLoadMoreView = createLoadMoreView();


        /**
         * 如果有加载更多view，添加到最末尾
         */
        if (null != mLoadMoreView)
        {
            mLoadMoreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLoadMore();
                }
            });
            mLoadMoreContainer = new LinearLayout(getContext());
            mLoadMoreContainer.addView(mLoadMoreView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (mLoadMoreAlwaysShow)
            {
                showLoadMoreView();
            } else {
                hideLoadMoreView();
            }
        }

        if (null != mListView)
        {
            initHeadersAndFooters(mListView);

            if (null != mLoadMoreContainer)
            {
                mListView.addFooterView(mLoadMoreContainer);
            }

            mListView.setEmptyView(mEmptyView);
            mListView.setOnScrollListener(this);

            initListView(mListView);
        }

        hideLoadingView();
        hideEmptyView();
    }

    public ListView getListView () {
        return mListView;
    }
    public BaseAdapter getListAdapter() {
        return mListAdapter;
    }

    @Override
    protected void onViewVisible(boolean isCreated, boolean isFirstCreated) {
        if (isCreated)
        onRefresh();
    }

    @Override
    public final void onLoadMore() {

        //没有更多数据了
        if (mPagingManager.isLastPage())
        {
            return;
        }

        if (mPagingManager.lock())
        {
            mPagingManager.loadMore();
            onLoadStart();
            onLoadData(false, mPagingManager.getNextPageIndex(), mPagingManager.getPageSize());
        }
    }

    @Override
    public final void onRefresh() {
        if (mPagingManager.lock())
        {
            /**
             * 获取当前listview的数据总量
             */

            int itemCount = getItemCount();
            int pageIndex = 0;
            int pageSize = mPagingManager.getPageSize();
            if (mIsSupportCustomPageSize && mIsRefreshAll) {

                if (itemCount < mPagingManager.getPageSize())
                {
                    pageIndex = mPagingManager.getPageIndexByTotalCount(itemCount)+1;
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

    public PagingManager createPagingManager(){
        return new PagingManager();
    }


    /**
     * 加载数据
     * @param pageIndex 分页索引（如果支持）
     * @param pageSize 分页数量（如果支持）
     */
    public  void onLoadData(boolean isRefresh, int pageIndex, int pageSize)
    {

    }

    /**
     * 返回当前数据个数
     * @return
     */
    private int getItemCount()
    {
        return null != mListAdapter ? mListAdapter.getCount() : 0;
    }


    @Override
    public void setShowLoadingViewForced(boolean forced) {

        mShowLoadingViewForeced = forced;
    }

    @Override
    public void setLoadMoreAlwaysShow(boolean alwaysShow) {
        mLoadMoreAlwaysShow = alwaysShow;

        if (!mLoadMoreAlwaysShow)
        {
           hideLoadMoreView();
        } else {
            showLoadMoreView();
        }
    }

    private void showLoadMoreView(){
        if (null != mLoadMoreView)
        {
            mLoadMoreView.setVisibility(View.VISIBLE);
        }
    }
    private void hideLoadMoreView(){
        if (null != mLoadMoreView)
        {
            mLoadMoreView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setLoadMoreEnabled(boolean enabled) {
        mLoadMoreEnabled = enabled;
    }

    @Override
    public void setPullToRefreshEnabled(boolean enabled) {
        mPullToRefreshEnabled = enabled;
    }

    /**
     * 设置刷新时是否刷新当前数据条数
     * 默认为true，同时需要mIsSupportCustomPageSize=true
     * @param isRefreshAll
     */
    public void setRefreshAll(boolean isRefreshAll)
    {
        this.mIsRefreshAll = isRefreshAll;
    }

    /**
     * 设置分页是否支持自定义每页数量
     * 根据服务器接口实现设置
     * @param isSupportCustomPageSize
     */
    public void setSupportCustomPageSize(boolean isSupportCustomPageSize) {
        this.mIsSupportCustomPageSize = isSupportCustomPageSize;
    }

    /**
     * 分页是否从0开始
     * 默认false，即从1开始
     * @param isPageIndexStartFromZero
     */
    public void setPageIndexStartFromZero(boolean isPageIndexStartFromZero) {
        this.mIsPageIndexStartFromZero = isPageIndexStartFromZero;
    }

    public void setEmptyView(View view){
        mEmptyView = view;
    }



    private void showLoadingView() {

        if (null == mLoadingView)
            return;

        if (mShowLoadingViewForeced) {
            mLoadingView.setVisibility(View.VISIBLE);
        } else {
            if (getItemCount() <= 0)
            {
                mLoadingView.setVisibility(View.VISIBLE);
            } else {
                mLoadingView.setVisibility(View.GONE);
            }
        }

    }

    private void hideLoadingView(){
        if (null != mLoadingView)
        {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    private void showEmptyView(){
        if (null != mEmptyView)
        {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    private void hideEmptyView(){
        if (null != mEmptyView)
        {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    protected boolean isRefresh()
    {
        return mPagingManager.isRefresh();
    }

    @Override
    public void onLoadCompleted() {
        mPagingManager.unlock();
        hideLoadingView();

        //有数据，显示加载更多
        if (getItemCount() > 0)
        {
            showLoadMoreView();
        } else {
            showEmptyView();
        }

        if (mLoadMoreView instanceof OnLoadListener) {
            ((OnLoadListener)mLoadMoreView).onLoadCompleted();
        }

    }

    @Override
    public void onLoadFailure(String msg) {

        if ( mLoadMoreView instanceof OnLoadListener) {
            ((OnLoadListener)mLoadMoreView).onLoadFailure(msg);
        }
        onLoadCompleted();
    }

    @Override
    public void onLoadStart() {
        showLoadingView();
        if ( mLoadMoreView instanceof OnLoadListener) {
            ((OnLoadListener)mLoadMoreView).onLoadStart();
        }
    }

    @Override
    public void onLoadSuccess() {
        if (mPagingManager.isRefresh())
        {
            mPagingManager.setIndexAfterRefresh(getItemCount());
        } else {
            mPagingManager.addPageIndex();
            mPagingManager.setTotalCount(getItemCount());
        }

        if (mLoadMoreView instanceof OnLoadListener) {
            ((OnLoadListener)mLoadMoreView).onLoadSuccess();
        }
        onLoadCompleted();
    }

    @Override
    public void onLoadSuccessNoData() {

        mPagingManager.setNoMore();

        if (mLoadMoreView instanceof OnLoadListener) {
            ((OnLoadListener)mLoadMoreView).onLoadSuccessNoData();
        }
        onLoadCompleted();
    }




    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (SCROLL_STATE_TOUCH_SCROLL == scrollState || SCROLL_STATE_FLING == scrollState) {
            mIsManScroll = true;
        } else {
            mIsManScroll = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        //最有一个item展示
        if (mLoadMoreEnabled && mIsManScroll
                && lastVisibleItem == totalItemCount) {
            onLoadMore();
        }
    }
}
