package com.hanbing.mytest.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by hanbing
 */
public class BaseAppCompatActivity extends com.common.activity.BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle arg0) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(arg0);
    }
}
