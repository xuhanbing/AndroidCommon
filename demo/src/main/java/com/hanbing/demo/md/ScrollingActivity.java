package com.hanbing.demo.md;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrollingActivity extends BaseActivity {


    ViewPager mViewPager;
    TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        {
            final String[] titles = {"文本", "列表"};
            final List<Fragment> fragments = new ArrayList<>();

            fragments.add(new MyRecyclerFragment());
            fragments.add(new MyRecyclerFragment());

            mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragments.get(position);
                }

                @Override
                public int getCount() {
                    return fragments.size();
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return titles[position];
                }
            });

            mTabLayout.setupWithViewPager(mViewPager);
        }

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio);

//        if (null != radioGroup)
//        {
//            final List<Fragment> fragments = new ArrayList<>();
//
//            fragments.add(new MyTextFragment());
//            fragments.add(new MyListFragment());
//
//
//
//            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    switch (checkedId) {
//                        case R.id.rb_text:
//                            showFragment(fragments, 0, R.id.layout_content);
//                            break;
//                        case R.id.rb_list:
//                            showFragment(fragments, 1, R.id.layout_content);
//                            break;
//                    }
//                }
//            });
//
//            showFragment(fragments, 0, R.id.layout_content);
//        }

    }

    private void showFragment(List<Fragment> list, int position, int layoutId) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(layoutId, list.get(position))
                .commit();
    }


    class MyTextFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            TextView textView = new TextView(getContext());
            textView.setText(R.string.large_text);

            textView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            return textView;
        }
    }

    class MyListFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ListView listView = new FullHeightListView(getContext());

            List<Map<String, String>> list = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Map<String, String> map = new HashMap<>();
                map.put("title", "title " + i);
                map.put("subTitle", "This is sub title " + i);

                list.add(map);
            }

            listView.setAdapter(new SimpleAdapter(getContext(), list, android.R.layout.simple_list_item_2, new String[]{"title", "subTitle"}, new int[]{android.R.id.text1, android.R.id.text2}));
            listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));
            return listView;
        }
    }

     public static class MyRecyclerFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            RecyclerView recyclerView = new RecyclerView(inflater.getContext());

            final List<Map<String, String>> list = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Map<String, String> map = new HashMap<>();
                map.put("title", "title " + i);
                map.put("subTitle", "This is sub title " + i);

                list.add(map);
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {

                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return new ViewHolder(LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false));
                }

                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                    Map<String, String> map = list.get(position);

                    holder.title.setText(map.get("title"));
                    holder.subTitle.setText(map.get("subTitle"));
                }

                @Override
                public int getItemCount() {
                    return list.size();
                }
            });
            recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));
            return recyclerView;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public TextView title;
            public TextView subTitle;
            public ViewHolder(View itemView) {
                super(itemView);

                title = (TextView) itemView.findViewById(android.R.id.text1);
                subTitle = (TextView) itemView.findViewById(android.R.id.text2);
            }
        }
    }

    class FullHeightListView extends ListViewCompat {

        public FullHeightListView(Context context) {
            super(context);
        }

        public FullHeightListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public FullHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

//        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//        public FullHeightListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//            super(context, attrs, defStyleAttr, defStyleRes);
//        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
        }
    }
}
