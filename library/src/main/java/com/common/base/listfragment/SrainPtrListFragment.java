package com.common.base.listfragment;

import android.view.View;
import android.widget.AbsListView;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by hanbing on 2016/3/29.
 */
public abstract class SrainPtrListFragment extends SimpleListFragment {

    PtrFrameLayout mPtrFrameLayout;


    public abstract PtrFrameLayout createPtrFrameLayout();


    @Override
    protected void initViews(View view) {
        super.initViews(view);

        mPtrFrameLayout = createPtrFrameLayout();
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                onRefresh();
            }
        });
    }

    @Override
    protected void onViewVisible(boolean isCreated, boolean isFirstCreated) {

        if (isFirstCreated)
        {
            mPtrFrameLayout.post(new Runnable() {
                @Override
                public void run() {
                    mPtrFrameLayout.autoRefresh();
                }
            });
        }
    }

    @Override
    public void onLoadCompleted() {
        super.onLoadCompleted();
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (null != mPtrFrameLayout)
        {
            View first = view.getChildAt(firstVisibleItem);
            if (0 == firstVisibleItem && (null == first || 0 == first.getTop())) {
                mPtrFrameLayout.setEnabled(true);
            } else {
                mPtrFrameLayout.setEnabled(false);
            }
        }


        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }
}
