package com.hanbing.mytest.activity.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.plugin.PinnedSectionWrapper;
import com.hanbing.library.android.view.recycler.OnItemClickListener;
import com.hanbing.library.android.view.recycler.PinnedSectionRecyclerView;
import com.hanbing.mytest.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class TestPinnedSectionRecyclerView extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PinnedSectionRecyclerView recyclerView = new PinnedSectionRecyclerView(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter();


        View view = ViewUtils.inflate(this, R.layout.item_pinned_section);
        recyclerView.setPinnedView(view);
        recyclerView.setAdapter(adapter);
        ViewUtils.bindOnItemClickListener(recyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position) {
                LogUtils.e("onItemClick " + position);
            }
        });

        setContentView(recyclerView);
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> implements PinnedSectionWrapper.Adapter{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(ViewUtils.inflate(getApplicationContext(), R.layout.item_pinned_section,  parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


            String tag = getHeader(position);
            if (0 == position % 5 ) {
                holder.mHeader.setVisibility(View.VISIBLE);
                holder.mTitle.setVisibility(View.VISIBLE);
                holder.mHeader.setText(tag);
            } else {
                holder.mHeader.setVisibility(View.GONE);
                holder.mTitle.setVisibility(View.VISIBLE);
            }

            holder.mTitle.setText(tag +  " " + (position % 5));

            holder.itemView.setBackgroundColor(position % 2== 0 ? Color.WHITE : Color.LTGRAY);
        }

        public String getHeader(int position) {
            String tag = "" + (char)('a' + position / 5);
            return tag;
        }


        @Override
        public int getItemCount() {
            return 30;
        }

        @Override
        public boolean isPinnedSection(int position) {
            return position % 5 == 0;
        }

        @Override
        public int currentPinnedSectionPosition(int position) {
            return position / 5 * 5;
        }

        @Override
        public int nextPinnedSectionPosition(int position) {
            return currentPinnedSectionPosition(position) + 5;
        }

        @Override
        public void configurePinnedSection(View pinnedView, int position) {
            ViewHolder holder = new ViewHolder(pinnedView);

            holder.mHeader.setVisibility(View.VISIBLE);
            holder.mTitle.setVisibility(View.GONE);

            holder.mHeader.setText(getHeader(position));
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @ViewInject(R.id.tv_header)
        TextView mHeader;

        @ViewInject(R.id.tv_title)
        TextView mTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            x.view().inject(this, itemView);
        }
    }
}
