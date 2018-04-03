package com.haocent.android.recyclerview.link;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocent.android.recyclerview.R;

import java.util.List;

public class CityCinemaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<CityCinema> mList;

    private OnItemClickListener mListener;

    public CityCinemaAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void setCityCinemaList(List<CityCinema> list) {
        mList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.link_city_cinema_title_recycle_item, parent, false);
            return new TitleViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.link_city_cinema_content_recycle_item, parent, false);
            return new ContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).tvArea.setText(mList.get(position).getName());
        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).tvCinema.setText(mList.get(position).getName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).isTitle() ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        TextView tvArea;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tv_title);
        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        TextView tvCinema;

        public ContentViewHolder(View itemView) {
            super(itemView);
            tvCinema = itemView.findViewById(R.id.tvCity);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}