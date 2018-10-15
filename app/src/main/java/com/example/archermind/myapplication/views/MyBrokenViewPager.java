package com.example.archermind.myapplication.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by archermind on 18-10-15.
 * https://www.jianshu.com/p/982a83271327 详解
 */

public class MyBrokenViewPager extends ViewPager {
    public MyBrokenViewPager(Context context) {
        super(context);
    }

    public MyBrokenViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
       float currentX;
       float currentY;

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                xNormal= ev.getX();
                yNormal= ev.getY();
                return super.onInterceptTouchEvent(ev);
            case MotionEvent.ACTION_UP:
                return super.onInterceptTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                currentX= ev.getX();
                currentY= ev.getY();
                if(Math.abs(currentX-xNormal)>Math.abs(currentY-yNormal))
                {
                    return true;
                }else {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }

    private  float xNormal;
    private  float yNormal;

}
