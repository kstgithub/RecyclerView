package com.haocent.android.recyclerview.slide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haocent.android.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 双向滑动 的 Activity
 *
 * Created by Tnno Wu on 2018/03/07.
 */

public class SlideActivity extends AppCompatActivity {

    private static final String TAG = SlideActivity.class.getSimpleName();

    private List<Integer> mTypeList = new ArrayList<>();

    private List<String> mHorizontalList = new ArrayList<>();
    private List<String> mVerticalList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_activity);

        initParam();

        initHorizontalData();

        initVerticalData();

        initView();
    }

    private void initParam() {
        mTypeList.add(0);   // 横向滑动
        mTypeList.add(1);   // 纵向滑动
    }

    private void initHorizontalData() {
        mHorizontalList.add("横向1");
        mHorizontalList.add("横向2");
        mHorizontalList.add("横向3");
        mHorizontalList.add("横向4");
        mHorizontalList.add("横向5");
        mHorizontalList.add("横向6");
        mHorizontalList.add("横向7");
        mHorizontalList.add("横向8");
        mHorizontalList.add("横向9");
        mHorizontalList.add("横向10");
    }

    private void initVerticalData() {
        mVerticalList.add("纵向1");
        mVerticalList.add("纵向2");
        mVerticalList.add("纵向3");
        mVerticalList.add("纵向4");
        mVerticalList.add("纵向5");
        mVerticalList.add("纵向6");
        mVerticalList.add("纵向7");
        mVerticalList.add("纵向8");
        mVerticalList.add("纵向9");
        mVerticalList.add("纵向10");
    }

    private void initView() {
        SlideAdapter adapter = new SlideAdapter(this, mTypeList);

        RecyclerView rcvSlide = findViewById(R.id.rcv_slide);

        rcvSlide.setLayoutManager(new LinearLayoutManager(this));
        rcvSlide.setHasFixedSize(false);
        rcvSlide.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcvSlide.setAdapter(adapter);

        adapter.setHorizontalDataList(mHorizontalList);
        adapter.setVerticalDataList(mVerticalList);
    }
}
