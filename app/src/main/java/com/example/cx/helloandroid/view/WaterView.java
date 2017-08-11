package com.example.cx.helloandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;



/**
 * Created by cx on 2017/8/11.
 *
 * 水波纹效果
 */

public class WaterView extends View{

    private int alpha;   //透明度
    private int radius;  //半径
    private int xDown,yDown;
    private Paint paint;
    private int width;
    private int MAX_ALPHA=255;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    flushState(); //刷新状态
                    invalidate(); //重绘
                    if(alpha!=0){
                        //若透明度没有到0，则继续延时刷新，并为了防止sendEmptyMessage()会导致发送越来越多的消息
                        //所以在发送延时消息之前，先把之前的消息remove掉
                        handler.removeMessages(0);
                        handler.sendEmptyMessageDelayed(0,50);
                    }

                    break;

                default:
                    break;
            }
        }
    };

    public WaterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        alpha=0;
        radius=0;
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(xDown,yDown,radius,paint);
    }

    public void initPaint(){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(width);   //设置画笔不填充

        //设置是环形方式绘制
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(alpha);
        paint.setColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                radius=0;
                alpha=MAX_ALPHA;
                width=radius/4;
                xDown= (int) event.getX();
                yDown= (int) event.getY();
                handler.sendEmptyMessage(0);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 刷新状态
     */
    private void flushState(){

        radius +=3;
        alpha -=10;
        if(alpha<0){
            alpha=0;
        }
        width=radius/3;
        paint.setAlpha(alpha);
        paint.setStrokeWidth(width);
    }
}
