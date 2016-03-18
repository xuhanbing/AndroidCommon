package com.hanbing.mytest.view;

/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014�?�?�?
 * Time : 下午5:32:07
 */

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.common.util.GeoUtils;


/**
 * MapView.java 
 * @author hanbing 
 * @date 2014�?�?�?
 * @time 下午5:32:07
 */
public class MapView
{
    WebView webView; //
    String latitude;      
    String longitude;
    String info;
    String icon;     //图片名称，需放在assets下面
    boolean showFootMap;  //是否显示足迹
    /**
     * 加载地图
     * @param webView  
     * @param latitude     纬度
     * @param longitude     经度
     * @param info    相关信息
     * @param icon    是否使用自定义图标，�?��在assets下面，如果为空使用默认图�?
     */
    
    public MapView(WebView webView, String latitude, String longitude, String info, String icon)
    {
        this.webView = webView;
        this.latitude = latitude;
        this.longitude = longitude;
        this.info = info;
        this.icon = TextUtils.isEmpty(icon) ? "location_small.png" : icon;
        this.showFootMap = true;
        
        init();
    }
    
    public MapView(WebView webView, String latitude, String longitude, String info, String icon, boolean showFootMap)
    {
        this.webView = webView;
        this.latitude = latitude;
        this.longitude = longitude;
        this.info = info;
        this.icon = TextUtils.isEmpty(icon) ? "location_small.png" : icon;
        this.showFootMap = showFootMap;
        
//        init();
    }
    
    private void init()
    {
      //禁用web view 的滚动条，否则右侧会有白�?
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        
//        //显示位置
//        final String centerURL = "javascript:centerAtWithIcon(\""
//                    + latitude+ "\",\""
//                    + longitude+ "\",\""
//                    + info + "\",\""
//                    + icon
//                    + "\")";
//        
//        String url = null;  
//        if(showFootMap && latitude !="" && longitude !=""){
//            url = "javascript:centerAt(\""
//                    + latitude+ "\",\""
//                    + longitude+ "\",\""
//                    + info
//                    + "\")";
//        }else{
//            url ="javascript:mapInit2()";
//        }
//        final String centerURL = url;
        
        
        //页面加载后调用JS中的centerAt函数
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
                
//              webView.loadUrl(centerURL);
            }
          });

        //google map 地址
        webView.setVisibility(View.VISIBLE);
        
        webView.addJavascriptInterface(new SendToJs(), "android");
    }
    
    public void load()
    {
        String MAP_URL = "file:///android_asset/map.html";//"file:///android_asset/citymap.html";
//        MAP_URL = "http://www.sohu.com/";
        webView.loadUrl(MAP_URL);   
    }
    
    
    class SendToJs  
    {
     private int[] scale = { 5000000,2000000, 1000000, 500000 , 200000, 100000, 50000, 25000, 20000, 10000,5000, 2000, 1000, 500, 200,100,50,20,1}; 
     private Double minLati,maxLati,minLongi,maxLongi ;
        public String  getLatCenter()  
        {  
            Double dl = 0.0;
            if(!"".equals(latitude)){
                String[] latArr  = latitude.split("_");
                
                minLati = maxLati = Double.parseDouble(latArr[0]);
                for(int i=0 ;i<latArr.length;i++){
                    Double dli = Double.parseDouble(latArr[i]);
                    dl += dli;
                    if(dli<minLati){
                        minLati = dli ;
                    }
                    if(maxLati<dli){
                        maxLati = dli ;
                    }
                }
                dl = dl/(latArr.length);
            }
            else
            {
                dl = 30.0;
            }
            
            return dl.toString();
        }  
        
        public String  getLngCenter()  
        {  
            Double dl = 0.0;
            if(!"".equals(longitude)){
                String[] latArr  = longitude.split("_");
                
                minLongi = maxLongi = Double.parseDouble(latArr[0]);
                for(int i=0 ;i<latArr.length;i++){
                    Double dli = Double.parseDouble(latArr[i]) ;
                    dl += dli;
                    if(dli<minLongi){
                        minLongi = dli ;
                    }
                    if(maxLongi<dli){
                        maxLongi = dli ;
                    }
                }
                dl = dl/(latArr.length);
            }
            else
            {
                dl = 120.0;
            }
            
            return dl.toString();
        } 
        
        public String getLatitude()
        {
            return latitude;
        }
        
        public String getLongitude()
        {
            return longitude;
        }
        
        public int getScale(){
            System.out.println(latitude);
            System.out.println(longitude);
            String[] latArr  = latitude.split("_");
            if(minLati==maxLati && minLongi == maxLongi && latArr.length ==0)
                return 1 ;
            double distance = 1000* GeoUtils.calcDistance(minLati, minLongi, maxLati, maxLongi);
            int i = 0;
            //真实距离和数组中相近的两个�?循环比较，以小�?为准，得出规定好的比例尺数�?赋�?给dis  
            for (int j = 1; j < scale.length; j++) {  
                if (scale[j - 1] >= distance  
                        && distance > scale[j]) {  
                    i = j + 1 ;  
                    break;  
                }  
            }  
            
            if (latArr.length ==1)
            {
                i = 10;
            }
            
            return i ;
        }
        
        public int showFootMap()
        {
            return showFootMap ? 1 : 0;
        }
        
        public String getIcon()
        {
            return icon;
        }
        
        public String getInfo()
        {
            return info;
        }
        
    } 
}
