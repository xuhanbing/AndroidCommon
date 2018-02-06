package com.hanbing.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;

public class TestHierachyRadioGroup extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_hierachy_radio_group);

        {
            RadioGroup rg1 = (RadioGroup) findViewById(R.id.rg_1);

            RadioButton rb1 = new RadioButton(this);
            rb1.setText("level1 add1");
            rb1.setChecked(true);
            rg1.addView(rb1);
        }

        {
            RadioGroup rg1 = (RadioGroup) findViewById(R.id.rg_2);

            RadioButton rb1 = new RadioButton(this);
            rb1.setText("level2 add1");
            rb1.setChecked(true);
            rg1.addView(rb1);
        }
    }
}
