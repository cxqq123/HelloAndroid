package com.example.cx.helloandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/8/4.
 */

public  abstract class BaseActivity extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        bindData();
        setListener();
    }

    public abstract int getLayoutId();
    public abstract void bindData();
    public abstract void initView();
    public abstract void setListener();

    public void setContentView(int layoutResId){
        super.setContentView(layoutResId);
    }
}
