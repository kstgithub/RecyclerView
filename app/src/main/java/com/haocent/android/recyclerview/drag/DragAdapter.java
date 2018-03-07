package com.haocent.android.recyclerview.drag;

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
 * Item 拖动 的 Adapter
 *
 * Created by Tnno Wu on 2018/03/06.
 */

public class DragAdapter extends RecyclerView.Adapter<DragAdapter.DragViewHolder> implements ItemTouchHelperListener {

    private static final String TAG = DragAdapter.class.getSimpleName();

    private Context mContext;

    private List<String> mList = new ArrayList<>();

    public DragAdapter(Context context) {
        mContext = context;
    }

    public void setDragDataList(List<String> list) {
        mList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.drag_recycle_item, parent, false);
        return new DragViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DragViewHolder holder, int position) {
        holder.tvContent.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        String prev = mList.remove(fromPosition);
        mList.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    public class DragViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent;

        public DragViewHolder(View itemView) {
            super(itemView);

            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
