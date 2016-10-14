package com.hanbing.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.hanbing.demo.DefaultAdapter;
import com.hanbing.demo.R;

public class TestSlideMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_slide_menu);

        ListView listView = new ListView(this);
        listView.setAdapter(new DefaultAdapter(30));
    }
}
