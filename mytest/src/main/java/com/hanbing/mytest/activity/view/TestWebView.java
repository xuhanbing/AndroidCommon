/**
 * 
 */
package com.hanbing.mytest.activity.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebStorage.QuotaUpdater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

		LinearLayout layout = new LinearLayout(this);

		LinearLayout layout1 = new LinearLayout(this);

		final EditText editText = new EditText(this);
		editText.setSingleLine();
		editText.setImeOptions(EditorInfo.IME_ACTION_GO);
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

				if (EditorInfo.IME_ACTION_GO == actionId) {

					load(editText.getText().toString());
					return true;
				}
				return false;
			}
		});


		Button button = new Button(this);
		button.setText("Go");
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				load(editText.getText().toString());
			}
		});

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -2, 1);
		layout1.addView(editText, params);
		layout1.addView(button);
		
		webView = new WebView(this);

		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(layout1);
		layout.addView(webView);

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
				LogUtils.e("onLoadResource:" + url);
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
//				return super.shouldOverrideUrlLoading(view, url);
				LogUtils.e("shouldOverrideUrlLoading:" + url);
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
		
		
		webView.setWebViewClient(webViewClient);
		webView.setWebChromeClient(webChromeClient);
		
		
		String url = null;

		Intent intent = getIntent();

		if (null != intent) {
			Uri data = intent.getData();

			if (null != data)
			url = data.getPath();

		}


		
		addJs();
		
		setContentView(layout);
	}

	private void addJs() {
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
						String json = "[{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}, {\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]";
						// 调用JS中的方法
						webView.loadUrl("javascript:show('" + json + "')");
					}
				});


			}
		}, "contact");
	}

	private void load(String url)
	{
		if (TextUtils.isEmpty(url)) {
			url =  "file:///android_asset/index.html";
			addJs();
		}
		webView.loadUrl(url);
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
