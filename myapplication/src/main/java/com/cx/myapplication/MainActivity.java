package com.cx.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cx.myapplication.model.Voice;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView showText;
    private Button btnStartSpeech;

    private List<ApplicationInfo> mApplicationInfos =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化科大讯飞的sdk
        SpeechUtility.createUtility(this, SpeechConstant.APPID+"=59c4d63a");
        showText = (TextView) findViewById(R.id.showText);
        btnStartSpeech = (Button) findViewById(R.id.btn_startSpeech);
        btnStartSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSpeech(MainActivity.this);
            }
        });

//        queryFilterAppInfo();
    }

    public void initSpeech(final Context context){

        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog =new RecognizerDialog(context,null);
        mDialog.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT,"mandarin");
        //2.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                if(!b){
                    //解析语音
                    String res =parseVoice(recognizerResult.getResultString());
                    showText.setText(res);
                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        //3.显示dialog,接收语音输入
        mDialog.show();
    }

    /**
     * 解析语音json
     */
    public String parseVoice(String resString){
        Gson gson =new Gson();
        Voice voiceBean =gson.fromJson(resString,Voice.class);
        StringBuffer sb= new StringBuffer();
        ArrayList<Voice.WSBean> ws=voiceBean.ws;
        for(Voice.WSBean wsBean :ws){
            String word= wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }


    private void queryFilterAppInfo() {
        PackageManager pm = this.getPackageManager();
        // 查询所有已经安装的应用程序
        List<ApplicationInfo> appInfos= pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);// GET_UNINSTALLED_PACKAGES代表已删除，但还有安装目录的
        List<ApplicationInfo> applicationInfos=new ArrayList<>();

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        // 通过getPackageManager()的queryIntentActivities方法遍历,得到所有能打开的app的packageName
        List<ResolveInfo>  resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        Set<String> allowPackages=new HashSet();
        for (ResolveInfo resolveInfo:resolveinfoList){
            allowPackages.add(resolveInfo.activityInfo.packageName);
        }

        for (ApplicationInfo app:appInfos) {
//            if((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0)//通过flag排除系统应用，会将电话、短信也排除掉
//            {
//                applicationInfos.add(app);
//            }
//            if(app.uid > 10000){//通过uid排除系统应用，在一些手机上效果不好
//                applicationInfos.add(app);
//            }
            if (allowPackages.contains(app.packageName)){
                applicationInfos.add(app);
            }
        }

        for(String str:allowPackages){
            Log.v("cx",str);
        }
        mApplicationInfos=applicationInfos;
    }
}
