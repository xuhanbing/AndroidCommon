package com.hanbing.demo.md;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanbing.demo.NumListFragment;
import com.hanbing.demo.NumRecyclerViewFragment;
import com.hanbing.demo.R;
import com.hanbing.library.android.adapter.BaseFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling2);
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

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NumListFragment());
        fragments.add(new NumRecyclerViewFragment());
        fragments.add(new Fragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                TextView textView = new TextView(getContext());
                textView.setText("This is fragment.");
                return textView;
            }
        });


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments, null) {
            @Override
            public CharSequence getPageTitle(int position) {
                return "Fragment " + position;
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
