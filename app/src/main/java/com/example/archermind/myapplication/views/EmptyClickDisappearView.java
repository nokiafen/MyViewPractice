package com.example.archermind.myapplication.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.archermind.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmptyClickDisappearView extends FrameLayout {
    @BindView(R.id.bt_left)
    AppCompatButton btLeft;
    @BindView(R.id.bt_right)
    AppCompatButton btRight;
    @BindView(R.id.bt_top)
    AppCompatButton btTop;
    @BindView(R.id.bt_bottom)
    AppCompatButton btBottom;
    @BindView(R.id.center_color)
    FrameLayout frameLayout;

    public EmptyClickDisappearView(@NonNull Context context) {
        super(context);
        init();
    }

    public EmptyClickDisappearView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyClickDisappearView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.empty_click_disappear_layout, this);
        ButterKnife.bind(this)
        ;

    }

    @OnClick({R.id.bt_left, R.id.bt_right, R.id.bt_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_left:
                Log.d(EmptyClickDisappearView.this.getClass().getName(),"bt_left");
                break;
            case R.id.bt_right:
                Log.d(EmptyClickDisappearView.this.getClass().getName(),"bt_right");
                break;
            case R.id.bt_top:
                Log.d(EmptyClickDisappearView.this.getClass().getName(),"bt_top");
                break;
            case R.id.bt_bottom:
                Log.d(EmptyClickDisappearView.this.getClass().getName(),"bt_bottom");
                break;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(EmptyClickDisappearView.this.getClass().getName(),"ontouch");
        return super.onTouchEvent(event);
    }
}
