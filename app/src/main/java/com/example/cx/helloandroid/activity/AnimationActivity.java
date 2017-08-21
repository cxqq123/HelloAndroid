package com.example.cx.helloandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.cx.helloandroid.MainActivity;
import com.example.cx.helloandroid.R;

public class AnimationActivity extends AppCompatActivity {


    private RelativeLayout rlAnim;
    private Button btnScale;
    private RelativeLayout rlImage;
    AnimationSet animationSet = new AnimationSet(true);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        rlAnim = (RelativeLayout) findViewById(R.id.rl_anim);
        btnScale = (Button) findViewById(R.id.btn_scale);
        rlImage = (RelativeLayout) findViewById(R.id.rl_Image);



        btnScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            /*
                参数解释：
                    第一个参数：X轴水平缩放起始位置的大小（fromX）。1代表正常大小
                    第二个参数：X轴水平缩放完了之后（toX）的大小，0代表完全消失了
                    第三个参数：Y轴垂直缩放起始时的大小（fromY）
                    第四个参数：Y轴垂直缩放结束后的大小（toY）
                    第五个参数：pivotXType为动画在X轴相对于物件位置类型
                    第六个参数：pivotXValue为动画相对于物件的X坐标的开始位置
                    第七个参数：pivotXType为动画在Y轴相对于物件位置类型
                    第八个参数：pivotYValue为动画相对于物件的Y坐标的开始位置

                   （第五个参数，第六个参数），（第七个参数,第八个参数）是用来指定缩放的中心点
                    0.5f代表从中心缩放
             */
                ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0,
                        Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,1.0f);
                //3秒完成动画
                scaleAnimation.setDuration(1000);
                //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
                animationSet.addAnimation(scaleAnimation);
                //启动动画
                AnimationActivity.this.rlAnim.startAnimation(animationSet);

                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rlAnim.clearAnimation(); //清除動畫效果

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
              rlImage.setVisibility(View.VISIBLE);
                animationSet.reset();
            }
        });


        rlImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationSet = new AnimationSet(true);
                ScaleAnimation scaleAnimation2 = new ScaleAnimation(0,1,0,1,
                        Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,1.0f);
                //3秒完成动画
                scaleAnimation2.setDuration(1000);
                //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
                animationSet.addAnimation(scaleAnimation2);
                //启动动画
                AnimationActivity.this.rlAnim.startAnimation(animationSet);

                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rlAnim.clearAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                rlImage.setVisibility(View.GONE);
                animationSet.reset();
            }
        });


    }
}
