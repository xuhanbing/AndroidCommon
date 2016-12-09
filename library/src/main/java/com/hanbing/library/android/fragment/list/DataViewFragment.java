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
        return mDataViewHelper.getDataView();
    }

    public void setDataView(DataView dataView) {
        mDataViewHelper.setDataView(dataView);
    }

    public DataAdapter getDataAdapter() {
        return mDataViewHelper.getDataAdapter();
    }

    public void setDataAdapter(DataAdapter dataAdapter) {
        this.mDataViewHelper.setDataAdapter(dataAdapter);
    }


    public void setPagingManager(PagingManager pagingManager) {
        mDataViewHelper.setPagingManager(pagingManager);
    }

    public PagingManager getPagingManager() {
        return mDataViewHelper.getPagingManager();
    }

    public void addLoadMoreIfNeed() {
        mDataViewHelper.addLoadMoreIfNeed();
    }

    public void setEmptyViewIfNeed() {
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
        mDataViewHelper.onLastItemVisible();
    }


    @Override
    public final void onLoadMore() {
        mDataViewHelper.onLoadMore();
    }

    @Override
    public final void onRefresh() {
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
        mDataViewHelper.setShowLoadingViewForced(forced);
    }

    @Override
    public void setLoadMoreAlwaysShow(boolean alwaysShow) {
        mDataViewHelper.setLoadMoreAlwaysShow(alwaysShow);
    }

    protected void showLoadMoreView() {
        mDataViewHelper.showLoadMoreView();
    }

    protected void hideLoadMoreView() {
        mDataViewHelper.hideLoadMoreView();
    }

    @Override
    public void setLoadMoreEnabled(boolean enabled) {
        mDataViewHelper.setLoadMoreEnabled(enabled);
    }

    public boolean isLoadMoreEnabled() {
        return mDataViewHelper.isLoadMoreEnabled();
    }

    @Override
    public void setScrollLoadMoreEnabled(boolean enabled) {
        mDataViewHelper.setScrollLoadMoreEnabled(enabled);
    }

    public boolean isScrollLoadMoreEnabled() {
        return mDataViewHelper.isScrollLoadMoreEnabled();
    }

    @Override
    public void setClickLoadMoreEnabled(boolean enabled) {
        mDataViewHelper.setClickLoadMoreEnabled(enabled);

    }

    public boolean isClickLoadMoreEnabled() {
        return mDataViewHelper.isClickLoadMoreEnabled();
    }

    /**
     * 设置刷新时是否刷新当前数据条数
     * 默认为true，同时需要mIsSupportCustomPageSize=true
     *
     * @param isRefreshAll
     */
    public void setRefreshAll(boolean isRefreshAll) {
        mDataViewHelper.setRefreshAll(isRefreshAll);
    }

    /**
     * 设置分页是否支持自定义每页数量
     * 根据服务器接口实现设置
     *
     * @param isSupportCustomPageSize
     */
    public void setSupportCustomPageSize(boolean isSupportCustomPageSize) {
        mDataViewHelper.setSupportCustomPageSize(isSupportCustomPageSize);
    }

    /**
     * 分页是否从0开始
     * 默认false，即从1开始
     *
     * @param isPageIndexStartFromZero
     */
    public void setPageIndexStartFromZero(boolean isPageIndexStartFromZero) {
        mDataViewHelper.setPageIndexStartFromZero(isPageIndexStartFromZero);
    }

    public void setEmptyView(View view) {
        mDataViewHelper.setEmptyView(view);
    }


    protected void showLoadingView() {
        mDataViewHelper.showLoadingView();
    }

    protected void hideLoadingView() {
        mDataViewHelper.hideLoadingView();
    }

    protected void showEmptyView() {
        mDataViewHelper.showEmptyView();
    }

    protected void hideEmptyView() {
        mDataViewHelper.hideEmptyView();
    }

    public boolean isRefresh() {
        return mDataViewHelper.isRefresh();
    }

    @Override
    public void onLoadCompleted() {
    }

    @Override
    public void onLoadFailure(String msg) {
        mDataViewHelper.onLoadFailure(msg);
        onLoadCompleted();
    }

    @Override
    public void onLoadStart() {
        mDataViewHelper.onLoadStart();
    }

    @Override
    public void onLoadSuccess() {
        mDataViewHelper.onLoadSuccess();
        onLoadCompleted();
    }

    @Override
    public void onLoadSuccessNoData() {
        mDataViewHelper.onLoadSuccessNoData();
        onLoadCompleted();
    }

    protected void onLoadMoreClick() {
        mDataViewHelper.onLoadMoreClick();
    }

    @Override
    public void notifyDataSetChanged() {
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
     *
     * @param rootView
     * @return
     */
    protected View addViewAtDataViewHierarchy(View rootView, View ... views) {

        if (null == rootView || null == views || 0 == views.length)
            return rootView;

        for (View view : views) {
            rootView = addViewAtDataViewHierarchy(rootView, view);
        }

        return rootView;
    }

    /**
     * 在dataview的同一层次添加view，如果没有parent或者不是relativelayout或者framelayout，则包裹一层
     * @param rootView
     * @param child
     * @return
     */
    protected View addViewAtDataViewHierarchy(View rootView, View child) {

        View dataView = createDataView();

        if (null == rootView || null == dataView || null == child)
            return rootView;


        ViewGroup parent = (ViewGroup) dataView.getParent();

        ViewGroup viewGroup = null;

        if (null == parent ) {
            //没有parent
            viewGroup = new RelativeLayout(getContext());
            viewGroup.addView(dataView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            rootView = viewGroup;
        } else {

            if (parent instanceof RelativeLayout || parent instanceof FrameLayout) {
                //直接添加
                viewGroup = parent;
            } else {
                //包裹一层relativelayout
                ViewGroup.LayoutParams params = dataView.getLayoutParams();
                int index = parent.indexOfChild(dataView);
                parent.removeView(dataView);

                viewGroup = new RelativeLayout(getContext());
                //将listview加入到新的parent
                viewGroup.addView(dataView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                //将包裹的layout替换原来listview的位置，且layout属性和listview一致
                parent.addView(viewGroup, index, params);
            }

        }

        //添加
        ViewUtils.removeFromParent(child);
        viewGroup.addView(child);

        return rootView;
    }

}
