package com.hanbing.mytest.activity;

import android.os.Bundle;
import android.view.Window;

/**
 * Created by hanbing
 */
public class BaseAppCompatActivity extends com.hanbing.library.android.activity.BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle arg0) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(arg0);
    }

    @Override
    protected void setContentView() {

    }
}
