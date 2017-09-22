package com.cx.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    private TextView showText;
    private Button btnStartSpeech;


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

}
