package com.hanbing.library.android.http;

import android.text.TextUtils;

import com.hanbing.library.android.http.callback.HttpCallback;
import com.hanbing.library.android.http.callback.HttpProgressCallback;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hanbing on 2016/3/28.
 */
public class XHttpRequest extends HttpRequest {


    /*
     * (non-Javadoc)
	 *
	 * @see com.ming.common.http.HttpRequestBase#doRequest(int,
	 * java.lang.String, java.util.Map, java.util.Map, java.util.Map,
	 * com.ming.common.http.HttpRequestCallback)
	 */
    @Override
    public synchronized Cancelable doRequest(final int requestCode, final String requestUrl, Map<String, String> headers,
                                Map<String, String> params, Map<String, Object> uploads, final HttpCallback callback) {
        // TODO Auto-generated method stub


        RequestParams requestParams = new RequestParams(requestUrl);
        if (isCacheEnabled()) {
            File file = new File(mCacheDir);
            //取路径的名称
            requestParams.setCacheDirName(file.getName());
            // 默认缓存存活时间, 单位:毫秒.(如果服务没有返回有效的max-age或Expires)
            requestParams.setCacheMaxAge(mCacheMaxAge);
            requestParams.setCacheSize(mCacheSize);
        }

        HttpMethod method = HttpMethod.GET;
        requestParams.setMethod(method);
        //请求头部
        if (null != headers) {
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();

                requestParams.addHeader(next.getKey(), next.getValue());
            }

        }

