package com.hanbing.dianping.core;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hanbing.dianping.Data;
import com.hanbing.dianping.R;
import com.hanbing.dianping.common.CityActivity;
import com.hanbing.dianping.model.MayFavorite;
import com.hanbing.dianping.model.ShortCut;
import com.hanbing.dianping.model.Status;
import com.hanbing.dianping.view.HomeShortCutLayout;
import com.hanbing.dianping.view.PullToRefreshLayout;
import com.hanbing.library.android.bind.annotation.BindContentView;
import com.hanbing.library.android.image.ImageLoader;
import com.hanbing.library.android.tool.ScrollableViewTransitionController;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
@BindContentView(R.layout.fragment_home)
public class HomeFragment extends com.hanbing.dianping.common.base.BaseListFragment {


    HomeShortCutLayout mShortCutLayout;


    @BindView(R.id.v_home_titlebar)
    View mTitlebar;

    View mMoveView;

    @BindView(R.id.pullToRefreshLayout)
    PullToRefreshLayout mPullToRefreshLayout;

    @BindView(R.id.lv_home_list)
    ListView mListView;


    BaseAdapter mAdapter;

    List<MayFavorite> mMayFavorites = null;

    View mOptionView;
    PopupWindow mPopupWindow;


    interface Option {
        int COMMENT = 0;
        int STORE = 1;
         int SCAN = 2;
       int PAY = 3;
    }




    @OnClick(value = {R.id.iv_home_titlebar_add})
    private void onOptionClick(View view) {
        showOptions(view);
    }


    @OnClick(value = R.id.layout_home_titlebar_city)
    private void onCityClick(View view) {
        startActivity(new Intent(getContext(), CityActivity.class));
    }

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public ListView createDataView() {
        return mListView;
    }

    @Override
    public BaseAdapter createAdapter() {
        return mAdapter;
    }

    public  class MyViewHolder extends com.hanbing.library.android.adapter.BaseAdapter.ViewHolder {

        @BindView(R.id.iv_home_may_favorite_picture)
        ImageView picture;

        @BindView(R.id.iv_home_may_favorite_status)
        ImageView status;

        @BindView(R.id.tv_home_may_favorite_title)
        TextView title;

        @BindView(R.id.tv_home_may_favorite_describe)
        TextView describe;

        @BindView(R.id.layout_home_may_favorite_price)
        LinearLayout layout;

        @BindView(R.id.tv_home_may_favorite_extra)
        TextView extra;

        public MyViewHolder(View itemView) {
            super(itemView);

            x.view().inject(this, itemView);
        }
    }
    class MyAdapter extends com.hanbing.library.android.adapter.BaseAdapter<MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

