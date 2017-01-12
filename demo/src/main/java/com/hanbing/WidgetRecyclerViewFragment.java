package com.hanbing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hanbing.demo.R;
import com.hanbing.library.android.fragment.list.RecyclerViewFragment;
import com.hanbing.library.android.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanbing on 2017/1/10
 */

public class WidgetRecyclerViewFragment extends RecyclerViewFragment {


    @Override
    public RecyclerView.Adapter createAdapter() {
        return new Adapter();
    }


    @Override
    public boolean onItemLongClick(RecyclerView recyclerView, View view, int position) {
        LogUtils.e("onItemLongClick " + position);
        return true;
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        LogUtils.e("onItemClick " + position);
    }

    @Override
    protected void bindOnItemClickListener(RecyclerView recyclerView) {
        super.bindOnItemClickListener(recyclerView);
    }

    public static final int LAYOUT_ID = R.layout.recycler_item;

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkbox)
        CheckBox mCheckbox;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.btn)
        Button mBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(LAYOUT_ID, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {


            holder.mTitle.setText("item " + position);

            holder.mCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtils.e("check " + position);
                }
            });

            holder.mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtils.e("click " + position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }
}
