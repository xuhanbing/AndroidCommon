package com.hanbing.library.android.fragment.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.hanbing.library.android.fragment.BaseFragment;
import com.hanbing.library.android.listener.OnLoadListener;
import com.hanbing.library.android.tool.DataViewHelper;
import com.hanbing.library.android.tool.PagingManager;
import com.hanbing.library.android.util.CollectionUtils;
import com.hanbing.library.android.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing on 2016/3/21.
 */
public abstract class DataViewFragment<DataView extends View, DataAdapter, Bean> extends BaseFragment implements IDataViewFragment<DataView, DataAdapter>, OnLoadListener {

    DataViewHelper<DataView, DataAdapter> mDataViewHelper;
    protected List<Bean> mDataList = new ArrayList<>();

    protected DataViewHelper<DataView, DataAdapter> createDataViewHelper() {
        return new DataViewHelper<DataView, DataAdapter>(getContext()) {

            @Override
            public void onLoadData(boolean isRefresh, int pageIndex, int pageSize) {
                DataViewFragment.this.onLoadData(isRefresh, pageIndex, pageSize);
            }

            @Override
            public int getItemCount() {
                return DataViewFragment.this.getItemCount();
            }


        };
    }

    public DataViewHelper<DataView, DataAdapter> getDataViewHelper() {
        return mDataViewHelper;
    }

    public void setDataViewHelper(DataViewHelper<DataView, DataAdapter> dataViewHelper) {
        mDataViewHelper = dataViewHelper;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);

        if (null == mDataViewHelper)
            mDataViewHelper = createDataViewHelper();

        if (null == mDataViewHelper)
            throw new IllegalArgumentException("mDataViewHelper must not be null.");

        mDataViewHelper.setDataView(createDataView());
        mDataViewHelper.setDataAdapter(createAdapter());
        mDataViewHelper.setEmptyView(createEmptyView());
        mDataViewHelper.setLoadingView(createLoadingView());
        mDataViewHelper.setLoadMoreView(createLoadMoreView());
        mDataViewHelper.setPagingManager(createPagingManager());
        mDataViewHelper.init();

