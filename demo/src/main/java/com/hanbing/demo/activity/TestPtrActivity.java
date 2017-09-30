package com.hanbing.demo.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;
import com.hanbing.library.android.adapter.BaseFragmentPagerAdapter;
import com.hanbing.library.android.adapter.ViewHolder;
import com.hanbing.library.android.fragment.list.GridFragment;
import com.hanbing.library.android.fragment.list.ListFragment;
import com.hanbing.library.android.fragment.list.RecyclerViewFragment;
import com.hanbing.library.android.tool.ViewChecker;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ReflectUtils;
import com.hanbing.library.android.util.TimeUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.ptr.IPtrOnRefreshListener;
import com.hanbing.library.android.view.ptr.IPtrPullChecker;
import com.hanbing.library.android.view.ptr.PtrLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

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


        List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment());
        fragments.add(new RecyclerFragment());
        fragments.add(new GridFragment());
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
        PtrFrameLayout mPtrFrameLayout;
        protected ListView mListView;
        protected RecyclerView mRecyclerView;


        boolean useMy = true;


        public class MyListView extends ListView {

            public static final String TAG = "MyListView";
            public MyListView(Context context) {
                super(context);
            }

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                LogUtils.e(TAG, "onMeasure");
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }

        public class MyRelativeLayout extends RelativeLayout {

            public static final String TAG = "MyRelativeLayout";
            public MyRelativeLayout(Context context) {
                super(context);
            }

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                LogUtils.e(TAG, "onMeasure");
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }


        @Override
        protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mListView = new MyListView(getContext());
            mRecyclerView = new RecyclerView(getContext());
            View view = mListView;

            TextView textView = new TextView(getContext());
            textView.setText(R.string.large_text);
            view.setBackgroundColor(Color.BLACK);


            RelativeLayout relativeLayout = new MyRelativeLayout(getContext());
            relativeLayout.setBackgroundColor(0xffe800e8);
            relativeLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            view = relativeLayout;



            ProgressBar progressBar = new ProgressBar(getContext());
            progressBar.setBackgroundColor(Color.LTGRAY);
            progressBar.setMinimumHeight(400);
            progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));

            ProgressBar progressBar2 = new ProgressBar(getContext());
            progressBar2.setBackgroundColor(Color.LTGRAY);
            progressBar2.setMinimumHeight(400);
            progressBar2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));

            if (useMy) {
                mPtrLayout = new PtrLayout(getContext());
                mPtrLayout.setContentView(view);
                mPtrLayout.setHeaderView(progressBar);
                mPtrLayout.setFooterView(progressBar2);
                mPtrLayout.setBackgroundResource(R.drawable.a);
                mPtrLayout.setHoldHeaderWhenRefreshing(false);
                int padding = 50;
                mPtrLayout.setPadding(padding, padding, padding, 0);

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
                        if (getItemCount() <= 0) {
                            return false;
                        }

                        return ViewChecker.arriveEnd(getDataView(), true);
                    }
                });

                mPtrLayout.setOnRefreshListener(new IPtrOnRefreshListener() {
                    @Override
                    public void onRefreshFromStart(PtrLayout ptrLayout) {
                        onRefresh();
                    }

                    @Override
                    public void onRefreshFromEnd(PtrLayout ptrLayout) {
                        onLoadMore();
                    }
                });

                view = mPtrLayout;
            } else {
                PtrFrameLayout ptrFrameLayout = new PtrFrameLayout(getContext());
                ptrFrameLayout.addView(view);
                ReflectUtils.setValue(ptrFrameLayout, "mContent", view);
                ptrFrameLayout.setHeaderView(progressBar);
                ptrFrameLayout.setKeepHeaderWhenRefresh(true);
                ptrFrameLayout.setPtrHandler(new PtrHandler() {
                    @Override
                    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                        if (getItemCount() <= 0) {
                            return ViewChecker.arriveStart(getDataViewHelper().getEmptyView(), true);
                        }
                        return ViewChecker.arriveStart(getDataView(), true);
                    }

                    @Override
                    public void onRefreshBegin(PtrFrameLayout frame) {
                        onRefresh();
                    }
                });

                view = mPtrFrameLayout = ptrFrameLayout;
            }

            return view;
        }


        @Override
        public ListView createListView() {
            ListView view = mListView;
//            ViewGroup.LayoutParams params = view.getLayoutParams();
//            if (null == params)
//                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            else {
//                params.width = params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            }
//            view.setLayoutParams(params);
            return view;
        }


