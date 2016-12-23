package com.hanbing.retrofit_rxandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hanbing.library.android.adapter.BaseFragmentPagerAdapter;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.retrofit_rxandroid.bean.Data;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mTextView = (TextView) findViewById(R.id.textView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new SubjectFragment());
        fragments.add(new SubjectFragment());
        fragments.add(new SubjectFragment());

        String[] titles = {"电影", "音乐啊", "小说小说"};

        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        mTabLayout.setupWithViewPager(mViewPager);

//        RetrofitClient.getApiService().getBaidu().enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                LogUtils.e("result = " + response.body().substring(0, 100));
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                LogUtils.e("error");
//            }
//        });

//        RetrofitClient.getApiService().getSubjects(0, 10)
//
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Data>() {
//                    @Override
//                    public void onCompleted() {
//                        LogUtils.e("onCompleted ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtils.e("onError " + e.getCause());
//                    }
//
//                    @Override
//                    public void onNext(Data data) {
//                        String id = data.getSubjects().get(0).getId();
//                        LogUtils.e("get data 1 = " + id);
//                    }
//                });

//        RetrofitClient.getApiService().getSubjects(0, 10)
//                .flatMap(new Func1<Data, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(Data data) {
//                        String id = data.getSubjects().get(0).getId();
//                        LogUtils.e("id = " + id);
//                        return RetrofitClient.getApiService().getPhotos(id);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtils.e("e = " + e);
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        LogUtils.e("photos = " + s);
//                    }
//                });

        RetrofitClient.getApiService().getPhotos("123")
                .flatMap(new Func1<String, Observable<Data>>() {
                    @Override
                    public Observable<Data> call(String s) {
                        return RetrofitClient.getApiService().getSubjects(0, 10);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Data>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("e = " + e);
                    }

                    @Override
                    public void onNext(Data data) {

                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
