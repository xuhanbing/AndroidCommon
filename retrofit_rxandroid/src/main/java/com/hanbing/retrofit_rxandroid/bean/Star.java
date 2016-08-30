package com.hanbing.retrofit_rxandroid.bean;

import android.text.TextUtils;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hanbing on 2016/8/30
 */
public class Star implements Serializable{



    String alt;

    Images avatars;

    String name;

    String id;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Images getAvatars() {
        return avatars;
    }

    public void setAvatars(Images avatars) {
        this.avatars = avatars;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
