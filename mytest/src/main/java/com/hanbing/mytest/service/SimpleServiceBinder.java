package com.hanbing.mytest.service;

import android.os.Binder;

/**
 * Created by hanbing on 2016/6/1.
 */
public  class SimpleServiceBinder extends Binder {

    SimpleService service;

    public SimpleServiceBinder(SimpleService service) {
        this.service = (service);
    }
    public void setService(SimpleService service) {
        this.service = service;
    }
    public SimpleService getService() {
        return service;
    }
}
