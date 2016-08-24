package com.hanbing.library.android.fragment.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.ScrollView;

import com.hanbing.library.android.tool.PagingManager;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.view.list.FullHeightListView;
import com.hanbing.library.android.view.scroll.CallbackScrollView;

/**
 * Created by hanbing on 2016/7/29
 */
public class FullHeightListFragment extends ListFragment {

    FullHeightListView mFullHeightListView;

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mFullHeightListView = new FullHeightListView(getContext());
        mFullHeightListView.setFullHeight(isFullHeight());
        return mFullHeightListView;
    }

    @Override
    public ListView createListView() {
        return mFullHeightListView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //默认查找最近的ScrollView
        if (isFullHeight()) {
            ScrollView scrollView = findScrollView(view);
            if (null != scrollView)
            setScrollView(scrollView);
        }
    }


    protected ScrollView findScrollView(View view) {
        if (null == view)
            return null;
        ViewParent parent = view.getParent();

        if (null == parent)
            return null;
        if (parent instanceof ViewGroup) {
            if (parent instanceof ScrollView) {
                return (ScrollView) parent;
            } else {
                return findScrollView((ViewGroup) parent);
            }
        }
        return null;
    }

    public void setScrollView(final ScrollView scrollView) {
        //如果不是自适应高度，不设置
        if (!isFullHeight())
            return;

        if (null == scrollView)
            return;

        if (scrollView instanceof CallbackScrollView) {
            ((CallbackScrollView) scrollView).addOnFlingChangedListener(new CallbackScrollView.OnFlingChangedListener() {
                @Override
                public void onFlingStarted() {

                }

                @Override
                public void onFlingFinished() {
                    onLastItemVisibleInScrollView(scrollView);
                }
            });
        }

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    onLastItemVisibleInScrollView(scrollView);
                }
                return false;
            }
        });
    }

    /**
     * 在scrollview中，判断最有一个item是否显示
     * @param scrollView
     * @return
     */
    public boolean onLastItemVisibleInScrollView(ScrollView scrollView) {
        //如果不可见，返回false
        if (!isVisible())
            return false;

        PagingManager pagingManager = getPagingManager();

        if (pagingManager.isBusy() || pagingManager.isLastPage())
            return false;

        if (null == getDataView() || getItemCount() <= 0)
            return false;

        int[] loc = new int[2];

        scrollView.getLocationOnScreen(loc);

        int bottom = loc[1] + scrollView.getMeasuredHeight();

        View child = getDataView().getChildAt(getItemCount() - 1);

        if (null != child) {
            child.getLocationOnScreen(loc);

            if (loc[1] < bottom) {
                onLoadMore();
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onLastItemVisible() {
        //自适应listview时，最后一个item会一直展示，因此该判断不准确，需要通过
        if (isFullHeight())
            return;
        super.onLastItemVisible();
    }

    protected boolean isFullHeight() {
        return true;
    }
}
