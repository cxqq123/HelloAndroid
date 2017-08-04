package com.example.cx.helloandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.cx.helloandroid.R;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 继承ImageView控件
 * Created by Administrator on 2017/8/2.
 */

public class PowerImageView extends AppCompatImageView implements View.OnClickListener{

    //android 中播放GIF动画的关键类
    private Movie mMovie;
    //开始播放按钮图片
    private Bitmap mStartButton;
    //记录动画开始的时间
    private long mMovieStart;
    //GIF图片的宽度和高度
    private int mImageWidth;
    private int mImageHeight;
    //图片是否正在播放
    private boolean isPlaying;
    //是否允许自动播放
    private boolean isAutoPlay;

    //PowerImageView 的构造函数
    public PowerImageView(Context context){
        super(context);
    }
    public PowerImageView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }
    public PowerImageView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        //TypedArray 是存储资源数组的容器，创建完后，不在使用，记得调用recycler()方法进行回收
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.PowerImageView);
        int resourceId = getResourceId(a, context, attrs); //获取资源id
        if(resourceId!=0){
            // 获取该资源的流
            InputStream inputStream = getResources().openRawResource(resourceId);
            //使用Movie类对流进行解码
            mMovie=Movie.decodeStream(inputStream);
            if(mMovie!=null){
                //如果返回值不为null的话，就说明这是一个gif图片，下面获取是否自动播放的属性
                isAutoPlay=a.getBoolean(R.styleable.PowerImageView_auto_play,false);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                mImageWidth=bitmap.getWidth();
                mImageHeight=bitmap.getHeight();
                bitmap.recycle();
                if(!isAutoPlay){
                    //当不允许自动播放的时候，得到开始播放按钮的图片，并注册点击事件
                    mStartButton=BitmapFactory.decodeResource(getResources(),R.mipmap.play);  //加载图片
                    setOnClickListener(this);
                }
            }
        }
        a.recycle(); //回收TypeArray
    }

    /**
     * 通过java反射，获取src指定图片资源对应的id
     * @param a
     * @param context
     * @param attrs
     * @return 返回布局文件中指定图片资源所对应的id，没有指定任何
     */
    private int getResourceId(TypedArray a,Context context,AttributeSet attrs){
        try {
            //getField("mValue") 只获取类中的公共（public）字段
            Field field=TypedArray.class.getDeclaredField("mValue"); //获取类中的所有字段属性，包括公共，私有，默认包访问的字段
            field.setAccessible(true);
            TypedValue typedValue= (TypedValue) field.get(a);
            return typedValue.resourceId;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==getId()){
            //当用户点击图片时，开始播放GIF动画
            isPlaying=true;
            invalidate();  //请求重新绘制视图
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mMovie==null){
            //mMovie 等于null，说明是张普通的图片，则直接调用父类的onDraw（）方法
            super.onDraw(canvas);
        }else{
            //mMovie不等于null，则说明是张gif图片
            if(isAutoPlay) {
                //如果允许自动播放，就调用playMovie()方法播放gif图片
                playMovie(canvas);
                invalidate();
            }else{
                //不允许自动播放时，判断当前图片是否正在播放
                if(isPlaying){
                    //正在播放就继续调用playMovie()方法，一直到动画播放结束为止
                    if(playMovie(canvas)){
                        isPlaying=false;
                    }
                    invalidate();
                }else{
                    //还没开始播放就只绘制GIF图片的第一帧，并绘制一个开始按钮
                    mMovie.setTime(0);
                    mMovie.draw(canvas,0,0);
                    int offsetW=(mImageWidth-mStartButton.getWidth())/2;
                    int offsetH=(mImageHeight-mStartButton.getHeight())/2;
                    canvas.drawBitmap(mStartButton,offsetW,offsetH,null);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mMovie!=null){
            //若是GIF图片则重写设定大小
            setMeasuredDimension(mImageWidth,mImageHeight);
        }
    }

    /**
     * 开始播放GIF动画，播放完成返回true，未完成返回false
     *
     * @param canvas
     * @return  播放完成返回true，未完成返回false
     */
    private boolean playMovie(Canvas canvas){
        long now= SystemClock.uptimeMillis();
        if(mMovieStart==0){
            mMovieStart=now;
        }
        int duration=mMovie.duration();
        if(duration==0){
            duration=1000;
        }
        int relTime=(int)((now-mMovieStart) % duration);
        mMovie.setTime(relTime);
        mMovie.draw(canvas,0,0);      //绘制canvas
        if((now-mMovieStart)>=duration){
            mMovieStart=0;
            return true;
        }
        return false;
    }

    public void addView(PowerImageView view){
        List<PowerImageView> list=new ArrayList<>();
        list.add(view);
    }
}