        //请求参数
        if (null != params) {
            requestParams.setMethod(method = HttpMethod.POST);
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();

                requestParams.addParameter(next.getKey(), next.getValue());
            }

        }

        //上传文件或数据
        if (null != uploads) {

            requestParams.setMethod(method = HttpMethod.POST);
            requestParams.setMultipart(true);

            Iterator<Map.Entry<String, Object>> iterator = uploads.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();

                String key = next.getKey();
                Object value = next.getValue();
                if (null != value) {

                    if (value instanceof String) {
                        File file = new File(value.toString());
                        if (file.exists())
                            requestParams.addBodyParameter(key, file);
                    } else if (value instanceof InputStream
                            || value instanceof byte[]) {
                        requestParams.addBodyParameter(key, value, "application/octet-stream", key);
                    } else if (value instanceof File) {
                        File file = (File) value;
                        if (file.exists())
                            requestParams.addBodyParameter(key, file);
                    }
                }

            }
        }

        Callback.CommonCallback commonCallback = new UploadCallback(requestCode, requestUrl, callback);

        Callback.Cancelable cancelable
                // 使用CacheCallback, xUtils将为该请求缓存数据.
                = (isForcePost() || (HttpMethod.POST == method))
                ? x.http().post(requestParams, commonCallback)
                : x.http().get(requestParams, commonCallback);

        return cache(new Task(cancelable));
    }

    @Override
    public Cancelable download(String localPath, String downloadUrl, HttpCallback callback) {


        DownloadCallback downloadCallback = new DownloadCallback(downloadUrl, callback);

        RequestParams params = new RequestParams(downloadUrl);
        params.setAutoResume(true); //断点续传
        params.setAutoRename(false); //自动重命名
        params.setSaveFilePath(localPath);
        params.setCancelFast(true);

        Callback.Cancelable cancelable
                = x.http().get(params, downloadCallback);

        return cache(new Task(cancelable));

    }

    class UploadCallback implements Callback.CacheCallback<String>, Callback.ProgressCallback<String> {

        int mRequestCode;
        String mRequestUrl;
        HttpCallback mCallback;

        public UploadCallback(int requestCode, String requestUrl, HttpCallback callback) {
            this.mRequestCode = requestCode;
            this.mRequestUrl = requestUrl;
            this.mCallback = callback;
        }


        @Override
        public void onWaiting() {

        }

        @Override
        public void onStarted() {
            if (mCallback instanceof HttpProgressCallback) {
                ((HttpProgressCallback) mCallback).onStarted(mRequestUrl);
            }
        }

        @Override
        public void onLoading(long total, long current, boolean b) {
            if (mCallback instanceof HttpProgressCallback) {
                ((HttpProgressCallback) mCallback).onUpdateProgress(mRequestUrl, total, current);
            }
        }

        private boolean hasError = false;
        private String result = null;
        private String cacheResult = null;
        private String errMessage;

        @Override
        public boolean onCache(String result) {
            // 得到缓存数据, 缓存过期后不会进入这个方法.
            // 如果服务端没有返回过期时间, 参考params.setCacheMaxAge(maxAge)方法.
            //
            // * 客户端会根据服务端返回的 header 中 max-age 或 expires 来确定本地缓存是否给 onCache 方法.
            //   如果服务端没有返回 max-age 或 expires, 那么缓存将一直保存, 除非这里自己定义了返回false的
            //   逻辑, 那么xUtils将请求新数据, 来覆盖它.
            //
            // * 如果信任该缓存返回 true, 将不再请求网络;
            //   返回 false 继续请求网络, 但会在请求头中加上ETag, Last-Modified等信息,
            //   如果服务端返回304, 则表示数据没有更新, 不继续加载数据.
            //
            this.cacheResult = result;
            //如果只用缓存，返回true
            return CacheProxy.ONLY == mCacheProxy; // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
        }

        @Override
        public void onSuccess(String result) {
            // 注意: 如果服务返回304或 onCache 选择了信任缓存, 这里将不会被调用,
            // 但是 onFinished 总会被调用.
            //bug，每次都会调用，添加判断
            this.result = result;

        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {

            hasError = true;
            String message = ex.getMessage();

            if (ex instanceof HttpException) { // 网络错误
                HttpException httpEx = (HttpException) ex;
                int responseCode = httpEx.getCode();
                String responseMsg = httpEx.getMessage();
                String errorResult = httpEx.getResult();

                message = responseMsg;
                // ...

            } else { // 其他错误
                // ...
            }

//            if (null != mCallback) {
//                mCallback.onFailure(mRequestCode, mRequestUrl, message);
//            }

            this.errMessage = message;
        }

        private void postSuccess(String result) {
            if (null != mCallback) {
                mCallback.onSuccess(mRequestCode, mRequestUrl, result);
            }
        }

        private void postError(String message) {
            if (null != mCallback) {
                mCallback.onFailure(mRequestCode, mRequestUrl, message);
            }
        }

        @Override
        public void onCancelled(CancelledException cex) {
            if (null != mCallback) {
                mCallback.onCancelled(mRequestCode, mRequestUrl, cex.getMessage());
            }
        }


        @Override
        public void onFinished() {

//            if (!hasError && result != null) {
//                // 成功获取数据
//                String str = result;
//
//                if (null != mCallback) {
//                    mCallback.onSuccess(mRequestCode, mRequestUrl, str);
//                }
//            }


            if (!hasError) {

                //如果只是用缓存，如果缓存存不存在都直接返回
                if (CacheProxy.ONLY == mCacheProxy) {
                    postSuccess(cacheResult);
                } else{
                    //如果结果不为空，返回成功
                    if (!TextUtils.isEmpty(result)) {
                        postSuccess(result);
                    } else {
                        //如果缓存策略为auto，如果缓存不为空，返回缓存数据
                        if (useCache()
                                && CacheProxy.AUTO == mCacheProxy
                                && !TextUtils.isEmpty(cacheResult)) {
                            postSuccess(cacheResult);
                        } else {
                            //否则，失败
                            postError("获取数据失败，请检查网络或稍后再试");
                        }
                    }
                }

            } else {
                //请求失败了，使用缓存
                if (useCache() && CacheProxy.AUTO == mCacheProxy
                        && !TextUtils.isEmpty(cacheResult)){
                    postSuccess(cacheResult);
                }else
                postError(errMessage);
            }

            if (mCallback instanceof HttpProgressCallback) {
                ((HttpProgressCallback) mCallback).onFinished(mRequestUrl);
            }
        }
    }

    class DownloadCallback implements Callback.CommonCallback<File>, Callback.ProgressCallback<File> {

        String mDownloadUrl;
        HttpCallback mCallback;

        public DownloadCallback(String downloadUrl, HttpCallback callback) {
            mDownloadUrl = downloadUrl;
            mCallback = callback;
        }

        @Override
        public void onWaiting() {

        }

        @Override
        public void onStarted() {
            if (mCallback instanceof HttpProgressCallback) {

                ((HttpProgressCallback) mCallback).onStarted(mDownloadUrl);
            }
        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {
            if (mCallback instanceof HttpProgressCallback) {

                ((HttpProgressCallback) mCallback).onUpdateProgress(mDownloadUrl, total, current);
            }
        }

        @Override
        public void onSuccess(File file) {
            if (null != mCallback) {
                mCallback.onSuccess(0, mDownloadUrl, file.getAbsolutePath());
            }
        }

        @Override
        public void onError(Throwable throwable, boolean b) {
            if (null != mCallback) {
                mCallback.onFailure(0, mDownloadUrl, throwable.getMessage());
            }
        }

        @Override
        public void onCancelled(CancelledException e) {
            if (null != mCallback) {
                mCallback.onCancelled(0, mDownloadUrl, e.getMessage());
            }
        }

        @Override
        public void onFinished() {
            if (mCallback instanceof HttpProgressCallback) {
                ((HttpProgressCallback) mCallback).onFinished(mDownloadUrl);
            }
        }
    }

    class Task implements Cancelable {

        Callback.Cancelable mCancelable;

        public Task(Callback.Cancelable call) {
            mCancelable = call;
        }

        @Override
        public void cancel() {
            if (!isCanceled()) {
                mCancelable.cancel();
            }
            mCancelable = null;
        }

        @Override
        public boolean isCanceled() {
            return null == mCancelable || mCancelable.isCancelled();
        }
    }
}