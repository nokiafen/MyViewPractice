package com.example.archermind.myapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.archermind.myapplication.R;
import com.example.archermind.myapplication.viewbean.FallObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archermind on 18-9-28.
 */

public class Fallview extends View {
    private int defaultHeight;
    private int defaultWith;
    private int viewWith;
    private int viewHeight;
    private AttributeSet attrs;
    Paint paint = new Paint();

    public Fallview(Context context) {
        super(context);
        init();
    }

    public Fallview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        init();
    }


    private void init() {
        defaultHeight = Utils.dp2px(getContext(), 200);
        defaultWith = Utils.dp2px(getContext(), 200);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(Utils.dp2px(getContext(), 2));

//        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.FanView);
//        bar_color=   typedArray.getColor(R.styleable.FanView_bar_color,Color.GRAY);
//        fan_color=typedArray.getColor(R.styleable.FanView_fan_color,Color.GRAY);
        snowY = Utils.dp2px(getContext(), 5);


    }

    List<FallObject> fallviewList;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWith = measureSelf(widthMeasureSpec, defaultWith);
        viewHeight = measureSelf(heightMeasureSpec, defaultHeight);
        setMeasuredDimension(viewWith, viewHeight);

        fallviewList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            FallObject fallObject = new FallObject.Builder().drawable(getResources().getDrawable(R.mipmap.nut)).build();
            fallObject.initSnow(viewWith, viewHeight, getContext());
            fallviewList.add(fallObject);
        }

    }

    private int measureSelf(int measureSpec, int defaultValue) {
        int resultSize = defaultValue;
        int mode = View.MeasureSpec.getMode(measureSpec);
        int measureSize = View.MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case View.MeasureSpec.EXACTLY:
                resultSize = measureSize;
                break;
            case View.MeasureSpec.AT_MOST:
                resultSize = Math.min(measureSize, defaultValue);
                break;
        }
        return resultSize;
    }


    int snowY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#2196f3"));
        paint.setColor(Color.WHITE);
//        canvas.drawCircle(Utils.dp2px(getContext(),10),snowY,Utils.dp2px(getContext(),2),paint);
//        Log.d("tt","ss");
        for (FallObject fallObject : fallviewList) {
            fallObject.drawCanvas(canvas, paint);
        }

        getHandler().postDelayed(runnable, 50);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//                  if (snowY<viewHeight){
//                      snowY+=Utils.dp2px(getContext(),8);
//                  }else {
//                      snowY=Utils.dp2px(getContext(),5);
//                  }
            invalidate();
        }
    };


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