        initDataView(getDataView());
    }

    public DataView getDataView() {
        if (null != mDataViewHelper)

            return mDataViewHelper.getDataView();
        return null;
    }

    public void setDataView(DataView dataView) {
        if (null != mDataViewHelper)
            mDataViewHelper.setDataView(dataView);
    }

    public DataAdapter getDataAdapter() {
        if (null != mDataViewHelper)
            return mDataViewHelper.getDataAdapter();
        return null;
    }

    public void setDataAdapter(DataAdapter dataAdapter) {
        if (null != mDataViewHelper)

            this.mDataViewHelper.setDataAdapter(dataAdapter);
    }


    public void setPagingManager(PagingManager pagingManager) {
        if (null != mDataViewHelper)

            mDataViewHelper.setPagingManager(pagingManager);
    }

    public PagingManager getPagingManager() {
        if (null != mDataViewHelper)
            return mDataViewHelper.getPagingManager();
        return null;
    }

    public void addLoadMoreIfNeed() {
        if (null != mDataViewHelper)
            mDataViewHelper.addLoadMoreIfNeed();
    }

    public void setEmptyViewIfNeed() {
        if (null != mDataViewHelper)
            mDataViewHelper.setEmptyViewIfNeed();
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
        if (null != mDataViewHelper)
            mDataViewHelper.onLastItemVisible();
    }


    @Override
    public void onLoadMore() {
        if (null != mDataViewHelper)
            mDataViewHelper.onLoadMore();
    }

    @Override
    public void onRefresh() {
        if (null != mDataViewHelper)
            mDataViewHelper.onRefresh();
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
        if (null != mDataViewHelper)
            mDataViewHelper.setShowLoadingViewForced(forced);
    }

    @Override
    public void setLoadMoreAlwaysShow(boolean alwaysShow) {
        if (null != mDataViewHelper)
            mDataViewHelper.setLoadMoreAlwaysShow(alwaysShow);
    }

    protected void showLoadMoreView() {
        if (null != mDataViewHelper)
            mDataViewHelper.showLoadMoreView();
    }

    protected void hideLoadMoreView() {
        if (null != mDataViewHelper)
            mDataViewHelper.hideLoadMoreView();
    }

    @Override
    public void setLoadMoreEnabled(boolean enabled) {
        if (null != mDataViewHelper) mDataViewHelper.setLoadMoreEnabled(enabled);
    }

    public boolean isLoadMoreEnabled() {
        if (null != mDataViewHelper)
            return mDataViewHelper.isLoadMoreEnabled();
        return false;
    }

    @Override
    public void setScrollLoadMoreEnabled(boolean enabled) {
        if (null != mDataViewHelper)
            mDataViewHelper.setScrollLoadMoreEnabled(enabled);
    }

    public boolean isScrollLoadMoreEnabled() {
        if (null != mDataViewHelper)
            return mDataViewHelper.isScrollLoadMoreEnabled();
        return false;
    }

    @Override
    public void setClickLoadMoreEnabled(boolean enabled) {
        if (null != mDataViewHelper)
            mDataViewHelper.setClickLoadMoreEnabled(enabled);
    }

    public boolean isClickLoadMoreEnabled() {
        if (null != mDataViewHelper)
            return mDataViewHelper.isClickLoadMoreEnabled();
        return false;
    }

    /**
     * 设置刷新时是否刷新当前数据条数
     * 默认为true，同时需要mIsSupportCustomPageSize=true
     *
     * @param isRefreshAll
     */
    public void setRefreshAll(boolean isRefreshAll) {
        if (null != mDataViewHelper)
            mDataViewHelper.setRefreshAll(isRefreshAll);
    }

    /**
     * 设置分页是否支持自定义每页数量
     * 根据服务器接口实现设置
     *
     * @param isSupportCustomPageSize
     */
    public void setSupportCustomPageSize(boolean isSupportCustomPageSize) {
        if (null != mDataViewHelper)
            mDataViewHelper.setSupportCustomPageSize(isSupportCustomPageSize);
    }

    /**
     * 分页是否从0开始
     * 默认false，即从1开始
     *
     * @param isPageIndexStartFromZero
     */
    public void setPageIndexStartFromZero(boolean isPageIndexStartFromZero) {
        if (null != mDataViewHelper)
            mDataViewHelper.setPageIndexStartFromZero(isPageIndexStartFromZero);
    }

    public void setEmptyView(View view) {
        if (null != mDataViewHelper)
            mDataViewHelper.setEmptyView(view);
    }


    protected void showLoadingView() {
        if (null != mDataViewHelper)
            mDataViewHelper.showLoadingView();
    }

    protected void hideLoadingView() {
        if (null != mDataViewHelper)
            mDataViewHelper.hideLoadingView();
    }

    protected void showEmptyView() {
        if (null != mDataViewHelper)
            mDataViewHelper.showEmptyView();
    }

    protected void hideEmptyView() {
        if (null != mDataViewHelper)
            mDataViewHelper.hideEmptyView();
    }

    public boolean isRefresh() {
        if (null != mDataViewHelper)
            return mDataViewHelper.isRefresh();
        return false;
    }

    @Override
    public void onLoadCompleted() {
    }

    @Override
    public void onLoadFailure(String msg) {
        if (null != mDataViewHelper)
            mDataViewHelper.onLoadFailure(msg);
        onLoadCompleted();
    }

    @Override
    public void onLoadStart() {
        if (null != mDataViewHelper)
            mDataViewHelper.onLoadStart();
    }

    @Override
    public void onLoadSuccess() {
        if (null != mDataViewHelper)
            mDataViewHelper.onLoadSuccess();
        onLoadCompleted();
    }

    @Override
    public void onLoadSuccessNoData() {
        if (null != mDataViewHelper)
            mDataViewHelper.onLoadSuccessNoData();
        onLoadCompleted();
    }

    protected void onLoadMoreClick() {
        if (null != mDataViewHelper)
            mDataViewHelper.onLoadMoreClick();
    }

    @Override
    public void notifyDataSetChanged() {
        if (null != mDataViewHelper)
            mDataViewHelper.notifyDataSetChanged();
    }

    /**
     * 数据加载成功
     *
     * @param list data list
     */
    protected void onLoadSuccess(List<? extends Bean> list) {

        if (isRefresh()) {
            mDataList.clear();
            notifyDataSetChanged();
        }

        if (null == list || list.isEmpty()) {
            onLoadSuccessNoData();
            return;
        }

        CollectionUtils.addAll(mDataList, list);
        notifyDataSetChanged();
        onLoadSuccess();
    }

    /**
     * @param rootView
     * @return
     */
    protected View addViewAtDataViewHierarchy(View rootView, View... views) {

        if (null == rootView || null == views || 0 == views.length)
            return rootView;

        for (View view : views) {
            rootView = addViewAtDataViewHierarchy(rootView, view);
        }

        return rootView;
    }

    /**
     * 在dataview的同一层次添加view，如果没有parent或者不是relativelayout或者framelayout，则包裹一层
     *
     * @param rootView
     * @param child
     * @return
     */
    protected View addViewAtDataViewHierarchy(View rootView, View child) {

        View dataView = createDataView();

        if (null == rootView || null == dataView || null == child)
            return rootView;


        return ViewUtils.wrapWithEmptyView(rootView, dataView, child);
    }

    protected void reset() {
        mDataViewHelper.reset();
    }


    protected View getEmptyView() {
        if (null != getDataViewHelper())
            return getDataViewHelper().getEmptyView();

        return null;
    }

    protected View getLoadingView() {
        if (null != getDataViewHelper())
            return getDataViewHelper().getLoadingView();
        return null;
    }
}
