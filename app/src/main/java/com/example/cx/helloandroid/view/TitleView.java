package com.example.cx.helloandroid.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cx.helloandroid.R;

/**
 * Created by Administrator on 2017/8/2.
 * 组合控件（将TextView和button这些已有的控件组合在一起）
 */

public class TitleView extends FrameLayout{

    private ImageButton image_Back;
    private TextView titleText;

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
        titleText= (TextView) findViewById(R.id.title_text);
        image_Back= (ImageButton) findViewById(R.id.image);

        image_Back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });
    }

    public void setTitleText(String text){
        titleText.setText(text);
    }

    public void setLeftButtonListener(OnClickListener l){
        image_Back.setOnClickListener(l);
    }
}
