package com.yc.studytooler;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;



/**
 * @ClassName VideoPlayerActivity
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/24 13:39
 * @VERSION 1.0
 */
public class VideoPlayerActivity extends AppCompatActivity {
    private ExoPlayer player;
    private PlayerView playerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        playerView = findViewById(R.id.player_view);
        String videoUri = getIntent().getStringExtra("video_uri");

        // 使用 ExoPlayer 的新版本初始化
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
