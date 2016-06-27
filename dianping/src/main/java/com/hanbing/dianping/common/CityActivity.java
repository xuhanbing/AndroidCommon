package com.hanbing.dianping.common;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.common.activity.BaseActivity;
import com.common.adapter.BaseAdapter;
import com.common.util.StringUtils;
import com.common.util.ViewUtils;
import com.common.widget.FastLocateLayout;
import com.hanbing.dianping.R;
import com.hanbing.dianping.common.city.City;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_city)
public class CityActivity extends BaseActivity {


    @ViewInject(R.id.lv_city_list)
    ListView mListView;


    @Event(R.id.iv_back)
    private void onBackClick(View view) {
        finish();
    }

    List<City> mCities;

    List<String> mTags;
    Map<String, Integer> mMap;
    @Override
    protected void initViews() {
        super.initViews();

        String json = StringUtils.readStringFromRaw(this, R.raw.cities2);

        if (!TextUtils.isEmpty(json)) {
            mCities = City.getCitiesFromJson(json);

            if (null != mCities) {

                Collections.sort(mCities, new Comparator<City>() {
                    @Override
                    public int compare(City lhs, City rhs) {
                        return lhs.getPinyin().compareTo(rhs.getPinyin());
                    }
                });


                mMap = new HashMap<>();
                mTags = new ArrayList<>();

                for (int i = 0; i < mCities.size(); i++) {

                    City city = mCities.get(i);

                    String index = city.getIndex();
                    if (!mTags.contains(index)) {
                        mTags.add(index);
                        mMap.put(index, i);
                    }

                }
            }

        }

        mListView.setAdapter(new CityAdapter());

    }


    class ViewHolder extends BaseAdapter.ViewHolder {

        @ViewInject(R.id.layout_city_index)
        View indexLayout;

        @ViewInject(R.id.tv_city_index)
        TextView index;

        @ViewInject(R.id.tv_city_name)
        TextView name;

        @ViewInject(R.id.v_city_short_line)
        View shortLine;

        @ViewInject(R.id.v_city_full_line)
        View fullLine;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class CityAdapter extends BaseAdapter<ViewHolder> implements FastLocateLayout.Adapter {

        @Override
        public List<String> getTags() {
            return mTags;
        }

        @Override
        public int positionOfTag(String tag) {
            return  mMap.get(tag);
        }

        @Override
        public int getCount() {
            return null == mCities ? 0 : mCities.size();
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
        public CityActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new CityActivity.ViewHolder(ViewUtils.inflate(getApplicationContext(), R.layout.item_city));
        }

        @Override
        public void onBindViewHolder(CityActivity.ViewHolder viewHolder, int position) {

            City city = mCities.get(position);

            viewHolder.name.setText(city.getName());

            if (isFirstItemInArray(position)) {
                viewHolder.indexLayout.setVisibility(View.VISIBLE);
                viewHolder.index.setText(city.getIndex());
            } else {
                viewHolder.indexLayout.setVisibility(View.GONE);
            }

            if (isLastItemInArray(position)) {
                viewHolder.shortLine.setVisibility(View.GONE);
                viewHolder.fullLine.setVisibility(View.VISIBLE);
            } else {
                viewHolder.shortLine.setVisibility(View.VISIBLE);
                viewHolder.fullLine.setVisibility(View.GONE);
            }

        }

        boolean isFirstItemInArray(int position) {

            if (position == 0)
                return true;

            City city = mCities.get(position);
            City prev = mCities.get(position - 1);

            return !city.getIndex().equals(prev.getIndex());
        }

        boolean isLastItemInArray(int position) {
            if (getCount() - 1 == position)
                return true;

            City city = mCities.get(position);
            City next = mCities.get(position + 1);

            return !city.getIndex().equals(next.getIndex());
        }
    }
}
