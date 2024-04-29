package com.yc.studytooler;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;
import com.yc.studytooler.adapter.AddPostImageAdapter;
import com.yc.studytooler.bean.MediaItem;
import com.yc.studytooler.bean.SubjectContent;
import com.yc.studytooler.utils.DateUtils;
import com.yc.studytooler.viewmodel.SubjectContentViewModel;
import com.yc.studytooler.viewmodel.SubjectContentViewModelFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName AddPostActivity
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/22 14:53
 * @VERSION 1.0
 */
public class AddPostActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE_STORAGE = 101;

    private static final int PERMISSION_CODE_AUDIO = 102;  // 定义一个常量作为请求码


    private static final int PERMISSION_CODE_PDF = 1000;

    private Toolbar tl_head;
    private Spinner spinnerMediaType;
    private FrameLayout frameMediaInput;

    private String user_name;
    private String semester_name;
    private String subject_name;
    private Date punch_date;
    private SubjectContentViewModelFactory subjectContentViewModelFactory;
    private SubjectContentViewModel subjectContentViewModel;


    //添加图片的适配器
    private AddPostImageAdapter adapter;

    //存储内容类型
    private String post_content_type;

    //存储纯文本数据
    private String post_content_text;

    //存储非文本的留言
    private String post_title;

    //存储音频的文件标题
    private String post_audio_title;

    //存储pdf文件的文件标题
    private String post_pdf_title;

    //存储图片的集合
    private List<Uri> imageUris;

    //存储音频的路径
    private String audioFilePath;

    //存储视频的路径
    private List<Uri> videoFileUris;

    //存储PDF文件
    private List<Uri> pdfFileUris;
    private ActivityResultLauncher<String> Launcher_images;
    private ActivityResultLauncher<String[]> Launcher_PDF;

    private ActivityResultLauncher<String> mLauncher_video;



    //音频
    private TextView tvAudioPath;
    private Button btnRecord;
    private Button btnStop;

    private MediaRecorder mediaRecorder;

    //以下是视频视图所用到的组件
    private VideoView videoPreview;
    private ImageView deleteVideoButton;


    //pdf
    private PDFView pdfView;
    private TextView pdfName;
    private ImageView deletePDFButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        setupToolBar();
        receiveDataFromSharedViewModel();

        setupViewModel();
        setupBasicView();
        setupRegisterResultLauncher();
    }

    private void setupRegisterResultLauncher() {

        //启动系统图库
        Launcher_images = registerForActivityResult(new ActivityResultContracts.GetMultipleContents(), uris -> {
            imageUris.clear();
            imageUris.addAll(uris);
            Log.d("AddPostActivity","进入到启动图库的启动器里:大小是"+imageUris.size());
            for (Uri uri:uris){
                adapter.addImage(uri);
            }
        });

        //启动系统音频


        //启动系统视频
         mLauncher_video = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                videoPreview.setVideoURI(uri);
                videoPreview.start();
                videoPreview.setVisibility(View.VISIBLE); // 确保VideoView是可见的
                deleteVideoButton.setVisibility(View.VISIBLE); // Show delete button when video is loaded
                videoFileUris.clear();
                videoFileUris.add(uri);
            }
        });


        //启动系统PDF文件
        Launcher_PDF = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                result -> {
                    if (result != null) {
                        // 请求持久化权限
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                        pdfFileUris.clear();
                        try{
                            Log.d("AddPostActivity","程序执行0");
                            // 请求持久化权限
                            getContentResolver().takePersistableUriPermission(
                                    result,
                                    takeFlags
                            );
                            Log.d("AddPostActivity","程序执行1");
                            pdfFileUris.add(result);
                            // 处理选择的 PDF 文件
                            pdfView.fromUri(result)
                                    .pages(0) // only display the first page
                                    .load();
                            Log.d("AddPostActivity","程序执行2");
                            // 获取并显示文件名
                            Cursor cursor = getContentResolver().query(result, null, null, null, null);
                            String displayName = "";
                            if (cursor != null && cursor.moveToFirst()) {
                                Log.d("AddPostActivity","程序执行3");
                                int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                                if (index != -1) {  // 确保索引有效
                                    Log.d("AddPostActivity","程序执行4");
                                    displayName = cursor.getString(index);
                                }
                                cursor.close();
                                Log.d("AddPostActivity","程序执行5");
                            }

                            pdfName.setText(displayName);
                            Log.d("AddPostActivity",new File(result.getPath()).getName());
                            Log.d("AddPostActivity",displayName);

                            pdfView.setVisibility(View.VISIBLE);
                            deletePDFButton.setVisibility(View.VISIBLE);
                        }catch (SecurityException e){
                            Log.e("AddPostActivity", "Failed to handle PDF: ", e);
                            Toast.makeText(this, "获取pdf文件失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }

    private void receiveDataFromSharedViewModel() {
        // 所有数据都已加载，现在可以更新 UI
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user_name = bundle.getString("user_name");
            semester_name = bundle.getString("semester_name");
            subject_name = bundle.getString("subject_name");
            punch_date = (Date) bundle.getSerializable("punch_date");
        }
        if (user_name != null) {
            Log.d("AddPostActivity","我从PostActivity里接受到了："+ user_name);
        }
        if (semester_name != null) {
            Log.d("AddPostActivity","我从PostActivity里接受到了："+semester_name);
        }
        if (subject_name != null) {
            Log.d("AddPostActivity","我从PostActivity里接受到了："+subject_name);
        }
        if (punch_date != null) {
            Log.d("AddPostActivity", "我从PostActivity里接受到了："+DateUtils.convertCommonDate(punch_date));
        }
    }


    private void setupBasicView() {
        spinnerMediaType = findViewById(R.id.spinner_media_type);
        frameMediaInput = findViewById(R.id.frame_media_input);
        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.media_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMediaType.setAdapter(adapter);

        spinnerMediaType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateInputArea(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateInputArea(int mediaTypeIndex) {
        // 根据mediaTypeIndex加载不同的视图到frameMediaInput
        frameMediaInput.removeAllViews();
        View inputView;
        switch (mediaTypeIndex) {
            case 0: // 纯文本
                inputView = getLayoutInflater().inflate(R.layout.input_text_add_post, frameMediaInput, false);
                post_content_type = "text";
                setupTextSubmission(inputView);
                break;
            case 1: // 图片
                inputView = getLayoutInflater().inflate(R.layout.input_image_add_post, frameMediaInput, false);
                post_content_type = "image";
                setupImageSubmission(inputView);
                break;
            case 2: // 语音
                inputView = getLayoutInflater().inflate(R.layout.input_audio_add_post, frameMediaInput, false);
                post_content_type = "audio";
                setupAudioSubmission(inputView);
                break;
            case 3: // 视频
                inputView = getLayoutInflater().inflate(R.layout.input_video_add_post, frameMediaInput, false);
                post_content_type = "video";
                setupVideoSubmission(inputView);
                break;
            case 4: // PDF
                inputView = getLayoutInflater().inflate(R.layout.input_pdf_add_post, frameMediaInput, false);
                post_content_type = "pdf";
                setupPDFSubmission(inputView);
                break;
            default:
                throw new IllegalArgumentException("Unsupported media type");
        }
        frameMediaInput.addView(inputView);
    }

    private void setupPDFSubmission(View inputView) {
        pdfView = inputView.findViewById(R.id.pdf_viewer);
        pdfName = inputView.findViewById(R.id.tv_pdf_name);
        Button selectPDFButton = inputView.findViewById(R.id.btn_select_pdf);
        deletePDFButton = inputView.findViewById(R.id.img_delete_pdf);
        EditText et_post_title = inputView.findViewById(R.id.et_post_title);
        EditText et_post_pdf_title = inputView.findViewById(R.id.et_post_pdf_title);
        pdfFileUris = new ArrayList<>();

        selectPDFButton.setOnClickListener(v -> {
            // 检查权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestStoragePermissionForPDF();
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            } else {
                Launcher_PDF.launch(new String[]{"application/pdf"});
            }
        });

        deletePDFButton.setOnClickListener(v -> {
            if (!pdfFileUris.isEmpty()) {
                pdfFileUris.clear();
                pdfView.invalidate(); // 刷新PDFView，确保视图被清空
                pdfView.setVisibility(View.GONE);
            }
            pdfFileUris.clear(); // 清除PDF文件的URI列表
            pdfName.setText("没有文件被选择");
            deletePDFButton.setVisibility(View.GONE);
        });

       et_post_title.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
                post_title = editable.toString();
           }
       });

       et_post_pdf_title.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
                post_pdf_title = editable.toString()+".pdf";
           }
       });
    }

    private void requestStoragePermissionForPDF() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed to pick PDF files from your storage.")
                    .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_PDF))
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_PDF);
        }
    }

    private void setupVideoSubmission(View inputView) {
        videoPreview = inputView.findViewById(R.id.video_preview);
        Button selectVideoButton = inputView.findViewById(R.id.btn_select_video);
        deleteVideoButton = inputView.findViewById(R.id.img_delete_video);
        EditText et_post_title = inputView.findViewById(R.id.et_post_title);
        videoFileUris = new ArrayList<>();

        // Set up select video button
        selectVideoButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, launch the video picker
                mLauncher_video.launch("video/*");
            } else {

                // Request storage permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_STORAGE);
            }
        });

        // Set up delete video button
        deleteVideoButton.setOnClickListener(v -> {
            if (videoPreview.isPlaying()) {
                videoPreview.stopPlayback();
            }
            videoPreview.setVideoURI(null);
            videoFileUris.clear();
            videoPreview.setVisibility(View.GONE); // 隐藏VideoView，移除残留的图像
            deleteVideoButton.setVisibility(View.GONE); // Hide delete button when video is removed
        });
       et_post_title.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
                post_title = editable.toString();
           }
       });
    }

    private void setupAudioSubmission(View inputView) {
        EditText et_post_title = inputView.findViewById(R.id.et_post_title);
        EditText et_post_audio_title = inputView.findViewById(R.id.et_post_audio_title);
        tvAudioPath = inputView.findViewById(R.id.tv_audio_path);
        btnRecord = inputView.findViewById(R.id.btn_record);
        btnStop = inputView.findViewById(R.id.btn_stop);
        Button btnPlay = inputView.findViewById(R.id.btn_play);
        Button btnDelete = inputView.findViewById(R.id.btn_delete);
        audioFilePath = "";

        btnRecord.setOnClickListener(v -> {
            if (checkPermission()) {
                Log.d("AddPostActivity","进入到音频的 if (checkPermission()) 里了");
                if (!tvAudioPath.getText().equals("暂无录音")) {
                    Toast.makeText(getApplicationContext(), "对不起，你有一个录音文件已被选择，若重新录制，请删除已上传的录音文件", Toast.LENGTH_LONG).show();
                }else{
                    startRecording();
                }
            } else {
                Log.d("AddPostActivity","没有权限,需要请求权限");
                requestStoragePermissionForAudio();
            }
        });

        btnStop.setOnClickListener(v -> {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                tvAudioPath.setText("录音已停止: " + audioFilePath);
                btnStop.setEnabled(false);
                btnPlay.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                btnRecord.setEnabled(true);
            }
        });

        btnPlay.setOnClickListener(v -> {
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(audioFilePath);
                mediaPlayer.prepare();
                mediaPlayer.start();
                Toast.makeText(getApplicationContext(), "正在播放中......", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnDelete.setOnClickListener(v -> {
            File file = new File(audioFilePath);
            if (file.delete()) {
                Toast.makeText(getApplicationContext(), "录音文件已经删除", Toast.LENGTH_SHORT).show();
                tvAudioPath.setText("暂无录音");
                btnPlay.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                audioFilePath = "";
            }
        });
        et_post_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                post_title = editable.toString();
            }
        });

        et_post_audio_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                post_audio_title = editable.toString() + ".audio";
            }
        });

    }

    private void startRecording(){
        // 先检查 MediaRecorder 状态，如果已经存在，先清理
        if (mediaRecorder != null) {
            mediaRecorder.reset();    // 重置 MediaRecorder
            mediaRecorder.release();  // 释放 MediaRecorder 对象
            mediaRecorder = null;     // 将 MediaRecorder 对象设置为 null
        }
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        audioFilePath = getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/audio_" + System.currentTimeMillis() + ".3gp";
        mediaRecorder.setOutputFile(audioFilePath);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            tvAudioPath.setText("录音已开始");
            btnStop.setEnabled(true);
            btnRecord.setEnabled(false);
        } catch (IOException e) {
            Log.e("AddPostActivity", "准备或开始录音失败", e);
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }


    private void requestStoragePermissionForAudio() {
        Log.d("AddPostActivity","进入到requestStoragePermissionForAudio()里了");
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
            Log.d("AddPostActivity","进入到requestStoragePermissionForAudio()里的对话框了");
            new AlertDialog.Builder(this)
                    .setTitle("被需要的许可")
                    .setMessage("需要录制音频的许可")
                    .setPositiveButton("允许", (dialog, which) -> ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, PERMISSION_CODE_AUDIO))
                    .setNegativeButton("禁止", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            // 用户之前拒绝了权限请求，并选择了“不再询问”
            if (wasPermissionAsked(Manifest.permission.RECORD_AUDIO)) {
                // 显示前往设置的对话框
                Log.d("AddPostActivity","显示前往设置的对话框");
                showSettingsDialog();
            } else {
                // 首次请求权限
                Log.d("AddPostActivity","首先请求权限");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_CODE_AUDIO);
            }
        }
        markPermissionAsked(Manifest.permission.RECORD_AUDIO);
    }

    private void showSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("请求音频权限禁止")
                .setMessage("你已经禁止了音频权限，请在设置里打开它.")
                .setPositiveButton("打开设置", (dialog, which) -> {
                    // 引导用户到设置页面
                    openAppSettings();
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .create().show();
    }


    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private boolean wasPermissionAsked(String permission) {
        SharedPreferences prefs = getSharedPreferences("permissions", MODE_PRIVATE);
        return prefs.getBoolean(permission, false);
    }

    private void markPermissionAsked(String permission) {
        SharedPreferences.Editor editor = getSharedPreferences("permissions", MODE_PRIVATE).edit();
        editor.putBoolean(permission, true);
        editor.apply();
    }


    private void setupImageSubmission(View inputView) {

        Button btn_add = inputView.findViewById(R.id.btn_add_image);
        EditText et_post_title = inputView.findViewById(R.id.et_post_title);
        RecyclerView rv_images = inputView.findViewById(R.id.rv_images);
        imageUris = new ArrayList<>();  // 保存选中的图片URI

        rv_images.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter = new AddPostImageAdapter(this,new ArrayList<>(),()->{
           //todo:
            Toast.makeText(this, "Image removed", Toast.LENGTH_SHORT).show();
        });
        rv_images.setAdapter(adapter);

        //添加图片的逻辑
        btn_add.setOnClickListener( v->{
            // 先检查权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 如果没有权限，则请求权限
                requestStoragePermissionForImages();
            } else {
                // 如果已经有权限，直接打开图库
                Launcher_images.launch("image/*");
            }
        });

        //得到图片的文本；
        et_post_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                post_title = editable.toString();
            }
        });
    }


    private void requestStoragePermissionForImages() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("被需要的允许许可")
                    .setMessage("This permission is needed to pick images from your gallery（需要从你图库里选择图片的许可）")
                    .setPositiveButton("允许", (dialog, which) -> ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_STORAGE))
                    .setNegativeButton("进制", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "图库权限已授权", Toast.LENGTH_SHORT).show();
                Launcher_images.launch("image/*");
            } else {
                Toast.makeText(this, "用户禁止图库权限", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == PERMISSION_CODE_AUDIO){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "音频权限已授权", Toast.LENGTH_SHORT).show();
                startRecording();
            } else {
                Toast.makeText(this, "用户禁止音频权限", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == PERMISSION_CODE_PDF){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "pdf权限已授权", Toast.LENGTH_SHORT).show();
                Launcher_PDF.launch(new String[]{"application/pdf"});
            } else {
                Toast.makeText(this, "用户禁止pdf权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupTextSubmission(View inputView) {

        EditText editText = inputView.findViewById(R.id.et_text_input);
        post_content_text = editText.getText().toString();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                post_content_text = editable.toString();
            }
        });
        Log.d("AddPostActivity","editText.getText().toString():"+editText.getText().toString());
        Log.d("AddPostActivity","post_content_text:"+post_content_text);
    }


    private void setupViewModel() {
        subjectContentViewModelFactory = new SubjectContentViewModelFactory();
        subjectContentViewModel = new ViewModelProvider(this,subjectContentViewModelFactory).get(SubjectContentViewModel.class);
    }

    private void setupToolBar() {
        tl_head = findViewById(R.id.tl_head_add_post);
        setSupportActionBar(tl_head);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 从menu_overflow.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_activity_add_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); // 获取菜单项的编号
        if (id == android.R.id.home) { // 点击了工具栏左边的返回箭头
            finish(); // 结束当前页面
        } else if (id == R.id.menu_submit) {
            //todo:提交,添加内容到数据库
            addDataToDatabase();
        }
        return super.onOptionsItemSelected(item);
    }


    private void addDataToDatabase(){
        SubjectContent subjectContent = new SubjectContent();
        List<MediaItem> mediaItemList = new ArrayList<>();
        switch(post_content_type){
            case "text":
                if (subjectContent != null) {
                    subjectContent.setUser_name(user_name);
                    subjectContent.setSemester_name(semester_name);
                    subjectContent.setSubject_name(subject_name);
                    subjectContent.setPunch_time(punch_date);
                    subjectContent.setContentType(post_content_type);
                    subjectContent.setContentText(post_content_text);
                    subjectContentViewModel.insertPostWithMedia(subjectContent,mediaItemList).observe(this,isSuccess->{
                        if (isSuccess) {
                            Toast.makeText(this, "文本插入数据库成功", Toast.LENGTH_SHORT).show();
                            Log.d("AddPostActivity","文本插入数据库成功");
                            Intent intent = new Intent();
                            setResult(Activity.RESULT_OK,intent);
                            finish();
                        }else{
                            Toast.makeText(this, "文本插入数据库失败", Toast.LENGTH_SHORT).show();
                            Log.d("AddPostActivity","文本插入数据库失败");
                        }
                    });
                }
                break;
            case "image":
                if (subjectContent != null && mediaItemList != null) {
                    Log.d("AddPostActivity","进入到插入图库的操作里了："+imageUris.size());
                    subjectContent.setUser_name(user_name);
                    subjectContent.setSemester_name(semester_name);
                    subjectContent.setSubject_name(subject_name);
                    subjectContent.setPunch_time(punch_date);
                    subjectContent.setContentType(post_content_type);
                    subjectContent.setTitle(post_title);

                    //将存储多张图片的路径存储在mediaItemList
                    for (int i = 0; i < imageUris.size(); i++) {
                        MediaItem mediaItem = new MediaItem();
                        mediaItem.setMediaType(post_content_type);
                        mediaItem.setUri(imageUris.get(i).toString());
                        mediaItemList.add(mediaItem);
                    }

                    subjectContentViewModel.insertPostWithMedia(subjectContent,mediaItemList).observe(this,isSuccess->{
                        if (isSuccess) {
                            Toast.makeText(this, "图片插入数据库成功", Toast.LENGTH_SHORT).show();
                            Log.d("AddPostActivity","图片插入数据库成功");
                            Intent intent = new Intent();
                            setResult(Activity.RESULT_OK,intent);
                            finish();
                        }else{
                            Toast.makeText(this, "图片插入数据库失败", Toast.LENGTH_SHORT).show();
                            Log.d("AddPostActivity","图片插入数据库失败");
                        }
                    });
                }
                break;
            case "audio":
                if (subjectContent != null && mediaItemList != null) {
                    Log.d("AddPostActivity", "进入到插入音频的操作里了：" + audioFilePath);
                    subjectContent.setUser_name(user_name);
                    subjectContent.setSemester_name(semester_name);
                    subjectContent.setSubject_name(subject_name);
                    subjectContent.setPunch_time(punch_date);
                    subjectContent.setContentType(post_content_type);
                    subjectContent.setTitle(post_title);

                    MediaItem mediaItem = new MediaItem();
                    mediaItem.setMediaType(post_content_type);
                    mediaItem.setMediaTitle(post_audio_title);
                    mediaItem.setUri(audioFilePath);
                    mediaItemList.add(mediaItem);

                    subjectContentViewModel.insertPostWithMedia(subjectContent,mediaItemList).observe(this,isSuccess->{
                        if (isSuccess) {
                            Toast.makeText(this, "音频插入数据库成功", Toast.LENGTH_SHORT).show();
                            Log.d("AddPostActivity","音频插入数据库成功");
                            Intent intent = new Intent();
                            setResult(Activity.RESULT_OK,intent);
                            finish();
                        }else{
                            Toast.makeText(this, "音频插入数据库失败", Toast.LENGTH_SHORT).show();
                            Log.d("AddPostActivity","音频插入数据库失败");
                        }
                    });
                }
                break;
            case "video":
                if (subjectContent != null && mediaItemList != null) {
                    Log.d("AddPostActivity", "进入到插入视频的操作里了：" + videoFileUris.size());
                    subjectContent.setUser_name(user_name);
                    subjectContent.setSemester_name(semester_name);
                    subjectContent.setSubject_name(subject_name);
                    subjectContent.setPunch_time(punch_date);
                    subjectContent.setContentType(post_content_type);
                    subjectContent.setTitle(post_title);

                    MediaItem mediaItem = new MediaItem();
                    mediaItem.setMediaType(post_content_type);
                    mediaItem.setUri(videoFileUris.get(0).toString());
                    mediaItemList.add(mediaItem);

                    subjectContentViewModel.insertPostWithMedia(subjectContent,mediaItemList).observe(this,isSuccess->{
                        if (isSuccess) {
                            Toast.makeText(this, "视频插入数据库成功", Toast.LENGTH_SHORT).show();
                            Log.d("AddPostActivity","视频插入数据库成功");
                            Intent intent = new Intent();
                            setResult(Activity.RESULT_OK,intent);
                            finish();
                        }else{
                            Toast.makeText(this, "视频插入数据库失败", Toast.LENGTH_SHORT).show();
                            Log.d("AddPostActivity","视频插入数据库失败");
                        }
                    });
                }
                break;
            case "pdf":
                if (subjectContent != null && mediaItemList != null) {
                    Log.d("AddPostActivity", "进入到插入pdf的操作里了：" + audioFilePath);
                    subjectContent.setUser_name(user_name);
                    subjectContent.setSemester_name(semester_name);
                    subjectContent.setSubject_name(subject_name);
                    subjectContent.setPunch_time(punch_date);
                    subjectContent.setContentType(post_content_type);
                    subjectContent.setTitle(post_title);

                    MediaItem mediaItem = new MediaItem();
                    mediaItem.setMediaType(post_content_type);
                    mediaItem.setMediaTitle(post_pdf_title);
                    mediaItem.setUri(pdfFileUris.get(0).toString());
                    mediaItemList.add(mediaItem);

                    subjectContentViewModel.insertPostWithMedia(subjectContent,mediaItemList).observe(this,isSuccess->{
                        if (isSuccess) {
                            Toast.makeText(this, "pdf插入数据库成功", Toast.LENGTH_SHORT).show();
                            Log.d("AddPostActivity","pdf插入数据库成功");
                            Intent intent = new Intent();
                            setResult(Activity.RESULT_OK,intent);
                            finish();
                        }else{
                            Toast.makeText(this, "pdf插入数据库失败", Toast.LENGTH_SHORT).show();
                            Log.d("AddPostActivity","pdf插入数据库失败");
                        }
                    });
                }
                break;
        }
    }
}
