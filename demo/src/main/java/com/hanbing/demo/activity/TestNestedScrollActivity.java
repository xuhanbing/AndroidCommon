package com.hanbing.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.NumFragment;
import com.hanbing.demo.NumListFragment;
import com.hanbing.demo.NumRecyclerViewFragment;
import com.hanbing.demo.R;
import com.hanbing.library.android.adapter.BaseFragmentPagerAdapter;
import com.hanbing.library.android.tool.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

public class TestNestedScrollActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_nested_scroll);

        View statusBar = findViewById(R.id.status_bar);

        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
        int statusBarHeight = systemBarTintManager.getConfig().getStatusBarHeight();

        ViewGroup.LayoutParams params = statusBar.getLayoutParams();
        params.height = statusBarHeight;

        statusBar.setLayoutParams(params);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);


        String[] titles = {"List", "Recycler", "Simple"};

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NumListFragment());
        fragments.add(new NumRecyclerViewFragment());
        fragments.add(new NumFragment());


        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));

        tabLayout.setupWithViewPager(viewPager);

    }
}
