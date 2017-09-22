package com.cx.web.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cx.web.R;


/**
 * Created by Administrator on 2017/8/2.
 * 组合控件（将TextView和button这些已有的控件组合在一起）
 */

public class TitleView extends FrameLayout{


    private RelativeLayout image_Back;
    private TextView titleText;
    private IBackListener listener;


    public TitleView(@NonNull final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
        titleText= (TextView) findViewById(R.id.title_text);
        image_Back= (RelativeLayout) findViewById(R.id.image);

        image_Back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                listener.onBack();
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

    public void setListener(IBackListener listener) {
        this.listener = listener;
    }

    public interface IBackListener{
        public void onBack();
    }
}
