package com.hanbing.demo;

import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hanbing.library.android.fragment.list.ListFragment;
import com.hanbing.library.android.tool.ViewChecker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanbing on 2017/5/25
 */

public class ImageListViewFragment extends ListFragment<String> {


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        System.out.println("arrive start " + ViewChecker.arriveStart(view, true));
        System.out.println("arrive end " + ViewChecker.arriveEnd(view, true));
    }

    @Override
    public BaseAdapter createAdapter() {
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


    class Adapter extends com.hanbing.library.android.adapter.BaseAdapter<ImageRecyclerViewFragment.ViewHolder> {

        @Override
        public ImageRecyclerViewFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageRecyclerViewFragment.ViewHolder(LayoutInflater.from(getContext()).inflate(ITEM_LAYOUT_ID, parent, false));
        }

        @Override
        public void onBindViewHolder(ImageRecyclerViewFragment.ViewHolder holder, int position) {

            ViewGroup.LayoutParams layoutParams = holder.mImage.getLayoutParams();

            layoutParams.height = (int) (200 + position * 100 * Math.random());

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
