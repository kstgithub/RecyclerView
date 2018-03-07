package com.haocent.android.recyclerview.section;

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
 * 顶部悬浮 的 Adapter
 *
 * Created by Tnno Wu on 2018/03/05.
 */

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {

    private static final String TAG = SectionAdapter.class.getSimpleName();

    private Context mContext;

    private List<SectionDataBean> mList = new ArrayList<>();

    public SectionAdapter(Context context) {
        mContext = context;
    }

    public void setHeaderDataList(List<SectionDataBean> list) {
        mList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.section_recycle_item, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        holder.tvTeam.setText(mList.get(position).getTeam());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {

        TextView tvTeam;

        public SectionViewHolder(View itemView) {
            super(itemView);
            tvTeam = itemView.findViewById(R.id.tv_team);
        }
    }
}
