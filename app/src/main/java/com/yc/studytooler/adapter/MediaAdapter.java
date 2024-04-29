package com.yc.studytooler.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yc.studytooler.ImagePreviewActivity;
import com.yc.studytooler.R;
import com.yc.studytooler.VideoPlayerActivity;
import com.yc.studytooler.bean.MediaItem;
import com.yc.studytooler.bean.SubjectContent;
import com.yc.studytooler.interfa.MediaActionListener;
import com.yc.studytooler.utils.MediaType;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName PunchAdapter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/11 14:45
 * @VERSION 1.0
 */
public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<MediaItem> mediaItems;

    private MediaActionListener mediaActionListener;
    public MediaAdapter(Context mContext, List<MediaItem> mediaItems,MediaActionListener listener) {
        this.mContext = mContext;
        this.mediaItems = mediaItems;
        this.mediaActionListener = listener;
        if (mediaItems != null && !mediaItems.isEmpty()) {
            Log.d("MediaAdapter","传过来的数据的帖子的ID是："+mediaItems.get(0).getSubjectContentId()
                                            +"帖子的所有媒体文件的个数是："+mediaItems.size()
            );
        }else{
            Log.d("MediaAdapter","传过来的数据的帖子可能为null，也可能为空"
            );
        }

    }


    @Override
    public int getItemViewType(int position) {

        MediaType type = MediaType.valueOf(mediaItems.get(position).getMediaType().toUpperCase());
        return type.ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MediaType type = MediaType.fromOrdinal(viewType);
        View view;
        switch (type) {
            case IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_media_image, parent, false);
                int size = parent.getResources().getDisplayMetrics().widthPixels / 3; // 计算每个图片视图的宽度和高度
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(size - 10, size); // 假设需要减去的10dp是间距
                layoutParams.setMargins(5, 5, 5, 5); // 设置间距
                view.setLayoutParams(layoutParams);
                return new ImageViewHolder(view);
            case VIDEO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_video_image, parent, false);
                return new VideoViewHolder(view);
            case AUDIO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_audio_image, parent, false);
                return new AudioViewHolder(view);
            case PDF:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_pdf_image, parent, false);
                return new PDFViewHolder(view,mediaActionListener);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private void setSquareLayout(View view, ViewGroup parent) {
        int size = parent.getWidth() / 3;  // Assuming RecyclerView has already calculated its size
        ViewGroup.LayoutParams params = new RecyclerView.LayoutParams(size, size);
        view.setLayoutParams(params);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MediaItem item = mediaItems.get(position);
        if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bind(item);
        } else if (holder instanceof VideoViewHolder) {
            ((VideoViewHolder) holder).bind(item);
        } else if (holder instanceof AudioViewHolder) {
            ((AudioViewHolder) holder).bind(item);
        } else if (holder instanceof PDFViewHolder) {
            ((PDFViewHolder) holder).bind(item);
        }

    }

    @Override
    public int getItemCount() {
        Log.d("MediaAdapter","图片的个数有多少个："+mediaItems.size());
        return mediaItems.size();
    }


    // 图片视图
    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.media_image);

        }

        void bind(MediaItem mediaItem) {

            Glide.with(itemView.getContext()).load(mediaItem.getUri()).into(imageView);

            itemView.setOnClickListener(v->{
                // Open full-screen activity or fragment with the image
                Context context = v.getContext();
                Intent intent = new Intent(context, ImagePreviewActivity.class);
                intent.putExtra("image_uri", mediaItem.getUri()); // Ensure MediaItem has a getUri method
                context.startActivity(intent);
            });
        }
    }


    // 音频视图
    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;
        ImageView playButton;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.video_thumbnail);
            playButton = itemView.findViewById(R.id.play_button);

            playButton.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), VideoPlayerActivity.class);
                intent.putExtra("video_uri", ((MediaItem) itemView.getTag()).getUri());
                v.getContext().startActivity(intent);
            });

        }

        void bind(MediaItem mediaItem) {
            itemView.setTag(mediaItem); // Store media item reference for click handling
            Glide.with(itemView.getContext()).load(mediaItem.getUri()).into(videoThumbnail);

        }

    }


    // 音频视图
    static class AudioViewHolder extends RecyclerView.ViewHolder {
        ImageView playIcon;
        TextView audioTitle;
        MediaPlayer mediaPlayer;
        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);
            playIcon = itemView.findViewById(R.id.play_icon);
            audioTitle = itemView.findViewById(R.id.audio_title);

            // 初始化 MediaPlayer
            mediaPlayer = new MediaPlayer();
            playIcon.setOnClickListener(v -> playAudio());  // 绑定播放控制到播放图标的点击事件
        }

        void bind(MediaItem mediaItem) {

            audioTitle.setText(mediaItem.getMediaTitle());
            try{
                mediaPlayer.reset();  // 重置 MediaPlayer 到其未初始化状态
                mediaPlayer.setDataSource(itemView.getContext(), Uri.parse(mediaItem.getUri()));
                mediaPlayer.prepareAsync(); // 异步准备音频
                mediaPlayer.setOnPreparedListener(mp -> playIcon.setImageResource(R.drawable.ic_play_audio));  // 准备完成后设置播放图标

                mediaPlayer.setOnCompletionListener(mp ->{
                    playIcon.setImageResource(R.drawable.ic_play_audio);// 当音频播放完成时，更改图标回播放图标
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            itemView.setOnClickListener(v -> {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playIcon.setImageResource(R.drawable.ic_play_audio);
                } else {
                    mediaPlayer.start();
                    playIcon.setImageResource(R.drawable.ic_pause_audio); // 确保你有一个 pause 图标在你的资源文件中
                }
            });
        }

        private void playAudio() {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playIcon.setImageResource(R.drawable.ic_play_audio);
            } else {
                mediaPlayer.start();
                playIcon.setImageResource(R.drawable.ic_pause_audio);
            }
        }

        // 清理资源
        void cleanup() {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }

    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof AudioViewHolder) {
            ((AudioViewHolder) holder).cleanup();  // 调用 cleanup 来释放资源
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        // 遍历所有 ViewHolder 实例，释放其中的资源
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
            if (holder instanceof AudioViewHolder) {
                ((AudioViewHolder) holder).cleanup();
            }
        }
    }

    // pdf视图
    static class PDFViewHolder extends RecyclerView.ViewHolder {
        TextView pdfTitle;
        ImageView pdfIcon;
        private MediaActionListener listener; // Store the listener as a field
        public PDFViewHolder(@NonNull View itemView,MediaActionListener listener) {
            super(itemView);
            pdfTitle = itemView.findViewById(R.id.pdf_title);
            pdfIcon = itemView.findViewById(R.id.pdf_icon);
            this.listener = listener;

        }

        void bind(MediaItem mediaItem) {
            // Set PDF title
            pdfTitle.setText(mediaItem.getMediaTitle());

            itemView.setOnClickListener(v -> {
                // Handle PDF view logic
                if (mediaItem != null) {
                    listener.onMediaAction("PDF",mediaItem.getUri());
                }
//                viewPDF(mediaItem.getUri());
            });
        }

        private void viewPDF(String pdfUri) {
            // 创建一个意图来打开 PDF
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(pdfUri), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // 验证是否有应用可以处理 PDF
            Context context = itemView.getContext();
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                // 如果没有应用可以打开 PDF，则提示用户
                Toast.makeText(context, "No application found to open PDF", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
