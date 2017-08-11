package com.example.cx.helloandroid.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cx.helloandroid.model.ModelBook;
import com.example.cx.helloandroid.model.ModelPen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cx on 2017/8/7.
 */

public class DBManager {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        dbHelper=DBHelper.getInstance(context);
        db=dbHelper.getWritableDatabase();
    }

    ////////////////////////////tb_book操作//////////////////////////////
    //TODO 根据bookID 查询是否有这本书
    public boolean hasBook(int bookID){
        Cursor c=db.rawQuery("select * from tb_Book where bookID=?",new String[]{bookID+" "});
        if(c!=null && c.moveToNext()){
            c.close();
            return true;
        }else{
            c.close();
            return false;
        }
    }

    //TODO 根据bookID添加一本书
    public synchronized void AddBookByID(int bookID){
        db.execSQL("insert into tb_book (bookID) values(?)",
                new Object[]{bookID});
    }

    //TODO 添加一本书
    public synchronized void AddBook(ModelBook book){
        if(book!=null){
            int bookID=book.bookID;
            String name=book.name;
            int price=book.price;
            String author=book.author;
            db.execSQL("insert into tb_book (bookID,name,price,author) values(?,?,?,?)",
                    new Object[]{bookID,name,price,author});
        }
    }

    //TODO 获取查询到的书
    public ModelBook getDefaultBook(String name){
        ModelBook book=new ModelBook();
        //根据书的名字获取书的所有信息
        Cursor cursor=db.rawQuery("select * from tb_book where name= ?",new String[]{name});
        if(cursor!=null){
            book=new ModelBook();
            book.bookID=cursor.getInt(cursor.getColumnIndex("bookID"));
            book.name=cursor.getString(cursor.getColumnIndex("name"));
            book.price=cursor.getInt(cursor.getColumnIndex("price"));
            book.author=cursor.getString(cursor.getColumnIndex("author"));
        }
        cursor.close();
        return book;
    }

    //TODO 获取所有的书
    public List<ModelBook> getAllBook(){
        List<ModelBook> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from tb_book order by price desc",null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                ModelBook book=new ModelBook();
                book.bookID=cursor.getInt(cursor.getColumnIndex("bookID"));
                book.name=cursor.getString(cursor.getColumnIndex("name"));
                book.price=cursor.getInt(cursor.getColumnIndex("price"));
                book.author=cursor.getString(cursor.getColumnIndex("author"));
                list.add(book);
            }
            cursor.close();
        }
        return list;
    }

    //TODO 更新图书(根据ModelBook的整个字段)
    public synchronized void updateBook(ModelBook book){
        db.execSQL("update tb_book set bookID=?,name=?,price=?,author=?",
                new Object[]{book.bookID,book.name,book.price,book.author});
    }

    //TODO 根据book的ID来删除该本书
    public void deleteBook(int bookID){
        db.execSQL("delete from tb_book where bookID="+bookID);
    }
    /////////////////////////////////tb_book操作/////////////////////////////////////


    /////////////////////////////////tb_pen操作//////////////////////////////////////

    //TODO 根据笔的ID查看是否有这支笔
    public boolean hasPen(int penID) {
        Cursor cursor = db.rawQuery("select * from tb_pen where penID=?",new String[]{" "+penID});
        if(cursor!=null && cursor.moveToNext()){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    public void addPen(ModelPen modelPen){
        Cursor cursor=null;
        if(modelPen!=null){
            int penID=modelPen.penID;
            String name=modelPen.name;
            int price=modelPen.price;
            String size=modelPen.size;
            cursor=db.rawQuery("insert into tb_pen(penID,name,price,size)",new String[]{""+penID,name,""+price,size});
        }
        cursor.close();
    }

    public void updatePen(ModelPen modelPen){
        int penID=modelPen.penID;
        String name=modelPen.name;
        int price=modelPen.price;
        String size=modelPen.size;
        db.execSQL("update tb_pen set penID=? ,name=?,price=?,size=?",new Object[]{penID,name,price,size});
    }

    public ModelPen getDefaultPen(String penName){
        ModelPen pen=new ModelPen();
        Cursor cursor=db.rawQuery("select * from tb_pen where name=?",new String[]{penName});
        if(cursor!=null){
            pen.penID=cursor.getInt(cursor.getColumnIndex("penID"));
            pen.name=cursor.getString(cursor.getColumnIndex("name"));
            pen.price=cursor.getInt(cursor.getColumnIndex("price"));
            pen.size=cursor.getString(cursor.getColumnIndex("size"));
        }
        cursor.close();
        return pen;
    }

    public List<ModelPen> getAllPen(){
        List<ModelPen> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from tb_pen order by price desc",null);
        if(cursor!=null && cursor.moveToNext()){
            while(cursor.moveToNext()){
                ModelPen pen=new ModelPen();
                pen.penID=cursor.getInt(cursor.getColumnIndex("penID"));
                pen.name=cursor.getString(cursor.getColumnIndex("name"));
                pen.price=cursor.getInt(cursor.getColumnIndex("price"));
                pen.size=cursor.getString(cursor.getColumnIndex("size"));
                list.add(pen);
            }
        }
        cursor.close();
        return list;
    }

    public void deletePen(String name){
        db.execSQL("delete from tb_pen where name=?"+name);
    }

    ///////////////////////////tb_pen操作////////////////////////////////

}
