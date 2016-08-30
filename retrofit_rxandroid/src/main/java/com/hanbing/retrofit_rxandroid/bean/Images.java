package com.hanbing.retrofit_rxandroid.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by hanbing on 2016/8/30
 */
public class Images implements Serializable{
    String small;
    String large;
    String medium;

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String get(){
        if (!TextUtils.isEmpty(large))
            return large;
        if (!TextUtils.isEmpty(medium))
            return medium;
        return small;
    }
}
