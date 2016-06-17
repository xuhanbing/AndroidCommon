/**
 *  
 */
package com.common.http.callback;

import java.io.InputStream;

/**
 * 一个普通的请求回调
 * @author hanbing
 * @date 2015-8-24
 */
public abstract class HttpSimpleCallback implements HttpCallback {

    @Override
    public void onCancelled(int requestCode, String requestUrl, String msg) {

    }
}
