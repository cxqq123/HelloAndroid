package com.example.cx.helloandroid.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cx.helloandroid.R;

/**
 * Created by cx on 2017/8/4.
 */

public class MainFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mainFragment=inflater.inflate(R.layout.fragment_main,null);
        return mainFragment;
    }
}
