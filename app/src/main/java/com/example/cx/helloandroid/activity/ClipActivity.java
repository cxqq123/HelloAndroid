package com.example.cx.helloandroid.activity;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cx.helloandroid.R;

import java.util.Timer;
import java.util.TimerTask;

public class ClipActivity extends AppCompatActivity {

    private ImageView showImage;
    private Button bnShowProgress;
    private boolean isFlag;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);

        showImage = (ImageView) findViewById(R.id.show_image);
        bnShowProgress = (Button) findViewById(R.id.bn_show_progress);

        final ClipDrawable circle= (ClipDrawable) showImage.getDrawable();
        handler =new Handler(){
            @Override
            public void handleMessage(Message msg) {

                //若消息是本程序发送的
                if(msg.what ==0x123){
                    //修改ClipDrawable 的level值
                    circle.setLevel(circle.getLevel() +200);
                    int currentLevel =circle.getLevel();
                    handler.removeMessages(0x123);
                }
            }
        };

        bnShowProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置不可被点击
                if(!isFlag){
                    isFlag =true;
                    final Timer timer =new Timer();
                    //用计时器去控制
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Message message =new Message();
                            message.what =0x123;
                            handler.sendMessage(message);
                            if(circle.getLevel()>=10000){
                                //取消计时器
                                timer.cancel();
                            }
                        }
                    },0,200);
                }else{
                    circle.setLevel(0);
                    final Timer timer =new Timer();
                    //用计时器去控制
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Message message =new Message();
                            message.what =0x123;
                            handler.sendMessage(message);
                            if(circle.getLevel()>=10000){
                                //取消计时器
                                timer.cancel();
                            }
                        }
                    },0,200);
                }

            }
        });
    }
}
