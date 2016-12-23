package com.hanbing.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanbing.library.android.fragment.list.RecyclerViewFragment;
import com.hanbing.library.android.util.ToastUtils;
import com.hanbing.library.android.view.recycler.HeaderRecyclerView;
import com.hanbing.library.android.view.recycler.decoration.LineItemDecoration;

/**
 * Created by hanbing on 2016/10/26
 */

public class NumRecyclerViewFragment extends RecyclerViewFragment {


    int count = 0;


    RecyclerView mRecyclerView;

    View mEmptyView;
    View mLoadingView;

    Handler mHandler = new Handler();

    @Override
    protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        RelativeLayout relativeLayout = new RelativeLayout(getContext());

        HeaderRecyclerView recyclerView = new HeaderRecyclerView(getContext());

        for (int i = 0; i < 3; i++)
        {
            TextView header = new TextView(getContext());
            header.setText("header " + i);

            TextView footer = new TextView(getContext());
            footer.setText("footer " + i);

            recyclerView.addHeaderView(header);
            recyclerView.addFooterView(footer);
        }



        TextView textView = new TextView(getContext());

        textView.setBackgroundColor(Color.LTGRAY);
        textView.setText("当前没有数据");
        textView.setGravity(Gravity.CENTER);

        mEmptyView = textView;
        mRecyclerView = recyclerView;

        relativeLayout.addView(recyclerView);
        relativeLayout.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return relativeLayout;
    }

    @Override
    protected void addItemDecoration(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new LineItemDecoration.Builder(getContext()).setSize(10).setColor(Color.RED).create());
    }

    @Override
    public RecyclerView createRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new Adapter();
    }

    @Override
    public View createEmptyView() {
        return mEmptyView;
    }

    @Override
    public void onLoadData(boolean isRefresh, int pageIndex, final int pageSize) {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                count += pageSize;
                getDataAdapter().notifyDataSetChanged();
                onLoadSuccess();
            }
        }, 500);
    }

    @Override
    public boolean onItemLongClick(RecyclerView recyclerView, View view, int position) {
        ToastUtils.showToast(getContext(), "Recycler long click " + position);
        return true;
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        ToastUtils.showToast(getContext(), "Recycler click " + position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            TextView title = (TextView) holder.itemView.findViewById(android.R.id.text1);

            title.setText("item " + position);
        }

        @Override
        public int getItemCount() {
            return count;
        }
    }
}
