package com.hanbing.mytest.activity.fragment;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class TestListFragment extends AppCompatActivity {


    public static class ListFragment extends com.hanbing.library.android.fragment.list.ListFragment {


        PtrFrameLayout mPtrFrameLayout;
        ListView mListView;

        GridView mGridView;
        BaseAdapter mAdapter;
        int count = 0;

        View mEmptyView;

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
        protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = ViewUtils.inflate(getContext(), R.layout.activity_test_list_view3, container, false);

            mListView = ViewUtils.findViewById(view, R.id.listView);
            mEmptyView = ViewUtils.findViewById(view, R.id.emptyView);

            return view;
        }

        @Override
        public ListView createListView() {
            return mListView;
        }

        @Override
        public View createEmptyView() {
            return mEmptyView;
        }

//        @Override
//        public BaseAdapter createAdapter() {
//            return null== mAdapter ?  mAdapter = new MyListViewAdapter() : mAdapter;
//        }

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

        @Override
        public boolean isEmpty() {
            return super.isEmpty();
        }
    }



        @Override
        protected void initViews(View view) {
            super.initViews(view);

//            int[] colors = {Color.RED, Color.GREEN, Color.BLUE};
//
//            view.setBackgroundColor(colors[((int) (System.currentTimeMillis() % 3))]);

            setLoadMoreEnabled(true);
            setScrollLoadMoreEnabled(true);
            setLoadMoreAlwaysShow(false);
            showEmptyView();
        }

        @Override
        public void initListView(ListView listView) {
            super.initListView(listView);
        }


        @Override
        public void initHeadersAndFooters(ListView dataView) {
//            for (int i = 0; i < 3; i++) {
//                TextView header = new TextView(getContext());
////                header.setText("header " + i);
//                header.setText(getArguments().getString("title"));
//                dataView.addHeaderView(header);
//
//                TextView footer = new TextView(getContext());
//                footer.setText("footer " + i);
//                dataView.addFooterView(footer);
//            }
        }


        @Override
        public void onLoadData(final boolean isRefresh, int pageIndex, final int pageSize) {


//            LogUtils.e(this + " onLoadData " + isRefresh);
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (isRefresh) {
//                        count = pageSize;
//                    } else {
//                        count += pageSize;
//                    }
//                    mAdapter.notifyDataSetChanged();
//                    onLoadCompleted();
//                }
//            }, 2000);

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
