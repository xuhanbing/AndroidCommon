package com.hanbing.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hanbing.demo.DefaultAdapter;
import com.hanbing.demo.R;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.view.SlideMenuLayout;

public class TestSlideMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_slide_menu);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new DefaultAdapter(30));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e("click item " + position);
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
        SlideMenuLayout menuLayout = (SlideMenuLayout) findViewById(R.id.menu);
        menuLayout.openLeftMenu();
    }
}
