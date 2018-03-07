package com.haocent.android.recyclerview.load;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.haocent.android.recyclerview.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 上拉加载 的 Activity
 *
 * 关键字：addOnScrollListener
 *
 * Created by Tnno Wu on 2018/03/06.
 */

public class LoadActivity extends AppCompatActivity {

    private static final String TAG = LoadActivity.class.getSimpleName();

    private LoadService mService;

    private LoadAdapter mAdapter;

    private LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);

    private int lastVisibleItem;

    private int total = 0;
    private int start = 0;
    private int count = 10;

    private boolean isFirst = true;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_activity);

        initService();

        initView();

        initData(0, 10);
    }

    private void initService() {
        mService = new RetrofitClient().getService();
    }

    private void initView() {
        mAdapter = new LoadAdapter(this);

        RecyclerView rcvLoad = findViewById(R.id.rcv_load);
        mProgressBar = findViewById(R.id.progress_bar);

        rcvLoad.setLayoutManager(mLinearLayoutManager);
        rcvLoad.setHasFixedSize(true);
        rcvLoad.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rcvLoad.setAdapter(mAdapter);

        rcvLoad.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // 判断 RecyclerView 的状态是空闲时，同时是最后一个可见的 item 时才加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    Log.d(TAG, "onScrollStateChanged: 上拉加载");

                    total = total - count;

                    Log.d(TAG, "onScrollStateChanged: " + total);

                    if (total > count) {
                        Log.d(TAG, "onScrollStateChanged: >");
                        count = 10;
                        start = 10;

                        initData(start, count);
                    } else if (total < count && total > 0) {
                        Log.d(TAG, "onScrollStateChanged: <");
                        count = total;
                        start = 20;

                        initData(start, count);
                    } else if (total == 0) {
                        Log.d(TAG, "onScrollStateChanged: =");

                        count = total;

                        Toast.makeText(LoadActivity.this, "已经没有数据了", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // 最后一个可见的 item
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initData(int start, int count) {
        Observable<LoadDataBean> observable = mService.getLoadData(
                "0b2bdeda43b5688921839c8ecb20399b",
                "%E5%8C%97%E4%BA%AC",
                start,
                count,
                "",
                ""
        );
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoadDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");

                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(LoadDataBean loadDataBean) {
                        Log.d(TAG, "onNext: ");

                        if (isFirst) {
                            total = loadDataBean.getTotal();

                            mAdapter.setLoadDataList(loadDataBean.getSubjects());

                            isFirst = false;
                        } else {
                            mAdapter.addItem(loadDataBean.getSubjects());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }
}
