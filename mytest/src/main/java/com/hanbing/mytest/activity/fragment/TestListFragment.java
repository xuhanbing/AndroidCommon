package com.hanbing.mytest.activity.fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.base.listfragment.SimpleListFragment;
import com.common.base.listfragment.SrainPtrListFragment;
import com.common.base.listfragment.SwipeRefreshListFragment;
import com.common.tool.PagingManager;
import com.common.util.LogUtils;
import com.common.util.ViewUtils;
import com.hanbing.mytest.R;

public class TestListFragment extends AppCompatActivity {


    public static class ListFragment extends SrainPtrListFragment {


        ListView mListView;
        BaseAdapter mAdapter;
        int count = 0;

        Handler mHandler = new Handler();




        @Override
        public PagingManager createPagingManager() {
            return new PagingManager(20);
        }

        @Override
        public BaseAdapter createListAdapter() {

            return mAdapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return count;
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


                    TextView textView = (TextView) convertView;

                    if (null == textView) {
                        textView = new TextView(getActivity());
                    }


                    textView.setText("Item position " + position);
                    textView.setPadding(20, 20, 20, 20);

                    return textView;
                }
            };




        }

        @Override
        protected void initViews(View view) {
            super.initViews(view);


            setLoadMoreEnabled(true);
            setLoadMoreAlwaysShow(true);
        }

        @Override
        public View createLoadMoreView() {
            TextView textView = new TextView(getActivity());

            textView.setText("Load more");
            return textView;
        }

        @Override
        public void initListView(ListView listView) {
            getListView().setAdapter(getListAdapter());
        }

        @Override
        public void onLoadData(final boolean isRefresh, int pageIndex, final int pageSize) {


            LogUtils.e("onLoadData " + isRefresh);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isRefresh) {
                        count = pageSize;
                    } else {
                        count += pageSize;
                    }
                    mAdapter.notifyDataSetChanged();
                    onLoadCompleted();
                }
            }, 2000);

        }

        @Override
        public void onLoadCompleted() {
            LogUtils.e("onLoadCompleted ");
            super.onLoadCompleted();


        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list_fragment);
    }


}
