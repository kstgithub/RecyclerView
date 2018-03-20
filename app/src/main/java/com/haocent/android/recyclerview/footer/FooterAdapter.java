package com.haocent.android.recyclerview.footer;

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
 * Created by Tnno Wu on 2018/03/20.
 */

public class FooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = FooterAdapter.class.getSimpleName();

    private Context mContext;

    private List<String> mList = new ArrayList<>();

    public static final int ITEM_TYPE_CONTENT = 0;
    public static final int ITEM_TYPE_FOOTER = 1;

    private int footerCount = 1;    // 底部 View 个数

    public FooterAdapter(Context context) {
        mContext = context;
    }

    public void setFooterListData(List<String> list) {
        mList = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_CONTENT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.footer_content_recycle_item, parent, false);
            return new ContentViewHolder(view);
        } else if (viewType == ITEM_TYPE_FOOTER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.footer_footer_recycle_item, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).tvNum.setText(position + 1 + "");
            ((ContentViewHolder) holder).tvContent.setText(mList.get(position));
        } else if (holder instanceof FooterViewHolder) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (footerCount != 0 && position >= (getContentItemCount())) {
            // 底部 View
            return ITEM_TYPE_FOOTER;
        } else {
            // 内容 View
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return getContentItemCount() + footerCount;
    }

    /**
     * 内容 ViewHolder
     */
    public class ContentViewHolder extends RecyclerView.ViewHolder {

        TextView tvNum, tvContent;

        public ContentViewHolder(View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    /**
     * 底部 ViewHolder
     */
    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    // 内容长度
    private int getContentItemCount() {
        return mList.size();
    }

    /**
     * 判断当前 item 是否是 FooterView
     */
    public boolean isFooterView(int position) {
        return footerCount != 0 && position >= (footerCount + getContentItemCount());
    }
}
