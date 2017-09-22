package com.cx.web;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnWeb;
    private TextView tvShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 若Android 系统版本 高于5.0 则直接显示透明式状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //若是4.4以上的手机
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }else if(Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT){
            //4.4以下的手机
//            ToastUtils.makeTextShort(mContext,"你的android 版本低于4.4太低，建议你升级android系统!!!");
        }
        setContentView(R.layout.activity_main);
//        initData();
        btnWeb = (Button) findViewById(R.id.btn_web);
        tvShow = (TextView) findViewById(R.id.tv_show);

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this,ActivityWeb.class);
//                startActivity(intent);
            new LoadOkHttpClientTask().execute("http://www.baidu.com");
            }
        });
    }

    public void initData(){
//        DBManager dbManager=new DBManager(MainActivity.this);
//        dbManager.AddUrl(new ModelUrl(1,"http://www.baidu.com"));
//        dbManager.AddUrl(new ModelUrl(2,"http://www.youku.com"));
//        dbManager.AddUrl(new ModelUrl(3,"http://www.taobao.com"));
    }

    class LoadOkHttpClientTask extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient=new OkHttpClient();
            Request request=new Request.Builder()
                                .url(strings[0])
                                .build();
            try {
                Response response=okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    return response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            tvShow.setText(s);
        }
    }
}
