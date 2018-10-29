package com.example.archermind.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by archermind on 18-10-24.
 */

//https://blog.csdn.net/qq_31340657/article/details/51918773?utm_source=blogxgwz0  CollopasingToolbarLayout 效果总结
public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        ButterKnife.bind(this);
        initStatus();
        init();
    }

    private void init() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Math.abs(verticalOffset * 1.0f / appBarLayout.getTotalScrollRange());
                Log.d(getClass().getName(), "percent:" + percent);
                tvTitle.setAlpha(percent);
//                StatusBarUtil.setColor(HomeActivity.this, Color.RED,200);
                StatusBarUtil.setTranslucentForImageView(HomeActivity.this, (int) (255f * percent), null);            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initStatus();
    }

    private void initStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以下不支持状态栏变色
            //注意了，这里使用了第三方库 StatusBarUtil，目的是改变状态栏的alpha
            StatusBarUtil.setTransparentForImageView(HomeActivity.this, null);
            //这里是重设我们的title布局的topMargin，StatusBarUtil提供了重设的方法，但是我们这里有两个布局
            //TODO 关于为什么不把Toolbar和@layout/layout_uc_head_title放到一起，是因为需要Toolbar来占位，防止AppBarLayout折叠时将title顶出视野范围
            int statusBarHeight = getStatusBarHeight(HomeActivity.this);
            TextView tvTitle = findViewById(R.id.tv_title);
            CollapsingToolbarLayout.LayoutParams lp1 = (CollapsingToolbarLayout.LayoutParams) tvTitle.getLayoutParams();
            lp1.topMargin = statusBarHeight;
            tvTitle.setLayoutParams(lp1);
            Toolbar mToolBar = findViewById(R.id.toolbar);
            CollapsingToolbarLayout.LayoutParams lp2 = (CollapsingToolbarLayout.LayoutParams) mToolBar.getLayoutParams();
            lp2.topMargin = statusBarHeight;
            mToolBar.setLayoutParams(lp2);
        }

    }


    public void toMainActivity(View view) {
        startAc(null,MainActivity.class);
    }

    public void toRevealActivity(View view) {
        startAc(null,RevealActivity.class);
    }

    public void toAnimTestActivity(View view) {
        startAc(null,AnimTestActivity.class);
    }

    public void toSlidingConfictActivity(View view) {
        startAc(null,SldingConfictActivity.class);
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

    public void startAc(Bundle bundle,Class aclass){
        Intent intent =new Intent(this,aclass);
        intent.putExtras(bundle==null?new Bundle():bundle);
        startActivity(intent);
//        startActivity(new Intent(HomeActivity.this,MainActivity.class));
    };

}
