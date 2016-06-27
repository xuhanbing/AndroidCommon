package com.hanbing.mytest.activity.action;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.common.http.HttpRequest;
import com.common.http.OkHttpRequest;
import com.common.http.XHttpRequest;
import com.common.http.callback.HttpSimpleCallback;
import com.common.http.callback.HttpSimpleProgressCallback;
import com.common.util.FileUtils;
import com.common.util.IOUtils;
import com.common.util.LogUtils;
import com.common.util.ViewUtils;
import com.hanbing.mytest.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TestHttp extends AppCompatActivity {


    static final String URL = "http://192.168.1.10:8080/TestWeb/upload";

    HttpRequest request;

    TextView textView;

    HttpSimpleCallback callback = new HttpSimpleProgressCallback() {
        @Override
        public void onUpdateProgress(String key, long total, long current) {

            LogUtils.e("onUpdateProgress " + key + ", " + current + "/" + total + ", percent = " + (current * 1.0f / total));

            showResult(key + "progress:" + ((int)(current * 100.0f / total) ) + "%");
        }

        @Override
        public void onStarted() {
            LogUtils.e("onStart");
        }

        @Override
        public void onFinished() {
            LogUtils.e("onFinished");
        }

        @Override
        public void onSuccess(int requestCode, String requestUrl, String result) {
            LogUtils.e("onSuccess " + result);
            showResult(result);
        }

        @Override
        public void onFailure(int requestCode, String requestUrl, String msg) {

            LogUtils.e("onFaulire " + msg);
            showResult(msg);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_http);

        textView = ViewUtils.findViewById(this, R.id.result);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        RadioGroup radioGroup = ViewUtils.findViewById(this, R.id.rg_type);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_xutils:
                        request = new XHttpRequest();
                        setTitle("XHttpRequest");
                        break;
                    default:
                        setTitle("OkHttpRequest");
                        request = new OkHttpRequest();
                        break;
                }
            }
        });

        request = new OkHttpRequest();

        FileUtils.writeToFile("/sdcard/http/1/1.txt", "haha你好".getBytes());


//        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//        downloadManager.enqueue(new DownloadManager.Request(Uri.parse("http://192.168.1.10:8080/TestWeb/upload/1.rar")));
//        request.doRequest(0, "http://192.168.1.10:8080/TestWeb/upload/1.txt", callback);

//        request.download( "/sdcard/11111.apk", "http://192.168.1.10:8080/TestWeb/upload/1.apk", callback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        request.cancelRequest();
    }

    private void showResult(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(result);
            }
        });
    }

    public void download(View view) {
        request.download("/sdcard/http/1.rar", "http://192.168.1.10:8080/TestWeb/upload/1.rar", callback);
    }

    private InputStream getInputStream() {
        try {
            return new FileInputStream("/sdcard/http/1.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getBytes(){

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("/sdcard/http/2.png");

            return IOUtils.read(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(inputStream);
        }
        return null;
    }

    private File getFile() {
        return new File("/sdcard/http/1.jpg");
    }

    private String getPath() {
        return "/sdcard/http/2.png";
    }

    private Map<String, String> getParams() {

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "xuhanbing");
        return map;
    }

    public void uploadInputStream(View view) {

        Map<String, String> params = getParams();

        params.put("type", "stream");

        Map<String, Object> files = new HashMap<>();

        files.put("stream.jpg", getInputStream());

        request.doRequest(0, URL, null, params, files, callback);
    }

    public void uploadBytes(View view) {
        Map<String, String> params = getParams();

        params.put("type", "bytes");

        Map<String, Object> files = new HashMap<>();

        files.put("bytes", getBytes());

        request.doRequest(0, URL, null, params, files, callback);
    }

    public void uploadPath(View view) {
        Map<String, String> params = getParams();

        params.put("type", "path");

        Map<String, Object> files = new HashMap<>();

        files.put("path", getPath());

        request.doRequest(0, URL, null, params, files, callback);
    }

    public void uploadFile(View view) {
        Map<String, String> params = getParams();

        params.put("type", "file");

        Map<String, Object> files = new HashMap<>();

        files.put("file", getFile());

        request.doRequest(0, URL, null, params, files, callback);
    }

    public void upload(View view) {
        Map<String, String> params = getParams();

        params.put("type", "all");

        Map<String, Object> files = new HashMap<>();

        files.put("stream", getInputStream());
        files.put("bytes", getBytes());
        files.put("path", getPath());
        files.put("file", getFile());

        request.doRequest(0, URL, null, params, files, callback);
    }

    public void post(View view) {
        request.doRequest(0, URL,getParams(), callback);
    }

    public void get(View view) {
        request.doRequest(0, URL, callback);
    }


}
