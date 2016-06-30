package com.common.fragment.list;

import android.view.View;
import android.widget.AbsListView;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * Created by hanbing on 2016/3/29.
 */
public abstract class SrainPtrGridFragment extends GridFragment {

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
        
        initHeaderView(mPtrFrameLayout);
    }

    protected void initHeaderView(PtrFrameLayout ptrFrameLayout) {
        //如果没有指定HeaderView，使用默认的
        if (null == mPtrFrameLayout.getHeaderView())
            setHeaderView(new PtrClassicDefaultHeader(getActivity()));
    }

    public void setHeaderView(View header) {
        if (null == mPtrFrameLayout || null == header) return;

        mPtrFrameLayout.setHeaderView(header);
        if (header instanceof PtrUIHandler)
            mPtrFrameLayout.addPtrUIHandler((PtrUIHandler) header);
    }

    @Override
    protected void onVisible(boolean isFirstVisibleToUser) {

        if (isFirstVisibleToUser)
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
