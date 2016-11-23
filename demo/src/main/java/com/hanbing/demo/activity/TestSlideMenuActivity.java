package com.hanbing.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hanbing.demo.DefaultAdapter;
import com.hanbing.demo.R;
import com.hanbing.library.android.adapter.BaseAdapter;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.view.SlideMenuLayout;

import java.util.HashSet;

public class TestSlideMenuActivity extends AppCompatActivity {


    boolean mOpen = false;

    MyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_slide_menu);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapter = new MyAdapter(30));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e("click item " + position);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e("long click item " + position);
                return false;
            }
        });
//
//        listView.setClickable(true);

//        findViewById(R.id.left_tv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogUtils.e("click left menu");
//            }
//        });

//        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogUtils.e("click right menu");
//            }
//        });
    }

    public void openMenu(View view) {
//        SlideMenuLayout menuLayout = (SlideMenuLayout) findViewById(R.id.menu);
//        menuLayout.openLeftMenu();

//        View view1 = findViewById(R.id.title_tv);
//        if (view1.getVisibility() == View.VISIBLE) {
//            view1.setVisibility(View.GONE);
//        } else {
//            view1.setVisibility(View.VISIBLE);
//        }

        mAdapter.edit();

    }

    class MyAdapter extends DefaultAdapter {

        SparseArray<Boolean> mHashSet = new SparseArray<>();
        public MyAdapter() {
        }

        public MyAdapter(int count) {
            super(count);

            mHashSet.clear();
            for (int i = 0; i < count; i++) {
                mHashSet.put(i, false);
            }
        }


        public void edit() {

            mOpen = !mOpen;

            for (int i = 0; i < getCount(); i++) {
                mHashSet.put(i, mOpen);
            }
           notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.slide_item, parent, false);
            }



            TextView title = (TextView) convertView.findViewById(R.id.title_tv);

            SlideMenuLayout slideMenuLayout = (SlideMenuLayout) convertView.findViewById(R.id.slideMenu);
            View view = slideMenuLayout;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.e("getView click " + position);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    LogUtils.e("getView longclick " + position);
                    return false;
                }
            });

            slideMenuLayout.setOnStateChangeListener(new SlideMenuLayout.OnStateChangeListener() {
                @Override
                public void onChanged(SlideMenuLayout slideMenuLayout, int state) {


                    mHashSet.put(position, state == SlideMenuLayout.STATE_LEFT_OPENED || state == SlideMenuLayout.STATE_RIGHT_OPENED);

                    LogUtils.e(slideMenuLayout + " onChanged position = " + position + ", state = " + state + ", value = " + mHashSet.get(position));
                }
            });

//            if (mOpen) slideMenuLayout.openLeftMenu();
//            else slideMenuLayout.closeMenu();

            Boolean value = mHashSet.get(position);
            LogUtils.e("position  " + position + ", value = " + value);
            if (value.booleanValue()) {
                slideMenuLayout.openLeftMenuImmediately();
            } else {
                slideMenuLayout.closeLeftMenuImmediately();
            }

            title.setText("item " + position);
            return convertView;
        }
    }


}
