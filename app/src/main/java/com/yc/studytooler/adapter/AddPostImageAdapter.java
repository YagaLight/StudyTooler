package com.yc.studytooler.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yc.studytooler.R;

import java.util.List;

/**
 * @ClassName AddPostImageAdapter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/22 20:05
 * @VERSION 1.0
 */
public class AddPostImageAdapter extends RecyclerView.Adapter<AddPostImageAdapter.ImageViewHolder>{

    private Context context;
    private List<Uri> images;
    private OnItemClickListener onItemClickListener;

    public AddPostImageAdapter(Context context, List<Uri> images, OnItemClickListener listener) {
        this.context = context;
        this.images = images;
        this.onItemClickListener = listener;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri uri = images.get(position);
        Glide.with(context)
                .load(uri)
                .centerCrop()
                .into(holder.imgThumbnail);

        holder.imgRemove.setOnClickListener(v -> {
            images.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, images.size());
            if (onItemClickListener != null) {
                onItemClickListener.onItemRemoved();
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void addImage(Uri uri) {
        images.add(uri);
        notifyItemInserted(images.size() - 1);
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail;
        ImageView imgRemove;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            imgRemove = itemView.findViewById(R.id.img_remove);
        }
    }

    public interface OnItemClickListener {
        void onItemRemoved();
    }
}
