package com.example.archermind.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archermind.myapplication.adapter.HomeAdapter;
import com.example.archermind.myapplication.views.Utils;

/**
 * Created by archermind on 18-10-9.
 */

public class RevealActivity extends AppCompatActivity {
    ImageView imageView;
    LinearLayout linearLayout;
    RecyclerView recyclerView;
    int overallXScroll=0;
    private int height = 640;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);
        imageView = (ImageView) findViewById(R.id.iv_imageview);
        linearLayout = (LinearLayout) findViewById(R.id.line1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        init();

    }

    HomeAdapter adapter;

    private void init() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, Utils.dp2px(this, 0.5f), ContextCompat.getColor(this, R.color.line_color)));
        recyclerView.setLayoutManager(manager);

        adapter = new HomeAdapter(this);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(getLocalClassName(),dy+"");
                overallXScroll = overallXScroll + dy;// 累加y值 解决滑动一半y值为0
                if (overallXScroll <= 0) {   //设置标题的背景颜色
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imageHide();
                    }
                } else if (overallXScroll > 0 && overallXScroll <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变

                } else {
                    imageShow();
                }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void imageShow() {
        Animator animator = ViewAnimationUtils.createCircularReveal(imageView, imageView.getWidth() / 2, imageView.getHeight() / 2, imageView.getWidth() / 2, imageView.getWidth());
        animator.setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageView.setScaleX(1f);
                imageView.setScaleY(1f);
                imageView.setVisibility(View.VISIBLE);
            }
        });
        animator.start();

        final Animator animatorLine=ViewAnimationUtils.createCircularReveal(linearLayout,linearLayout.getWidth(),linearLayout.getHeight()/2,linearLayout.getWidth(),0);
        animatorLine.setDuration(400);
        animatorLine.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                linearLayout.setScaleX(1);
//                linearLayout.setScaleY(0);
                linearLayout.setVisibility(View.INVISIBLE);
            }
        });
        animatorLine.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void imageHide() {
        Animator animator = ViewAnimationUtils.createCircularReveal(imageView, imageView.getWidth() / 2, imageView.getHeight() / 2, imageView.getWidth(), imageView.getWidth() / 2);
        animator.setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageView.setVisibility(View.INVISIBLE);
                imageView.setScaleX(0.5f);
                imageView.setScaleY(0.5f);
            }
        });
        animator.start();

//        final Animator animatorLine=ViewAnimationUtils.createCircularReveal(linearLayout,linearLayout.getWidth(),linearLayout.getHeight()/2,0,linearLayout.getWidth());
//        linearLayout.setVisibility(View.VISIBLE);
        final Animator animatorLine=ViewAnimationUtils.createCircularReveal(linearLayout,linearLayout.getRight()-linearLayout.getLeft(),(linearLayout.getBottom()-linearLayout.getTop())/2,30,linearLayout.getRight()-linearLayout.getLeft());
        animatorLine.setDuration(800);
        animatorLine.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                linearLayout.setScaleX(1);
                linearLayout.setScaleY(1);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        animatorLine.start();

    }


    public void show(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageShow();
        }
    }

    public void hide(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageHide();
        }
    }
}
