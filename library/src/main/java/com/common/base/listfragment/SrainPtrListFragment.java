package com.common.base.listfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.common.util.LogUtils;

import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by hanbing on 2016/3/29.
 */
public class SrainPtrListFragment extends SimpleListFragment {

    PtrFrameLayout mPtrFrameLayout;

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View child = super.onCreateViewImpl(inflater, container, savedInstanceState);

        mPtrFrameLayout = new PtrFrameLayout(getActivity());
        View header = createHeader();

        if(null != header)
        {
            mPtrFrameLayout.setHeaderView(header);
            if (header instanceof  PtrUIHandler)
            {
                mPtrFrameLayout.addPtrUIHandler((PtrUIHandler) header);
            }
        }

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

        mPtrFrameLayout.addView(child);

        return mPtrFrameLayout;

    }


    @Override
    public void initListView(ListView listView) {
    }

    protected View createHeader() {
        int type = 2;

        if (0 == type) {
            StoreHouseHeader header = new StoreHouseHeader(getActivity());
            header.setBackgroundColor(Color.WHITE);
            header.setTextColor(Color.BLACK);
            ArrayList<float[]> points = new ArrayList<>();
            points.add(new float[]{8, 0, 0, 8});
            points.add(new float[]{8, 4, 0, 12});
            points.add(new float[]{6, 6, 6, 20});
            points.add(new float[]{16, 0, 12, 12});
            points.add(new float[]{16, 0, 24, 12});
            points.add(new float[]{14, 8, 18, 8});
            points.add(new float[]{12, 12, 20, 12});
            points.add(new float[]{16, 8, 16, 24});
            points.add(new float[]{14, 16, 12, 22});
            points.add(new float[]{18, 16, 22, 22});

            header.initWithPointList(points);

            return header;
        } else if (1 == type) {
            MaterialHeader materialHeader = new MaterialHeader(getActivity());

            materialHeader.setPtrFrameLayout(mPtrFrameLayout);
            materialHeader.setColorSchemeColors(new int[]{Color.RED, Color.GRAY, Color.BLUE, Color.GRAY});

            return materialHeader;
        } else {
            PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getActivity());

            return defaultHeader;

        }

    }


    @Override
    protected void onViewVisible(boolean isCreated) {

        mPtrFrameLayout.post(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh();
            }
        });
    }

    @Override
    public void onLoadCompleted() {
        super.onLoadCompleted();
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        View first = view.getChildAt(firstVisibleItem);
        if (0 == firstVisibleItem && (null == first || 0 == first.getTop())) {
            mPtrFrameLayout.setEnabled(true);
        } else {
            mPtrFrameLayout.setEnabled(false);
        }

        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }
}
