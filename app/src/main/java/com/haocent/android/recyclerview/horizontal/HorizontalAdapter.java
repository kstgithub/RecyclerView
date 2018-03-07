package com.haocent.android.recyclerview.horizontal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocent.android.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 横向布局 的 Adapter
 *
 * Created by Tnno Wu on 2018/03/05.
 */

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private static final String TAG = HorizontalAdapter.class.getSimpleName();

    private Context mContext;

    private List<String> mList = new ArrayList<>();

    public HorizontalAdapter(Context context) {
        mContext = context;
    }

    public void setHorizontalDataList(List<String> list) {
        Log.d(TAG, "setHorizontalDataList: " + list.size());

        mList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.horizontal_recycle_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        holder.tvNum.setText(position + 1 + "");
        holder.tvContent.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        TextView tvNum, tvContent;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
