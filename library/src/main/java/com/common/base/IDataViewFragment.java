package com.common.base;

import android.view.View;

/**
 *
 * Created by hanbing on 2016/3/28.
 */
public interface IDataViewFragment<DataView extends View, DataAdapter> {

    /**
     * 指定绑定数据视图
     * @return
     */
    public abstract DataView createDataView();

    /**
     * 指定适配器
     * @return
     */
    public abstract DataAdapter createAdapter();


    /**
     * 返回数据个数
     * @return
     */
    public abstract int getItemCount();

    /**
     * 当没有数据时展示
     * 使用时需要在listview外嵌套一个RelativeLayout或者FrameLayout，然后在上面覆盖该View即可
     * @return
     */
    public abstract View createEmptyView();

    /**
     * 加载数据时展示
     * 使用时需要在listview外嵌套一个RelativeLayout或者FrameLayout，然后在上面覆盖该View即可
     * 如果没有指定，将使用createEmptyView返回的view
     * @return
     */
    public abstract View createLoadingView();

    /**
     * 加载更多view，该view会自动添加到最末尾
     * 在initListViewHeadersAndFooters后调用
     * @return
     */
    public abstract View createLoadMoreView();

    /**
     * 初始化数据视图
     * @param view
     */
    public abstract void initDataView(DataView view);


    /**
     * 是否强制显示loadingview
     * 如果为true，只要开始加载数据，就显示
     * 否则，只有当getItemCount返回0时才显示
     */
    public abstract void setShowLoadingViewForced(boolean forced);


    /**
     * 加载更多是否总是展示
     * 如果true，会一直展示在最后一个item
     * 如果false，只有当数据超过一屏时，滑动到底部才展示
     * 默认为false
     * @param alwaysShow
     */
    public abstract void setLoadMoreAlwaysShow(boolean alwaysShow);

    /**
     * 设置是否支持加载更多
     * @param enabled
     */
    public abstract void setLoadMoreEnabled(boolean enabled);

    /**
     * 设置是否支持滑动到底部加载更多
     * @param enabled
     */
    public abstract void setScrollLoadMoreEnabled(boolean enabled);

    /**
     * 设置是否支持点击加载更多
     * @param enabled
     */
    public abstract void setClickLoadMoreEnabled(boolean enabled);


    /**
     * 刷新
     */
    public abstract void onRefresh();

    /**
     * 加载更多
     */
    public abstract void onLoadMore();
}
