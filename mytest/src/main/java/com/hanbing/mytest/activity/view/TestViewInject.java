package com.hanbing.mytest.activity.view;

import android.os.Bundle;
import android.widget.TextView;

import com.common.activity.BaseAppCompatActivity;
import com.common.util.ViewUtils;
import com.hanbing.mytest.R;

import org.xutils.view.annotation.ViewInject;

public class TestViewInject extends BaseAppCompatActivity {


    @ViewInject(R.id.text)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_view_inject);

        textView = ViewUtils.findViewById(this, R.id.text);
        textView.setText("Text from viewinject");
    }
}
