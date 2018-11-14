package com.example.archermind.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.example.archermind.myapplication.views.EmptyClickDisappearView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyViewTestActivity extends AppCompatActivity {
    @BindView(R.id.empty)
    EmptyClickDisappearView empty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_emty_test);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
    empty.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    });
    }

    public void scale5(View view) {
        empty.animate().scaleY(0.5f).scaleX(0.5f).setDuration(300).start();
    }

    public void scale1(View view) {
        empty.animate().scaleY(1.0f).scaleX(1.0f).setDuration(300).start();
    }
}
