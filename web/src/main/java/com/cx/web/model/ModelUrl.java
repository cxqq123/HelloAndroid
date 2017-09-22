package com.cx.web.model;

/**
 * Created by Administrator on 2017/9/13.
 */

public class ModelUrl {

    //ID
    public String urlID;
    //Key
    public String urlName;
    //Body
    public byte[] urlBody;
    //Type
    public int urlType;     //0 表示css文件，1 表示js文件,2表示图片（.png,.jpg）3 表示html

    public String mimeType; //文件的格式的mimeType
}
