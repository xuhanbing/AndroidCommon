package com.hanbing.library.android.tool;

import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.hanbing.library.android.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;


/**
 * Use for check if view has arrive start(top or left) or end(bottom or right).
 * Created by hanbing on 2016/7/26
 */
public class ViewChecker {


    public static boolean arriveStart(View view, boolean vertical) {
        if (null == view) return false;

        if (view instanceof AbsListView) {
            return arriveStart((AbsListView) view, vertical);
        } else if (view instanceof RecyclerView) {
            return arriveStartInner((RecyclerView) view, vertical);
        } else {
            return arriveStartInner(view, vertical);
        }


    }

    public static boolean arriveEnd(View view, boolean vertical) {
        if (null == view) return false;

        if (view instanceof AbsListView) {
            return arriveEnd((AbsListView) view, vertical);
        } else if (view instanceof RecyclerView) {
            return arriveEndInner((RecyclerView) view, vertical);
        } else {
            return arriveEndInner(view, vertical);
        }
    }

    private static boolean arriveStartInner(View view, boolean vertical) {
        if (null == view) return false;

        return vertical ? !ViewCompat.canScrollVertically(view, -1) : !ViewCompat.canScrollHorizontally(view, -1);
    }

    private static boolean arriveEndInner(View view, boolean vertical) {
        if (null == view) return false;

        return vertical ? !ViewCompat.canScrollVertically(view, 1) : !ViewCompat.canScrollHorizontally(view, 1);
    }

    public static boolean arriveStart(AbsListView absListView, boolean vertical) {
        if (null == absListView) return false;

        if (!vertical) return arriveStartInner(absListView, vertical);

        BaseAdapter adapter = (BaseAdapter) absListView.getAdapter();

        if (null != adapter && adapter.getCount() > 0 && absListView.getFirstVisiblePosition() == 0) {

            View child = absListView.getChildAt(0);

            if (null != child) {
                return child.getTop() == absListView.getPaddingTop();
            }
        }
        return false;
    }

    public static boolean arriveEnd(AbsListView absListView, boolean vertical) {
        if (null == absListView) return false;

        if (!vertical) return arriveEndInner(absListView, vertical);

        BaseAdapter adapter = (BaseAdapter) absListView.getAdapter();

        if (null != adapter && adapter.getCount() > 0) {

            int firstVisiblePosition = absListView.getFirstVisiblePosition();
            int lastVisiblePosition = absListView.getLastVisiblePosition();

            /**
             * last item
             */
            if (lastVisiblePosition == adapter.getCount() - 1) {


                View child = absListView.getChildAt(lastVisiblePosition - firstVisiblePosition);

                if (null != child) {
                    return child.getBottom() == absListView.getHeight() - absListView.getPaddingBottom();
                }
            }

        }

        return false;
    }

    public static boolean isLastItemVisible(View view, boolean vertical) {

        if (view instanceof AbsListView) {
            AbsListView listView = (AbsListView) view;

            BaseAdapter adapter = (BaseAdapter) listView.getAdapter();

            if (null == adapter || adapter.getCount() <= 0)
                return false;

            int lastVisiblePosition = listView.getLastVisiblePosition();

            if (lastVisiblePosition == adapter.getCount() - 1)
                return true;

        } else if (view instanceof RecyclerView) {


            RecyclerView recyclerView = (RecyclerView) view;

            RecyclerView.Adapter adapter = recyclerView.getAdapter();

            if (null == adapter || adapter.getItemCount() == 0)
                return false;

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

            if (layoutManager instanceof LinearLayoutManager) {

                /**
                 * check if  last completely visible position is last item
                 */
                if (layoutManager.getItemCount() - 1 == ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition())
                    return true;

            } else {
                /**
                 * if StaggeredGridLayoutManager we can't check if last visible items are real last items, so just check if arrive end
                 */
                return arriveEndInner(view, vertical);
            }
        }

        return false;
    }
}
