package com.example.cx.helloandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/8/2.
 * 自绘控件
 */

public class CounterView extends View implements View.OnClickListener{
    private Paint mPaint;
    private Rect mBounds;
    private int mCount;

    public CounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds=new Rect();
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint); //绘制矩形

        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(30);
        String text =String.valueOf(mCount);
        mPaint.getTextBounds(text,0,text.length(),mBounds); //这里先调用getTextBounds()方法来获取到文字的宽度和高度
        float textWidth=mBounds.width();
        float textHeight=mBounds.height();
        canvas.drawText(text,getWidth()/2-textWidth/2,getHeight()/2+textHeight/2,mPaint);//绘制中间的文字，控制好距离
    }

    @Override
    public void onClick(View view) {
        mCount++;
        invalidate();
    }
}
