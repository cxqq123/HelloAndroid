package com.example.cx.helloandroid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by cx on 2017/8/4.
 */

public class ToastUtils {
    public static void makeTextShort(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void makeTextLong(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration)
    {
            Toast.makeText(context, message, duration).show();
    }
}
