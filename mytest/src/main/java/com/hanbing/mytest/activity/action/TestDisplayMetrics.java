package com.hanbing.mytest.activity.action;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.hanbing.library.android.util.SystemUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.mytest.R;

public class TestDisplayMetrics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_display_metrics);

        TextView textView = ViewUtils.findViewById(this, R.id.textView);

        DisplayMetrics dm = new DisplayMetrics();

        DisplayMetrics dm1 = SystemUtils.getDisplayMetrics(this);
        DisplayMetrics dm2 = SystemUtils.getDisplayMetrics2(this);


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("------------------------------\n");
        stringBuilder.append(dm1 + "\n");
        stringBuilder.append("------------------------------\n");
        stringBuilder.append(dm2 + "\n");


        textView.setText(stringBuilder);

    }
}
