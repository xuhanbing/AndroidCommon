package com.hanbing.mytest.activity.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.TimeUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.scroll.CallbackScrollView;
import com.hanbing.mytest.R;
import com.hanbing.mytest.adapter.DefaultAdapter;
import com.hanbing.mytest.view.PinnedLinearLayout;

public class TestScrollView4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scroll_view4);

        CallbackScrollView scrollView = ViewUtils.findViewById(this, R.id.scrollView);

        scrollView.setOnFlingChangedListener(new CallbackScrollView.OnFlingChangedListener() {
            @Override
            public void onFlingStarted() {
                LogUtils.e("fling start");
            }

            @Override
            public void onFlingFinished() {
                LogUtils.e("flign finished");
            }
        });

        PinnedLinearLayout pinnedLinearLayout = ViewUtils.findViewById(this, R.id.pinnedLinearLayout);

//        pinnedLinearLayout.setPinnedView(ViewUtils.findViewById(this, R.id.btn));

        ListView listView = ViewUtils.findViewById(this, R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e("onItemClick " + position);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e("onItemLongClick " + position);
                return true;
            }
        });
        listView.setAdapter(new DefaultAdapter(100));
    }

    String string = "这是个按钮";

    public void onClick(View view) {

        LogUtils.e("click");
        ((Button) view).setText(string+= ("\n" +   TimeUtils.getTime() ));
    }
}
