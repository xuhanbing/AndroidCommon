package com.hanbing.library.android.view;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hanbing on 2016/7/26
 */
public class ViewChecker {


    public static boolean arriveStart(View view, boolean vertical) {
        if (null == view) return  false;

        if (view instanceof AbsListView) {
            return arriveStart((AbsListView) view, vertical);
        } else if (view instanceof RecyclerView) {
            return arriveStart((RecyclerView) view, vertical);
        } else {
            return arriveStartInner(view, vertical);
        }


    }

    public static boolean arriveEnd(View view, boolean vertical) {
        if (null == view) return  false;

        if (view instanceof AbsListView) {
            return arriveEnd((AbsListView) view, vertical);
        } else if (view instanceof RecyclerView) {
            return arriveEnd((RecyclerView)view, vertical);
        } else {
            return arriveEndInner(view, vertical);
        }
    }

    private static boolean arriveStartInner(View view, boolean vertical) {
        if (null == view) return  false;

        return  vertical ? !ViewCompat.canScrollVertically(view, -1) : !ViewCompat.canScrollHorizontally(view, -1);
    }

    private static boolean arriveEndInner(View view, boolean vertical) {
        if (null == view) return  false;

        return vertical ? !ViewCompat.canScrollVertically(view, 1) : !ViewCompat.canScrollHorizontally(view, 1);
    }

    public static boolean arriveStart(AbsListView absListView, boolean vertical) {
        if (null == absListView) return  false;

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
        if (null == absListView) return  false;

        if (!vertical) return arriveEndInner(absListView, vertical);

        BaseAdapter adapter = (BaseAdapter) absListView.getAdapter();

        if (null != adapter && adapter.getCount() > 0 ) {

            int firstVisiblePosition = absListView.getFirstVisiblePosition();
            int lastVisiblePosition = absListView.getLastVisiblePosition();

            /**
             * last item
             */
            if (lastVisiblePosition == adapter.getCount() - 1)
            {
                View child = absListView.getChildAt(lastVisiblePosition - firstVisiblePosition);

                if (null != child) {
                    return child.getBottom() == absListView.getHeight() - absListView.getPaddingBottom();
                }
            }

        }

        return false;
    }

    public static boolean arriveStart(RecyclerView recyclerView, boolean vertical) {
        if (null == recyclerView) return  false;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();


        if (layoutManager instanceof LinearLayoutManager) {

            /**
             * check if first completely visible position is first item
             */
            if (0 == ((LinearLayoutManager)layoutManager).findFirstCompletelyVisibleItemPosition())
                return true;

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;

            /**
             * find first completely visible positions
             */
            int[] positions = staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(null);

            if (null != positions && positions.length > 0) {

                for (int i = 0; i < positions.length; i++) {
                    /**
                     * return true if each position equals the index in the array or positions all = 0, otherwise return false
                     */
                    if (positions[i] != 0 && positions[i] != i ) {
                        return false;
                    }
                }

                return true;
            }

        } else {

            /**
             * check use default mode
             */
            View child = layoutManager.getChildAt(0);

            if (null != child) {
                if (vertical)
                    return child.getTop() == recyclerView.getPaddingTop();
                else
                    return child.getLeft() == recyclerView.getPaddingLeft();
            }
        }

        return false;
    }

    public static boolean arriveEnd(RecyclerView recyclerView, boolean vertical) {
        if (null == recyclerView) return  false;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager) {

            /**
             * check if  last completely visible position is last item
             */
            if (layoutManager.getItemCount() - 1 == ((LinearLayoutManager)layoutManager).findLastCompletelyVisibleItemPosition())
                return true;

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;

            int spanCount = staggeredGridLayoutManager.getSpanCount();
            int itemCount = staggeredGridLayoutManager.getItemCount();

            /**
             * each last position in spans
             */
            Integer[] lastPositions = new Integer[spanCount];

            int count = itemCount / spanCount;
            int leftCount = itemCount % spanCount;

            int offset = count > 0 ? (count - 1) * spanCount : 0;

            for (int i = 0; i < spanCount; i++) {

                lastPositions[i] = offset + i;
                if (leftCount > i) {
                    lastPositions[i] += spanCount;
                }
            }

            /**
             * items may not in order
             */
            List<Integer> positionList = Arrays.asList(lastPositions);

            /**
             * find last completely visible positions
             */
            int[] positions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);

            if (null != positions && positions.length > 0) {

                int length = positions.length;

                for (int i = 0; i < length; i++) {

                    /**
                     * if item is not the last one in span ,return false
                     */
                    if (!positionList.contains(positions[i])) {
                        return false;
                    }

                }

                return true;
            }

        } else {

            /**
             * check use default mode
             */
            View child = layoutManager.getChildAt(0);

            if (null != child) {
                if (vertical)
                    return child.getBottom() == recyclerView.getHeight() - recyclerView.getPaddingBottom();
                else
                    return child.getRight() == recyclerView.getWidth() - recyclerView.getPaddingRight();
            }
        }

        return false;
    }
}
