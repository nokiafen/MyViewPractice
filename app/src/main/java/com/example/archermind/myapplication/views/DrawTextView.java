package com.example.archermind.myapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by archermind on 18-9-30.  文本绘制居中
 */

public class DrawTextView extends View {
    private int defaultHeight;
    private int defaultWith;
    private int viewWith;
    private int viewHeight;
    private AttributeSet attrs;
    Paint paint = new Paint();

    public DrawTextView(Context context) {
        super(context);
        init();
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        init();
    }

    private void init() {
        defaultHeight = Utils.dp2px(getContext(), 200);
        defaultWith = Utils.dp2px(getContext(), 200);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(getContext(), 2));

//        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.FanView);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWith = measureSelf(widthMeasureSpec, defaultWith);
        viewHeight = measureSelf(heightMeasureSpec, defaultHeight);
        setMeasuredDimension(viewWith, viewHeight);
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF();
        rectF.set(50, 50, 150, 150);
        canvas.drawRect(rectF, paint);


        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float baseline = (rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("hello world", rectF.centerX(), baseline, paint);
    }
}
