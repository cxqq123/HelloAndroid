package com.example.cx.helloandroid.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.adapter.AdapterListView;
import com.example.cx.helloandroid.view.MyListView;
import com.example.cx.helloandroid.view.PowerImageView;

import java.util.ArrayList;
import java.util.List;

public class PhotoViewActivity extends AppCompatActivity {

    private List<PowerImageView> powerData;
    private MyListView myListView;
    private AdapterListView adapterListView;

    private List<String> tvData;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        mContext=PhotoViewActivity.this;
        myListView = (MyListView) findViewById(R.id.myListView);
        initData();
        adapterListView=new AdapterListView(mContext,powerData,tvData);
        myListView.setAdapter(adapterListView);

    }

    public void initData(){
        powerData=new ArrayList<>();
        tvData=new ArrayList<>();
        for(int i=0;i<12;i++){
            powerData.add(new PowerImageView(mContext));
            tvData.add(new String("content"+i));
        }
    }
}
