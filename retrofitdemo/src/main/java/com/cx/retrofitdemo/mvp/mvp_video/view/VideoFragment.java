package com.cx.retrofitdemo.mvp.mvp_video.view;

import android.os.Bundle;
import android.view.View;

import com.cx.retrofitdemo.R;
import com.cx.retrofitdemo.base.BaseFragment;

/**
 * Created by Administrator on 2017/9/6.
 */

public class VideoFragment extends BaseFragment{

    public static VideoFragment newInstance(String title){
        VideoFragment fragment=new VideoFragment();
        Bundle b=new Bundle();
        b.putString("msg",title);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View initView() {
        view=View.inflate(context, R.layout.fragment_video,null);
        return view;
    }

    @Override
    public void initData() {

    }
}
