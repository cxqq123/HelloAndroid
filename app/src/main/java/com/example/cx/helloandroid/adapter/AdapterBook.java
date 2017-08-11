package com.example.cx.helloandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.cx.helloandroid.model.ModelBook;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class AdapterBook extends BaseAdapter{
    public Context mContext;
    public List<ModelBook> list;

    public AdapterBook(Context context,List<ModelBook> list){
        this.mContext=context;
        this.list=list;
    }

    public void bindData(List<ModelBook> books){
         this.list=books;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){

        }
        return null;
    }
}
