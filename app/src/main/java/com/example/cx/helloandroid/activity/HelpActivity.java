package com.example.cx.helloandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.webfragment.WebActivity;

public class HelpActivity extends ActivtyBase implements View.OnClickListener{

    private Context mContext;

    private Button btnMap;
    private Button btnStatus;
    private Button btnScanCode;
    private Button btnWaterView;
    private Button btnPhotoview;
    private Button btnSensor;
    private Button btnAnim;
    private Button btnWeb;
    private Button btnWelcome;



    @Override
    public int gerLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    public void initBaseView() {
        mContext=HelpActivity.this;
        initView();
    }

    public void initView(){
        btnMap = (Button) findViewById(R.id.btn_map);
        btnStatus = (Button) findViewById(R.id.btn_status);
        btnScanCode = (Button) findViewById(R.id.btn_scan_code);
        btnWaterView = (Button) findViewById(R.id.btn_water_view);
        btnPhotoview = (Button) findViewById(R.id.btn_photoview);
        btnSensor = (Button) findViewById(R.id.btn_sensor);
        btnAnim = (Button) findViewById(R.id.btn_anim);
        btnWeb = (Button) findViewById(R.id.btn_web);
        btnWelcome = (Button) findViewById(R.id.btn_welcome);


        btnMap.setOnClickListener(this);
        btnStatus.setOnClickListener(this);
        btnScanCode.setOnClickListener(this);
        btnWaterView.setOnClickListener(this);
        btnPhotoview.setOnClickListener(this);
        btnSensor.setOnClickListener(this);
        btnAnim.setOnClickListener(this);
        btnWeb.setOnClickListener(this);
        btnWelcome.setOnClickListener(this);

    }

    public void openActivity(Class<?> mClass){
        Intent mIntent=new Intent(mContext,mClass);
        startActivity(mIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_map:
                openActivity(MapActivity.class);
                break;
            case R.id.btn_status:
                openActivity(StatusActivity.class);
                break;
            case R.id.btn_scan_code:
                openActivity(ScanCodeActivity.class);
                break;
            case R.id.btn_water_view:
                openActivity(WaterViewActivity.class);
                break;
            case R.id.btn_photoview:
                openActivity(PhotoViewActivity.class);
                break;
            case R.id.btn_sensor:
                openActivity(SensorActivity.class);
                break;
            case R.id.btn_anim:
                openActivity(AnimationActivity.class);
                break;
            case R.id.btn_web:
                openActivity(WebActivity.class);
                break;
            case R.id.btn_welcome:
                openActivity(WelcomeActivity.class);
                break;
        }
    }

}
