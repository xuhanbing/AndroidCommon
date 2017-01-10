package com.hanbing.demo.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.DefaultAdapter;
import com.hanbing.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestSlidingPaneActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sliding_pane);
        ButterKnife.bind(this);

        mListView.setAdapter(new DefaultAdapter(40));

        ContextCompat.startActivities(this, )
    }
}
