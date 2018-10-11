package com.example.archermind.myapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.archermind.myapplication.views.AnimHelper;
import com.example.archermind.myapplication.views.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by archermind on 18-10-10.
 */

public class AnimTestActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_scan)
    ImageView ivRight;
    @BindView(R.id.lin_content)
    LinearLayout linContent;
    @BindView(R.id.iv_avar)
    ImageView iv_avar;
    AnimHelper animHelper;
    HomeAdapter adapter;

    int overallXScroll=0;
    private int height = 640;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        animHelper=new AnimHelper(linContent,ivRight,iv_avar);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, Utils.dp2px(this, 0.5f), ContextCompat.getColor(this, R.color.line_color)));
        recyclerView.setLayoutManager(manager);

        adapter = new HomeAdapter(this);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(getLocalClassName(),dy+"");
                overallXScroll = overallXScroll + dy;// 累加y值 解决滑动一半y值为0
                if (overallXScroll <= 0) {   //设置标题的背景颜色  //
                    if (animHelper.expand==true) {
                        return;
                    }
                        animHelper.expand();
                } else if (overallXScroll > 0 && overallXScroll <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    if (animHelper.expand==false) {
                        return;
                    }
                    animHelper.collopse();
                } else {
                    if (animHelper.expand==false) {
                        return;
                    }
                    animHelper.collopse();
                }
            }
        });

    }


    public void left(View view) {
        animHelper.collopse();
    }

    public void right(View view) {
        animHelper.expand();
    }
}
