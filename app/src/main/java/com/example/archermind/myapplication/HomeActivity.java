package com.example.archermind.myapplication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.archermind.myapplication.R;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by archermind on 18-10-24.
 */

//https://blog.csdn.net/qq_31340657/article/details/51918773?utm_source=blogxgwz0  CollopasingToolbarLayout 效果总结
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        initStatus();
    }

    private void initStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以下不支持状态栏变色
            //注意了，这里使用了第三方库 StatusBarUtil，目的是改变状态栏的alpha
            StatusBarUtil.setTransparentForImageView(HomeActivity.this, null);
            //这里是重设我们的title布局的topMargin，StatusBarUtil提供了重设的方法，但是我们这里有两个布局
            //TODO 关于为什么不把Toolbar和@layout/layout_uc_head_title放到一起，是因为需要Toolbar来占位，防止AppBarLayout折叠时将title顶出视野范围
            int statusBarHeight = getStatusBarHeight(HomeActivity.this);
            TextView tvTitle=findViewById(R.id.tv_title);
            CollapsingToolbarLayout.LayoutParams lp1 = (CollapsingToolbarLayout.LayoutParams) tvTitle.getLayoutParams();
            lp1.topMargin = statusBarHeight;
            tvTitle.setLayoutParams(lp1);
             android.support.v7.widget.Toolbar mToolBar= findViewById(R.id.toolbar);
            CollapsingToolbarLayout.LayoutParams lp2 = (CollapsingToolbarLayout.LayoutParams) mToolBar.getLayoutParams();
            lp2.topMargin = statusBarHeight;
            mToolBar.setLayoutParams(lp2);
        }

    }


    public void toMainActivity(View view) {
    }

    public void toRevealActivity(View view) {
    }

    public void toAnimTestActivity(View view) {
    }

    public void toSlidingConfictActivity(View view) {
    }


    /**
     * 获取状态栏高度
     * ！！这个方法来自StatusBarUtil,因为作者将之设为private，所以直接copy出来
     *
     * @param context context
     * @return 状态栏高度
     */
    private int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

}
