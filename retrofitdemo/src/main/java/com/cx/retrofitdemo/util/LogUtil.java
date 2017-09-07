package com.cx.retrofitdemo.util;

import android.util.Log;

/**
 * Created by Administrator on 2017/9/7.
 */

public class LogUtil {
    /***
     *	带tag的日志
     * @param msg
     */
    public static void logByTag( String msg){
        String tag="cx";
        Log.i(tag, msg);
    }
}
