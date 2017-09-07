package com.cx.retrofitdemo.mvp.mvp_news.presenter;

import com.cx.retrofitdemo.base.BasePresenter;
import com.cx.retrofitdemo.mvp.mvp_news.model.NewsModel;
import com.cx.retrofitdemo.mvp.mvp_news.view.NewsView;
import com.cx.retrofitdemo.retrofit.ApiClient;
import com.cx.retrofitdemo.retrofit.ApiService;
import com.cx.retrofitdemo.util.LogUtil;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/9/7.
 */

public class NewsPresenter extends BasePresenter<NewsView>{


    public NewsPresenter(NewsView view){
        attachView(view);
        apiService= ApiClient.retrofit().create(ApiService.class);
    }

    public void loadNewData(String type,String num,int start,String key){
        addSubscribe(apiService.LoadNewsData(type, num, start, key), new Subscriber<NewsModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.logByTag("请求失败");
                mvpView.onError(e);
            }

            @Override
            public void onNext(NewsModel newsModel) {
                LogUtil.logByTag("请求成功");
                mvpView.onSuccess(newsModel);
            }
        });
    }
}
