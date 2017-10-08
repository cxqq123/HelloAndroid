package com.example.cx.helloandroid.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cx.helloandroid.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


/**
 * Created by cx on 2017/9/25.
 */

public class RevealFollowButton extends FrameLayout{
    protected boolean mIsFollowed;
    protected boolean mIsFirstInit = true;
    private TextView mFollowTv;
    private TextView mUnFollowTv;
    float mCenterX;
    float mCenterY;
    float mRevealRadius = 0;
    private ImageView ivBg;
    private ImageView izdIvCareAdd;
    private Path mPath = new Path();

    public RevealFollowButton(Context paramContext) {
        super(paramContext);
        init();
    }

    public RevealFollowButton(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public RevealFollowButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private boolean isValidClick(float x, float y) {
        if (x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight()) {
            return true;
        }
        return false;
    }

//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_UP:
//                return true;
//        }
//        return false;
//    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isValidClick(event.getX(), event.getY())) {
                    return false;
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (!isValidClick(event.getX(), event.getY())) {
                    return false;
                }
                mIsFirstInit =false;
                mCenterX = event.getX();
                mCenterY = event.getY();
                mRevealRadius = 0;
                izdIvCareAdd = (ImageView) findViewById(R.id.izd_iv_care_add);
                mUnFollowTv = (TextView) findViewById(R.id.tv_care);  //关注
                mFollowTv = (TextView) findViewById(R.id.tv_already_care); //已关注
                ivBg = (ImageView) findViewById(R.id.iv_bg);

                if(ivBg.getVisibility() ==GONE){
                    mUnFollowTv.setVisibility(GONE);
                    mFollowTv.setVisibility(VISIBLE);
                    izdIvCareAdd.setVisibility(GONE); //添加
                    ivBg.setVisibility(VISIBLE);
                }else if(ivBg.getVisibility() ==VISIBLE){
                    mUnFollowTv.setVisibility(View.VISIBLE);
                    mFollowTv.setVisibility(View.GONE);
                    izdIvCareAdd.setVisibility(VISIBLE);
                    ivBg.setVisibility(GONE);
                }

                setFollowed(!mIsFollowed, true);
                return true;
        }
        return false;
    }

    private void init() {



        izdIvCareAdd = (ImageView) findViewById(R.id.izd_iv_care_add);
        mUnFollowTv = (TextView) findViewById(R.id.tv_care);  //关注
        mFollowTv = (TextView) findViewById(R.id.tv_already_care); //已关注
        ivBg = (ImageView) findViewById(R.id.iv_bg);

//        setFollowed(false,false);
//        mUnFollowTv = new TextView(getContext());
//        mUnFollowTv.setText("关注");
//        mUnFollowTv.setGravity(17);
//        mUnFollowTv.setSingleLine();
//        mUnFollowTv.setBackgroundColor(Color.WHITE);
//        mUnFollowTv.setTextColor(Color.BLACK);
//
//        addView(this.mUnFollowTv);
//
//        mFollowTv = new TextView(getContext());
//        mFollowTv.setText("未关注");
//        mFollowTv.setGravity(17);
//        mFollowTv.setSingleLine();
//        mFollowTv.setBackgroundColor(Color.parseColor("#000000"));
//        mFollowTv.setTextColor(Color.WHITE);
//        addView(this.mFollowTv);
//
//        mFollowTv.setPadding(40, 40, 40, 40);
//        mUnFollowTv.setPadding(40, 40, 40, 40);
//        setFollowed(false, false);

//        View view=LayoutInflater.from(mContext).inflate(R.layout.layout_add_care,null);
//        mUnFollowTv= (TextView) view.findViewById(R.id.izd_tv_care_show);
//        mUnFollowTv.setVisibility(VISIBLE);
//        View view2=LayoutInflater.from(mContext).inflate(R.layout.layout_text,null);
//        mFollowTv= (TextView) view2.findViewById(R.id.tv_already_care);
//        mFollowTv.setVisibility(VISIBLE);
////        setFollowed(false,false);
    }

    protected void setFollowed(boolean isFollowed, boolean needAnimate) {
        mIsFollowed = isFollowed;
        if (isFollowed) {
            mUnFollowTv.setVisibility(View.GONE);
            mFollowTv.setVisibility(View.VISIBLE);
            izdIvCareAdd.setVisibility(View.GONE);
            ivBg.setVisibility(VISIBLE);
            mFollowTv.bringToFront();
        } else {
            mUnFollowTv.setVisibility(View.VISIBLE);
            mFollowTv.setVisibility(View.GONE);
            izdIvCareAdd.setVisibility(View.VISIBLE);
            ivBg.setVisibility(GONE);
            mUnFollowTv.bringToFront();
        }
        if (needAnimate) {
            ValueAnimator animator = ObjectAnimator.ofFloat(mFollowTv, "empty", 0.0F, (float) Math.hypot(getMeasuredWidth(), getMeasuredHeight()));
            animator.setDuration(500L);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRevealRadius = (Float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }
    }

    private boolean drawBackground(View paramView) {
        if(mIsFirstInit) {
            return true;
        }
        if (mIsFollowed && paramView == mUnFollowTv) {
            return true;
        } else if (!mIsFollowed && paramView == mFollowTv) {
            return true;
        }
        return false;
    }

    protected boolean drawChild(Canvas canvas, View paramView, long paramLong) {
        if (drawBackground(paramView)) {
            return super.drawChild(canvas, paramView, paramLong);
        }
        int i = canvas.save();
        mPath.reset();
        mPath.addCircle(mCenterX, mCenterY, mRevealRadius, Path.Direction.CW);
        canvas.clipPath(this.mPath);
        boolean bool2 = super.drawChild(canvas, paramView, paramLong);
        canvas.restoreToCount(i);
        return bool2;
    }
}
