package com.example.archermind.myapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.archermind.myapplication.R;

/**
 * Created by archermind on 18-9-28.
 */

public class FanView extends View{
    private int defaultHeight;
    private int defaultWith;
    private  int viewWith;
    private int viewHeight;
    private AttributeSet attrs;
    Paint paint=new Paint();
    private Point centerPoint;
    Thread thread;

    private  int raduis;
    private int barSize;
    boolean counter=false;
    public FanView(Context context) {
        super(context);
        init();
    }

    public FanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs=attrs;
        init();
    }


    private  int bar_color;
    private int fan_color;
    private void init() {
        defaultHeight=Utils.dp2px(getContext(),200);
        defaultWith=Utils.dp2px(getContext(),200);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(getContext(),2));
        centerPoint = new Point();

        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.FanView);
        bar_color=   typedArray.getColor(R.styleable.FanView_bar_color,Color.GRAY);
        fan_color=typedArray.getColor(R.styleable.FanView_fan_color,Color.GRAY);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWith=  measureSelf(widthMeasureSpec,defaultWith );
        viewHeight=  measureSelf(heightMeasureSpec,defaultHeight );
        setMeasuredDimension(viewWith,viewHeight);
        centerPoint.set(viewWith/2,viewHeight/3);
        raduis=viewWith/3;
        barSize=viewHeight/2;
    }

    private int measureSelf(int measureSpec,int defaultValue) {
        int resultSize=defaultValue;
        int mode=MeasureSpec.getMode(measureSpec);
        int measureSize=MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                resultSize=measureSize;
                break;
            case MeasureSpec.AT_MOST:
                resultSize=Math.min(measureSize,defaultValue);
                break;
        }
        return  resultSize;
    }


    int degree=0;
    RectF rectF =new RectF();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(bar_color);
        canvas.drawLine(centerPoint.x,centerPoint.y+Utils.dp2px(getContext(),5),centerPoint.x,centerPoint.y+barSize,paint);//draw bar


        canvas.save();
        paint.setColor(fan_color);
        canvas.rotate(degree,centerPoint.x,centerPoint.y);
        rectF.set(centerPoint.x-raduis,centerPoint.y-raduis,centerPoint.x+raduis,centerPoint.y+raduis);
        canvas.drawArc(rectF,0,20,true,paint);
        canvas.drawArc(rectF,120,20,true,paint);
        canvas.drawArc(rectF,240,20,true,paint);
        canvas.restore();
        Log.d("tt","ss");
        counter();

    }

    private void counter() {
        if (counter==false) {
            counter=true;
            thread=  new Thread(new Runnable() {
                @Override
                public void run() {
                    while (counter){
                        degree+=30;
                        postInvalidate();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        thread.stop();
    }
}
