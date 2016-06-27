package com.hanbing.dianping.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.listener.OnLoadListener;
import com.common.tool.PagingManager;
import com.common.util.ViewUtils;
import com.hanbing.dianping.R;

/**
 * Created by hanbing on 2016/6/14.
 */
public class LoadMoreView extends LinearLayout implements OnLoadListener {

    ProgressBar mProgressBar;
    TextView mTitle;

    PagingManager mPagingManager;

    public LoadMoreView(Context context) {
        super(context);
        init();
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){

        inflate(getContext(), R.layout.layout_load_more, this);

        mProgressBar = ViewUtils.findViewById(this, R.id.pb_load_more_progress);
        mTitle = ViewUtils.findViewById(this, R.id.tv_load_more_title);
        mTitle.setText(R.string.loading);
    }


    @Override
    public void onLoadStart() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTitle.setText(R.string.loading);
    }

    @Override
    public void onLoadSuccess() {

    }

    @Override
    public void onLoadSuccessNoData() {
        mProgressBar.setVisibility(View.GONE);
        mTitle.setText(R.string.no_more_data);
    }

    @Override
    public void onLoadFailure(String msg) {

    }

    @Override
    public void onLoadCompleted() {

    }

    public void setPagingManager(PagingManager pagingManager) {
        this.mPagingManager = pagingManager;
    }
}
