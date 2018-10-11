package com.example.archermind.myapplication.views;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by archermind on 18-10-11.
 */

public class ViewWrapper {
    private View mTarget;

    public ViewWrapper(View mTagert) {
        this.mTarget = mTagert;
    }

    private ViewGroup.MarginLayoutParams getMarginLayoutParams() {
        return (ViewGroup.MarginLayoutParams) mTarget.getLayoutParams();
    }

    public int getWidth(){
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width){
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }


}
