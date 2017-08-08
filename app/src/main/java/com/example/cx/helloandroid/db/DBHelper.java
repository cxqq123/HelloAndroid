package com.example.cx.helloandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cx on 2017/8/7.
 */

public class DBHelper extends SQLiteOpenHelper{

    private final static String mDBName="cx.db";
    private final static int mDBVersion=1;
    private static DBHelper mInstance=null;  //采用单利模式获取实例

    //创建一张书表
    private String sql_CreateTable_Book="Create table [tb_book]("
            +"[bookID] integer NOT NULL DEFAULT 0,"
            +"[name] varchar NOT NULL DEFAULT '',"
            +"[price] integer NOT NULL DEFAULT 0,"
            +"[author] varchar NOT NULL DEFAULT ''"
            + ")";
    //创建一张笔表
    private String sql_CreateTable_Pen="Create table [tb_pen]("
            +"[penID] integer NOT NUll DEFAULT 0,"
            +"[name] varchar NOT NULL DEFAULT '',"
            +"[price] integer NOT NULL DEFAULT 0,"
            +"[size] varchar NOT NULL DEFAULT ''"
            +")";

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
        sqLiteDatabase.execSQL(sql_CreateTable_Book);
        sqLiteDatabase.execSQL(sql_CreateTable_Pen);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //更新数据库版本
    }

    //更新表结构的方法
    protected void upgradeTables(SQLiteDatabase db ,String tableName,String[] columns){

    }

    //获取数据库表中的所有列
    protected String[] getColumnNames(SQLiteDatabase db,String tableName){
        return null;
    }
}
