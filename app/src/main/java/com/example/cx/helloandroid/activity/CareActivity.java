package com.example.cx.helloandroid.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cx.helloandroid.R;

public class CareActivity extends AppCompatActivity {
    private Button btnCare;
    private boolean isFlag;

    private boolean isSecondFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care);
        btnCare = (Button) findViewById(R.id.btn_care);
        // 若Android 系统版本 高于5.0 则直接显示透明式状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            isSecondFlag =true;
        }else{
            //若是5.0以上的手机
            isSecondFlag=false;
        }
        if(isSecondFlag){
            btnCare.setBackgroundResource(R.drawable.bg_ripper);
        }else{
            btnCare.setBackgroundColor(Color.parseColor("#f60"));
        }

        btnCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFlag){
                    btnCare.setText("已关注");
                    isFlag =true;
                }else{
                    btnCare.setText("关注");
                    isFlag=false;
                }
            }
        });



    }
}
