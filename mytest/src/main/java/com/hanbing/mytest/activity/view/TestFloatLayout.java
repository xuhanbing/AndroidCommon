package com.hanbing.mytest.activity.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.FloatLayout;
import com.hanbing.mytest.R;
import com.hanbing.mytest.activity.BaseAppCompatActivity;

public class TestFloatLayout extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_float_layout);

        FloatLayout floatLayout = ViewUtils.findViewById(this, R.id.floatLayout);


        //for test
        String[] hotwords = new String[]{
                "世界互联网大会", "乌镇", "名商网", "暴雨", "台风", "雄三",
                "世界互联网大会", "乌镇", "名商网", "暴雨", "台风", "雄三"};

        for (String word : hotwords) {

            TextView textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.bg_edittext);
            textView.setText(word);


            floatLayout.addView(textView);
        }

    }
}
