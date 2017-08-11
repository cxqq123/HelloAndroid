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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class WaterWareView extends View{

    /**
     * 用一个Wave的list来存
     * @param context
     * @param attrs
     */

    private List<Wave> waveList;
    private static final int MAX_ALPHA=255;
    protected static final int FLUSH_ALL=1;
    private boolean isStart=true;
    private int alpha;
    private int width;

    public WaterWareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //使ArrayList变得 线程安全
        waveList= Collections.synchronizedList(new ArrayList<Wave>());
        alpha=0;
        width=0;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //重绘所有圆环
        for(int i=0;i<waveList.size();i++){
            Wave wave=waveList.get(i);
            canvas.drawCircle(wave.xDown,wave.yDown,wave.radius,initPaint(alpha,width));
        }

    }

    //初始化画笔
    private Paint initPaint(int alpha,float width){
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(width);
        //设置是环形方式绘制
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(alpha);
        paint.setColor(Color.RED);
        return paint;
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    flushState(); //处理消息，来处理刷新状态
                    invalidate(); //重绘

                    if(waveList!=null && waveList.size()>0){
                         handler.sendEmptyMessageDelayed(0,50);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    //刷新状态
    public void flushState(){
        for(int i=0;i<waveList.size();i++){
            Wave wave=waveList.get(i);
            wave.paint=initPaint(alpha,width);
            if(isStart ==false && wave.alpha ==0){
                waveList.remove(i);  //当不是第一次按下时，
                wave.paint=null; //将该波纹wave对象引用的paint对象置为null
                wave = null;   //将该波纹wave对象置为null
                continue;
            }else if(isStart ==true){
                isStart =false;
            }
            wave.radius+=5;
            wave.alpha-=10;
            if(wave.alpha < 0){
                wave.alpha=0;
            }
            wave.width=wave.radius/2;
            wave.paint.setAlpha(wave.alpha);
            wave.paint.setStrokeWidth(wave.width);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                //每点击一次屏幕，就生成一个wave对象，并将其放入list列表中
                Wave wave=new Wave();
                wave.radius=0;
                wave.alpha=MAX_ALPHA;
                wave.width=wave.radius/4;
                wave.xDown=(int) event.getX();
                wave.yDown=(int) event.getY();
                if(waveList.size()==0){
                    //说明是第一次点击，为了防止发送sendEmptyMessageDelayed延时消息时，会有越来越多的消息，所以先做个标志位来判断一下;
                    isStart=true;
                }
                waveList.add(wave);
                //点击一次刷新一次图形
                invalidate();
                if(isStart){
                    handler.sendEmptyMessage(0);
                }
                break;

            default:
                break;
        }
        return true;
    }

    private class Wave{
        float radius; //半径
        Paint paint; //画笔
        //按下的时候x,y坐标
        int xDown;
        int yDown;
        float width; //绘制画笔线的宽度
        int alpha; //透明度
    }
}
