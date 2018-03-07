package com.haocent.android.recyclerview.refresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.haocent.android.recyclerview.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 下拉刷新 的 Activity
 *
 * 关键字：setOnRefreshListener
 *
 * Created by Tnno Wu on 2018/03/06.
 */

public class RefreshActivity extends AppCompatActivity {

    private static final String TAG = RefreshActivity.class.getSimpleName();

    private RefreshService mService;

    private RefreshAdapter mAdapter;

    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_activity);

        initService();

        initView();

        initData();
    }

    private void initService() {
        mService = new RetrofitClient().getService();
    }

    private void initView() {
        mAdapter = new RefreshAdapter(this);

        mRefreshLayout = findViewById(R.id.srl_refresh);
        RecyclerView rcvRefresh = findViewById(R.id.rcv_refresh);

        // 初始画面，使用 SwipeRefreshLayout 做 Loading
        mRefreshLayout.setRefreshing(true);

        // 自定义 SwipeRefreshLayout 颜色
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_purple
        );

        // 设定下拉圆圈的背景色
        mRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        // 下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        rcvRefresh.setLayoutManager(new LinearLayoutManager(this));
        rcvRefresh.setHasFixedSize(true);
        rcvRefresh.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcvRefresh.setAdapter(mAdapter);
    }

    private void initData() {
        Observable<RefreshDataBean> observable = mService.getRefreshData(
                "0b2bdeda43b5688921839c8ecb20399b",
                "%E5%8C%97%E4%BA%AC",
                0,
                10,
                "",
                ""
        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RefreshDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(RefreshDataBean refreshDataBean) {
                        Log.d(TAG, "onNext: ");

                        mAdapter.setRefreshDataList(refreshDataBean.getSubjects());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                        mRefreshLayout.setRefreshing(false);
                    }
                });
    }
}
