package com.common.http;

import com.common.http.callback.HttpSimpleCallback;

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
public class XHttpRequest extends HttpRequestBase {

    Callback.Cancelable mCancelable;
    /*
	 * (non-Javadoc)
	 *
	 * @see com.ming.common.http.HttpRequestBase#doRequest(int,
	 * java.lang.String, java.util.Map, java.util.Map, java.util.Map,
	 * com.ming.common.http.HttpRequestCallback)
	 */
    @Override
    public  void doRequest(final int requestCode, final String requestUrl, Map<String, String> headers,
                           Map<String, String> params, Map<String, Object> uploads, final HttpSimpleCallback callback) {
        // TODO Auto-generated method stub


        RequestParams requestParams = new RequestParams(requestUrl);
        // 默认缓存存活时间, 单位:毫秒.(如果服务没有返回有效的max-age或Expires)
        requestParams.setCacheMaxAge(1000 * 60);

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

                Object value = next.getValue();
                if (null != value)
                {
                    if (value instanceof String)
                    {
                        File file = new File(value.toString());
                        if (file.exists())
                            requestParams.addParameter(next.getKey(), file);
                    } else if (value instanceof InputStream
                            || value instanceof byte[])
                    {
                        requestParams.addParameter(next.getKey(), value);
                    }
                }

            }
        }


        Callback.CacheCallback<String> commonCallback = new Callback.CacheCallback<String>() {

            private boolean hasError = false;
            private String result = null;

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
                this.result = result;
                return false; // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
            }

            @Override
            public void onSuccess(String result) {
                // 注意: 如果服务返回304或 onCache 选择了信任缓存, 这里将不会被调用,
                // 但是 onFinished 总会被调用.
                this.result = result;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasError = true;
                String message = "";

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

                if (null != callback) {
                    callback.onFailure(requestCode, requestUrl, message);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (null != callback) {
                    callback.onCancelled(requestCode, requestUrl, cex.getMessage());
                }
            }

            @Override
            public void onFinished() {
                if (!hasError && result != null) {
                    // 成功获取数据
//					Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();

                    String str = result;

                    if (null != callback) {
                        callback.onSuccess(requestCode, requestUrl,str);
                    }
                }
            }
        };
        Callback.Cancelable cancelable
                // 使用CacheCallback, xUtils将为该请求缓存数据.
                = HttpMethod.POST == method
                ? x.http().post(requestParams, commonCallback)
                : x.http().get(requestParams, commonCallback);

        this.mCancelable = cancelable;
    }

    @Override
    public void cancelRequest() {
        if (null != mCancelable)
        {
            mCancelable.cancel();
        }
    }
}
