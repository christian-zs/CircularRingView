package com.example.zs.circularringview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zs.library.CircularRingView;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:
 * Created by zs on 2017/8/18.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private ArrayList<Date> mData = new ArrayList<>();
    private Context mContext;
    private int mWeekStarIndex = 0;

    @Override
    public int getItemCount() {
        return mData.size() + mWeekStarIndex;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_calendar, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 设置初始数据
     */
    public void setData(ArrayList<Date> data) {
        mData.clear();
        addData(data);
    }

    /**
     * 添加数据
     */
    public void addData(ArrayList<Date> data) {
        if (data != null && data.size() > 0)
            mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position < mWeekStarIndex) {
            holder.itemView.setVisibility(View.GONE);
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.circularRingView.setCenterDate(mData.get(position - mWeekStarIndex));
            holder.circularRingView.setSignDateRecords(getSignRecord());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.circularRingView.setCenterColor(ContextCompat.getColor(mContext, R.color.inside_circular_background1));
                    holder.circularRingView.setCenterTextColor(Color.WHITE);
                }
            });
        }
    }

    /**
     * 设置开始日期为周几
     *
     * @param index 这周开始位置
     */
    public void setWeekStartIndex(int index) {
        mWeekStarIndex = index;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view)
        CircularRingView circularRingView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private ArrayList<Float> getSignRecord() {
        ArrayList<Float> signRecords = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            signRecords.add((float) (20 + i * 46));
        }
        return signRecords;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }
}
