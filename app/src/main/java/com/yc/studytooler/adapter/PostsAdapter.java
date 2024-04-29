package com.yc.studytooler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.studytooler.R;
import com.yc.studytooler.bean.MediaItem;
import com.yc.studytooler.bean.SubjectContent;
import com.yc.studytooler.interfa.MediaActionListener;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @ClassName PostsAdapter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/22 13:51
 * @VERSION 1.0
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder>{
    private Context context;
    private List<SubjectContent> postList;
    private Map<SubjectContent, List<MediaItem>> mediaMap;
    private MediaActionListener mediaActionListener;

    public PostsAdapter(Context context, List<SubjectContent> postList, Map<SubjectContent, List<MediaItem>> mediaMap,MediaActionListener listener) {
        this.context = context;
        this.postList = postList;
        this.mediaMap = mediaMap;
        this.mediaActionListener = listener;
    }

    public void updateData(List<SubjectContent> postList,Map<SubjectContent, List<MediaItem>> mediaMap){
        this.postList = postList;
        this.mediaMap = mediaMap;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PostsAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_punch_content, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.PostViewHolder holder, int position) {
        SubjectContent post = postList.get(position);
        holder.bind(post, mediaMap.get(post),mediaActionListener);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    class PostViewHolder extends RecyclerView.ViewHolder{

        TextView userName, postTime, postText;
        RecyclerView mediaRecyclerView;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tv_post_user_name);
            postTime = itemView.findViewById(R.id.tv_post_time);
            postText = itemView.findViewById(R.id.tv_post_text);
            mediaRecyclerView = itemView.findViewById(R.id.rv_media_recycler_view);
//            mediaRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//            mediaRecyclerView.setLayoutManager(new GridLayoutManager(context, 3)); // 设置为每行显示3个项目
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            mediaRecyclerView.setLayoutManager(layoutManager);
        }

        void bind(SubjectContent post, List<MediaItem> mediaItems,MediaActionListener listener) {
            userName.setText(post.getUser_name());
            postTime.setText(new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(post.getPunch_time()));
            if (post.getContentType().equals("text")) {
                postText.setText(post.getContentText());
            }else{
                postText.setText(post.getTitle());
            }
            MediaAdapter mediaAdapter = new MediaAdapter(context, mediaItems,listener);
            mediaRecyclerView.setAdapter(mediaAdapter);
        }
    }
}
