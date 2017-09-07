package com.cx.retrofitdemo.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/6.
 */

public class ApiClient {
    //京东万象API Key 76b555c21ae45af6754f14a486ba935d
    public static final String baseUrl="https://way.jd.com/jisuapi/";
    public static Retrofit mRetrofit_New;

    public static Retrofit retrofit(){
        if(mRetrofit_New==null){
            //启动Retrofit
            OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
            mRetrofit_New=new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(okHttpClient)
                            .build();
        }
        return mRetrofit_New;
    }

}
