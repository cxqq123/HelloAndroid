package com.cx.retrofitdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/9/6.
 */

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
        initView();
        loadData();
    }

    //初始化变量
    protected abstract void initVars();
    //初始化界面
    protected abstract void initView();
    //加载数据
    protected abstract void loadData();
}
