package com.hanbing.mytest.activity.action;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hanbing.library.android.http.OkHttpRequest;
import com.hanbing.library.android.http.callback.HttpCallback;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.mytest.R;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class TestHttpCacheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_http_cache);

        final TextView textView = ViewUtils.findViewById(this, R.id.text);

        Map<String, String> params = new HashMap<>();
        params.put("title", "123");
        new OkHttpRequest(this).doRequest("http://www.baidu.com/", params, new HttpCallback() {
            @Override
            public void onSuccess(int requestCode, String requestUrl, String result) {
                textView.setText(result);
            }

            @Override
            public void onFailure(int requestCode, String requestUrl, String msg) {

            }

            @Override
            public void onCancelled(int requestCode, String requestUrl, String msg) {

            }
        });
    }
}
