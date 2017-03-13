package com.hanbing.demo.activity;

import android.os.Bundle;

import com.hanbing.library.android.rxbus.Subscribe;
import com.hanbing.library.android.util.LogUtils;

public class RxBusSubActivity extends RxBusActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Subscribe
    public void onGetMessage(String msg) {
        LogUtils.e(this + "sub onGetMessage : " + msg);
    }
}
