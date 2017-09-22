package com.cx.web.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cx.web.model.ModelUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public class DBManager {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        dbHelper=DBHelper.getInstance(context);
        db=dbHelper.getWritableDatabase();
    }

    ////////////////////////////tb_url操作//////////////////////////////
    public boolean hasUrl(String urlName){
        Cursor c=db.rawQuery("select * from tb_url where urlName=?",new String[]{urlName});
        if(c!=null && c.moveToNext()){
            c.close();
            return true;
        }else{
            c.close();
            return false;
        }
    }

    public synchronized void AddUrlByName(int urlName){
        db.execSQL("insert into tb_url (urlName) values(?)",
                new Object[]{urlName});
    }

    public synchronized void AddUrl(ModelUrl url){
        //若url不为null,并查找urlName 是否已存在
        if(url!=null && !hasUrl(url.urlName)){
            String name=url.urlName;
            byte[] body=url.urlBody;  //字节数组存入数据库中
            int type=url.urlType;
            String mimeType=url.mimeType;
            db.execSQL("insert into tb_url (urlName,urlBody,urlType,mimeType) values(?,?,?,?)",
                    new Object[]{name,body,type,mimeType});
        }
    }

    public synchronized ModelUrl getDefaultUrl(String name){
        ModelUrl modelUrl = null;
        Cursor cursor=db.rawQuery("select * from tb_url where urlName=?" , new String[]{name});
        if(cursor!=null){
            modelUrl=new ModelUrl();
            while (cursor.moveToNext()){
                modelUrl.urlName=cursor.getString(cursor.getColumnIndex("urlName"));
                modelUrl.urlBody=cursor.getBlob(cursor.getColumnIndex("urlBody"));  //获取字节数组流数组
                modelUrl.urlType=cursor.getInt(cursor.getColumnIndex("urlType"));
                modelUrl.mimeType=cursor.getString(cursor.getColumnIndex("mimeType"));
            }
        }
        cursor.close();
        return modelUrl;
    }

    public List<ModelUrl> getAllUrl(){
        List<ModelUrl> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from tb_url",null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                ModelUrl modelUrl=new ModelUrl();
                modelUrl.urlName=cursor.getString(cursor.getColumnIndex("urlName"));
                modelUrl.urlBody=cursor.getBlob(cursor.getColumnIndex("urlBody"));
                modelUrl.urlType=cursor.getInt(cursor.getColumnIndex("urlType"));
//                modelUrl.mimeType=cursor.getString(cursor.getColumnIndex("mimeType"));
                list.add(modelUrl);
            }
            cursor.close();
        }
        return list;
    }

    public synchronized void updateUrl(ModelUrl url){
        db.execSQL("update tb_url set urlName=?",
                new Object[]{url.urlName});
    }

    public void deleteUrl(int urlID){
        db.execSQL("delete from tb_url where urlID="+urlID);
    }
    /////////////////////////////////tb_book操作/////////////////////////////////////
}
