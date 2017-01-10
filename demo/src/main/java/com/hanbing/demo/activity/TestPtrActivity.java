package com.hanbing.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;
import com.hanbing.library.android.adapter.BaseFragmentPagerAdapter;
import com.hanbing.library.android.fragment.list.ListFragment;
import com.hanbing.library.android.tool.ViewChecker;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.ptr.IPtrOnRefreshListener;
import com.hanbing.library.android.view.ptr.IPtrPullChecker;
import com.hanbing.library.android.view.ptr.PtrLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestPtrActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ptr);
        ButterKnife.bind(this);


        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment());
        fragments.add(new Fragment());
        fragments.add(new Fragment());
        fragments.add(new EmptyFragment());

        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"Fragment 1", "Fragment 2", "Fragment 3", "Empty Fragment"}));

        mTabLayout.setupWithViewPager(mViewPager);
    }


    public static class EmptyFragment extends Fragment {

        @Override
        protected void onLoadSuccess(List<? extends String> list) {
            super.onLoadSuccess(new ArrayList<String>());
        }
    }

    public static class Fragment extends ListFragment<String> {


        PtrLayout mPtrLayout;

        @Override
        protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateViewImpl(inflater, container, savedInstanceState);

            if (view instanceof ListView) {
                RelativeLayout relativeLayout = new RelativeLayout(getContext());

                relativeLayout.addView(view);

                view = relativeLayout;
            }

            ProgressBar progressBar = new ProgressBar(getContext());
            progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));

            mPtrLayout = new PtrLayout(getContext());
            mPtrLayout.setContentView(view);
            mPtrLayout.setHeaderView(progressBar);

            mPtrLayout.setPullChecker(new IPtrPullChecker() {
                @Override
                public boolean canPullFromStart(PtrLayout ptrLayout) {
                    if (getItemCount() <= 0) {
                        return ViewChecker.arriveStart(getDataViewHelper().getEmptyView(), true);
                    }
                    return ViewChecker.arriveStart(getDataView(), true);
                }

                @Override
                public boolean canPullFromEnd(PtrLayout ptrLayout) {
                    return false;
                }
            });

            mPtrLayout.setOnRefreshListener(new IPtrOnRefreshListener() {
                @Override
                public void onRefreshFromStart(PtrLayout ptrLayout) {
                    onRefresh();
                }

                @Override
                public void onRefreshFromEnd(PtrLayout ptrLayout) {

                }
            });

            view = mPtrLayout;

            return view;
        }


        @Override
        public ListView createListView() {
            ListView view = super.createListView();
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (null == params)
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            else {
                params.width = params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            }
            return view;
        }

        @Override
        protected void onVisible(boolean isFirstVisibleToUser) {
            if (isFirstVisibleToUser)
                autoPullToRefresh();
        }


        protected void autoPullToRefresh() {
            mPtrLayout.post(new Runnable() {
                @Override
                public void run() {
                    mPtrLayout.autoRefresh();

                }
            });
        }

        @Override
        public void onLoadCompleted() {
            super.onLoadCompleted();

            mPtrLayout.postOnRefreshCompleted();
        }

        protected View wrapWithEmptyAndLoading(View rootView) {
            return wrapWithLoadingView(wrapWithEmptyView(rootView));
        }

        protected View wrapWithEmptyView(View rootView) {
            View child = getDataViewHelper().getEmptyView();
            return addViewAtDataViewHierarchy(rootView, child);
        }

        protected View wrapWithLoadingView(View rootView) {

            View child = getDataViewHelper().getLoadingView();
            return addViewAtDataViewHierarchy(rootView, child);
        }

        @Override
        public View createEmptyView() {
            View view = new ImageView(getContext());
            view.setBackgroundColor(Color.LTGRAY);

            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        protected void initViews(View view) {
            super.initViews(view);

            wrapWithEmptyView(view);
            hideEmptyView();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtils.e("click item " + position);
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtils.e("long click item " + position);
            return false;
        }

        @Override
        public void onLoadData(boolean isRefresh, final int pageIndex, final int pageSize) {

LogUtils.e("load " + isRefresh);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    List<String> list = new ArrayList();

                    for (int i = 0; i < pageSize; i++) {
                        list.add(pageIndex + " " + i);
                    }

                    onLoadSuccess(list);
                }
            }, 2000);
        }


        @Override
        public BaseAdapter createAdapter() {
            return new Adapter();
        }

        class Adapter extends BaseAdapter {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                if (null == view) {
                    view = ViewUtils.inflate(viewGroup.getContext(), android.R.layout.simple_list_item_1);
                    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                }

                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(mDataList.get(i));
                return view;
            }
        }
    }
}
