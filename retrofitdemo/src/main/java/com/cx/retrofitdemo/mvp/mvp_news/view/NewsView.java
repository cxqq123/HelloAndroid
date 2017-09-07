package com.cx.retrofitdemo.mvp.mvp_news.view;

import com.cx.retrofitdemo.mvp.mvp_news.model.NewsModel;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface NewsView {
    void onSuccess(NewsModel newsModel);
    void onError(Throwable e);
}
