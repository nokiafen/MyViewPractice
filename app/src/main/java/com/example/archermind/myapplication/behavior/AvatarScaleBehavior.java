package com.example.archermind.myapplication.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.archermind.myapplication.R;

/**
 * Created by archermind on 18-11-1.
 */

public class AvatarScaleBehavior extends CoordinatorLayout.Behavior<ImageView> {
    int contentStartY=0;
    int contentCurrentY=0;
    int toolBarBottomY=0;

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        init(parent,child,dependency);
        float percent=1f*(contentCurrentY-toolBarBottomY) /(contentStartY-toolBarBottomY);
        Log.d("Avatarbehavor:percent",percent+"");
        ViewCompat.setScaleY(child,percent);
        ViewCompat.setScaleX(child,percent);
       return  true;
    }

    private void init(CoordinatorLayout parent, ImageView child, View dependency) {
     TextView tvTitle= (TextView) parent.findViewById(R.id.tv_title);
     NestedScrollView nestedContent= (NestedScrollView) parent.findViewById(R.id.scrollerView);
        if (toolBarBottomY==0) {
            toolBarBottomY=tvTitle.getBottom();
        }

        if (contentStartY==0) {
            contentStartY=  nestedContent.getTop();
        }

        contentCurrentY=nestedContent.getTop();
        Log.d("AvatarbehavorContentTop",contentCurrentY+"");
    }

    public AvatarScaleBehavior() {
    }

    public AvatarScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        if (dependency.getId()== R.id.lin_content) {
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }
}
