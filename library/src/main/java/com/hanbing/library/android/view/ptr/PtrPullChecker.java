package com.hanbing.library.android.view.ptr;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.hanbing.library.android.view.ViewChecker;

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
               return ViewChecker.arriveStart(content, ptrLayout.isVertical());
            }

            return false;
        }

        @Override
        public boolean canPullFromEnd(PtrLayout ptrLayout) {
            if (null != ptrLayout && null != ptrLayout.getContentView()) {
                View content = ptrLayout.getContentView();
                return ViewChecker.arriveEnd(content, ptrLayout.isVertical());
            }

            return false;
        }
    }

    class AbsListViewPullChecker implements IPtrPullChecker {

        @Override
        public boolean canPullFromStart(PtrLayout ptrLayout) {
            if (null != ptrLayout && null != ptrLayout.getContentView()) {
                AbsListView absListView = (AbsListView) ptrLayout.getContentView();

                return ViewChecker.arriveStart(absListView, ptrLayout.isVertical());

            }
            return false;
        }

        @Override
        public boolean canPullFromEnd(PtrLayout ptrLayout) {
            if (null != ptrLayout && null != ptrLayout.getContentView()) {
                AbsListView absListView = (AbsListView) ptrLayout.getContentView();

                return ViewChecker.arriveEnd(absListView, ptrLayout.isVertical());

            }
            return false;
        }
    }

    class RecyclerViewPullChecker implements IPtrPullChecker {


        @Override
        public boolean canPullFromStart(PtrLayout ptrLayout) {
            if (null != ptrLayout && null != ptrLayout.getContentView()) {
                RecyclerView recyclerView = (RecyclerView) ptrLayout.getContentView();

                return ViewChecker.arriveStart(recyclerView, ptrLayout.isVertical());
            }

            return false;
        }

        @Override
        public boolean canPullFromEnd(PtrLayout ptrLayout) {

            if (null != ptrLayout && null != ptrLayout.getContentView()) {
                RecyclerView recyclerView = (RecyclerView) ptrLayout.getContentView();

                return ViewChecker.arriveEnd(recyclerView, ptrLayout.isVertical());
            }

            return false;
        }
    }

    public static IPtrPullChecker defaultPtrPullChecker() {
        return new PtrPullChecker();
    }
}
