package com.example.archermind.myapplication.viewbean;

import android.graphics.drawable.Drawable;

/**
 * Created by archermind on 18-9-30.
 */

public class PrizeObjct {
    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getDescriptin() {
        return descriptin;
    }

    public void setDescriptin(String descriptin) {
        this.descriptin = descriptin;
    }

    private Drawable drawable;
    private String descriptin;

    public PrizeObjct(Builder builder) {
        this.drawable=builder.drawable;
        this.descriptin=builder.description;
    }

    public static class Builder {
        private  Drawable drawable;
        private String description;

        public Builder Drawable(Drawable drawable){
            this.drawable=drawable;
            return this;
        }

        public Builder Description(String description){
            this.description=description;
            return this;
        }

        public PrizeObjct Build(){
            return new PrizeObjct(this);
        }
    }




}
