package com.hanbing.demo.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;

public class TestTransitionActivity extends BaseActivity {



    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_transition);

        view = findViewById(R.id.image1);


    }


    public void onClick(View view) {

        Intent intent = new Intent(this, TestTransition2Activity.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this, view, "my_image").toBundle());
        } else {
            startActivity(intent);
        }
    }
}
