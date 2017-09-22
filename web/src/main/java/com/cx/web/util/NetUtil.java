package com.cx.web.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by qk on 2017/5/24.
 */

public class NetUtil {
    //私有化构造方法
    private NetUtil(){
    }

    /**
     * 获取当前网络类型
     * @return  //返回值 -1：没有网络 1：WIFI网络 2：wap网络 3：net网络
     */
    public static int getNetworkState(Context mContext)
    {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo==null)
        {
            return netType;
        }
        int nType = networkInfo.getType();
        if(nType==ConnectivityManager.TYPE_MOBILE)
        {
            if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet"))
            {
                netType = 3;
            }
            else
            {
                netType = 2;
            }
        }
        else if(nType==ConnectivityManager.TYPE_WIFI)
        {
            netType = 1;
        }
        return netType;
    }
}
