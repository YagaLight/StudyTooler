package com.yc.studytooler.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @ClassName ImageSpinnerAdapter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/17 0:55
 * @VERSION 1.0
 */
public class ImageSpinnerAdapter extends ArrayAdapter<Integer> {
    private Context mContext;
    private Integer[] images;

    public ImageSpinnerAdapter(@NonNull Context context, Integer[] images) {
        super(context, android.R.layout.simple_spinner_item, images);
        this.mContext = context;
        this.images = images;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createImageView(position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createImageView(position);
    }

    private View createImageView(int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(images[position]);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return imageView;
    }

}
