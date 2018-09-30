package com.example.archermind.myapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
 import  com.example.archermind.myapplication.R;
import com.example.archermind.myapplication.viewbean.PrizeObjct;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by archermind on 18-9-30.
 */

public class PriceView extends View {

    private int defaultHeight;
    private int defaultWith;
    private int viewWith;
    private int viewHeight;
    private AttributeSet attrs;
    Paint paint = new Paint();

    public PriceView(Context context) {
        super(context);
        init();
    }

    public PriceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        init();
    }

    List<PrizeObjct> priceList;
    Animation rotationAnim;
    int [] colors= {R.color.blue,R.color.color_qin,R.color.color_dblue};
    private void init() {

        defaultHeight = Utils.dp2px(getContext(), 200);
        defaultWith = Utils.dp2px(getContext(), 200);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(getContext(), 2));
        paint.setTextSize(Utils.dp2px(getContext(),14));

//        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.FanView);
        priceList = new ArrayList();
        for (int i = 0; i < 6; i++) {
            PrizeObjct prizeObjct = new PrizeObjct.Builder().Drawable(getResources().getDrawable(R.mipmap.nut)).Description(i + "priceZ").Build();
            priceList.add(prizeObjct);
        }

        rotationAnim= new RotateAnim();


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWith = measureSelf(widthMeasureSpec, defaultWith);
        viewHeight = measureSelf(heightMeasureSpec, defaultHeight);
        setMeasuredDimension(viewWith, viewHeight);

        pointCalculate();
    }


    Point baseRectCenter;
    int textHeight;
    int raduis;
    Point centerPoint;
    RectF viewRecf=new RectF();
    private void pointCalculate() {

        centerPoint = new Point();
        centerPoint.set(viewWith / 2, viewHeight / 2);
        raduis = Math.min(viewWith / 2, viewHeight / 2);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        textHeight = (int) Math.ceil(metrics.descent - metrics.ascent);

        baseRectCenter = new Point();
        baseRectCenter.set(centerPoint.x, (int)(centerPoint.y -  Math.cos(Math.PI / priceList.size()) * raduis + textHeight));

        viewRecf.set(centerPoint.x-raduis,centerPoint.y-raduis,centerPoint.x+raduis,centerPoint.y+raduis);
    }


    int degree = 0; //画布旋转角度
    RectF rectF = new RectF();
    Random random=new Random();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int increDgree = 360 / priceList.size();
        for (int i = 0; i < priceList.size(); i++) {
            canvas.save();
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setColor(getResources().getColor(colors[i%3]));
            canvas.rotate(i*increDgree+degree,centerPoint.x,centerPoint.y);
            canvas.drawArc(centerPoint.x - raduis, centerPoint.y - raduis, centerPoint.x + raduis, centerPoint.y + raduis, - 90 - 180 / priceList.size(), 360 / priceList.size(), true, paint);

            float textWidth = paint.measureText(priceList.get(i).getDescriptin());
            rectF.set(baseRectCenter.x - textWidth / 2, baseRectCenter.y - textHeight, baseRectCenter.x + textWidth / 2, baseRectCenter.y);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float baseline = (rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2;
//        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#ffffff"));
            canvas.drawText(priceList.get(i).getDescriptin(), rectF.centerX(), baseline, paint);
            canvas.restore();
        }



    }


    private int measureSelf(int measureSpec, int defaultValue) {
        int resultSize = defaultValue;
        int mode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                resultSize = measureSize;
                break;
            case MeasureSpec.AT_MOST:
                resultSize = Math.min(measureSize, defaultValue);
                break;
        }
        return resultSize;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event); //必须调用父方法 否则只有down事件 不会有后续的事件
        float downx= event.getX();
        float downy=event.getY();
        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                downx=event.getX();
                downy=event.getY();
                break;
            case  MotionEvent.ACTION_MOVE:


                break;
            case  MotionEvent.ACTION_UP:
                if (viewRecf.contains(downx,downy)) {
//                    Toast.makeText(getContext(),"you touched me",Toast.LENGTH_SHORT).show();
                    ratateAnim();
                }

                break;


        }

        return true;
    }

    private void ratateAnim() {
        rotationAnim.setDuration(6000);
        rotationAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        startAnimation(rotationAnim);
    }

    public  class RotateAnim extends Animation{
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            degree=(int) (360*4*interpolatedTime);
            postInvalidate();
        }
    }

}
