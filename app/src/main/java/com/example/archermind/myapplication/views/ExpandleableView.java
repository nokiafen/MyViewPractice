package com.example.archermind.myapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.archermind.myapplication.R;

import java.util.jar.Attributes;

/**
 * Created by hailin on 18-9-26.
 */

public class ExpandleableView  extends RelativeLayout{

    private int defaultHeight;
    private int defaultWith;
    private AttributeSet attributeSet;
    private int strokeColor;
    private float strokeWith;
    private  float roundRaduis;
    private int backColor;
    private int viewWith;
    private int viewHeight;

    public ExpandleableView(Context context) {
        super(context);
        init();
    }



    public ExpandleableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        attributeSet=attrs;
        init();
    }

    private void init() {
        defaultWith=Utils.dp2px(getContext(),200);
        defaultHeight=Utils.dp2px(getContext(),40);
        TypedArray typedArray= getContext().obtainStyledAttributes(attributeSet, R.styleable.ExpandleableView);
        roundRaduis=  typedArray.getDimension(R.styleable.ExpandleableView_round_raduis,Utils.dp2px(getContext(),20));
        strokeWith= typedArray.getDimension(R.styleable.ExpandleableView_stroke_with,2);
        strokeColor=typedArray.getColor(R.styleable.ExpandleableView_stroke_color, Color.BLACK);
        backColor=typedArray.getColor(R.styleable.ExpandleableView_back_color,Color.BLACK);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight=   measureSize(defaultHeight,heightMeasureSpec);
        viewWith= measureSize(defaultWith,widthMeasureSpec);
        setMeasuredDimension(viewWith,viewHeight);

        if (getBackground()==null) {
            setgBackground();
        }
    }

    private void setgBackground() {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(backColor);
        gd.setStroke((int)strokeWith, strokeColor);
        gd.setCornerRadius(roundRaduis);
        setBackground(gd);

//        作者：Anlia
//        链接：https://www.jianshu.com/p/045da19ca6ea
//        來源：简书
//        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

}
