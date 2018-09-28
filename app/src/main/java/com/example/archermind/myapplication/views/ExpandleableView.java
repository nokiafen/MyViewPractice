package com.example.archermind.myapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.archermind.myapplication.R;

import java.util.jar.Attributes;

/**
 * Created by hailin on 18-9-26.
 */

public class ExpandleableView  extends RelativeLayout{

    private int defaultHeight;
    private int defaultWith;
    private AttributeSet attributeSet;
    private int strokeColor;//背景线色
    private float strokeWith; //背景线宽
    private  float roundRaduis;//背景弧度
    private int backColor; //背景颜色
    private int viewWith;//
    private int viewHeight;
    private int button_color; //button颜色
    private float buttonStrokeSize;//button线色
    private  float buttonSize; //button大小
    private  ExpandMenuAnim expandMenuAnim;
    private Path buttonPath;
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

        button_color= typedArray.getColor(R.styleable.ExpandleableView_button_color,Color.BLUE);
        buttonStrokeSize=typedArray.getDimension(R.styleable.ExpandleableView_button_stroke_size,Utils.dp2px(getContext(),2));
        buttonSize=typedArray.getDimension(R.styleable.ExpandleableView_button_size,Utils.dp2px(getContext(),20));

        buttonPath=new Path();

        expandMenuAnim=new ExpandMenuAnim();

        typedArray.recycle();
    }

    int minWith;
    int maxChangedWith;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight=   measureSize(defaultHeight,heightMeasureSpec);
        viewWith= measureSize(defaultWith,widthMeasureSpec);
        setMeasuredDimension(viewWith,viewHeight);

        if (getBackground()==null) {
            setgBackground();
        }

        buttonMeasure();
        minWith=(int) roundRaduis*2;
        maxChangedWith=viewWith-minWith;

    }


    int normalLeft;
    int normalTop;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        normalLeft=   getLeft();
        normalTop=   getTop();
    }

    Paint bPaint;
    Point bCenterPoint;
    int halfBWith;
    RectF btRectf=new RectF();

    private void buttonMeasure() {
        bCenterPoint =new Point();
        bCenterPoint.set(Math.max((int) buttonSize/2,(int) roundRaduis),viewHeight/2);
        halfBWith=(int) buttonSize/2;
        btRectf.set(roundRaduis-halfBWith,viewHeight/2-halfBWith,roundRaduis+halfBWith,viewHeight/2+halfBWith);

        bPaint=new Paint();
        bPaint.setAntiAlias(true);
        bPaint.setStrokeWidth(buttonStrokeSize);
        bPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        bPaint.setColor(button_color);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        buttonPath.reset();
        buttonPath.moveTo(bCenterPoint.x-halfBWith,bCenterPoint.y);
        buttonPath.lineTo(bCenterPoint.x+halfBWith,bCenterPoint.y);
        canvas.drawPath(buttonPath,bPaint);
        buttonPath.reset();

        canvas.save();
        canvas.rotate(-buttonIconDegrees,bCenterPoint.x,bCenterPoint.y);
        buttonPath.moveTo(bCenterPoint.x,bCenterPoint.y-halfBWith);
        buttonPath.lineTo(bCenterPoint.x,bCenterPoint.y+halfBWith);
        canvas.drawPath(buttonPath,bPaint);
        canvas.restore();

        super.onDraw(canvas);
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


    Point touchPoint =new Point();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event); //必须调用父方法 否则只有down事件 不会有后续的事件
        float downx= event.getX();
        float downy=event.getY();
        touchPoint.set((int) downx,(int) downy);
        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                downx=event.getX();
                downy=event.getY();
                touchPoint.set((int) downx,(int) downy);
                break;
            case  MotionEvent.ACTION_MOVE:


                break;
            case  MotionEvent.ACTION_UP:
                if (btRectf.contains(downx,downy)) {
                    Toast.makeText(getContext(),"you touched me",Toast.LENGTH_SHORT).show();
                    expandAnim();
                }

                break;


        }

        return true;
    }

    private void expandAnim() {
        expandMenuAnim.setDuration(300);
        isExpand=!isExpand;
      startAnimation(expandMenuAnim);
    }


    private  boolean isExpand=false;
    private  float buttonIconDegrees=0; //旋转度数
    private class ExpandMenuAnim extends Animation {
        public ExpandMenuAnim() {}

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            Log.d("interpolatedTime",interpolatedTime+"");
            super.applyTransformation(interpolatedTime, t);
            if(isExpand){//展开菜单
                buttonIconDegrees = 90-90 * interpolatedTime;
                Log.d("Anim","normalleft:"+normalLeft+"normaltop:"+normalTop);
                layout(normalLeft,normalTop,normalLeft+ (int)(viewWith-maxChangedWith*interpolatedTime),normalTop+viewHeight);
            }else {//收起菜单
                buttonIconDegrees =  90 * interpolatedTime;
                Log.d("Anim","normalleft:"+normalLeft+"normaltop:"+normalTop);
                layout(normalLeft,normalTop, normalLeft+(int)(minWith+maxChangedWith*interpolatedTime),normalTop+viewHeight);
            }



            postInvalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("onLayout","l:"+l+"t:"+t);
        super.onLayout(changed, l, t, r, b);
        int childCount=getChildCount();
        if (childCount>=1) {
            View chidView=getChildAt(0);
            if (!isExpand) {
                chidView.setVisibility(View.VISIBLE);
                LayoutParams layoutParams =new LayoutParams(viewWith,viewHeight);
                layoutParams.setMargins((int) roundRaduis+halfBWith,0,(int) roundRaduis,0);
                chidView.setLayoutParams(layoutParams);
                chidView.layout((int)(2*roundRaduis),0,(int)(viewWith-roundRaduis-halfBWith),viewHeight);
            }else {
                chidView.setVisibility(View.GONE);
            }
        }

        }

}
