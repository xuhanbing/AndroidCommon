package com.hanbing.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hanbing.demo.R;

public class TestRestoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_restore);


        TestRestoreFragment fragment = new TestRestoreFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_test_restore, fragment)
                .commit();
    }
}
