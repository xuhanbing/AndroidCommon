package com.hanbing.mytest.activity.view;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hanbing.library.android.view.recycler.OnItemClickListener;
import com.hanbing.library.android.view.recycler.OnItemLongClickListener;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.ptr.IPtrOnRefreshListener;
import com.hanbing.library.android.view.recycler.HeaderRecyclerView;
import com.hanbing.library.android.view.recycler.decoration.GridItemDecoration;
import com.hanbing.library.android.view.recycler.decoration.LineItemDecoration;
import com.hanbing.mytest.activity.BaseAppCompatActivity;
import com.hanbing.mytest.fragment.NumFragment;
import com.hanbing.mytest.view.HeaderView;
import com.hanbing.mytest.view.HeaderView2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class TestPtr extends BaseAppCompatActivity {


    public static final String PTR_CONFIG = "PtrConfig";
    public static final String CONTENT_TYPE = "content";
    public static final String ORIENTATION = "orientation";
    public static final String LAYOUT_MANAGER = "layout_manager";

    interface Type {
        int DEFAULT = 0;
        int SCROLLVIEW = 1;
        int LISTVIEW = 2;
        int RECYCLERVIEW = 3;
        int VIEWPAGER = 4;
        int WEBVIEW = 5;
    }

    interface LayoutManagerType {
        int LINNER = 0;
        int GRID = 1;
        int STAGGERED = 2;
    }

    private PtrLayout mPtrLayout;

    int orientation = PtrLayout.VERTICAL;
    int contentType = Type.DEFAULT;
    int layoutManagerType = LayoutManagerType.LINNER;


    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_ptr);
        mPtrLayout = ViewUtils.findViewById(this, R.id.ptrLayout);



        SharedPreferences sp = getSharedPreferences(PTR_CONFIG, 0);
        contentType = sp.getInt(CONTENT_TYPE, contentType);
        orientation = sp.getInt(ORIENTATION, PtrLayout.VERTICAL);
        layoutManagerType = sp.getInt(LAYOUT_MANAGER, layoutManagerType);

