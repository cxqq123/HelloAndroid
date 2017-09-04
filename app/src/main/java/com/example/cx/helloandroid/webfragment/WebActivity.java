package com.example.cx.helloandroid.webfragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.webfragment.webChildFragment.BaiduFragment;
import com.example.cx.helloandroid.webfragment.webChildFragment.TaobaoFragment;
import com.example.cx.helloandroid.webfragment.webChildFragment.YoukuFragment;

public class WebActivity extends AppCompatActivity implements View.OnClickListener{
    private Button webBtnBaidu;
    private Button webBtnYouku;
    private Button webBtnTaobaos;

    private Fragment baiduFragment;
    private Fragment youkuFragment;
    private Fragment taobaoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
    }


    public void  initView(){
        webBtnBaidu = (Button) findViewById(R.id.web_btn_baidu);
        webBtnYouku = (Button) findViewById(R.id.web_btn_youku);
        webBtnTaobaos = (Button) findViewById(R.id.web_btn_taobaos);

        webBtnBaidu.setOnClickListener(this);
        webBtnYouku.setOnClickListener(this);
        webBtnTaobaos.setOnClickListener(this);

        switchFragment(0);

    }

    private void switchFragment(int i) {
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch(i){
            case 0:
                if(baiduFragment==null){
                    baiduFragment=new BaiduFragment();
                    transaction.add(R.id.rl_web_content,baiduFragment);
                }else{
                    transaction.show(baiduFragment);
                }
                break;
            case 1:
                if(youkuFragment==null){
                    youkuFragment=new YoukuFragment();
                    transaction.add(R.id.rl_web_content,youkuFragment);
                }else{
                    transaction.show(youkuFragment);
                }
                break;
            case 2:
                if(taobaoFragment==null){
                    taobaoFragment=new TaobaoFragment();
                    transaction.add(R.id.rl_web_content,taobaoFragment);
                }else{
                    transaction.show(taobaoFragment);
                }
                break;
        }
        transaction.commit();
    }

    //隐藏Fragment
    public void hideFragment(FragmentTransaction transaction){
        if(baiduFragment!=null){
            transaction.hide(baiduFragment);
        }
        if(youkuFragment!=null){
            transaction.hide(youkuFragment);
        }
        if(taobaoFragment!=null){
            transaction.hide(taobaoFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.web_btn_baidu:
                switchFragment(0);
                break;
            case R.id.web_btn_youku:
                switchFragment(1);
                break;
            case R.id.web_btn_taobaos:
                switchFragment(2);
                break;
        }
    }
}
