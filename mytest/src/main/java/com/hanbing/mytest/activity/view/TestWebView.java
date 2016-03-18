/**
 * 
 */
package com.hanbing.mytest.activity.view;

import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebStorage.QuotaUpdater;
import com.common.util.LogUtils;
import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-10-9
 */
public class TestWebView extends BaseActivity {

	WebView webView;
	/**
	 * 
	 */
	public TestWebView() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		webView = new WebView(this);
		
		WebSettings webSettings = webView.getSettings();  
	        webSettings.setJavaScriptEnabled(true); 
//	        //启用数据库   
//	        webSettings.setDatabaseEnabled(true);     
//	        String root = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ConstantValues.FOLDER_NAME
//	        	+ "/Web";
//	        String dir = root;
//	        new File(dir).mkdirs();
//	        
//	        //设置数据库路径   
//	        webSettings.setDatabasePath(dir);
//	        //使用localStorage则必须打开   
//	        webSettings.setDomStorageEnabled(true);   
//	        
//	        //启用地理定位   
//	        webSettings.setGeolocationEnabled(true);   
//	        //设置定位的数据库路径   
//	        webSettings.setGeolocationDatabasePath(dir);   
//	        
//	        dir = root + "/cache";
//	        new File(dir).mkdirs();
//	        
//	        //开启应用程序缓存   
//	        webSettings.setAppCacheEnabled(true);   
//	        //设置应用缓存的路径   
//	        webSettings.setAppCachePath(dir);   
//	        //设置缓存的模式   
//	        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);   
//	        //设置应用缓存的最大尺寸   
//	        webSettings.setAppCacheMaxSize(1024*1024*8);  
	        
		WebViewClient webViewClient = new WebViewClient(){
			@Override
			public void onLoadResource(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onLoadResource(view, url);
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
//				return super.shouldOverrideUrlLoading(view, url);
				view.loadUrl(url);
				return true;
			}
			
		};
		
		
		WebChromeClient webChromeClient = new WebChromeClient() {
		    @Override
		    public boolean onJsAlert(WebView view, String url,
		            String message, JsResult result) {
		        // TODO Auto-generated method stub
		        return super.onJsAlert(view, url, message, result);
		    }
		    
		    @Override
		    public boolean onConsoleMessage(
		            ConsoleMessage consoleMessage) {
		        // TODO Auto-generated method stub
//			Log.i("onConsoleMessage", "" + consoleMessage.message()
//				+ ","+ consoleMessage.lineNumber() + ", "  + consoleMessage.sourceId());
		        return super.onConsoleMessage(consoleMessage);
		    }
		    
		    @Override
		    @Deprecated
		    public void onExceededDatabaseQuota(String url,
		            String databaseIdentifier, long quota,
		            long estimatedDatabaseSize, long totalQuota,
		            QuotaUpdater quotaUpdater) {
		        // TODO Auto-generated method stub
			Log.i("onExceededDatabaseQuota", url + "," + databaseIdentifier);
		        super.onExceededDatabaseQuota(url, databaseIdentifier, quota,
		        	estimatedDatabaseSize, totalQuota, quotaUpdater);
		    }
		    
		    @Override
		    public void onGeolocationPermissionsShowPrompt(
		            String origin, Callback callback) {
		        // TODO Auto-generated method stub
			callback.invoke(origin, true, false);   
		        super.onGeolocationPermissionsShowPrompt(origin, callback);
		    }
		   
		};
		
		
//		webView.setWebViewClient(webViewClient);
//		webView.setWebChromeClient(webChromeClient);
		
		
		String url = "file:///android_asset/index.html";
		
		webView.loadUrl(url);
		
		webView.addJavascriptInterface(new Object() {  
		    
	            public void show() {  
	                runOnUiThread(new Runnable() {  
	                    @Override  
	                    public void run() {  
	                        Log.i("通知", "调用了该方法哦");  
	                          
	                        webView.loadUrl("javascript:show('hello world')");  
	                    }  
	                });  
	  
	            }  
	            
	            public void print(final String string)
	            {
	        	runOnUiThread(new Runnable() {
			    public void run() {
				Log.i("", "print from js : " + string);
			    }
			});
	            }
	  
	        }, "test");  
		
		
		
		// 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
		webView.addJavascriptInterface(new Object() {
		 // JavaScript调用此方法拨打电话
			public void call(final String phone) {
			    runOnUiThread(new  Runnable() {
				public void run() {
				    LogUtils.e("phone:" + phone);
//				    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
//					    + phone)));
				}
			    });
			    
			}

			// Html调用此方法传递数据
			public void showcontacts() {
			    
			    runOnUiThread(new Runnable() {
				public void run() {
				    String json = "[{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]";
				    // 调用JS中的方法
				    webView.loadUrl("javascript:show('" + json + "')");
				}
			    });
			    
			    
			}
		}, "contact");
		
		setContentView(webView);
	}
	
	
	private final class Contact {
		// JavaScript调用此方法拨打电话
		public void call(final String phone) {
		    runOnUiThread(new  Runnable() {
			public void run() {
			    LogUtils.e("phone:" + phone);
//			    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
//				    + phone)));
			}
		    });
		    
		}

		// Html调用此方法传递数据
		public void showcontacts() {
		    
		    runOnUiThread(new Runnable() {
			public void run() {
			    String json = "[{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]";
			    // 调用JS中的方法
			    webView.loadUrl("javascript:show('" + json + "')");
			}
		    });
		    
		    
		}
	    }

}
