package com.example.cx.helloandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.utils.ToastUtils;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    private TextView tvTiaoguo;
    private TextView tvShowTimeNumber;

    private final static long TIME=1000L*1;
    private Context mContext;
    private int count=3; //计数器
    private boolean flag=true;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0x123){
                tvShowTimeNumber.setText(""+count);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext=this;
        initView();
        bindData();
    }

    public void initView(){
        imageView = (ImageView) findViewById(R.id.imageView);
        tvTiaoguo = (TextView) findViewById(R.id.tv_tiaoguo);
        tvShowTimeNumber = (TextView) findViewById(R.id.tv_showTimeNumber);
        setListener();
    }

    public void setListener(){
        imageView.setOnClickListener(this);
        tvTiaoguo.setOnClickListener(this);
        tvShowTimeNumber.setOnClickListener(this);
    }

    public void bindData(){
        new MyThread().start();

    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_tiaoguo:
                Intent intent=new Intent(mContext, ContentActivity.class);
                startActivity(intent);
                flag=false;  //当点击跳过时，停止线程的运行
                finish();
                break;
            case R.id.imageView:
                ToastUtils.makeTextShort(mContext,"点击ImageView 跳转页面");
                break;
        }
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            while(count>0 && flag){
                try {
                    sleep(TIME);
                    count--;
                    mHandler.sendEmptyMessage(0x123);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(count==0){
                //当 count==0 的时候，也休眠1s
                try {
                    sleep(TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(mContext, ContentActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
