package com.cx.web.util;

import android.util.Log;

/**
 * Created by qk on 2016/11/14.
 */

public class LogUtil {
    private static final boolean isShowLog = true;
    public static final void Log(String Tag, String msg){
        if(isShowLog && msg != null){
            Log.i(Tag, msg);
        }
    }
}
