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

import java.util.List;

/**
 * @ClassName SemesterAdapter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/20 11:50
 * @VERSION 1.0
 */
public class SemesterSpinnerAdapter extends ArrayAdapter<Semester> {

    private Context context;
//    private List<Semester> semesters;

    public SemesterSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Semester> objects) {
        super(context, resource, objects);
        this.context = context;
//        this.semesters = objects;
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_semester, parent, false);
            holder = new ViewHolder();
            holder.semester_name = convertView.findViewById(R.id.tv_semester_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Semester semester = getItem(position);
        if (semester != null) {
            holder.semester_name.setText(semester.getSemester_name());
        }

        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    static class ViewHolder {
        TextView semester_name;
    }
}
