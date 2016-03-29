package com.common.base.listfragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidcommon.R;
import com.common.base.BaseListFragment;
import com.common.listener.OnLoadListener;
import com.common.util.LogUtils;
import com.common.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing on 2016/3/29.
 */
public class SimpleListFragment extends BaseListFragment {

    ListView mListView;
    List<String> mList = new ArrayList<>();
    BaseAdapter adapter;
    Handler mHandler = new Handler();

    View mLoadingView = null;

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout layout = new RelativeLayout(getActivity());
        mListView = new ListView(getActivity());
        mLoadingView = ViewUtils.inflate(getActivity(), R.layout.layout_loading);

        layout.addView(mListView);
        layout.addView(mLoadingView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setSupportCustomPageSize(true);
        return layout;
    }

    @Override
    public ListView createListView() {
        return mListView;
    }

    @Override
    public BaseAdapter createListAdapter() {
        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                textView.setTextSize(25);

                textView.setText(mList.get(position));
                textView.setBackgroundResource(position % 2 == 0 ? R.color.brown : R.color.lightblue);

                return textView;
            }
        };
        return adapter;
    }

    @Override
    public View createEmptyView() {
        return null;
    }

    @Override
    public View createLoadingView() {
        return mLoadingView;
    }

    @Override
    public View createLoadMoreView() {

        MyView view = new MyView(getActivity());
        view.setText("click");
        return view;
    }

    @Override
    public void initListView(ListView listView) {
        listView.setAdapter(adapter);
    }

    @Override
    public void initHeadersAndFooters(ListView listView) {
        TextView header = new TextView(getActivity());
        TextView footer = new TextView(getActivity());

        header.setText("header");
        footer.setText("footer");

        listView.addHeaderView(header);
        listView.addFooterView(footer);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoadData(final boolean isRefresh, int pageIndex, final int pageSize) {
        LogUtils.e("onLoadData isRefresh=" + isRefresh + ", pageIndex=" + pageIndex + ", pageSize=" + pageSize);
        onLoadStart();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    mList.clear();

                    for (int i = 0; i < pageSize; i++) {
                        mList.add("Refresh " + i);
                    }

                    adapter.notifyDataSetChanged();

                } else {

                    for (int i = 0; i < pageSize; i++) {
                        mList.add("load more " + i);
                    }
                    adapter.notifyDataSetChanged();
                }

                onLoadCompleted();
            }
        },3000);

    }

    class MyView extends TextView implements OnLoadListener{

        public MyView(Context context) {
            super(context);
        }

        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }


        @Override
        public void onLoadStart() {
            setText("onLoadStart");
        }

        @Override
        public void onLoadSuccess() {
            setText("onLoadSuccess");
        }

        @Override
        public void onLoadSuccessNoData() {
            setText("onLoadSuccessNoData");
        }

        @Override
        public void onLoadFailure(String msg) {
            setText("onLoadFailure：" + msg);
        }

        @Override
        public void onLoadCompleted() {
            setText("点击加载更多" );
        }
    }
}
