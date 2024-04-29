package com.yc.studytooler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.studytooler.R;
import com.yc.studytooler.bean.Punch;
import com.yc.studytooler.bean.Subject;
import com.yc.studytooler.utils.DateUtils;

import java.util.List;

/**
 * @ClassName SubjectPunchDateAdapter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/21 22:13
 * @VERSION 1.0
 */
public class SubjectPunchDateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Punch> punchList;
    private Context mContext; // 声明一个上下文对象

    private OnSubjectPunchDateListen mListener;

    public SubjectPunchDateAdapter(Context mContext, List<Punch> punchList, OnSubjectPunchDateListen mListener) {
        this.punchList = punchList;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    public void setSubjects(List<Punch> subjectPunchDateList) {
        this.punchList = subjectPunchDateList;
        notifyDataSetChanged();
    }

    public interface OnSubjectPunchDateListen{
        //点击事件的方法
        void onSubjectPunchDateClick(Punch punch);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 根据布局文件item_linear.xml生成视图对象
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_activity_punch, parent, false);
        return new ItemHolder(v,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        Punch punch = punchList.get(position);
        itemHolder.tv_subject_punch_date.setText(DateUtils.convertCommonDate(punch.getSubject_punch_date()));
    }

    @Override
    public int getItemCount() {
        return punchList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public TextView tv_subject_punch_date;

        public ItemHolder(@NonNull View itemView, OnSubjectPunchDateListen listener) {
            super(itemView);
            tv_subject_punch_date = itemView.findViewById(R.id.tv_item_punch_date);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getBindingAdapterPosition(); // 更新使用新的方法
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onSubjectPunchDateClick(punchList.get(position));
                    }
                }
            });
        }
    }
}
