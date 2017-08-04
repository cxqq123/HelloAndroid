package com.example.cx.helloandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.cx.helloandroid.adapter.AdapterListView;
import com.example.cx.helloandroid.view.MyListView;
import com.example.cx.helloandroid.view.PowerImageView;
import com.example.cx.helloandroid.view.TitleView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TitleView mTitleBar;
    private Context mContext ;
    private MyListView myListView;
    private AdapterListView adapter;

    private List<PowerImageView> data=new ArrayList<>();
    private List<String> tvData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=MainActivity.this;

        mTitleBar= (TitleView) findViewById(R.id.title_view);  //获取你自定义View的实例，然后可以调用其中的方法
        mTitleBar.setTitleText("新设置的标题");
        mTitleBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"点击了返回按钮",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        initListData();
        myListView= (MyListView) findViewById(R.id.myListView);
        myListView.setDeleteListener(new MyListView.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                data.remove(index);
                tvData.remove(index);
                adapter.notifyDataSetChanged();
            }
        });
        adapter=new AdapterListView(mContext,data,tvData);
        myListView.setAdapter(adapter);
    }

    public void initListData(){
        for(int i=0;i<20;i++){
            data.add(new PowerImageView(mContext));
            tvData.add("Content"+i);
        }
    }

    //个人笔记本电脑对代码的第一次修改
}
