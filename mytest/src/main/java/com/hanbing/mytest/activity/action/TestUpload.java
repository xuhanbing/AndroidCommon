/**
 * 
 */
package com.hanbing.mytest.activity.action;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.common.util.FileUtils;
//import com.lidroid.xutils.HttpUtils;
//import com.lidroid.xutils.exception.HttpException;
//import com.lidroid.xutils.http.RequestParams;
//import com.lidroid.xutils.http.ResponseInfo;
//import com.lidroid.xutils.http.callback.RequestCallBack;
//import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-11-23
 */
public class TestUpload extends BaseActivity {

    String url = "http://192.168.1.101:8080/ServletTest/upload";
    /**
     * 
     */
    public TestUpload() {
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        
        
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        
        startActivityForResult(intent, 123);
    }
    
    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
	if (RESULT_OK == arg1
		&& 123 == arg0)
	{
	    if (null != arg2)
	    {
		Uri uri = arg2.getData();
		
		String path = FileUtils.getAbsPath(this, uri);
		
//		HttpUtils httpUtils = new HttpUtils();
//		RequestParams params = new RequestParams();
//		params.addBodyParameter("file1", new File(path));
//		RequestCallBack<String> callBack = new RequestCallBack<String>() {
//		    
//		    @Override
//		    public void onSuccess(ResponseInfo<String> responseInfo) {
//			// TODO Auto-generated method stub
//			LogUtils.e("success result=" + responseInfo.result);
//		    }
//		    
//		    @Override
//		    public void onFailure(HttpException error, String msg) {
//			// TODO Auto-generated method stub
//			LogUtils.e("failure result=" + msg);
//		    }
//		};
//		httpUtils.send(HttpMethod.GET, url, params, callBack);
	    }
	}
        super.onActivityResult(arg0, arg1, arg2);
    }
}
