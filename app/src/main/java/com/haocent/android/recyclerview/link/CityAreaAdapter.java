package com.haocent.android.recyclerview.link;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocent.android.recyclerview.R;

import java.util.List;

public class CityAreaAdapter extends RecyclerView.Adapter<CityAreaAdapter.CityAreaViewHolder> {

    private Context mContext;

    private List<String> mAreaList;

    private int checkedPosition;

    private OnItemClickListener mListener;

    public CityAreaAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void setCityAreaList(List<String> list) {
        mAreaList = list;

        notifyDataSetChanged();
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CityAreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.link_city_area_recycle_item, parent, false);
        return new CityAreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAreaViewHolder holder, final int position) {
        holder.tvName.setText(mAreaList.get(position));

        if (position == checkedPosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F0F0F0"));
            holder.tvName.setTextColor(Color.parseColor("#3F51B5"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.tvName.setTextColor(Color.parseColor("#1E1D1D"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAreaList == null ? 0 : mAreaList.size();
    }

    public class CityAreaViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        public CityAreaViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_sort);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
