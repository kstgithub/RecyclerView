package com.haocent.android.recyclerview.section;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

/**
 * 顶部悬浮 的 自定义 ItemDecoration
 *
 * Created by Tnno Wu on 2018/03/06.
 */

public class SectionItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = SectionItemDecoration.class.getSimpleName();

    private List<SectionDataBean> mList;
    private Paint mPaint;
    private Rect mBounds;

    private int mTitleHeight;
    private int COLOR_TITLE_BG = Color.parseColor("#FFCFCFCF");
    private int COLOR_TITLE_FONT = Color.parseColor("#FF000000");
    private int mTitleFontSize;


    public SectionItemDecoration(Context context, List<SectionDataBean> list) {
        super();
        mList = list;
        mPaint = new Paint();
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
        mPaint.setAntiAlias(true);
    }

    /**
     * 设置ItemView的内嵌偏移长度（inset）
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position > -1) { // RecyclerView 的item position在重置时可能为-1
            if (position == 0) {
                outRect.set(0, mTitleHeight, 0, 0);
            } else {
                if (null != mList.get(position).getArea() && !mList.get(position).getArea().equals(mList.get(position - 1).getArea())) {
                    outRect.set(0, mTitleHeight, 0, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }

    /**
     * 在子视图上设置绘制范围，并绘制内容
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position > -1) { // RecyclerView 的item position在重置时可能为-1
                if (position == 0) {
                    drawTitleArea(c, left, right, child, params, position);
                } else {
                    if (null != mList.get(position).getArea() && !mList.get(position).getArea().equals(mList.get(position - 1).getArea())) {
                        drawTitleArea(c, left, right, child, params, position);
                    } else {

                    }
                }
            }
        }
    }

    /**
     * 同样是绘制内容，但与 onDraw() 的区别是：绘制在图层的最上层
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();

        String tag = mList.get(pos).getArea();
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.getTextBounds(tag, 0, tag.length(), mBounds);
        c.drawText(tag, child.getPaddingLeft(), parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }

    /**
     * 绘制 Title 区域背景和文字的方法
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.getTextBounds(mList.get(position).getArea(), 0, mList.get(position).getArea().length(), mBounds);
        c.drawText(mList.get(position).getArea(), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }
}