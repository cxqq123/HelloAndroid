package com.example.cx.helloandroid.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cx.helloandroid.R;

/**
 * Created by cx on 2017/9/21.
 */

public class RefreshableView extends LinearLayout implements View.OnClickListener{

    //下拉状态
    public static final int STATUS_PULL_TO_REFRESH =0;
    //释放立即刷新状态
    public static final int STATUS_RELEASE_TO_REFRESH=1;
    //正在刷新状态
    public static final int STATUS_REFRESHING=2;
    //刷新完成或未刷新状态
    public static final int STATUS_REFRESH_FINISHED=3;

    //下拉头部回滚的速度
    public static final int SCROLL_SPEED= -20;

    //一分钟的毫秒值，用于判断上次的更新时间
    public static final long ONE_MINUTE =60*1000;
    //一小时的毫秒值，用于判断上次的更新时间
    public static final long ONE_HOUR =60*ONE_MINUTE;
    //一天的毫秒值，用于判断上次的更新时间
    public static final long ONE_DAY =24*ONE_HOUR;
    //一月的毫秒值，用于判断上次的更新时间
    public static final long ONE_MONTH =30*ONE_DAY;
    //一年的毫秒值，用于判断上次的更新时间
    public static final long ONE_YEAR =12*ONE_MONTH;

    /**
     * 上次更新时间的字符串常量，用于作为SharedPreferences的键值
     */
    private static final String UPDATED_AT = "updated_at";

    /**
     * 下拉刷新的回调接口
     */
    private PullToRefreshListener mListener;

    /**
     * 用于存储上次更新时间
     */
    private SharedPreferences preferences;

    /**
     * 下拉头的View
     */
    private View header;

    /**
     * 需要去下拉刷新的ListView
     */
    private ListView listView;

    /**
     * 刷新时显示的进度条
     */
    private ProgressBar progressBar;

    /**
     * 指示下拉和释放的箭头
     */
    private ImageView arrow;

    /**
     * 指示下拉和释放的文字描述
     */
    private TextView description;

    /**
     * 上次更新时间的文字描述
     */
    private TextView updateAt;

    /**
     * 下拉头的布局参数
     */
    private MarginLayoutParams headerLayoutParams;

    /**
     * 上次更新时间的毫秒值
     */
    private long lastUpdateTime;

    /**
     * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分
     */
    private int mId = -1;

    /**
     * 下拉头的高度
     */
    private int hideHeaderHeight;

    /**
     * 当前处理什么状态，可选值有STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH,
     * STATUS_REFRESHING 和 STATUS_REFRESH_FINISHED
     */
    private int currentStatus = STATUS_REFRESH_FINISHED;

    /**
     * 记录上一次的状态是什么，避免进行重复操作
     */
    private int lastStatus = currentStatus;

    /**
     * 手指按下时的屏幕纵坐标
     */
    private float yDown;

    /**
     * 在被判定为滚动之前用户手指可以移动的最大值。
     */
    private int touchSlop;

    /**
     * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次
     */
    private boolean loadOnce;

    /**
     * 当前是否可以下拉，只有ListView滚动到头的时候才允许下拉
     */
    private boolean ableToPull;


    public RefreshableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh,null,true);
        arrow = (ImageView) findViewById(R.id.arrow);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        description = (TextView) findViewById(R.id.tv_desc);
        updateAt = (TextView) findViewById(R.id.tv_updateAt);

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        refreshUpdatedAtValue();
        setOrientation(VERTICAL);
        addView(header,0); //LinearLayout 添加头部
    }

    // 进行一些关键性的初始化操作，比如：将下拉头向上偏移进行隐藏，给ListView注册touch事件
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 根据当前的状态来旋转箭头
     */
    private void rotateArrow(){
        float pivotX =arrow.getWidth() /2f;
        float pivotY =arrow.getHeight() /2f;
        float fromDegrees =0f;
        float toDegrees =0f;
        if(currentStatus ==STATUS_PULL_TO_REFRESH){
            fromDegrees =180f;
            toDegrees =360f;
        }else if(currentStatus ==STATUS_RELEASE_TO_REFRESH){
            fromDegrees =0f;
            toDegrees =180f;
        }

        //加一个箭头的旋转动画
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setDuration(100);
        animation.setFillAfter(true);
        arrow.startAnimation(animation);
    }

    /**
     * 刷新下拉头中上次更新时间的文字描述
     */
    private void refreshUpdatedAtValue(){
        lastUpdateTime = preferences.getLong(UPDATED_AT + mId, -1);
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - lastUpdateTime;
        long timeIntoFormat;
        String updateAtValue;
        if (lastUpdateTime == -1) {
            updateAtValue = getResources().getString(R.string.not_updated_yet);
        } else if (timePassed < 0) {
            updateAtValue = getResources().getString(R.string.time_error);
        } else if (timePassed < ONE_MINUTE) {
            updateAtValue = getResources().getString(R.string.updated_just_now);
        } else if (timePassed < ONE_HOUR) {
            timeIntoFormat = timePassed / ONE_MINUTE;
            String value = timeIntoFormat + "分钟";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_DAY) {
            timeIntoFormat = timePassed / ONE_HOUR;
            String value = timeIntoFormat + "小时";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_MONTH) {
            timeIntoFormat = timePassed / ONE_DAY;
            String value = timeIntoFormat + "天";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else if (timePassed < ONE_YEAR) {
            timeIntoFormat = timePassed / ONE_MONTH;
            String value = timeIntoFormat + "个月";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        } else {
            timeIntoFormat = timePassed / ONE_YEAR;
            String value = timeIntoFormat + "年";
            updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
        }
        updateAt.setText(updateAtValue);
    }

    /**
     * 正在刷新的任务，在此任务中会去回调注册进来的下拉刷新监听器
     */
    class RefreshingTask extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            int topMargin =headerLayoutParams.topMargin;
            while (true){
                topMargin =topMargin +SCROLL_SPEED; //回退
                if(topMargin <=0){
                    topMargin =0;
                    break;
                }
                publishProgress(topMargin);
//                sleep(10);
            }
            //当前状态 为正在刷新中状态
            currentStatus =STATUS_REFRESHING;
            publishProgress(0);
            if(mListener != null){
                mListener.onRefresh();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
//            updateHeaderView();
            headerLayoutParams.topMargin =topMargin[0];
            header.setLayoutParams(headerLayoutParams);
        }
    }

    /**
     * 隐藏下拉头的任务，当未进行下拉刷新或下拉刷新完成后，此任务将会使下拉头重新隐藏
     */
    class HideHeaderTask extends AsyncTask<Void,Integer,Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            int topMargin =headerLayoutParams.topMargin;
            while (true){
                topMargin =topMargin +SCROLL_SPEED; //回退
                if(topMargin <= hideHeaderHeight){
                    topMargin =hideHeaderHeight;
                    break;
                }
                publishProgress(topMargin);
//                sleep(10);
            }
            return topMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... topMargin) {
            headerLayoutParams.topMargin =topMargin[0];
            header.setLayoutParams(headerLayoutParams);
        }

        @Override
        protected void onPostExecute(Integer topMargin) {
            headerLayoutParams.topMargin =topMargin;
            header.setLayoutParams(headerLayoutParams);
            currentStatus =STATUS_REFRESH_FINISHED;
        }
    }

    //使当前线程睡眠多长时间
    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //响应下拉刷新的事件
    public interface PullToRefreshListener{
        //刷新会回调此方法，在方法内编写具体的刷新逻辑，此方法是在子线程中调用的，你可以不必另开线程
        //来进行耗时操作

        void onRefresh();
    }
}
