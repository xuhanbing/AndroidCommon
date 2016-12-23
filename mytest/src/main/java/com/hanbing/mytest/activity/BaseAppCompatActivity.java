package com.hanbing.mytest.activity;

import android.os.Bundle;

import com.hanbing.library.android.bind.ObjectBinder;

import org.xutils.x;

import butterknife.ButterKnife;

/**
 * Created by hanbing
 */
public class BaseAppCompatActivity extends com.hanbing.library.android.activity.BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle arg0) {

        super.onCreate(arg0);
        setContentView();
    }

    @Override
    protected void setContentView() {
        ObjectBinder.bind(this);
        ButterKnife.bind(this);
        x.view().inject(this);
    }
}
