package com.example.cx.helloandroid.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.cx.helloandroid.R;
import com.example.cx.helloandroid.ui.fragment.MainFragment;
import com.example.cx.helloandroid.ui.fragment.SecondFragment;
import com.example.cx.helloandroid.ui.fragment.SettingFragment;
import com.example.cx.helloandroid.ui.fragment.ThirdFragment;

import java.util.List;

public class ContentActivity extends Activity {

    private RelativeLayout rlContainter;
    private RadioGroup rdgAll;
    private RadioButton rbVideos;
    private RadioButton rbNovel;
    private RadioButton rbCartoon;
    private RadioButton rbMine;

    private List<Fragment> fragments;
    private int currentIndex=0;

    private MainFragment mainFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();

    }

    public int getLayoutId() {
        return R.layout.activity_content;
    }

    public void initView() {

        rlContainter = (RelativeLayout) findViewById(R.id.rl_containter);
        rdgAll = (RadioGroup) findViewById(R.id.rdg_all);
        rbVideos = (RadioButton) findViewById(R.id.rb_videos);
        rbNovel = (RadioButton) findViewById(R.id.rb_novel);
        rbCartoon = (RadioButton) findViewById(R.id.rb_cartoon);
        rbMine = (RadioButton) findViewById(R.id.rb_mine);

       switchFragment(0);
        setListener();
    }

    public void setListener() {
        rdgAll.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    if (radioGroup.getChildAt(i).getId() == checkedId) {
                        switchFragment(i);  //来回切换Fragment
                    }
                }
            }
        });
    }

    private void switchFragment(int i) {
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch(i){
            case 0:
                if(mainFragment==null){
                    mainFragment=new MainFragment();
                    transaction.add(R.id.rl_containter,mainFragment);
                }else{
                    transaction.show(mainFragment);
                }
                break;
            case 1:
                if(secondFragment==null){
                    secondFragment=new SecondFragment();
                    transaction.add(R.id.rl_containter,secondFragment);
                }else{
                    transaction.show(secondFragment);
                }
                break;
            case 2:
                if(thirdFragment==null){
                    thirdFragment=new ThirdFragment();
                    transaction.add(R.id.rl_containter,thirdFragment);
                }else{
                    transaction.show(thirdFragment);
                }
                break;
            case 3:
                if(settingFragment==null){
                    settingFragment=new SettingFragment();
                    transaction.add(R.id.rl_containter,settingFragment);
                }else{
                    transaction.show(settingFragment);
                }
        }
        transaction.commit();
    }

    //隐藏Fragment
    public void hideFragment(FragmentTransaction transaction){
        if(mainFragment!=null){
            transaction.hide(mainFragment);
        }
        if(secondFragment!=null){
            transaction.hide(secondFragment);
        }
        if(thirdFragment!=null){
            transaction.hide(thirdFragment);
        }
        if(settingFragment!=null){
            transaction.hide(settingFragment);
        }
    }

}
