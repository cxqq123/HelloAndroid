package com.cx.jnidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    static{
        //加载动态库,名字和c文件名相同
        System.loadLibrary("hello");
    }

    //声明本地方法
    public native String helloWorld();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
