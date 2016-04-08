package com.common.base;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 *
 * Created by hanbing on 2016/3/28.
 */
public interface IBaseListFragment {

    /**
     * 指定listview
     * @return
     */
    public abstract ListView createListView();

    /**
     * 指定listadapter
     * @return
     */
    public abstract BaseAdapter createListAdapter();

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
     * 初始化listview，可以在此配置listview的一些属性
     * @param listView
     */
    public abstract void initListView(ListView listView);

    /**
     * 初始化ListView的header和footer，该方法会在initListView之前调用
     * @param listView
     */
    public abstract void initHeadersAndFooters(ListView listView);

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
     * 设置是否支持滑动到底部加载更多
     * @param enabled
     */
    public abstract void setLoadMoreEnabled(boolean enabled);

    /**
     * 设置是否支持下拉刷新
     * @param enabled
     */
    public abstract void setPullToRefreshEnabled(boolean enabled);

    /**
     * 刷新
     */
    public abstract void onRefresh();

    /**
     * 加载更多
     */
    public abstract void onLoadMore();
}
