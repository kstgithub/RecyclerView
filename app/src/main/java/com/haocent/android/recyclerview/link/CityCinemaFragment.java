package com.haocent.android.recyclerview.link;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haocent.android.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class CityCinemaFragment extends Fragment implements CheckListener, CityCinemaAdapter.OnItemClickListener {

    private RecyclerView mRv;

    private CityCinemaAdapter mAdapter;

    private GridLayoutManager mManager;

    private List<CityCinema> mDatas = new ArrayList<>();

    private ItemHeaderDecoration mDecoration;

    private boolean move = false;

    private int mIndex = 0;

    private CheckListener checkListener;

    public CityCinemaFragment() {

    }

    public static CityCinemaFragment newInstance() {
        return new CityCinemaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.link_city_cinema_fragment, container, false);

        initView(view);

        initPresenter();

        return view;
    }

    private void initView(View view) {
        mRv = view.findViewById(R.id.rv);

        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    move = false;
                    int n = mIndex - mManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < mRv.getChildCount()) {
                        int top = mRv.getChildAt(n).getTop();
                        mRv.smoothScrollBy(0, top);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (move) {
                    move = false;
                    int n = mIndex - mManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < mRv.getChildCount()) {
                        int top = mRv.getChildAt(n).getTop();
                        mRv.scrollBy(0, top);
                    }
                }
            }
        });
    }

    private void initPresenter() {
        mManager = new GridLayoutManager(getActivity(), 3);

        // 通过 isTitle 的标志来判断是否是 title
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mDatas.get(position).isTitle() ? 3 : 1;
            }
        });
        mRv.setLayoutManager(mManager);

        mAdapter = new CityCinemaAdapter(getActivity(), this);

        mRv.setAdapter(mAdapter);
        mDecoration = new ItemHeaderDecoration(getActivity(), mDatas);
        mRv.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(checkListener);

        initData();
    }

    private void initData() {
        ArrayList<CityArea.CategoryOneArrayBean> rightList = getArguments().getParcelableArrayList("right");
        for (int i = 0; i < rightList.size(); i++) {
            CityCinema head = new CityCinema(rightList.get(i).getName());
            //头部设置为true
            head.setTitle(true);
            head.setTitleName(rightList.get(i).getName());
            head.setTag(String.valueOf(i));
            mDatas.add(head);
            List<CityArea.CategoryOneArrayBean.CategoryTwoArrayBean> categoryTwoArray = rightList.get(i).getCategoryTwoArray();
            for (int j = 0; j < categoryTwoArray.size(); j++) {
                CityCinema body = new CityCinema(categoryTwoArray.get(j).getName());
                body.setTag(String.valueOf(i));
                String name = rightList.get(i).getName();
                body.setTitleName(name);
                mDatas.add(body);
            }
        }

        mAdapter.setCityCinemaList(mDatas);

        mDecoration.setData(mDatas);
    }

    public void setData(int n) {
        mIndex = n;
        mRv.stopScroll();
        smoothMoveToPosition(n);
    }

    public void setListener(CheckListener listener) {
        this.checkListener = listener;
    }

    private void smoothMoveToPosition(int n) {
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRv.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRv.getChildAt(n - firstItem).getTop();
            mRv.scrollBy(0, top);
        } else {
            mRv.scrollToPosition(n);
            move = true;
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        checkListener.check(position, isScroll);
    }

    @Override
    public void onItemClick(int position) {
        Snackbar.make(mRv, "当前点击的是：" + mDatas.get(position).getName(), Snackbar.LENGTH_SHORT).show();
    }
}
