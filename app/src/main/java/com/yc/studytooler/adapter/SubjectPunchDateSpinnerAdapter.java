package com.yc.studytooler.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yc.studytooler.R;
import com.yc.studytooler.bean.Punch;
import com.yc.studytooler.utils.DateUtils;

import java.util.List;

/**
 * @ClassName SubjectPunchDateSpinnerAdapter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/21 21:43
 * @VERSION 1.0
 */
public class SubjectPunchDateSpinnerAdapter extends ArrayAdapter<Punch> {

   private Context context;
    public SubjectPunchDateSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Punch> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            // 如果 convertView 为空，说明是新的项，需要创建视图和 ViewHolder
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_subject_punch_date, parent, false);
            holder = new ViewHolder();
            holder.subject_punch_date = convertView.findViewById(R.id.tv_subject_punch_date); //
            convertView.setTag(holder);
        } else {
            // 重用已存在的视图和其对应的 ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        Punch punch = getItem(position);
        if (punch!= null) {
            String converted_date = DateUtils.convertCommonDate(punch.getSubject_punch_date());
            Log.d("SubjectPunchDateSpinnerAdapter","显示的转换日期是："+converted_date);
            holder.subject_punch_date.setText(converted_date);
        }

        return convertView;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return createItemView(position,convertView,parent);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_subject_punch_date, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.tv_subject_punch_date);
        Punch item = getItem(position);
        if (item != null) {
            textView.setText(DateUtils.convertCommonDate(item.getSubject_punch_date()));
        }
        return convertView;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position,convertView,parent);
    }

    static class ViewHolder {
        TextView subject_punch_date;


    }
}
