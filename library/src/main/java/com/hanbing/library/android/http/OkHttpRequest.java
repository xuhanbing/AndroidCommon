package com.hanbing.library.android.http;

import android.text.TextUtils;

import com.hanbing.library.android.http.callback.HttpCallback;
import com.hanbing.library.android.http.callback.HttpProgressCallback;
import com.hanbing.library.android.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import okhttp3.internal.Util;
import okio.BufferedSink;

/**
 * Created by hanbing on 2016/6/17.
 */
public class OkHttpRequest extends HttpRequest {

    static final int BLOCK_SIZE = 4 * 1024;

    OkHttpClient mOkHttpClient = null;

    public OkHttpRequest() {
        mOkHttpClient =  new OkHttpClient.Builder().build();
    }

    @Override
    public Cancelable doRequest(final int requestCode, final String requestUrl, Map<String, String> headers, Map<String, String> params, Map<String, Object> uploads, final HttpCallback cb) {

        final HttpCallback callback = wrapHttpCallback(cb);

        Request request = createRequest(requestUrl, headers, params, uploads, callback);

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                if (null != callback) {
                    callback.onFailure(requestCode, requestUrl, e.getMessage());
                }

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (null != callback)
                callback.onSuccess(requestCode, requestUrl, response.body().string());
            }

        });

       return cache(new Task(call));

    }

    @Override
    public Cancelable download(final String localPath, final String downloadUrl, final HttpCallback cb) {

        final HttpCallback callback = wrapHttpCallback(cb);
        Call call = mOkHttpClient.newCall(new Request.Builder().url(downloadUrl).build());

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                if (null != callback) {
                    callback.onFailure(0, downloadUrl, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                long totalLength = 0;
                try {
                    totalLength = Long.parseLong( response.header("Content-Length"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                HttpProgressCallback progressCallback = null;

                if (callback instanceof HttpProgressCallback) progressCallback = (HttpProgressCallback) callback;

                writeToFile(localPath, downloadUrl, response.body().byteStream(), totalLength, progressCallback);

                if (null != callback) {
                    callback.onSuccess(0, downloadUrl, localPath);
                }
            }

        });

        return cache(new Task(call));
    }

    private void writeToFile(String localPath, String downloadUrl, InputStream inputStream, long totalLength, HttpProgressCallback callback) {

        if (null != callback) callback.onStarted(downloadUrl);

        if (!TextUtils.isEmpty(localPath) && null != inputStream)
        {
            File file = new File(localPath);

            file.getParentFile().mkdirs();

            byte[] data = new byte[BLOCK_SIZE];
            try {
                FileOutputStream os = new FileOutputStream(file);

                int readLen;
                int curLen = 0;


                while ((readLen = inputStream.read(data)) != -1) {
                    os.write(data, 0, readLen);
                    curLen += readLen;
                    if (null != callback) callback.onUpdateProgress(downloadUrl, totalLength, curLen);
                }

                os.flush();
                os.close();


            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                if (null != callback) callback.onFinished(downloadUrl);
            }
        }
    }

    private Request createRequest(String requestUrl, Map<String, String> headers, Map<String, String> params, Map<String, Object> uploads, HttpCallback callback) {
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
                    MediaType mediaType = MediaType.parse("application/octet-scream");
                    HttpProgressCallback progressCallback = (callback instanceof HttpProgressCallback) ? (HttpProgressCallback) callback : null;

                    RequestBody requestBody = createUploadRequestBody(mediaType, value, key, progressCallback);

                    if (value instanceof String)
                    {
                        File file = new File(value.toString());
                        multipartBodyBuilder.addFormDataPart(key, file.getName(), requestBody);

                    } else if (value instanceof InputStream
                            || value instanceof byte[])
                    {
                        multipartBodyBuilder.addFormDataPart(key, key, requestBody);

                    } else if (value instanceof File) {
                        File file = (File) value;
                        if (file.exists())
                            multipartBodyBuilder.addFormDataPart(key, file.getName(), requestBody);
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
        else if (isForcePost()) {
            MultipartBody multipartBody = new MultipartBody.Builder().addFormDataPart("", "").build();
            builder.post(multipartBody);
        }

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



    /** Returns a new request body that transmits {@code content}. */
    public static RequestBody create(final MediaType contentType, final byte[] content,String key, HttpProgressCallback callback) {
        return create(contentType, content, 0, content.length, key, callback);
    }

    /** Returns a new request body that transmits {@code content}. */
     static RequestBody create(final MediaType contentType, final byte[] content,
                                     final int offset, final int byteCount, final String key, final HttpProgressCallback callback) {
        if (content == null) throw new NullPointerException("content == null");
        Util.checkOffsetAndCount(content.length, offset, byteCount);
        return new RequestBody() {
            @Override public MediaType contentType() {
                return contentType;
            }

            @Override public long contentLength() {
                return byteCount;
            }

            @Override public void writeTo(BufferedSink sink) throws IOException {
                if (null == callback)
                sink.write(content, offset, byteCount);
                else {
                    if (null != callback) callback.onStarted(key);

                    //each block size
                    int block_size = BLOCK_SIZE;
                    int writeLen = 0;

                    while (writeLen < byteCount) {

                        int len = Math.min(block_size, byteCount - writeLen);
                        sink.write(content, offset + writeLen, len);
                        writeLen += len;

                        callback.onUpdateProgress(key, byteCount, writeLen);
                    }

                    if (null != callback) callback.onFinished(key);
                }
            }
        };
    }

    /** Returns a new request body that transmits {@code content}. */
    static RequestBody create(final MediaType contentType, final InputStream content,  long contentLength, final String key, final HttpProgressCallback callback) {
        if (content == null) throw new NullPointerException("content == null");

        final long realContentLength = (contentLength == -1) ? IOUtils.readStreamLength(content) : contentLength;

        return new RequestBody() {
            @Override public MediaType contentType() {
                return contentType;
            }

            @Override public long contentLength() {
                return realContentLength;
            }

            @Override public void writeTo(BufferedSink sink) throws IOException {
                if (null != callback) callback.onStarted(key);

                    //each block size
                    int block_size = BLOCK_SIZE;
                    int writeLen = 0;
                    int readLen = 0;
                    byte[] buffer = new byte[block_size];

                    while ((readLen = content.read(buffer)) != -1) {
                        sink.write(buffer, 0, readLen);

                        writeLen += readLen;

                        if (null != callback) {
                            callback.onUpdateProgress(key, realContentLength, writeLen);
                        }
                    }
                if (null != callback) callback.onFinished(key);
            }
        };
    }

    /** Returns a new request body that transmits the content of {@code file}. */
     static RequestBody create(final MediaType contentType, final File file, String key, final HttpProgressCallback callback) {
        if (file == null) throw new NullPointerException("content == null");

         try {
             InputStream inputStream = new FileInputStream(file);
             return create(contentType,inputStream, file.length(), key, callback);
         } catch (FileNotFoundException e) {
             throw new NullPointerException("file not found");
         }
    }

    static RequestBody createUploadRequestBody(final MediaType contentType, Object content, String key, final HttpProgressCallback callback) {
        if (null == content) return null;

        if (content instanceof String) {

            File file = new File((String) content);
            if (file.exists() && file.length() > 0) {
                return create(contentType, file, key, callback);
            }
        } else if (content instanceof File) {
            File file = (File) content;
            if (file.exists() && file.length() > 0)
            return create(contentType, file, key, callback);
        } else if (content instanceof InputStream) {
            return create(contentType, (InputStream)content, -1,  key, callback);
        } else if (content instanceof byte[]) {
            return create(contentType, (byte[]) content, key, callback);
        }

        return null;
    }

    class Task implements Cancelable {

        Call mCall;
        public Task(Call call) {
            mCall = call;
        }
        @Override
        public void cancel() {
            if (null != mCall && !mCall.isCanceled())
            {
                mCall.cancel();
            }
            mCall = null;
        }

        @Override
        public boolean isCanceled() {
            return null == mCall || mCall.isCanceled();
        }
    }

}
