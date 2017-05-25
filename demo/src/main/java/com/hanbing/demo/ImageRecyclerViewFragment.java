package com.hanbing.demo;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hanbing.library.android.adapter.BaseRecyclerViewAdapter;
import com.hanbing.library.android.fragment.list.RecyclerViewFragment;
import com.hanbing.library.android.tool.ViewChecker;
import com.hanbing.library.android.view.recycler.decoration.GridItemDecoration;
import com.hanbing.library.android.view.recycler.decoration.LineItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanbing on 2017/5/25
 */

public class ImageRecyclerViewFragment extends RecyclerViewFragment<String> {


    int orientation = OrientationHelper.VERTICAL;

    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {

        orientation = OrientationHelper.VERTICAL;
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), orientation, false);
        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 2);
        layoutManager2.setOrientation(orientation);
        StaggeredGridLayoutManager layoutManager3 = new StaggeredGridLayoutManager(2, orientation);


        recyclerView.setLayoutManager(layoutManager3);

        int padding = 20;
        recyclerView.setPadding(padding, padding, padding, padding);
        super.initRecyclerView(recyclerView);
    }

    @Override
    protected void addItemDecoration(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new GridItemDecoration.Builder(getContext()).setSize(40).setColor(Color.GREEN).create());
        recyclerView.addItemDecoration(new GridItemDecoration.Builder(getContext()).setMargin(20, 20, 20, 20).setColor(Color.RED).create());

    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new Adapter();
    }



    @Override
    public void onLoadData(boolean isRefresh, int pageIndex, int pageSize) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            list.add(Constants.IMAGES[i % Constants.IMAGES.length] + "?"+System.currentTimeMillis() + "" + i);
        }


        onLoadSuccess(list);

        getPagingManager().setNoMore();
    }


    public static final int ITEM_LAYOUT_ID = R.layout.image_item;


    static class ViewHolder extends com.hanbing.library.android.adapter.ViewHolder{
        @BindView(R.id.image)
        ImageView mImage;
        @BindView(R.id.title)
        TextView mTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    class Adapter extends BaseRecyclerViewAdapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(ITEM_LAYOUT_ID, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            ViewGroup.LayoutParams layoutParams = holder.mImage.getLayoutParams();

            if (orientation == OrientationHelper.VERTICAL)
            layoutParams.height = (int) (200 + position * 100 * Math.random());
            else
                layoutParams.width =  (int) (200 + position * 100 * Math.random());

            holder.mImage.setLayoutParams(layoutParams);

            Glide.with(getContext()).load(mDataList.get(position)).asBitmap().into(holder.mImage);
            holder.mTitle.setText("Item " + position);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }
}