//        initWithIntent();

    }


    void initWithIntent() {

        SharedPreferences.Editor editor = getSharedPreferences(PTR_CONFIG, 0).edit();
        editor.putInt(CONTENT_TYPE, contentType);
        editor.putInt(ORIENTATION, orientation);
        editor.putInt(LAYOUT_MANAGER, layoutManagerType);

        editor.commit();


        View contentView = null;

        mPtrLayout.removeAllViews();
        mPtrLayout.setOrientation(orientation);
        mPtrLayout.setOnRefreshListener(null);

        TextView textView = new TextView(this);

        textView.setText(R.string.large_text);
        textView.setBackgroundColor(Color.YELLOW);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("click ");
            }
        });

        if (mPtrLayout.isVertical()) {
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3000));
        } else {
            textView.setLayoutParams(new ViewGroup.LayoutParams(3000, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        switch (contentType) {
            case Type.SCROLLVIEW: {
                if (mPtrLayout.isVertical()) {

                    ScrollView scrollView = new ScrollView(this);
                    scrollView.addView(textView);

                    contentView = scrollView;
                } else {

                    HorizontalScrollView scrollView = new HorizontalScrollView(this);
                    scrollView.addView(textView);

                    contentView = scrollView;
                }
            }
                break;
            case Type.LISTVIEW: {
                ListView listView = new ListView(this);
                contentView = listView;

                final List<Map<String, String>> list = new ArrayList<>();



                for (int i = 0; i < 20; i ++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("value", "item " + i);

                    list.add(map);
                }
                final SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_1, new String[]{"value"}, new int[]{android.R.id.text1});
                listView.setAdapter(adapter);

                mPtrLayout.setOnRefreshListener(new IPtrOnRefreshListener() {
                    @Override
                    public void onRefreshFromStart(final PtrLayout ptrLayout) {


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int count = list.size();
                                for (int i = 0; i < count; i++) {
                                    Map<String, String> map = list.get(i);

                                    String value = "start " + map.get("value");
                                    map.put("value", value);
                                }
                                adapter.notifyDataSetChanged();
                                ptrLayout.postOnRefreshCompleted();
                            }
                        }, 3000);


                    }

                    @Override
                    public void onRefreshFromEnd(final PtrLayout ptrLayout) {


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 20; i++) {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("value", "refresh end item " + i);

                                    list.add(map);
                                }

                                adapter.notifyDataSetChanged();
                                ptrLayout.postOnRefreshCompleted();
                            }
                        }, 2000);


                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LogUtils.e("ListView onItemClick " + position);
                    }
                });

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        LogUtils.e("ListView onItemLongClick " + position);
                        return false;
                    }
                });
            }
                break;
            case Type.RECYCLERVIEW: {
                HeaderRecyclerView recyclerView = new HeaderRecyclerView(this);
                contentView = recyclerView;

                RecyclerView.LayoutManager layoutManager ;

                TextView header = new TextView(this);
                header.setText("This is header");
                header.setGravity(Gravity.CENTER);

                TextView footer = new TextView(this);
                footer.setText("This is footer");
                footer.setGravity(Gravity.CENTER);

                recyclerView.addHeaderView(header);
                recyclerView.addFooterView(footer);

                switch (layoutManagerType) {
                    case R.id.grid:{
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                        gridLayoutManager.setOrientation(mPtrLayout.isVertical() ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);

                        layoutManager = gridLayoutManager;

                        recyclerView.addItemDecoration(new GridItemDecoration.Builder(this).setSize(10).setColor(Color.GREEN).create());
                    }
                        break;
                    case R.id.staggered: {
                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4,
                                mPtrLayout.isVertical() ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);

                        layoutManager = staggeredGridLayoutManager;

                        recyclerView.addItemDecoration(new GridItemDecoration.Builder(this).setSize(10).setColor(Color.GREEN).create());
                    }
                        break;
                    default: {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

                        linearLayoutManager.setOrientation(mPtrLayout.isVertical() ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);

                        layoutManager = linearLayoutManager;

                        recyclerView.addItemDecoration(new LineItemDecoration.Builder(this).setSize(10).setOrientation(linearLayoutManager.getOrientation()).setColor(Color.GREEN).create());
                    }
                    break;
                }

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new MyListViewAdapter(recyclerView));

                ViewUtils.bindOnItemClickListener(recyclerView, new OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView recyclerView, View view, int position) {
                        LogUtils.e("RecyclerView onItemClick " + position);
                    }
                }, new OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(RecyclerView recyclerView, View view, int position) {
                        LogUtils.e("RecyclerView onItemLongClick " + position);
                        return false;
                    }
                });
            }
                break;
            case Type.VIEWPAGER: {
                ViewPager viewPager = new ViewPager(this);
                viewPager.setId(android.R.id.tabhost);
                contentView = viewPager;

                final List<Fragment> fragments = new ArrayList<>();

                for (int i = 0; i < 4; i ++) {
                    Fragment fragment = NumFragment.newInstance(i);
                    fragments.add(fragment);
                }
                viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        return fragments.get(position);
                    }

                    @Override
                    public int getCount() {
                        return fragments.size();
                    }
                });
            }
                break;
            case Type.WEBVIEW:
            {
                final WebView webView = new WebView(this);
                contentView = webView;

                WebViewClient webViewClient = new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        webView.loadUrl(url);
                        return true;
                    }
                };
                webView.setWebViewClient(webViewClient);

                webView.loadUrl("http://www.baidu.com");

            }
                break;
            default:
            {
                contentView = textView;
            }
                break;
        }



        mPtrLayout.setContentView(contentView);
        mPtrLayout.setHeaderView(new HeaderView2(this));
        mPtrLayout.setFooterView(new HeaderView(this));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        }
    }

    class MyListViewAdapter extends RecyclerView.Adapter<ViewHolder> {


        RecyclerView mRecyclerView;

        SparseArray<Integer> mSizes = new SparseArray<>();
        public MyListViewAdapter(RecyclerView recyclerView) {
            mRecyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ViewHolder viewHolder = new ViewHolder(new TextView(getApplicationContext()));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TextView textView = holder.textView;

            if (null == textView) {
                textView = new TextView(getApplicationContext());
            }

            int base = 200;

            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                int size = (int) (base + Math.random() * base);

                if (mSizes.get(position) != null) {
                    size = mSizes.get(position);
                } else {
                    mSizes.put(position, size);
                }

                holder.itemView.setMinimumWidth(size);
                holder.itemView.setMinimumHeight(size);
            } else {
                holder.itemView.setMinimumWidth(base);
                holder.itemView.setMinimumHeight(base);

            }


            textView.setGravity(Gravity.CENTER);
            textView.setText("Item position " + position);
            textView.setPadding(20, 20, 20, 20);


            int[] colors = {getColor(R.color.blueviolet), getColor(R.color.orangered), getColor(R.color.indianred)};
            holder.itemView.setBackgroundColor(colors[((int) (Math.random() * colors.length))]);
        }

        @Override
        public int getItemCount() {
            return 46;
        }
    }

    public void autoPullFromStart(View view) {
        mPtrLayout.autoPullFromStart();
    }

    public void autoPullFromEnd(View view) {
        mPtrLayout.autoPullFromEnd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_ptr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.autoPullFromStart:
                autoPullFromStart(item.getActionView());
                break;
            case R.id.autoPullFromEnd:
                autoPullFromEnd(item.getActionView());
                break;
            case R.id.vertical:
                orientation = PtrLayout.VERTICAL;
                initWithIntent();
                break;
            case R.id.horizontal:
                orientation = PtrLayout.HORIZONTAL;
                initWithIntent();
                break;
            case R.id.opt_simpleView:
                contentType = Type.DEFAULT;
                initWithIntent();
                break;
            case R.id.opt_scrollView:
                contentType = Type.SCROLLVIEW;
                initWithIntent();
                break;
            case R.id.opt_listView:
                contentType = Type.LISTVIEW;
                initWithIntent();
                break;
            case R.id.liner:
                layoutManagerType = LayoutManagerType.LINNER;
                contentType = Type.RECYCLERVIEW;
                initWithIntent();
                break;
            case R.id.grid:
                layoutManagerType = LayoutManagerType.GRID;
                contentType = Type.RECYCLERVIEW;
                initWithIntent();
                break;
            case R.id.staggered:
                layoutManagerType = LayoutManagerType.STAGGERED;
                contentType = Type.RECYCLERVIEW;
                initWithIntent();
                break;
            case R.id.opt_viewPager:
                contentType = Type.VIEWPAGER;
                initWithIntent();
                break;
            case R.id.opt_webView:
                contentType = Type.WEBVIEW;
                initWithIntent();
                break;
        }

        return true;
    }




}
