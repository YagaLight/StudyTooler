package com.yc.studytooler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.studytooler.R;
import com.yc.studytooler.bean.Subject;

import java.util.List;

/**
 * @ClassName SubjectAdapter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/21 13:16
 * @VERSION 1.0
 */
public class SubjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Subject> subjectList;
    private Context mContext; // 声明一个上下文对象

    private OnSubjectClickListener mListener; // 接口监听器
    public SubjectAdapter(Context mContext,List<Subject> subjectList, OnSubjectClickListener listener) {
        this.subjectList = subjectList;
        this.mContext = mContext;
        this.mListener = listener;
    }

    public void setSubjects(List<Subject> newSubjects) {
        this.subjectList = newSubjects;
        notifyDataSetChanged();
    }

    public interface OnSubjectClickListener {
        void onSubjectClick(Subject subject); // 点击事件的方法
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 根据布局文件item_linear.xml生成视图对象
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_activity_subject, parent, false);
        return new ItemHolder(v,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder)holder;
        Subject subject = subjectList.get(position);
        itemHolder.tv_subject_name.setText(subject.getSubject_name());
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public TextView tv_subject_name;

        public ItemHolder(@NonNull View itemView,OnSubjectClickListener listener) {
            super(itemView);
            tv_subject_name = itemView.findViewById(R.id.tv_item_subject);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getBindingAdapterPosition(); // 更新使用新的方法
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onSubjectClick(subjectList.get(position));
                    }
                }
            });
        }
    }
}
