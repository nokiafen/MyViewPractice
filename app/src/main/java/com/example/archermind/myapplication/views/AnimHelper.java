package com.example.archermind.myapplication.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by archermind on 18-10-10.
 */

public class AnimHelper {
    private View content;
    public boolean expand=true;

    private int normalWidth;
    private int maxWidth;
    private ViewWrapper viewWrapper;
    private ImageView ivScan;
    private int scanNormalLeft;
    private  int scanEndLeft;
    private  ImageView avar;

    public AnimHelper(View content,ImageView ivScan,ImageView avar) {
        this.content = content;
        this.ivScan=ivScan;
        ivScan.setVisibility(View.INVISIBLE);
        this.avar=avar;
        init();
    }

    private void init() {
        viewWrapper = new ViewWrapper(content);
        ViewTreeObserver viewTreeObserver = content.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                normalWidth = content.getWidth();
                maxWidth = Utils.dp2px(content.getContext(), 100);
                scanNormalLeft=ivScan.getLeft();
                scanEndLeft=content.getRight()-ivScan.getWidth()-Utils.dp2px(content.getContext(),40);
                content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


    }


    public void collopse() {
        expand=false;
        avar.setVisibility(View.INVISIBLE);
        ivScan.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(viewWrapper, "width", normalWidth, maxWidth);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(content, "alpha", 1, 0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(ivScan, "translationX", scanNormalLeft, scanEndLeft);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(ivScan, "alpha", 0, 1f);
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setDuration(300).playTogether(objectAnimator,objectAnimator1,objectAnimator2,objectAnimator3);
        set.start();

    }

    public void expand() {
        expand=true;
        ivScan.setVisibility(View.INVISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(viewWrapper, "width", maxWidth, normalWidth);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(content, "alpha", 0.3f, 1);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(ivScan, "translationX", scanEndLeft, scanNormalLeft);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(ivScan, "alpha", 1, 0f);
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                avar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.setDuration(300).playTogether(objectAnimator,objectAnimator1,objectAnimator3,objectAnimator2);
        set.start();

    }

}
