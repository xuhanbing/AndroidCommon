package com.hanbing.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;
import com.hanbing.library.android.util.ToastUtils;

public class TestSlideMenu2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_slide_menu2);
    }

    public void onClick(View view) {
        ToastUtils.showToast(this, "点击");
    }
}
