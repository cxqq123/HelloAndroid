package com.example.cx.helloandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cx.helloandroid.R;

/**
 * Created by Administrator on 2017/8/2.
 */

public class MyListView extends ListView implements View.OnTouchListener,GestureDetector.OnGestureListener{

    private GestureDetector gestureDetector; //手势
    private View deleteButton;       //delete 按钮的View
    private ViewGroup itemLayout;    //listView的那些item布局
    private int selectedItem;        //listView中选中的item
    private boolean isDeleteShown;   //delete按钮是否显示出来
    private OnDeleteListener listener;  //新建一个删除的listener

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector=new GestureDetector(context,this);   //初始化手势
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //若delete按钮显示出来
        if(isDeleteShown){
            itemLayout.removeView(deleteButton); //layout布局移除deleteButton的View
            deleteButton=null;
            isDeleteShown=false;
            return false;
        }
        //delete按钮没有显示出来，则让手势继续去处理onTouchEvent事件，
        // 将捕捉到的MotionEvent交由GestureDetector去处理
        return gestureDetector.onTouchEvent(motionEvent);
    }

    /**
     * 用户（轻触触摸屏） 1个Action_down
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        if(!isDeleteShown){
            selectedItem=pointToPosition((int)motionEvent.getX(),(int)motionEvent.getY());
        }
        return false;
    }

    /**
     * 用户（轻触触摸屏）尚未松开或拖动，强调的是没有松开或者拖动的状态 1个Action_down
     * @param motionEvent
     */
    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    /**
     *  用户（轻触触摸屏）松开  1个Action_up
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    /**
     * 用户按下触摸屏，并拖动
     * @param motionEvent
     * @param motionEvent1
     * @param v
     * @param v1
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    /**
     * 用户长按触摸屏，由多个MotionEvent Action_down触发
     * @param motionEvent
     */
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Toast.makeText(getContext(),"点击长按事件",Toast.LENGTH_SHORT).show();
    }

    /**
     * 用户按下触摸屏，快速移动后松开
     * @param motionEvent
     * @param motionEvent1
     * @param vX
     * @param vY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float vX, float vY) {
        if(!isDeleteShown && Math.abs(vX)>Math.abs(vY)){
            deleteButton= LayoutInflater.from(getContext()).inflate(R.layout.delete_button,null);
            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemLayout.removeView(deleteButton);
                    deleteButton=null;
                    isDeleteShown=false;
                    listener.onDelete(selectedItem);
                }
            });
            itemLayout=(ViewGroup)getChildAt(selectedItem-getFirstVisiblePosition()); //确认是listView中的哪个item被选中
            //动态加载RelativeLayout（相对布局）
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            itemLayout.addView(deleteButton,params);
            isDeleteShown=true;  //显示删除按钮
        }
        return false;
    }

    public void setDeleteListener(OnDeleteListener listener) {
        this.listener = listener;
    }

    public interface OnDeleteListener{
        void onDelete(int index);
    }
}
