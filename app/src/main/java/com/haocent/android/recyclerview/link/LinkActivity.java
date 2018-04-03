package com.haocent.android.recyclerview.link;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.haocent.android.recyclerview.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 联动
 * <p>
 * Created by Tnno Wu on 2018/04/03.
 */

public class LinkActivity extends AppCompatActivity implements CheckListener, CityAreaAdapter.OnItemClickListener {

    private RecyclerView mRcv;

    private CityAreaAdapter mAdapter;

    private CityCinemaFragment mFragment;

    private LinearLayoutManager mLinearLayoutManager;

    private int targetPosition; // 点击左边某一个具体的item的位置

    private boolean isMoved;

    private CityArea cityArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_activity);

        initView();

        initData();
    }

    private void initView() {
        mAdapter = new CityAreaAdapter(this, this);

        mRcv = findViewById(R.id.rv_city_area);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRcv.setLayoutManager(mLinearLayoutManager);
        mRcv.setHasFixedSize(true);
        mRcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRcv.setAdapter(mAdapter);
    }

    private void initData() {
        // 获取 assets 目录下的资源文件
        String assetsData = getAssetsData("cinema.json");
        Gson gson = new Gson();
        cityArea = gson.fromJson(assetsData, CityArea.class);
        List<CityArea.CategoryOneArrayBean> categoryOneArray = cityArea.getCategoryOneArray();
        List<String> list = new ArrayList<>();
        // 初始化左侧列表数据
        for (int i = 0; i < categoryOneArray.size(); i++) {
            list.add(categoryOneArray.get(i).getName());
        }

        mAdapter.setCityAreaList(list);

        createFragment();
    }

    /**
     * 从资源文件中获取分类 Json
     */
    private String getAssetsData(String path) {
        String result = "";
        try {
            // 获取输入流
            InputStream mAssets = getAssets().open(path);
            // 获取文件的字节数
            int length = mAssets.available();
            // 创建 byte 数组
            byte[] buffer = new byte[length];
            // 将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }

    public void createFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragment = CityCinemaFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("right", cityArea.getCategoryOneArray());
        mFragment.setArguments(bundle);
        mFragment.setListener(this);
        fragmentTransaction.add(R.id.lin_fragment, mFragment);
        fragmentTransaction.commit();
    }

    private void setChecked(int position, boolean isLeft) {
        if (isLeft) {
            mAdapter.setCheckedPosition(position);
            // 此处的位置需要根据每个分类的集合来进行计算
            int count = 0;
            for (int i = 0; i < position; i++) {
                count += cityArea.getCategoryOneArray().get(i).getCategoryTwoArray().size();
            }
            count += position;
            mFragment.setData(count);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition)); // 凡是点击左边，将左边点击的位置作为当前的 tag
        } else {
            if (isMoved) {
                isMoved = false;
            } else
                mAdapter.setCheckedPosition(position);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(position));   // 如果是滑动右边联动左边，则按照右边传过来的位置作为 tag

        }
        moveToCenter(position);
    }

    // 将当前选中的item居中
    private void moveToCenter(int position) {
        // 将点击的 position 转换为当前屏幕上可见的 item 的位置以便于计算距离顶部的高度，从而进行移动居中
        View childAt = mRcv.getChildAt(position - mLinearLayoutManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - mRcv.getHeight() / 2);
            mRcv.smoothScrollBy(0, y);
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        setChecked(position, isScroll);
    }

    @Override
    public void onItemClick(int position) {
        if (mFragment != null) {
            isMoved = true;
            targetPosition = position;
            setChecked(position, true);
        }
    }
}
