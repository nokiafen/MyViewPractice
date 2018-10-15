package com.example.archermind.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.archermind.myapplication.adapter.BasePagerAdapter;
import com.example.archermind.myapplication.views.MyBrokenViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by archermind on 18-10-15.
 */

public class SldingConfictActivity extends AppCompatActivity {
    @BindView(R.id.brokenPager)
    MyBrokenViewPager brokenPager;

    private List<View> mViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_confict_layout);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mViews = new ArrayList<>();
        ListView listView = null;
        List<String> datas = null;

        for (int i = 0; i < 5; i++) {
            datas = new ArrayList<String>();
            for (int j = 0; j < i+15 + 1; j++) {

                datas.add("I am page:" + i + "item:" + j);
            }

            listView = new ListView(this);
            listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas));
            mViews.add(listView);
        }
        brokenPager.setAdapter(new BasePagerAdapter<>(mViews));
    }
}
