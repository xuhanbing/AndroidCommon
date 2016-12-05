package com.hanbing.demo;

import android.app.Application;
import android.graphics.Bitmap;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

/**
 * Created by hanbing on 2016/12/2
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();



        {
            ImageLoader.getInstance().init( ImageLoaderConfiguration.createDefault(this));
        }


        Fresco.initialize(this);

        Picasso.Builder builder = new Picasso.Builder(this)
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .loggingEnabled(true)
                .indicatorsEnabled(false);

        Picasso.setSingletonInstance(builder.build());
    }
}
