package com.hanbing.mytest;

import android.app.Application;
import android.content.Intent;

import org.xutils.x;


/**
 */
public class MyTestApplication extends Application {

    private static MyTestApplication mInstance;
    /**
     * 
     */
    public MyTestApplication() {
        // TODO Auto-generated constructor stub
        mInstance = this;



    }
    
    public static MyTestApplication getApplication()
    {
        return mInstance;
    }
    
    public void restart()
    {
    	System.out.println("restart");
    	//����app
        Intent i = getBaseContext().getPackageManager()  
                .getLaunchIntentForPackage(getBaseContext().getPackageName());  
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
        startActivity(i);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
    }
}
