package com.hanbing.mytest.activity.action;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hanbing.library.android.http.HttpRequest;
import com.hanbing.library.android.http.HttpResult;
import com.hanbing.library.android.http.OkHttpRequest;
import com.hanbing.library.android.http.callback.HttpCallback;
import com.hanbing.library.android.util.FileUtils;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.SystemUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.mytest.R;

import org.xutils.cache.DiskCacheEntity;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

public class TestHttpCacheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_http_cache);

        final TextView textView = ViewUtils.findViewById(this, R.id.text);

//        Map<String, String> params = new HashMap<>();
//        params.put("title", "123");
//        OkHttpRequest okHttpRequest = new OkHttpRequest() {
//            @Override
//            protected boolean useCache() {
//                return !SystemUtils.isNetworkOk(getApplicationContext());
//            }
//        };
//        okHttpRequest.setCacheProxy(HttpRequest.CacheProxy.AUTO);
//        okHttpRequest.setCacheDir(HttpRequest.createCacheDir(this));
//        okHttpRequest.doRequest("http://www.baidu.com/", params, new HttpCallback() {
//            @Override
//            public void onSuccess(int requestCode, String requestUrl, String result) {
//                textView.setText(result);
//                LogUtils.e("cache", result);
//            }
//
//            @Override
//            public void onFailure(int requestCode, String requestUrl, String msg) {
//                LogUtils.e("cache", "error " + msg);
//            }
//
//            @Override
//            public void onCancelled(int requestCode, String requestUrl, String msg) {
//
//            }
//        });

        Network network = new BasicNetwork(new HurlStack());
        final Cache cache = new DiskBasedCache(new File(FileUtils.getCacheDirAuto(this) , "volley"));
        RequestQueue requestQueue = new RequestQueue(cache, network);

        Volley.newRequestQueue(this);

        requestQueue.start();

        requestQueue.add(new StringRequest("http://www.sina.com.cn/", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                textView.setText(s);
                LogUtils.e("result=" + s);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                textView.setText("error");
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                } catch (UnsupportedEncodingException var4) {
                    parsed = new String(response.data);
                }

                Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);

                if (0 == entry.ttl)
                entry.ttl  = System.currentTimeMillis() + 24 * 3600 * 1000;
                if(0 == entry.softTtl)
                    entry.softTtl = 5 * 1000;

                return Response.success(parsed, entry);
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
