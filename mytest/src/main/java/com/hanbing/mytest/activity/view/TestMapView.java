/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��6��4�� 
 * Time : ����10:13:12
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.mytest.view.MapView;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


/**
 * TestMapView.java 
 * @author hanbing 
 * @date 2014��6��4�� 
 * @time ����10:13:12
 */
public class TestMapView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_mapview);
        WebView webView = (WebView) findViewById(R.id.webview);
        
//        String lat = "36.66182_37.47878_39.89218_39.15503_";
//        String lng = "116.8592_116.2904_116.3307_117.1085_";
//        String city = "Jinan_Dezhou_Beijing_Tianjin_";
        
        String lat = "36.66182";
        String lng = "116.8592";
        String city = "Jinan";
        
//        String lat = "36.66182_37.47878_39.89218_39.15503";
//        String lng = "116.8592_116.2904_116.3307_117.1085";
//        String city = "Jinan_Dezhou_Beijing_Tianjin";
        
        new MapView(webView, lat, lng, city, "").load();
        
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setHorizontalScrollBarEnabled(true);
//        webView.setVerticalScrollBarEnabled(false);
//        
//        
//        
//        //ҳ����غ����JS�е�centerAt����
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url){
//                
////              webView.loadUrl(centerURL);
//            }
//          });
//
//        //google map ��ַ
//        webView.loadUrl("http://www.sohu.com");
        
        
        
    }


    
}
