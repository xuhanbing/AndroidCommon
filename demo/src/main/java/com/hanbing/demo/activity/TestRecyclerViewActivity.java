package com.hanbing.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hanbing.WidgetRecyclerViewFragment;
import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;
import com.hanbing.library.android.adapter.BaseFragmentPagerAdapter;
import com.hanbing.library.android.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestRecyclerViewActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler_view);
        ButterKnife.bind(this);


        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new WidgetRecyclerViewFragment());
        fragments.add(new WidgetRecyclerViewFragment());


        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"F1", "F2"}));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick({R.id.checkbox, R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkbox:
                LogUtils.e("onclick checkbox");
                break;
            case R.id.btn:
                LogUtils.e("onclick button");
                break;
        }
    }
}
