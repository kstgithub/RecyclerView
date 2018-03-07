package com.haocent.android.recyclerview.load;

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
 * 上拉加载 的 Adapter
 *
 * Created by Tnno Wu on 2018/03/06.
 */

public class LoadAdapter extends RecyclerView.Adapter<LoadAdapter.LoadViewHolder> {

    private static final String TAG = LoadAdapter.class.getSimpleName();

    private Context mContext;

    private List<LoadDataBean.SubjectsBean> mList = new ArrayList<>();

    public LoadAdapter(Context context) {
        mContext = context;
    }

    public void setLoadDataList(List<LoadDataBean.SubjectsBean> list) {
        mList = list;

        notifyDataSetChanged();
    }

    public void addItem(List<LoadDataBean.SubjectsBean> beanList) {
        mList.addAll(beanList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LoadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.load_recycle_item, parent, false);
        return new LoadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadViewHolder holder, int position) {
        holder.tvTitle.setText("片名：" + mList.get(position).getTitle());
        holder.tvMainlandPubdate.setText("日期：" + mList.get(position).getMainland_pubdate());
        holder.tvYear.setText("年代：" + mList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class LoadViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvMainlandPubdate, tvYear;

        public LoadViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvMainlandPubdate = itemView.findViewById(R.id.tv_mainland_pubdate);
            tvYear = itemView.findViewById(R.id.tv_year);
        }
    }
}