//        @Override
//        public RecyclerView createRecyclerView() {
//            return mRecyclerView;
//        }

        @Override
        protected void onVisible(boolean isFirstVisibleToUser) {
            if (isFirstVisibleToUser)
                autoPullToRefresh();
        }


        protected void autoPullToRefresh() {

            if (useMy) {
                mPtrLayout.autoRefresh();
            } else {
                mPtrFrameLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrameLayout.autoRefresh();
                    }
                });
            }

        }

        @Override
        public void onLoadCompleted() {
            super.onLoadCompleted();

            if (useMy) {
                mPtrLayout.postOnRefreshCompleted();
            } else {
                mPtrFrameLayout.refreshComplete();
            }
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
            view.setBackgroundColor(Color.BLUE);

            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        protected void initViews(View view) {
            super.initViews(view);

            setScrollLoadMoreEnabled(false);
            wrapWithEmptyView(view);
//            hideEmptyView();
        }

//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            LogUtils.e("click item " + position);
//        }
//
//        @Override
//        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//            LogUtils.e("long click item " + position);
//            return false;
//        }


//        @Override
//        public void onItemClick(RecyclerView recyclerView, View view, int position) {
//            LogUtils.e("click item " + position);
//            super.onItemClick(recyclerView, view, position);
//        }
//
//        @Override
//        public boolean onItemLongClick(RecyclerView recyclerView, View view, int position) {
//            LogUtils.e("long click item " + position);
//
//            return super.onItemLongClick(recyclerView, view, position);
//        }

        int count = 0;
        @Override
        public void onLoadData(boolean isRefresh, final int pageIndex, final int pageSize) {

LogUtils.e("load " + isRefresh);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    List<String> list = new ArrayList();

                    if (true) {
                        int size = pageSize;//Math.max(1, (int) (pageSize * Math.random()));
                        String time = TimeUtils.getTime("HH-mm-ss");
                        for (int i = 0; i < size; i++) {
                            list.add(size + "->>" + i + "->>" + time);
                        }
                    }


                    onLoadSuccess(list);
//                    mDataList.clear();
//                    mDataList.addAll(list);
//                    notifyDataSetChanged();
//                    onLoadSuccess();


                }
            }, 10000);
        }


        @Override
        public BaseAdapter createAdapter() {
            return new Adapter();
        }


//        @Override
//        public RecyclerView.Adapter createAdapter() {
//            return new Adapter();
//        }

        class ViewHolder extends com.hanbing.library.android.adapter.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }

        }
        class Adapter extends com.hanbing.library.android.adapter.BaseAdapter<ViewHolder> {

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public int getItemCount() {
                return mDataList.size();
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = ViewUtils.inflate(parent.getContext(), android.R.layout.simple_list_item_1);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                View view = holder.itemView;

                view.setBackgroundColor(Color.YELLOW);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(mDataList.get(position));

            }
        }
    }


    public static class GridFragment extends com.hanbing.library.android.fragment.list.GridFragment<String> {


        PtrLayout mPtrLayout;

        @Override
        protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateViewImpl(inflater, container, savedInstanceState);;

            TextView textView = new TextView(getContext());
            textView.setText(R.string.large_text);
            view.setBackgroundColor(Color.BLACK);


            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            relativeLayout.setBackgroundColor(0xffe800e8);
            relativeLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            view = relativeLayout;



            ProgressBar progressBar = new ProgressBar(getContext());
            progressBar.setBackgroundColor(Color.LTGRAY);
            progressBar.setMinimumHeight(400);
            progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));

            ProgressBar progressBar2 = new ProgressBar(getContext());
            progressBar2.setBackgroundColor(Color.LTGRAY);
            progressBar2.setMinimumHeight(400);
            progressBar2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));

            mPtrLayout = new PtrLayout(getContext());
            mPtrLayout.setContentView(view);
            mPtrLayout.setHeaderView(progressBar);
//            mPtrLayout.setFooterView(progressBar2);

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
//                    if (getItemCount() <= 0) {
//                        return ViewChecker.arriveEnd(getDataViewHelper().getEmptyView(), true);
//                    }
//
//                    return ViewChecker.arriveEnd(getDataView(), true);
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
                    onLoadMore();
                }
            });

            view = mPtrLayout;

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

            LogUtils.e("Will complete");
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
            view.setBackgroundColor(Color.BLUE);

            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        protected void initViews(View view) {
            super.initViews(view);

            setScrollLoadMoreEnabled(false);
            wrapWithEmptyView(view);
//            hideEmptyView();
        }

        @Override
        public void initDataView(GridView view) {
            view.setAdapter(getDataAdapter());
        }

        //        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            LogUtils.e("click item " + position);
//        }
//
//        @Override
//        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//            LogUtils.e("long click item " + position);
//            return false;
//        }


//        @Override
//        public void onItemClick(RecyclerView recyclerView, View view, int position) {
//            LogUtils.e("click item " + position);
//            super.onItemClick(recyclerView, view, position);
//        }
//
//        @Override
//        public boolean onItemLongClick(RecyclerView recyclerView, View view, int position) {
//            LogUtils.e("long click item " + position);
//
//            return super.onItemLongClick(recyclerView, view, position);
//        }

        int count = 0;
        @Override
        public void onLoadData(boolean isRefresh, final int pageIndex, final int pageSize) {

            LogUtils.e("load " + isRefresh);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    List<String> list = new ArrayList();

                    if (true) {
                        int size = pageSize;//Math.max(1, (int) (pageSize * Math.random()));
                        String time = TimeUtils.getTime("HH-mm-ss");
                        for (int i = 0; i < size; i++) {
                            list.add(size + "->>" + i + "->>" + time);
                        }
                    }


                    onLoadSuccess(list);
//                    mDataList.clear();
//                    mDataList.addAll(list);
//                    notifyDataSetChanged();
//                    onLoadSuccess();


                }
            }, 2000);
        }


        @Override
        public BaseAdapter createAdapter() {
            return new Adapter();
        }


