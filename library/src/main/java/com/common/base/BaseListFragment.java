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
public abstract class BaseListFragment extends BaseFragment implements IBaseListFragment,AdapterView.OnItemClickListener, OnLoadListener{


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
     * 是否一直显示加载更多
     * 默认false
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

    @Override
    protected void initViews(View view) {
        super.initViews(view);

        mListView = createListView();
        mListAdapter = createListAdapter();
        mEmptyView = createEmptyView();
        mLoadingView = createLoadingView();
        mLoadMoreView = createLoadMoreView();


        hideLoadingView();
        hideEmptyView();

        initHeadersAndFooters(mListView);

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
            mListView.addFooterView(mLoadMoreContainer);
            mLoadMoreContainer.addView(mLoadMoreView);
            if (mLoadMoreAlwaysShow)
            {
                mLoadMoreView.setVisibility(View.VISIBLE);
            } else {
                mLoadMoreView.setVisibility(View.GONE);
            }
        }

        if (null != mListView)
        {
            mListView.setAdapter(mListAdapter);
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    /**
                     * 所有item都展示，表示数据不够
                     */
                    if (visibleItemCount == totalItemCount) {
                        //如果一直显示加载更多，隐藏
                        if (!mLoadMoreAlwaysShow)
                            hideLoadMoreView();
                    } else {
                        //最有一个item展示
                        if (mLoadMoreEnabled
                                &&firstVisibleItem + visibleItemCount == totalItemCount) {
                            /**
                             * 最有一个item展示了，显示加载更多
                             * 显示点击加载更多
                             */
                            if (!mLoadMoreAlwaysShow)
                                showLoadMoreView();

                            onLoadMore();
                        }
                    }
                }
            });
        }
        initListView(mListView);
        setEmptyView();


    }

    @Override
    protected void onViewCreatedOrVisible() {
        onRefresh();
    }

    @Override
    public final void onLoadMore() {

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

    private void setEmptyView()
    {
        if (null != mListView)
        {
            mListView.setEmptyView(mEmptyView);
        }
    }


    private void showLoadingView()
    {
        if (null != mLoadingView)
        {
            mLoadingView.setVisibility(View.VISIBLE);
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
        if (mPagingManager.isRefresh())
        {
            mPagingManager.setIndexAfterRefresh(getItemCount());
        } else {
            mPagingManager.addPageIndex();
            mPagingManager.setTotalCount(getItemCount());
        }

        hideLoadingView();

    }

    @Override
    public void onLoadFailure(String msg) {

    }

    @Override
    public void onLoadStart() {
        showLoadingView();

    }

    @Override
    public void onLoadSuccess() {

    }

    @Override
    public void onLoadSuccessNoData() {

    }
}
