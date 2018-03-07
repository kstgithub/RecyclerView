package com.haocent.android.recyclerview.click;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.haocent.android.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Item 点击对应的 Adapter
 *
 * Created by Tnno Wu on 2018/03/05.
 */

public class RcvClickAdapter extends RecyclerView.Adapter<RcvClickAdapter.RcvClickViewHolder> {

    private static final String TAG = RcvClickAdapter.class.getSimpleName();

    private Context mContext;

    private List<String> mList = new ArrayList<>();

    private OnItemClickListener mListener;

    public RcvClickAdapter(Context context, OnItemClickListener listener) {
        mContext = context;

        mListener = listener;
    }

    public void setRcvClickDataList(List<String> list) {
        Log.d(TAG, "setRcvClickDataList: " + list.size());

        mList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RcvClickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rcv_click_recycle_item, parent, false);
        return new RcvClickViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcvClickViewHolder holder, int position) {
        final String content = mList.get(position);

        holder.tvNum.setText(position + 1 + "");
        holder.tvContent.setText(content);

        // 第一种写法：直接在 Adapter 里写
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "你点击的是：" + content, Toast.LENGTH_SHORT).show();
//            }
//        });

        // 第二种写法：将点击事件传到 Activity 里去写
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(content);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class RcvClickViewHolder extends RecyclerView.ViewHolder {

        TextView tvNum, tvContent;

        public RcvClickViewHolder(View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String content);
    }
}
