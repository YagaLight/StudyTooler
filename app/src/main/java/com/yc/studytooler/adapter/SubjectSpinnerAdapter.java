package com.yc.studytooler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yc.studytooler.R;
import com.yc.studytooler.bean.Semester;
import com.yc.studytooler.bean.Subject;

import java.util.List;

/**
 * @ClassName SubjectSpinnerAdapter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/21 1:13
 * @VERSION 1.0
 */
public class SubjectSpinnerAdapter extends ArrayAdapter<Subject> {

    private Context context;

//    private List<Subject> subjects;

    public SubjectSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Subject> objects) {
        super(context, resource, objects);
        this.context = context;
//        this.subjects = objects;
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            // 如果 convertView 为空，说明是新的项，需要创建视图和 ViewHolder
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_subject, parent, false);
            holder = new ViewHolder();
            holder.subject_name = convertView.findViewById(R.id.tv_subject_name); //
            convertView.setTag(holder);
        } else {
            // 重用已存在的视图和其对应的 ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        Subject subject = getItem(position);
//        Subject subject = subjects.get(position);
        if (subject != null) {
            holder.subject_name.setText(subject.getSubject_name());
        }

        return convertView;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position,convertView,parent);
    }

    static class ViewHolder {
        TextView subject_name;


    }

}
