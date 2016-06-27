package com.hanbing.mytest.activity.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.fragment.list.SrainPtrRecyclerViewFragment;
import com.common.util.LogUtils;
import com.hanbing.mytest.R;

import in.srain.cube.views.ptr.PtrFrameLayout;

public class TestRecyclerViewFragment extends AppCompatActivity {


    public static class RecyclerViewFragment extends SrainPtrRecyclerViewFragment {

        PtrFrameLayout mPtrFrameLayout;

        RecyclerView.Adapter mAdapter;
        int count = 0;

        Handler mHandler = new Handler();


        @Override
        protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View child = super.onCreateViewImpl(inflater, container, savedInstanceState);
            mPtrFrameLayout = new PtrFrameLayout(getActivity());
            child.setBackgroundColor(Color.GRAY);
            mPtrFrameLayout.addView(child, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return mPtrFrameLayout;
        }

        @Override
        public PtrFrameLayout createPtrFrameLayout() {
            return mPtrFrameLayout;
        }

        @Override
        public RecyclerView.Adapter createAdapter() {
            return mAdapter;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView textView;
            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
                textView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            }
        }
        class MyListViewAdapter extends RecyclerView.Adapter<ViewHolder> {


            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                ViewHolder viewHolder = new ViewHolder(new TextView(getContext()));
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                TextView textView = holder.textView;

                if (null == textView) {
                    textView = new TextView(getActivity());
                }


                textView.setHeight(200);
                textView.setText("Item position " + position);
                textView.setPadding(20, 20, 20, 20);

            }

            @Override
            public int getItemCount() {
                return count;
            }
        }


        @Override
        protected void initViews(View view) {
            super.initViews(view);

            setLoadMoreEnabled(true);
            setLoadMoreAlwaysShow(false);
        }

        @Override
        public View createLoadMoreView() {
            TextView textView = new TextView(getActivity());

            textView.setText("Load more");
            return textView;
        }

//        @Override
//        public void initListView(ListView listView) {
//            if (null == mAdapter)
//                mAdapter =  createAdapter();
//            listView.setAdapter(mAdapter);
//        }


        @Override
        public void initDataView(RecyclerView view) {
            super.initDataView(view);
            if (null == mAdapter)
                mAdapter = new MyListViewAdapter();
            view.setAdapter(mAdapter);
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
        setContentView(R.layout.activity_test_recycler_view_fragment);
    }
}
