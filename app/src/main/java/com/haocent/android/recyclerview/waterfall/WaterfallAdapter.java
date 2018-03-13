package com.haocent.android.recyclerview.waterfall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.haocent.android.recyclerview.R;

import java.util.List;

/**
 * 瀑布流 的 Adapter
 *
 * Created by Tnno Wu on 2018/03/13.
 */

public class WaterfallAdapter extends RecyclerView.Adapter<WaterfallAdapter.WaterfallViewHolder> {

    private static final String TAG = WaterfallAdapter.class.getSimpleName();

    private Context mContext;

    private List<WaterfallBean.ResultsBean> mList;

    public WaterfallAdapter(Context context) {
        mContext = context;
    }

    public void setWaterfallData(List<WaterfallBean.ResultsBean> list) {
        mList = list;

        Log.d(TAG, "setWaterfallData: " + mList.size());

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WaterfallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.waterfall_recycle_item, parent, false);
        return new WaterfallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterfallViewHolder holder, int position) {
        GlideApp.with(mContext).load(mList.get(position).getUrl()).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class WaterfallViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;

        public WaterfallViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }
}
