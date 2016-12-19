package com.hanbing.mytest.activity.view;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hanbing.library.android.adapter.BaseFragmentPagerAdapter;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.view.ScrollLayout;
import com.hanbing.library.android.tool.ViewChecker;
import com.hanbing.mytest.R;
import com.hanbing.mytest.adapter.DefaultAdapter;
import com.hanbing.mytest.fragment.NumFragment;
import com.hanbing.mytest.view.CheckScrollLayout;

import java.util.ArrayList;
import java.util.List;

public class TestScrollLayout extends AppCompatActivity {

    CheckScrollLayout mScrollLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_scroll_layout);
        mScrollLayout = new CheckScrollLayout(this);
        mScrollLayout.setOritention(ScrollLayout.VERTICAL);

        {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.p1);

            mScrollLayout.addView(imageView);
        }

        LinearLayout linearLayout  = new LinearLayout(this);

        TextView textView = new TextView(this);
        textView.setText("This this tab");

        textView.setBackgroundColor(Color.YELLOW);

        linearLayout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));

        final ListView listView = new ListView(this);
        listView.setAdapter(new DefaultAdapter(40));
        listView.setBackgroundColor(Color.RED);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e("click " + position);
            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LogUtils.e("touch " + event.getAction());
                return false;
            }
        });


        mScrollLayout.setScrollChecker(new com.hanbing.library.android.view.CheckScrollLayout.ScrollChecker() {
            @Override
            public boolean canScrollFromStart() {
                return ViewChecker.arriveStart(listView, true);
            }

            @Override
            public boolean canScrollFromEnd() {

                return ViewChecker.arriveEnd(listView, true);
            }
        });

        linearLayout.addView(listView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        mScrollLayout.addView(linearLayout);


        {
            ViewPager viewPager = new ViewPager(this);

            viewPager.setId(android.R.id.text1);
            List<Fragment> fragments = new ArrayList<>();
            for (int i = 0; i < 4; i++){
                fragments.add(NumFragment.newInstance(i));
            }
            viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments, null));
            mScrollLayout.addView(viewPager);
        }

        {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.p1);

            mScrollLayout.addView(imageView);
        }



        setContentView(mScrollLayout);
    }


}
