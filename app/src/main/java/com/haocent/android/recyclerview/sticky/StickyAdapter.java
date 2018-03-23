package com.haocent.android.recyclerview.sticky;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocent.android.recyclerview.R;

import java.util.List;

/**
 * 顶部悬浮 的 Adapter
 *
 * Created by Tnno Wu on 2018/03/23.
 */

public class StickyAdapter extends RecyclerView.Adapter<StickyAdapter.StickyViewHolder> {

    private static final String TAG = StickyAdapter.class.getSimpleName();

    private Context mContext;

    private List<StickyData> mList;

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    public StickyAdapter(Context context) {
        mContext = context;
    }

    public void setStickyDataList(List<StickyData> list) {
        mList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StickyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sticky_recycle_item, parent, false);
        return new StickyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickyViewHolder holder, int position) {
        StickyData stickyData = mList.get(position);

        holder.tvTeam.setText(stickyData.getTeam());

        if (position == 0) {
            holder.tvArea.setVisibility(View.VISIBLE);
            holder.tvArea.setText(stickyData.area);
            holder.itemView.setTag(FIRST_STICKY_VIEW);
        } else {
            if (!TextUtils.equals(stickyData.area, mList.get(position - 1).area)) {
                holder.tvArea.setVisibility(View.VISIBLE);
                holder.tvArea.setText(stickyData.area);
                holder.itemView.setTag(HAS_STICKY_VIEW);
            } else {
                holder.tvArea.setVisibility(View.GONE);
                holder.itemView.setTag(NONE_STICKY_VIEW);
            }
        }
        holder.itemView.setContentDescription(stickyData.area);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class StickyViewHolder extends RecyclerView.ViewHolder {

        TextView tvArea, tvTeam;

        public StickyViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tv_sticky_header_view);
            tvTeam = itemView.findViewById(R.id.tv_team);
        }
    }
}
