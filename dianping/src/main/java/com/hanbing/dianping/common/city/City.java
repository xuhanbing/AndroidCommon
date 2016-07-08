package com.hanbing.dianping.common.city;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hanbing on 2016/6/15.
 */
public class City {

    public class CityList {
        List<City> cities;
    }

    String name;
    String pinyin;
    String index;
    String zip;

    @SerializedName(value = "areas", alternate = {"area"})
    List<String> areas;

    public List<String> getAreas() {
        return areas;
    }

    public void setAreas(List<String> areas) {
        this.areas = areas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public static List<City> getCitiesFromJson(String json) {
        CityList cityList = new Gson().fromJson(json, CityList.class);

        return null == cityList ? null : cityList.cities;
    }

    public String getIndex() {
        if (TextUtils.isEmpty(pinyin))
            return null;

        return pinyin.substring(0, 1).toUpperCase();

    }


}
