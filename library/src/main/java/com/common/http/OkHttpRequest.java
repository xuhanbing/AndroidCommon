package com.common.http;

import com.common.http.callback.HttpCallback;
import com.common.util.FileUtils;
import com.common.util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hanbing on 2016/6/17.
 */
public class OkHttpRequest extends HttpRequestBase {


    OkHttpClient mOkHttpClient = null;

    Call mCall;

    public OkHttpRequest() {
        mOkHttpClient =  new OkHttpClient.Builder().build();
    }

    @Override
    public void doRequest(final int requestCode, final String requestUrl, Map<String, String> headers, Map<String, String> params, Map<String, Object> uploads, final HttpCallback callback) {

        Request request = createRequest(requestUrl, headers, params, uploads);

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if (null != callback) {
                    callback.onFailure(requestCode, requestUrl, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != callback) {
                    callback.onSuccess(requestCode, requestUrl, response.body().string());
                }
            }

        });

        mCall = call;

    }

    @Override
    public void download(final String localPath, final String downloadUrl, final HttpCallback callback) {


        Call call = mOkHttpClient.newCall(new Request.Builder().url(downloadUrl).build());

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if (null != callback) {
                    callback.onFailure(0, downloadUrl, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                FileUtils.writeToFile(localPath, response.body().byteStream());

                if (null != callback) {
                    callback.onSuccess(0, downloadUrl, localPath);
                }
            }

        });

        mCall = call;
    }

    private Request createRequest(String requestUrl, Map<String, String> headers, Map<String, String> params, Map<String, Object> uploads) {
        Request.Builder builder = new Request.Builder();


        //默认get
        builder.url(requestUrl).get();

        MultipartBody.Builder multipartBodyBuilder = null;
        FormBody.Builder formBodyBuilder = null;

        //上传文件或数据
        if (null != uploads) {

            multipartBodyBuilder  = new MultipartBody.Builder();

            Iterator<Map.Entry<String, Object>> iterator = uploads.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();

                String key = next.getKey();
                Object value = next.getValue();
                if (null != value)
                {
                    if (value instanceof String)
                    {
                        File file = new File(value.toString());
                        if (file.exists())
                            multipartBodyBuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("application/octet-scream"), file));

                    } else if (value instanceof InputStream
                            || value instanceof byte[])
                    {
                        byte[] buffer ;
                        if (value instanceof InputStream) {
                            buffer = IOUtils.read((InputStream) value);
                            IOUtils.close((InputStream)value);
                        } else {
                            buffer = (byte[]) value;
                        }
                        if (null != buffer)
                        multipartBodyBuilder.addFormDataPart(key, key, RequestBody.create(MediaType.parse("application/octet-scream"), buffer));

                    } else if (value instanceof File) {
                        File file = (File) value;
                        if (file.exists())
                            multipartBodyBuilder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse("application/octet-scream"), file));
                    }
                }



            }
        }

        //请求参数
        if (null != params) {

            formBodyBuilder = new FormBody.Builder();

            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();

                if (null != multipartBodyBuilder)
                    multipartBodyBuilder.addFormDataPart(next.getKey(), next.getValue());
                 else
                    formBodyBuilder.add(next.getKey(), next.getValue());
            }


        }

        //
        if (null != multipartBodyBuilder)
            builder.post(multipartBodyBuilder.build());
        else if (null != formBodyBuilder)
            builder.post(formBodyBuilder.build());

        //请求头部
        if (null != headers) {
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();

                builder.addHeader(next.getKey(), next.getValue());
            }

        }

        return builder.build();
    }

    @Override
    public void cancelRequest() {
        if (null != mCall) {
            mCall.cancel();
        }
    }
}
