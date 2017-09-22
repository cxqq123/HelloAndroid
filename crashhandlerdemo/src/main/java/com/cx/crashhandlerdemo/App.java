package com.cx.crashhandlerdemo;

import android.app.Application;

/**
 * Created by cx on 2017/9/16.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化CrashHandler
        CrashHandler crashHandler=CrashHandler.getInstance();
        crashHandler.init(this);

    }
}
