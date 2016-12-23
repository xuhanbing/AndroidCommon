package com.hanbing.retrofit_rxandroid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by hanbing on 2016/8/30
 */
public class RetrofitClient {

    static RetrofitClient mRetrofitClient = new RetrofitClient();
    ApiService mApiService;
    public static ApiService getApiService() {
        return mRetrofitClient.mApiService;
    }

    private RetrofitClient(){

        GsonBuilder gsonBuilder = new GsonBuilder();

        retrofit2.Retrofit build = new retrofit2.Retrofit.Builder()
                .baseUrl("http://www.baidu.com/")
                .client(new OkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(new MyConferter())
                .build();

        mApiService = build.create(ApiService.class);
    }

    class MyConferter extends Converter.Factory{


        GsonConverterFactory mGsonConverterFactory = GsonConverterFactory.create();
        ScalarsConverterFactory mScalarsConverterFactory = ScalarsConverterFactory.create();


        Gson gson = new Gson();

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

            Converter<ResponseBody, ?> responseBodyConverter = mScalarsConverterFactory.responseBodyConverter(type, annotations, retrofit);
            if (null != responseBodyConverter) {
                return responseBodyConverter;
            } else {
                return mGsonConverterFactory.responseBodyConverter(type, annotations, retrofit);
            }
        }
    }

    final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override public T convert(ResponseBody value) throws IOException {

            String json = value.string();

            return adapter.fromJson(json);
//            JsonReader jsonReader = gson.newJsonReader(value.charStream());
//            try {
//                return adapter.read(jsonReader);
//            } finally {
//                value.close();
//            }
        }
    }
}
