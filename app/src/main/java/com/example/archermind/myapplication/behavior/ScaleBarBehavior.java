package com.example.archermind.myapplication.behavior;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.archermind.myapplication.R;

/**
 * Created by archermind on 18-10-30.
 */

public class ScaleBarBehavior extends AppBarLayout.Behavior {
    NestedScrollView imageView;
    NestedScrollView tvContent;
    int startHeight;
    int imageStartHeight;
    int maxDistance;
    int contentHeight;


    public ScaleBarBehavior() {
    }

    public ScaleBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {

        boolean result= super.onLayoutChild(parent, abl, layoutDirection);
        initView(abl);
        return  result;
    }

    private void initView(AppBarLayout abl) {
         imageView= (NestedScrollView) abl.findViewById(R.id.imageView);
         tvContent= (NestedScrollView) abl.findViewById(R.id.lin_content);
         startHeight=abl.getHeight();
        imageStartHeight=imageView.getHeight();
        maxDistance=(int)(startHeight*0.6f);
        contentHeight= tvContent.getHeight();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        if (target.getId() == R.id.lin_content) return true;//这个布局就是middleLayout
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed) {
        if (isBounding) {
            return;
        }
            if(target!=null&&((dy>0&&child.getBottom()>startHeight)||(child.getBottom()>=startHeight&&dy<0))){
                scaleView(child,dy);
                return;
            }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

    }

    int totalDistance=0;
    float radio;
    private void scaleView(AppBarLayout child,int dy) {
        totalDistance=totalDistance+dy;

        if (totalDistance<=0) { //向下位移
            int compuValue=Math.min(-totalDistance,maxDistance);
             radio=1+(1f)*compuValue/startHeight;
        }else { //位移向上
             radio=1f;
        }
        Log.d(getClass().getName(),"radio:"+radio+"totolDistance:"+totalDistance);
//        imageView.setScaleY(ratio+1);
//        imageView.setScaleY(ratio+1);
        ViewCompat.setScaleX(imageView,radio);
        ViewCompat.setScaleY(imageView,radio);
//       CoordinatorLayout.LayoutParams layoutParams= (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//       layoutParams.height=layoutParams.height+(int)(ratio*imageStartHeight*2);
//       child.setLayoutParams(layoutParams);
        int  adbCurrentBottom=startHeight+(int)((radio-1)*imageStartHeight*0.5);
        child.setBottom(adbCurrentBottom);
        tvContent.setTop(adbCurrentBottom-contentHeight);
        tvContent.setBottom(adbCurrentBottom);
    }


    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        if (velocityY > 100) {//当y速度>100,就秒弹回
//            isAnimate = false;
        }
        return true;
//        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target) {
//        recovery(abl);
        if (isBounding==false) {
            boundDistance(abl);
        }
        super.onStopNestedScroll(coordinatorLayout, abl, target);
    }

    //弹回逻辑
    boolean isBounding=false;
    private void boundDistance(final AppBarLayout abl) {
        if (totalDistance>=0) {
            return;
        }
        isBounding=true;
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(radio,1f);
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cvalue=(float)animation.getAnimatedValue();
                ViewCompat.setScaleY(imageView,cvalue);
                ViewCompat.setScaleX(imageView,cvalue);

                int cBottom=startHeight+(int) ((cvalue-1)*imageStartHeight*0.5f);

                abl.setBottom(cBottom);
                tvContent.setTop(cBottom-tvContent.getHeight());
                tvContent.setBottom(cBottom);
            }
        });
        valueAnimator.start();
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                totalDistance=0;
                isBounding=false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


}
