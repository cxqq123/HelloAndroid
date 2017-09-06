package com.cx.retrofitdemo.mvp.mvp_news.view;

import android.os.Bundle;
import android.view.View;

import com.cx.retrofitdemo.R;
import com.cx.retrofitdemo.base.BaseFragment;

/**
 * Created by Administrator on 2017/9/6.
 */

public class NewsFragment extends BaseFragment{
    private String  strtype="";

    public static NewsFragment newInstance(String title) {
        NewsFragment fragment = new NewsFragment();
        Bundle b = new Bundle();
        b.putString("msg", title);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View initView() {
        view=View.inflate(context, R.layout.fragment_news,null);
        return view;
    }

    @Override
    public void initData() {

    }
}
