package com.hanbing.demo.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.NumFragment;
import com.hanbing.demo.R;
import com.hanbing.library.android.adapter.BaseFragmentPagerAdapter;
import com.hanbing.library.android.view.TabLayout;
import com.hanbing.library.android.view.tab.TabWidget;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);



        View view = findViewById(R.id.include_view);
        View view2 = findViewById(R.id.scrollView);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NumFragment());
        fragments.add(new NumFragment());
        fragments.add(new NumFragment());


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        TabWidget tabWidget = (TabWidget) findViewById(R.id.tabWidget);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"关于", "F333 2", "Fragt 3adsfaljflajflakjflajfafa"}));

        tabLayout.setupWithViewPager(viewPager);
//        tabWidget.setViewPager(viewPager);
    }
}
