package com.example.cx.helloandroid.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cx.helloandroid.R;

/**
 * Created by Administrator on 2017/8/4.
 */

public class SettingFragment extends Fragment{
    //这是公司台式机电脑的代码
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View secondFragment=inflater.inflate(R.layout.fragment_second,null);
        return secondFragment;
    }
}
