package com.cx.retrofitdemo.util;

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

}
