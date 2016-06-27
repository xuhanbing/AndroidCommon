package com.hanbing.dianping.common.city;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hanbing on 2016/6/15.
 */
public class Province {

    public static class ProvinceList {
        List<Province> provinces;
    }

    String name;

    @SerializedName(value = "cities", alternate = {"city"})
    List<City> cities;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static List<Province> getProvincesFromJson(String json) {

        ProvinceList provinceList = new Gson().fromJson(json, ProvinceList.class);

        return null == provinceList ? null : provinceList.provinces;
    }
}
