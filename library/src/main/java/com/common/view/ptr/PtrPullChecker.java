package com.common.view.ptr;

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
 * Created by hanbing
 */
public class PtrPullChecker implements IPtrPullChecker{


    DefaultPullChecker mDefaultPullChecker = new DefaultPullChecker();
    AbsListViewPullChecker mAbsListViewPullChecker = new AbsListViewPullChecker();
    RecyclerViewPullChecker mRecyclerViewPullChecker = new RecyclerViewPullChecker();

    @Override
    public boolean canPullFromStart(PtrLayout ptrLayout) {
        if (null == ptrLayout || null == ptrLayout.getContentView())
            return false;

        View content = ptrLayout.getContentView();

        if (content instanceof AbsListView) {
            return mAbsListViewPullChecker.canPullFromStart(ptrLayout);
        } else if (content instanceof RecyclerView) {
            return mRecyclerViewPullChecker.canPullFromStart(ptrLayout);
        } else {
            return mDefaultPullChecker.canPullFromStart(ptrLayout);
        }

    }

    @Override
    public boolean canPullFromEnd(PtrLayout ptrLayout) {
        if (null == ptrLayout || null == ptrLayout.getContentView())
            return false;

        View content = ptrLayout.getContentView();

        if (content instanceof AbsListView) {
            return mAbsListViewPullChecker.canPullFromEnd(ptrLayout);
        } else if (content instanceof RecyclerView) {
            return mRecyclerViewPullChecker.canPullFromEnd(ptrLayout);
        } else {
            return mDefaultPullChecker.canPullFromEnd(ptrLayout);
        }
    }


    class DefaultPullChecker implements IPtrPullChecker {

        @Override
        public boolean canPullFromStart(PtrLayout ptrLayout) {

            if (null != ptrLayout && null != ptrLayout.getContentView()) {
                View content = ptrLayout.getContentView();
                return ptrLayout.isVertical() ? !ViewCompat.canScrollVertically(content, -1) : !ViewCompat.canScrollHorizontally(content, -1);
            }

            return false;
        }

        @Override
        public boolean canPullFromEnd(PtrLayout ptrLayout) {
            if (null != ptrLayout && null != ptrLayout.getContentView()) {
                View content = ptrLayout.getContentView();
                return ptrLayout.isVertical() ? !ViewCompat.canScrollVertically(content, 1) : !ViewCompat.canScrollHorizontally(content, 1);
            }

            return false;
        }
    }

    class AbsListViewPullChecker implements IPtrPullChecker {

        @Override
        public boolean canPullFromStart(PtrLayout ptrLayout) {
            if (null != ptrLayout && null != ptrLayout.getContentView()) {
                AbsListView listView = (AbsListView) ptrLayout.getContentView();

                BaseAdapter adapter = (BaseAdapter) listView.getAdapter();

                if (null != adapter && adapter.getCount() > 0 && listView.getFirstVisiblePosition() == 0) {

                    View child = listView.getChildAt(0);

                    if (null != child) {
                        return child.getTop() == listView.getPaddingTop();
                    }
                }

            }
            return false;
        }

        @Override
        public boolean canPullFromEnd(PtrLayout ptrLayout) {
            if (null != ptrLayout && null != ptrLayout.getContentView()) {
                AbsListView listView = (AbsListView) ptrLayout.getContentView();

                BaseAdapter adapter = (BaseAdapter) listView.getAdapter();

                if (null != adapter && adapter.getCount() > 0 ) {

                    int firstVisiblePosition = listView.getFirstVisiblePosition();
                    int lastVisiblePosition = listView.getLastVisiblePosition();

                    /**
                     * last item
                     */
                    if (lastVisiblePosition == adapter.getCount() - 1)
                    {
                        View child = listView.getChildAt(lastVisiblePosition - firstVisiblePosition);

                        if (null != child) {
                            return child.getBottom() == listView.getHeight() - listView.getPaddingBottom();
                        }
                    }

                }

            }
            return false;
        }
    }

    class RecyclerViewPullChecker implements IPtrPullChecker {


        @Override
        public boolean canPullFromStart(PtrLayout ptrLayout) {
            if (null != ptrLayout && null != ptrLayout.getContentView()) {
                RecyclerView recyclerView = (RecyclerView) ptrLayout.getContentView();
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
                        if (ptrLayout.isVertical())
                        return child.getTop() == recyclerView.getPaddingTop();
                        else
                            return child.getLeft() == recyclerView.getPaddingLeft();
                    }
                }
            }

            return false;
        }

        @Override
        public boolean canPullFromEnd(PtrLayout ptrLayout) {

            if (null != ptrLayout && null != ptrLayout.getContentView()) {
                RecyclerView recyclerView = (RecyclerView) ptrLayout.getContentView();
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
                    List<Integer>  positionList = Arrays.asList(lastPositions);

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
                        if (ptrLayout.isVertical())
                        return child.getBottom() == recyclerView.getHeight() - recyclerView.getPaddingBottom();
                        else
                            return child.getRight() == recyclerView.getWidth() - recyclerView.getPaddingRight();
                    }
                }
            }

            return false;
        }
    }

    public static IPtrPullChecker defaultPtrPullChecker() {
        return new PtrPullChecker();
    }
}
