package com.yc.studytooler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


/**
 * @ClassName ImagePreviewActivity
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/24 1:07
 * @VERSION 1.0
 */
public class ImagePreviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        String uri = getIntent().getStringExtra("image_uri");
        Glide.with(this).load(uri).into(photoView);
    }
}
