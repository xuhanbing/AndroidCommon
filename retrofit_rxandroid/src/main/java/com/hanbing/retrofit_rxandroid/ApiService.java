package com.hanbing.retrofit_rxandroid;

import com.hanbing.retrofit_rxandroid.bean.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hanbing on 2016/8/30
 */
public interface ApiService {

    @GET("http://www.baidu.com/")
    public Call<String> getBaidu();

    @GET("https://api.douban.com/v2/movie/top250")
//    Call<Data> getSubjects(@Query("start") int start, @Query("count")int count);
    Observable<Data> getSubjects(@Query("start") int start, @Query("count")int count);

    @GET("https://api.douban.com/v2/movie/subject/{id}")
    Observable<String> getPhotos(@Path("id") String id);
}
