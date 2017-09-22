package com.cx.web.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/11.
 */

public class DBHelper extends SQLiteOpenHelper{

    private final static String mDBName="cx.db";
    private final static int mDBVersion=1;
    private static DBHelper mInstance=null;  //采用单利模式获取实例

    //创建一张书表
    private String sql_CreateTable_Url="Create table [tb_Url]("
            +"[urlID] integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            +"[urlName] varchar DEFAULT '',"
            +"[urlBody] BLOB,"
            +"[urlType] integer,"
            +"[mimeType] varchar"
            + ")";

    Context mContext=null;

    private DBHelper(Context context){
        super(context,mDBName,null,mDBVersion);
        mContext=context;
    }

    public static DBHelper getInstance(Context context){
        if(mInstance==null){
            mInstance=new DBHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.v("CX","cxDB onCreate");
        //创建表
        sqLiteDatabase.execSQL(sql_CreateTable_Url);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