//        @Override
//        public RecyclerView.Adapter createAdapter() {
//            return new Adapter();
//        }

        class ViewHolder extends com.hanbing.library.android.adapter.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }

        }
        class Adapter extends com.hanbing.library.android.adapter.BaseAdapter<ViewHolder> {

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public int getItemCount() {
                return mDataList.size();
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = ViewUtils.inflate(parent.getContext(), android.R.layout.simple_list_item_1);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                View view = holder.itemView;

                view.setBackgroundColor(Color.YELLOW);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(mDataList.get(position));

            }
        }
    }

    public static class RecyclerFragment extends RecyclerViewFragment<String> {


        PtrLayout mPtrLayout;
        protected ListView mListView;
        protected RecyclerView mRecyclerView;




        @Override
        protected View onCreateViewImpl(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setDataView(null);
            mListView = new ListView(getContext());
            mRecyclerView = new RecyclerView(getContext());
            View view = mRecyclerView;

            TextView textView = new TextView(getContext());
            textView.setText(R.string.large_text);
            view.setBackgroundColor(Color.BLACK);


            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            relativeLayout.setBackgroundColor(0xffe800e8);
            relativeLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            view = relativeLayout;



            ProgressBar progressBar = new ProgressBar(getContext());
            progressBar.setBackgroundColor(Color.LTGRAY);
            progressBar.setMinimumHeight(400);
            progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));

            ProgressBar progressBar2 = new ProgressBar(getContext());
            progressBar2.setBackgroundColor(Color.LTGRAY);
            progressBar2.setMinimumHeight(400);
            progressBar2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));

            mPtrLayout = new PtrLayout(getContext());
            mPtrLayout.setContentView(view);
            mPtrLayout.setHeaderView(progressBar);
//            mPtrLayout.setFooterView(progressBar2);

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
//                    if (getItemCount() <= 0) {
//                        return ViewChecker.arriveEnd(getDataViewHelper().getEmptyView(), true);
//                    }
//
//                    return ViewChecker.arriveEnd(getDataView(), true);
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
                    onLoadMore();
                }
            });

            view = mPtrLayout;

            return view;
        }


//        @Override
//        public ListView createListView() {
//            ListView view = mListView;
////            ViewGroup.LayoutParams params = view.getLayoutParams();
////            if (null == params)
////                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
////            else {
////                params.width = params.height = ViewGroup.LayoutParams.MATCH_PARENT;
////            }
////            view.setLayoutParams(params);
//            return view;
//        }


        @Override
        public RecyclerView createRecyclerView() {
            return mRecyclerView;
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

            LogUtils.e("Will complete");
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
            view.setBackgroundColor(Color.BLUE);

            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        protected void initViews(View view) {
            super.initViews(view);

            setScrollLoadMoreEnabled(false);
            wrapWithEmptyView(view);
//            hideEmptyView();
        }

//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            LogUtils.e("click item " + position);
//        }
//
//        @Override
//        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//            LogUtils.e("long click item " + position);
//            return false;
//        }


//        @Override
//        public void onItemClick(RecyclerView recyclerView, View view, int position) {
//            LogUtils.e("click item " + position);
//            super.onItemClick(recyclerView, view, position);
//        }
//
//        @Override
//        public boolean onItemLongClick(RecyclerView recyclerView, View view, int position) {
//            LogUtils.e("long click item " + position);
//
//            return super.onItemLongClick(recyclerView, view, position);
//        }

        int count = 0;
        @Override
        public void onLoadData(boolean isRefresh, final int pageIndex, final int pageSize) {

            LogUtils.e("load " + isRefresh);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    List<String> list = new ArrayList();

                    if (true) {
                        int size = pageSize;//Math.max(1, (int) (pageSize * Math.random()));
                        String time = TimeUtils.getTime("HH-mm-ss");
                        for (int i = 0; i < size; i++) {
                            list.add(size + "->>" + i + "->>" + time);
                        }
                    }


                    onLoadSuccess(list);
//                    mDataList.clear();
//                    mDataList.addAll(list);
//                    notifyDataSetChanged();
//                    onLoadSuccess();


                }
            }, 2000);
        }


//        @Override
//        public BaseAdapter createAdapter() {
//            return new Adapter();
//        }


        @Override
        public RecyclerView.Adapter createAdapter() {
            return new Adapter();
        }

        class ViewHolder extends com.hanbing.library.android.adapter.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }

        }
        class Adapter extends RecyclerView.Adapter<ViewHolder> {

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public int getItemCount() {
                return mDataList.size();
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = ViewUtils.inflate(parent.getContext(), android.R.layout.simple_list_item_1);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                View view = holder.itemView;

                view.setBackgroundColor(Color.YELLOW);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(mDataList.get(position));

            }
        }
    }

}
