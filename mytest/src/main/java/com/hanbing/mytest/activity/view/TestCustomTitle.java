package com.hanbing.mytest.activity.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class TestCustomTitle extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_test_custom_title);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_toolbar);

    }
}
