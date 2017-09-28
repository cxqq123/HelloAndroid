package com.example.cx.helloandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cx.helloandroid.R;


public class CareActivity extends AppCompatActivity {
    private Button mTestBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care);
        mTestBt = (Button) findViewById(R.id.test_bt);
        mTestBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTestBt.getText().toString().equals("关注")) {
                    mTestBt.setText("未关注");
                } else {
                    mTestBt.setText("关注");
                }
            }
        });
    }
}
