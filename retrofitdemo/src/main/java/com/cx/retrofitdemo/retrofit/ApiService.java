package com.cx.retrofitdemo.retrofit;

import com.cx.retrofitdemo.mvp.mvp_news.model.NewsModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/6.
 */

public interface ApiService {

    //Api接口来自于京东万象 的数据
    //https://way.jd.com/jisuapi/get?channel=头条&num=10&start=0&appkey=您申请的APPKEY
    //新闻
    @GET("get")
    Observable<NewsModel> LoadNewsData(@Query("channel") String type,@Query("num") String num,@Query("start") int start,@Query("appkey") String key);
}
