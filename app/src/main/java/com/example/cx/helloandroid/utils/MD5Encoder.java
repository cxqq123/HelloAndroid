package com.example.cx.helloandroid.utils;

import java.security.MessageDigest;

/**
 * Created by cx on 2017/8/24.
 */

public class MD5Encoder {

    //将字符串进行MD5加密
    public static String encode(String string) throws Exception {
        byte[] hash= MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        StringBuilder hex=new StringBuilder(hash.length * 2);
        for(byte b:hash){
            if((b & 0xFF)<0x10){
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
