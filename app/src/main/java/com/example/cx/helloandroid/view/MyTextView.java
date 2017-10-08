package com.example.cx.helloandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by cx on 2017/9/25.
 */

public class MyTextView  extends TextView{

    public MyTextView(Context context){
        super(context);
    }
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint =new Paint();
        canvas.drawRoundRect(40,40,40,40,10,10,paint);
    }


}
