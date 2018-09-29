package com.example.archermind.myapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by archermind on 18-9-26.
 */

public class CircleView extends View{
    private Paint paint;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(200,MeasureSpec.UNSPECIFIED),MeasureSpec.makeMeasureSpec(200,MeasureSpec.UNSPECIFIED));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvas.drawArc(0,0,200,200,0,30,true,paint);
        RectF rectF=new RectF();
        rectF.set(0,0,200,200);
        canvas.drawArc(rectF,-90,30,true,paint);
        canvas.drawArc(rectF,-120,30,true,paint);
    }


    private void init() {
         paint =new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setColor(Color.parseColor("#00bcd4"));
    }
}
