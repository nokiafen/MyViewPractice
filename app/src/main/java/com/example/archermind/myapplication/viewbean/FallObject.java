package com.example.archermind.myapplication.viewbean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import com.example.archermind.myapplication.views.Utils;

import java.util.Random;

/**
 * Created by archermind on 18-9-28.
 */

public class FallObject {

    private int startX;
    private int startY;
    private int speedY;
    private int speedX;
    private int rootWith;
    private int rootHeight;
    private int raduis;
    private Random random=new Random();

    private Drawable fDrawable;
    private Bitmap bitmap;
    private Context mContext;
    private double angle;
    private static double Pi=Math.PI;

    public FallObject(Builder builder) {
        this.fDrawable = builder.drawable;
    }

    public void initSnow(int rootWith, int rootHeight, Context mContext){
        this.mContext=mContext;
        this.rootHeight=rootHeight;
        this.rootWith=rootWith;
        startX=random.nextInt(rootWith);
        startY=random.nextInt(rootHeight);
        speedY=random.nextInt(Utils.dp2px(mContext,5))+Utils.dp2px(mContext,5);
        raduis=random.nextInt(Utils.dp2px(mContext,5));
        angle=(random.nextBoolean()?-1:1)*random.nextDouble()*Pi;
        initDrawable();
    }

    private void initDrawable() {

        float scale=random.nextFloat()+0.5f;

        // 取 drawable 的长宽
        int w = fDrawable.getIntrinsicWidth();
        int h = fDrawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = fDrawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmapc = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmapc);
        fDrawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        fDrawable.draw(canvas);

        int oldW = bitmapc.getWidth();
        int oldH = bitmapc.getHeight();
//        // 计算缩放比例
//        float scaleWidth = ((float) newW) / oldW;
//        float scaleHeight = ((float) newH) / oldH;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        bitmap = Bitmap.createBitmap(bitmapc, 0, 0, oldW, oldH, matrix, true);
    }

    public void drawCanvas(Canvas canvas , Paint paint){
//        canvas.drawCircle(startX,startY,raduis,paint);
        canvas.drawBitmap(bitmap,startX,startY,paint);
        startY+=speedY;

        startX+=Math.sin(angle)*Utils.dp2px(mContext,2);
        if(startY>rootHeight){
            speedY=random.nextInt(Utils.dp2px(mContext,5))+Utils.dp2px(mContext,5);
            startY= 0;
        }
        if(startX>rootWith){
            speedX=random.nextInt(Utils.dp2px(mContext,5))+Utils.dp2px(mContext,5);
            startX=random.nextInt(rootWith);
        }
        angle+=Pi/5*(random.nextBoolean()?-1:1);
        if (angle>Pi) {
            angle=Pi;
        }else if (angle<-Pi){
            angle=-Pi;
        }

    }

    public static class Builder {
        private Drawable drawable;
        public Builder drawable(Drawable drawable){
            this.drawable=drawable;
            return  this;
        }

        public FallObject build(){
            return new FallObject(this);
        }
    }

}
