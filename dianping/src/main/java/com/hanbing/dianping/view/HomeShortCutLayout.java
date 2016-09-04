package com.hanbing.dianping.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanbing.library.android.image.ImageLoader;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.tab.TabWidget;
import com.hanbing.dianping.R;
import com.hanbing.dianping.model.ShortCut;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing on 2016/6/12.
 */
public class HomeShortCutLayout extends LinearLayout {




    ViewPager mViewPager;
    PagerAdapter mPagerAdapter;
    TabWidget mTabWidget;

    List<ShortCut> mData;

    static  final int ROW_COUNT = 2;
    static final int COLUMN_COUNT = 5;

    List<View> mItemViews;



    public HomeShortCutLayout(Context context) {
        super(context);
        init();
    }

    public HomeShortCutLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeShortCutLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    void init() {

        initItemViews();

        inflate(getContext(), R.layout.layout_home_viewpager, this);

        mViewPager = ViewUtils.findViewById(this, R.id.viewPager);
        mTabWidget = ViewUtils.findViewById(this, R.id.tabWidget);

        mPagerAdapter = new PagerAdapter() {
            @Override
            public int getItemCount() {
                return null == mItemViews ? 0 : mItemViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                View view = mItemViews.get(position);

                if (container.indexOfChild(view) < 0) {
                    container.addView(view);
                }

                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View)object);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);

        initIndicators();


    }

    void initItemViews(){
        if (null != mItemViews)  {
            mItemViews.clear();
        } else {
            mItemViews = new ArrayList<>();
        }

        if (null == mData)
            return;

        int size = mData.size();


        int count = COLUMN_COUNT * ROW_COUNT;


        final Context context = getContext();
        for (int i = 0 ; i < size;) {

            final int start = i;
            final int gridCount = Math.min(count, size - i);


            GridView gridView = new GridView(context);
//            gridView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
//            gridView.setBackgroundColor(Color.RED);
            gridView.setNumColumns(COLUMN_COUNT);
            gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView.setAdapter(new BaseAdapter() {
                @Override
                public int getItemCount() {
                    return gridCount;
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

                    ViewHolder holder = null;

                    if (null == convertView) {
                        View view = ViewUtils.inflate(parent.getContext(), R.layout.item_home_shortcut, parent, false);

                        ImageView icon = ViewUtils.findViewById(view, R.id.iv_home_shortcut_icon);
                        TextView title = ViewUtils.findViewById(view, R.id.tv_home_shortcut_title);

                        holder = new ViewHolder();
                        holder.icon = icon;
                        holder.title = title;

                        convertView = view;

                        convertView.setTag(holder);
                    } else {

                        holder = (ViewHolder) convertView.getTag();
                    }

                    final ShortCut shortCut = mData.get(position + start);

                    ImageLoader.getInstance(getContext()).displayImage(holder.icon, shortCut.getIconUrl());
                    holder.title.setText(shortCut.getName());

                    convertView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String linkUrl = shortCut.getLinkUrl();

                            if (TextUtils.isEmpty(linkUrl)) {
                                //open search
                            } else {
                                //open webview
                            }
                        }
                    });



                    return convertView;
                }

                class ViewHolder {
                    ImageView icon;
                    TextView title;
                }
            });


            mItemViews.add(gridView);
            i += count;
        }


    }

    private void initIndicators() {


        mTabWidget.removeAllTabs();
        if (null == mItemViews || mItemViews.size() == 0)
            return;

        mTabWidget.setTabViewFactory(new TabWidget.TabViewFactory() {
            @Override
            public View createView(TabWidget.TabSpec tabSpec) {
                View view = ViewUtils.inflate(getContext(), R.layout.item_home_shortcut_indicator, mTabWidget, false);

                return view;
            }
        });

        mTabWidget.setViewPager(mViewPager);
        mTabWidget.setSelectedItem(0);


//        for (int i = 0; i < mItemViews.size(); i++) {
//
//            View view = ViewUtils.inflate(getContext(), R.layout.item_home_shortcut_indicator);
//            mTabWidget.addTab(view);
//        }
//
//        mTabWidget.setSelectedItem(0);

    }


    private void refresh(){
        initItemViews();
        initIndicators();

        mPagerAdapter.notifyDataSetChanged();

    }

    public void setData(List<ShortCut> data) {
        this.mData = data;

        refresh();
    }

}
