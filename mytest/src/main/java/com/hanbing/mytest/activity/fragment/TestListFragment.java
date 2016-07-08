package com.hanbing.mytest.activity.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.mytest.R;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

public class TestListFragment extends AppCompatActivity {


    public static class ListFragment extends com.hanbing.library.android.fragment.list.ListFragment {


        PtrFrameLayout mPtrFrameLayout;
        ListView mListView;

        GridView mGridView;
        BaseAdapter mAdapter;
        int count = 0;

        Handler mHandler = new Handler();

        public ListFragment() {

        }


//        @Override
//        protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View child = super.onCreateViewImpl(inflater, container, savedInstanceState);
//
//            mPtrFrameLayout = new PtrFrameLayout(getActivity());
//            View header = createHeader();
//            setHeaderView(header);
//
//            mPtrFrameLayout.addView(child);
//
//            return mPtrFrameLayout;
//        }
//
//        @Override
//        public PtrFrameLayout createPtrFrameLayout() {
//            return mPtrFrameLayout;
//        }


        @Override
        public BaseAdapter createAdapter() {
            return mAdapter;
        }

    class MyListViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return count;
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


            TextView textView = (TextView) convertView;

            if (null == textView) {
                textView = new TextView(getActivity());
            }

            textView.setHeight(200);

            textView.setText("Item position " + position);
            textView.setPadding(20, 20, 20, 20);

            return textView;
        }
    }
        protected View createHeader() {
            int type = 2;


            if (0 == type) {
                StoreHouseHeader header = new StoreHouseHeader(getActivity());
                header.setBackgroundColor(Color.WHITE);
                header.setTextColor(Color.BLACK);
                ArrayList<float[]> points = new ArrayList<>();
                points.add(new float[]{8, 0, 0, 8});
                points.add(new float[]{8, 4, 0, 12});
                points.add(new float[]{6, 6, 6, 20});
                points.add(new float[]{16, 0, 12, 12});
                points.add(new float[]{16, 0, 24, 12});
                points.add(new float[]{14, 8, 18, 8});
                points.add(new float[]{12, 12, 20, 12});
                points.add(new float[]{16, 8, 16, 24});
                points.add(new float[]{14, 16, 12, 22});
                points.add(new float[]{18, 16, 22, 22});

                header.initWithPointList(points);

                return header;
            } else if (1 == type) {
                MaterialHeader materialHeader = new MaterialHeader(getActivity());

                materialHeader.setPtrFrameLayout(mPtrFrameLayout);
                materialHeader.setColorSchemeColors(new int[]{Color.RED, Color.GRAY, Color.BLUE, Color.GRAY});

                return materialHeader;
            } else {
                PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(getActivity());

                return defaultHeader;

            }

        }


        @Override
        protected void initViews(View view) {
            super.initViews(view);

            int[] colors = {Color.RED, Color.GREEN, Color.BLUE};

            view.setBackgroundColor(colors[((int) (System.currentTimeMillis() % 3))]);

            setLoadMoreEnabled(true);
            setScrollLoadMoreEnabled(true);
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
        public void initHeadersAndFooters(ListView dataView) {
            for (int i = 0; i < 3; i++) {
                TextView header = new TextView(getContext());
//                header.setText("header " + i);
                header.setText(getArguments().getString("title"));
                dataView.addHeaderView(header);

                TextView footer = new TextView(getContext());
                footer.setText("footer " + i);
                dataView.addFooterView(footer);
            }
        }


        @Override
        public void initDataView(ListView view) {
            super.initDataView(view);
            mAdapter = new MyListViewAdapter();
            view.setAdapter(mAdapter);

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtils.e("onItemClick " + position);
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtils.e("onItemLongClick " + position);
            return true;
        }

        @Override
        public void onLoadData(final boolean isRefresh, int pageIndex, final int pageSize) {


            LogUtils.e(this + " onLoadData " + isRefresh);
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
            LogUtils.e(this + " onLoadCompleted ");
            super.onLoadCompleted();


        }
    }

    ViewPager viewPager;

    Fragment current ;
    final List<Fragment> fragments = new ArrayList<>();

    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list_fragment);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        if (null == savedInstanceState) {
            for (int i = 0; i < 3; i++) {
                Fragment fragment = new ListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", "title " + i);
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }

            fragmentManager.beginTransaction()
                    .add(R.id.layout_content, fragments.get(0), "0")
                    .add(R.id.layout_content, fragments.get(1), "1")
                    .add(R.id.layout_content, fragments.get(2), "2")
                    .hide(fragments.get(0))
                    .hide(fragments.get(1))
                    .hide(fragments.get(2))
                    .commit();
        } else {

            fragments.add(fragmentManager.findFragmentByTag("0"));
            fragments.add(fragmentManager.findFragmentByTag("1"));
            fragments.add(fragmentManager.findFragmentByTag("2"));
        }

        show(position);

        RadioGroup radioGroup = ViewUtils.findViewById(this, R.id.radio);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
//                        fragmentManager.beginTransaction().replace(R.id.layout_content, fragments.get(0)).commit();
                        show(0);
                        break;
                    case R.id.rb_2:
//                        fragmentManager.beginTransaction().replace(R.id.layout_content, fragments.get(1)).commit();
                        show(1);
                        break;
                    case R.id.rb_3:
//                        fragmentManager.beginTransaction().replace(R.id.layout_content, fragments.get(2)).commit();
                        show(2);
                        break;
                }
            }
        });

//        ListFragment fragment = new ListFragment();
//
//        getSupportFragmentManager().beginTransaction().add(R.id.layout_content, fragment).commit();


//        viewPager = ViewUtils.findViewById(this, R.id.viewPager);
//
//

//
//        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                return fragments.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return fragments.size();
//            }
//        });
    }

    private void show(int position) {


        Fragment fragment = fragments.get(position);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(fragment);


        if (null != current) {
            fragmentTransaction.hide(current);
        }

        fragmentTransaction.commit();

        current = fragment;

        this.position = position;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.e("onSaveInstanceState");

        outState.putInt("position", position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        LogUtils.e("onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);

        position = savedInstanceState.getInt("position");
    }
}
