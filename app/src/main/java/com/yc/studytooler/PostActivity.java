package com.yc.studytooler;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.Manifest;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.studytooler.adapter.PostsAdapter;
import com.yc.studytooler.bean.MediaItem;
import com.yc.studytooler.bean.SubjectContent;
import com.yc.studytooler.interfa.MediaActionListener;
import com.yc.studytooler.utils.DateUtils;
import com.yc.studytooler.viewmodel.SubjectContentViewModel;
import com.yc.studytooler.viewmodel.SubjectContentViewModelFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PunchContentActivity
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/21 17:04
 * @VERSION 1.0
 */
public class PostActivity extends AppCompatActivity implements View.OnClickListener, MediaActionListener {

    //todo:记得注册
    private String user_name;
    private String semester_name;
    private String subject_name;
    private Date punch_date;


    private Toolbar toolbar;
    private RecyclerView rv_posts;
    private ImageView img_add;
    private List<SubjectContent> subjectContentList;

    private SubjectContentViewModelFactory subjectContentViewModelFactory;
    private SubjectContentViewModel subjectContentViewModel;

    private Map<SubjectContent, List<MediaItem>> postMap;

    private PostsAdapter postsAdapter;

    private ActivityResultLauncher mLauncher;

    private String file_url;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch_content);
        setupToolBar();
        receiveDataFromSharedViewModel();
        UpdateUI();
        setupRegisterForActivityResult();
    }

    private void receiveDataFromSharedViewModel() {
       Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user_name = bundle.getString("user_name");
            Log.d("PostActivity","从PunchActivity里获取的用户名："+user_name);
            semester_name = bundle.getString("semester_name");
            Log.d("PostActivity","从PunchActivity里获取的学期："+semester_name);
            subject_name = bundle.getString("subject_name");
            Log.d("PostActivity","从PunchActivity里获取的科目："+subject_name);
            punch_date = (Date) bundle.getSerializable("punch_date");
            Log.d("PostActivity","从PunchActivity里获取的日期："+ DateUtils.convertCommonDate(punch_date));
            Log.d("PostActivity","从PunchActivity里获取的日期："+ punch_date);
        }
    }

    private void UpdateUI() {
        // 所有数据都已加载，现在可以更新 UI
        setupViewModel();
        loadData();

    }

    private void setupRegisterForActivityResult() {
        mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
            if (result.getResultCode() == RESULT_OK) {
                //todo:获取数据：文本类型-----文本内容，如果是非纯文本，则要------title-----desc-----mediaType----多个url，
                updateAdapter();
            }
        });

    }

    private void setupAddImageView() {
        img_add = findViewById(R.id.img_add);
        img_add.setOnClickListener(this);
    }

    private void setupRecyclerView() {
        rv_posts = findViewById(R.id.rv_posts);
        rv_posts.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));
    }

    private void setupViewModel() {
        subjectContentViewModelFactory = new SubjectContentViewModelFactory();
        subjectContentViewModel = new ViewModelProvider(this,subjectContentViewModelFactory).get(SubjectContentViewModel.class);
    }

    private void loadData() {
        subjectContentList = new ArrayList<>();
        postMap = new HashMap<>();
        if (user_name != null && semester_name != null && subject_name != null && punch_date != null) {
            Log.d("PostActivity","进入到了load()里的if (user_name != null && semester_name != null && subject_name != null && punch_date != null)");
            subjectContentViewModel.getAllPosts(user_name,semester_name,subject_name,punch_date).observe(this,posts->{
                Log.d("PostActivity",user_name+"      "+semester_name+"        "+subject_name+"     "+punch_date);
                if (posts != null && !posts.isEmpty()) {
                    Log.d("PostActivity","数据不为空");
                    subjectContentList = new ArrayList<>(posts.keySet());
                    postMap = posts;
                    Log.d("PostActivity","===============打印数据===========================");
                    // 打印 subjectContentList

                    System.out.println("===============打印数据===========================");
                    for (SubjectContent key : subjectContentList) {
                        Log.d("PostActivity", "Key from subjectContentList: " + key);
                        System.out.println(key.toString());
                    }
                    System.out.println("posts数据时："+posts);
                    // 打印 postMap
                    for (Map.Entry<SubjectContent, List<MediaItem>> entry : postMap.entrySet()) {  // 假设Map的值类型为String
                        Log.d("PostActivity", "Key: " + entry.getKey() + ", Value: " + entry.getValue());
                        System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
                    }
                    Log.d("PostActivity","===============打印数据===========================");
                    System.out.println("===============打印数据===========================");
                    //更新视图：
                    setupRecyclerView();
                    setupAddImageView();
                    updateAdapter();
                }else{
                    setupRecyclerView();
                    setupAddImageView();
                    updateAdapter();
                }
            });
        }
    }

    private void updateAdapter() {
        if (postsAdapter == null) {
            printMediaItemsCount();
            postsAdapter = new PostsAdapter(this,subjectContentList,postMap,this);
            rv_posts.setAdapter(postsAdapter);

        }else{
            if (user_name != null && semester_name != null && subject_name != null && punch_date != null) {
                subjectContentViewModel.getAllPosts(user_name,semester_name,subject_name,punch_date).observe(this,posts->{
                    if (posts != null && !posts.isEmpty()) {
                        subjectContentList = new ArrayList<>(posts.keySet());
                        postMap = posts;
                        Log.d("PostActivity","posts不为空，subjectContentList的大小是："+subjectContentList.size()+"========="+"postMap的大小是："+postMap.values().size());
                        printMediaItemsCount();
                        postsAdapter.updateData(subjectContentList,postMap);
                    }else{
                        subjectContentList = new ArrayList<>();
                        postMap = new HashMap<>();
                        Log.d("PostActivity","posts为空，subjectContentList的大小是："+subjectContentList.size()+"========="+"postMap的大小是："+postMap.size());
                        printMediaItemsCount();
                        postsAdapter.updateData(subjectContentList,postMap);
                    }
                });
            }
        }
    }

    // 方法定义，用于打印或获取每个帖子的多媒体项数量
    public void printMediaItemsCount() {
        if (postMap != null && !postMap.isEmpty()) {
            for (Map.Entry<SubjectContent, List<MediaItem>> entry : postMap.entrySet()) {
                SubjectContent subjectContent = entry.getKey();
                List<MediaItem> mediaItems = entry.getValue();
                int itemCount = mediaItems != null ? mediaItems.size() : 0;
                Log.d("PostActivity", "SubjectContent ID: " + subjectContent.getSubject_content_id() + " 有" + itemCount + " 多媒体项.");
            }
        }
    }

    private void setupToolBar() {
        toolbar = findViewById(R.id.tl_head_add_post);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.img_add) {
           Intent intent = new Intent(this,AddPostActivity.class);
           Bundle bundle = new Bundle();
            bundle.putString("user_name",user_name);
            bundle.putString("semester_name",semester_name);
            bundle.putString("subject_name",subject_name);
            // 将Date对象作为Serializable存入Bundle
            bundle.putSerializable("punch_date",punch_date);
            intent.putExtras(bundle);
           mLauncher.launch(intent);
        }
    }


    @Override
    public void onMediaAction(String type, String uri) {
        file_url = uri;
        switch (type) {
            case "IMAGE":
                handleImage();
                break;
            case "AUDIO":
                // Play audio
                break;
            case "VIDEO":
                // Play video
                break;
            case "PDF":
                // Open PDF
                handlePDF(uri);
                break;
            // Add cases for other types as necessary
        }
    }

    private void handlePDF(String pdfUri) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            openPDF(pdfUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            Log.d("PostActivity","程序执行了if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限被授予
                Log.d("PostActivity","程序执行了这里4");
                Log.d("PostActivity","程序执行了权限被授予");
                openPDF(file_url); // 确保你有途径获取到当前尝试打开的 PDF 的 URI
            } else {
                // 权限被拒绝
                Log.d("PostActivity","程序执行了这里5");
                Log.d("PostActivity","程序执行了权限被禁止");
                Toast.makeText(this, "Permission Denied to read your External storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openPDF(String pdfUri) {
//        File pdfFile = new File(pdfUri); // 根据文件路径创建 File 对象
//        Uri contentUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile);
        Log.d("PostActivity","pdfUri:"+pdfUri);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Log.d("PostActivity","Uri.parse(pdfUri):"+Uri.parse(pdfUri));
        intent.setDataAndType(Uri.parse(pdfUri), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 检查是否还有访问权限
        try {
            if (intent.resolveActivity(getPackageManager()) != null) {
                Log.d("PostActivity","程序执行了这里1");
                startActivity(intent);
            } else {
                Toast.makeText(this, "No application found to open PDF", Toast.LENGTH_SHORT).show();
                // 无法打开，可能需要重新选择文件
                Log.d("PostActivity","程序执行了这里2");
            }
        } catch (SecurityException e) {
            //执行的是这个a
            Log.d("PostActivity","程序执行了这里3");
            Toast.makeText(this, "Permission Denied or File Not Accessible", Toast.LENGTH_SHORT).show();
            // 这里可以提示用户重新获取权限或选择文件
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            // 用户可能需要重新选择文件
        }

//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        } else {
////            Toast.makeText(this, "No application found to open PDF", Toast.LENGTH_SHORT).show();
//            Intent internalIntent = new Intent(this, PdfViewerActivity.class);
//            internalIntent.putExtra("pdf_path", pdfUri);
//            startActivity(internalIntent);
//        }
    }

    private void handleImage() {
    }
}
