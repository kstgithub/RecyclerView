package com.haocent.android.recyclerview.slide;

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
 * 双向滑动的子布局适配器 纵向滑动
 *
 * Created by Tnno Wu on 2018/03/07.
 */

public class SlideVerticalAdapter extends RecyclerView.Adapter<SlideVerticalAdapter.SlideVerticalViewHolder> {

    private static final String TAG = SlideVerticalAdapter.class.getSimpleName();

    private Context mContext;
    private List<String> mList = new ArrayList<>();

    public SlideVerticalAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public SlideVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.slide_vertical_include_item, parent, false);
        return new SlideVerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideVerticalViewHolder holder, int position) {
        holder.tvText.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class SlideVerticalViewHolder extends RecyclerView.ViewHolder {

        TextView tvText;

        public SlideVerticalViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_slide_vertical_text);
        }
    }
}
