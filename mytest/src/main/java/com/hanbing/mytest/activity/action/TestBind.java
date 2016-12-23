package com.hanbing.mytest.activity.action;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hanbing.library.android.bind.ObjectBinder;
import com.hanbing.library.android.bind.annotation.BindContentView;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_test_bind)
@BindContentView(R.layout.activity_test_bind)
public class TestBind extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_bind);
        ObjectBinder.bind(this);
//        x.view().inject(this);
    }


}