            View itemView = ViewUtils.inflate(getContext(), R.layout.item_home_may_favorite);
            MyViewHolder viewHolder = new MyViewHolder(itemView);


            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int position) {

            MayFavorite favorite = mMayFavorites.get(position);

            ImageLoader imageLoader = ImageLoader.getInstance(getContext());
            imageLoader.displayImage(viewHolder.picture, favorite.getPictureUrl(), R.mipmap.ic_launcher);

            if (favorite.getStatus() > 0) {

                viewHolder.status.setVisibility(View.VISIBLE);
                imageLoader.displayImage(viewHolder.status, imageLoader.createResource("" + Status.getDrawableResId(favorite.getStatus())));
            } else {
                viewHolder.status.setVisibility(View.GONE);
            }

            viewHolder.title.setText(favorite.getTitle());
            viewHolder.describe.setText(favorite.getDescribe());

            String linkUrl = favorite.getLinkUrl();

            viewHolder.layout.removeAllViews();
            if (TextUtils.isEmpty(linkUrl)) {


                TextView price = new TextView(getContext());
                price.setText(String.format("￥%.0f", favorite.getPrice()));
                price.setTextColor(Color.BLACK);
                price.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.textsize_large));

                viewHolder.layout.addView(price);

                if (favorite.getOriginalPrice() > 0) {
                    TextView originalPrice = new TextView(getContext());
                    originalPrice.setText(String.format("￥%.0f", favorite.getOriginalPrice()));
                    originalPrice.setTextColor(Color.BLACK);
                    originalPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.textsize));
                    originalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                    viewHolder.layout.addView(originalPrice);
                }


                viewHolder.extra.setText("已售" + favorite.getSaleCount() );
            } else {
                viewHolder.extra.setText(favorite.getViewCount() + "人浏览");
            }

            List<String> tags = favorite.getTags();

            if (null != tags && tags.size() > 0) {

                for (int i = 0; i < tags.size(); i++)
                {
                    TextView tag = new TextView(getContext());
                    tag.setBackgroundResource(R.drawable.mark_text_bg_light_red);
                    tag.setTextSize(TypedValue.COMPLEX_UNIT_PX,  getResources().getDimensionPixelSize(R.dimen.textsize));
                    tag.setText(tags.get(i));
                    viewHolder.layout.addView(tag);
                }
            }
        }

        @Override
        public int getItemCount() {
            return null == mMayFavorites ? 0 : mMayFavorites.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }



    @Override
    public void initListView(ListView listView) {
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void initHeadersAndFooters(ListView listView) {

        View view = ViewUtils.inflate(getActivity(), R.layout.layout_home_header);

        mShortCutLayout = ViewUtils.findViewById(view, R.id.layout_home_shortcut);
        mMoveView = ViewUtils.findViewById(view, R.id.layout_home_content);

        listView.addHeaderView(view);

    }


    class TestFavoriteData implements Serializable {
        public List<MayFavorite> data;
    }
    class TestData implements Serializable {
        public List<ShortCut> data;
    }

    @Override
    public PtrFrameLayout createPtrFrameLayout() {
        return mPullToRefreshLayout;
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);

//        imageView.setImageResource(R.drawable.dropdown_anim_00);

        //for test
        Gson gson = new Gson();

        TestData testData = gson.fromJson(Data.SHORTCUT_JSON, TestData.class);

        mShortCutLayout.setData(testData.data);

        ScrollableViewTransitionController.setControlViews(mListView, mTitlebar, mMoveView, null);
    }

    //for test
    private List<MayFavorite> createMayFavoriteData(int count) {

        TestFavoriteData data = new Gson().fromJson(Data.FAVORITE_JSON, TestFavoriteData.class);

        List<MayFavorite> mayFavorites = data.data;

        List<MayFavorite> list = new ArrayList<>();

        if (null != mayFavorites && mayFavorites.size() > 0) {

            for (int i = 0; i < count; i++) {

                MayFavorite favorite = mayFavorites.get(i % mayFavorites.size());


                favorite.setPrice((float) (20.0f + (Math.random() * 1000)));
                favorite.setOriginalPrice((System.currentTimeMillis() % 2 == 0) ? (float) (favorite.getPrice() + Math.random() * 200) : 0);
                favorite.setViewCount((int) (Math.random() * 1000 + Math.random() * 100));
                favorite.setSaleCount((int) (Math.random() * 1000 + 200));


                list.add(favorite);
            }
        }

        return list;
    }

    @Override
    public void onLoadData(boolean isRefresh, int pageIndex, final int pageSize) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null == mMayFavorites) {
                    mMayFavorites = new ArrayList<>();
                }

                if (mMayFavorites.size() >= 20) {
                    onLoadSuccessNoData();
                    return;
                }

                mMayFavorites.addAll(createMayFavoriteData(pageSize));
                mAdapter.notifyDataSetChanged();
                onLoadSuccess();
            }
        }, 3000);

    }


    class OptionViewHolder extends com.hanbing.library.android.adapter.BaseAdapter.ViewHolder {

        @BindView(R.id.iv_home_option_icon)
        ImageView icon;

        @BindView(R.id.tv_home_option_title)
        TextView title;
        public OptionViewHolder(View itemView) {
            super(itemView);

            x.view().inject(this, itemView);
        }
    }

    public void showOptions(View anchor) {

        PopupWindow popupWindow = new PopupWindow(getContext());

        if (null == mOptionView) {

            View optionView = ViewUtils.inflate(getContext(), R.layout.layout_home_options);

            ListView listView = ViewUtils.findViewById(optionView, R.id.lv_home_options);

            final int[][] OPTION_ITEM_ARRAY = {
                    {Option.COMMENT, R.drawable.main_home_navibar_tips_icon_comment, R.string.home_option_comment},
                    {Option.STORE, R.drawable.main_home_navibar_tips_icon_store, R.string.home_option_store},
                    {Option.SCAN, R.drawable.main_home_navibar_tips_icon_scan, R.string.home_option_scan},
                    {Option.PAY, R.drawable.main_home_add_icon_pay, R.string.home_option_pay}};

            listView.setAdapter(new com.hanbing.library.android.adapter.BaseAdapter<OptionViewHolder>() {
                @Override
                public int getItemCount() {
                    return OPTION_ITEM_ARRAY.length;
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
                public OptionViewHolder onCreateViewHolder(ViewGroup parent, int position) {

                    return new OptionViewHolder(ViewUtils.inflate(getContext(), R.layout.item_home_option));
                }

                @Override
                public void onBindViewHolder(OptionViewHolder viewHolder, int position) {

                    int[] array = OPTION_ITEM_ARRAY[position];

                    viewHolder.icon.setImageResource(array[1]);
                    viewHolder.title.setText(array[2]);
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    doOption(OPTION_ITEM_ARRAY[position][0]);

                    if (null != mPopupWindow
                            && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }

                }
            });

            mOptionView = optionView;
        }



        popupWindow.setContentView(mOptionView);
//        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(500);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        popupWindow.showAsDropDown(anchor);

        mPopupWindow = popupWindow;

    }

    void doOption(int option) {
        LogUtils.e("option = " + option);
    }

}
