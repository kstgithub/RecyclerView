package com.haocent.android.recyclerview.swipe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocent.android.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 滑动删除 的 Adapter
 *
 * Created by Tnno Wu on 2018/03/06.
 */

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder> implements ItemTouchHelperListener {

    private static final String TAG = SwipeAdapter.class.getSimpleName();

    private Context mContext;

    private List<String> mList = new ArrayList<>();

    public SwipeAdapter(Context context) {
        mContext = context;
    }

    public void setSwipeDataList(List<String> list) {
        mList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.swipe_recycle_item, parent, false);
        return new SwipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeViewHolder holder, int position) {
        holder.tvContent.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        if (position < 0 || position > getItemCount()) {
            return;
        }

        mList.remove(position);
        notifyItemRemoved(position);

        // 解决 RecyclerView 删除 Item 导致位置错乱的问题
        if (position != mList.size()) {
            notifyItemRangeChanged(position, mList.size() - position);
        }
    }

    public class SwipeViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent;

        public SwipeViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
