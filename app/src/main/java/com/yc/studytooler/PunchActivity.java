package com.yc.studytooler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.studytooler.adapter.SubjectPunchDateAdapter;
import com.yc.studytooler.adapter.SubjectPunchDateSpinnerAdapter;
import com.yc.studytooler.bean.Punch;
import com.yc.studytooler.utils.DateUtils;
import com.yc.studytooler.viewmodel.SharedViewModel;
import com.yc.studytooler.viewmodel.PunchViewModel;
import com.yc.studytooler.viewmodel.PunchViewModelFactory;
import com.yc.studytooler.widget.SpacesDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName PunchActivity
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/19 20:22
 * @VERSION 1.0
 */
public class PunchActivity extends AppCompatActivity implements View.OnClickListener{


    private Toolbar tl_head_punch;
    private Spinner sp_punch_date;
    private ImageView iv_add;
    private SubjectPunchDateSpinnerAdapter subjectPunchDateSpinnerAdapter;
    private RecyclerView rv_punch_date;

    private SubjectPunchDateAdapter subjectPunchDateAdapter;
    private SharedViewModel sharedViewModel;
    private String user_name;
    private String semester_name;
    private String subject_name;

    private List<Punch> punchList;

    private PunchViewModelFactory punchViewModelFactory;
    private PunchViewModel punchViewModel;

    private Punch selected_punch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("PunchActivity","进入到onCreate() 里面了");
        setContentView(R.layout.activity_punch);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        setupToolbar();
        receiveDataFromSharedViewModel();
        UpdateUI();
    }

    private void receiveDataFromSharedViewModel() {
        Bundle bundle = getIntent().getExtras();
        user_name = bundle.getString("user_name");
        semester_name = bundle.getString("semester_name");
        subject_name = bundle.getString("subject_name");
    }

    private void UpdateUI() {
        // 所有数据都已加载，现在可以更新 UI
        Log.d("PunchActivity","进入到UpdateUI()了");
        setupViewModel();
        punchList = loadPunchDate();
    }

    private void setupAddImageView() {

        Log.d("PunchActivity","进入到setupAddImageView()里面了");
        iv_add = findViewById(R.id.iv_add_punch);
        iv_add.setOnClickListener(this);
    }

    private void setupViewModel() {
        Log.d("PunchActivity","进入到setupViewModel() 里面了");
        punchViewModelFactory = new PunchViewModelFactory();
        punchViewModel = new ViewModelProvider(this,punchViewModelFactory).get(PunchViewModel.class);
    }

    private void setupFocusSpinner() {
        sp_punch_date = findViewById(R.id.sp_punch_date);
        subjectPunchDateSpinnerAdapter = new SubjectPunchDateSpinnerAdapter(this,R.layout.item_spinner_subject_punch_date, punchList);
        sp_punch_date.setAdapter(subjectPunchDateSpinnerAdapter);
        sp_punch_date.setSelection(0);
        Log.d("PunchActivity", "Adapter is set with " + punchList.size() + " items.");
        sp_punch_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selected_punch = punchList.get(position);
                Log.d("PunchActivity","选中的日期是："+DateUtils.convertCommonDate(selected_punch.getSubject_punch_date()));
                updateRecyclerView();  // 只显示选中的科目
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("PunchActivity", "未选中代码.");
            }
        });
    }

    private void updateRecyclerView() {

        if (selected_punch != null) {
            if (selected_punch.getSubject_punch_date() != null) {
                punchViewModel.getPunchesByDate(user_name,semester_name,subject_name, selected_punch.getSubject_punch_date()).observe(this, punches -> {
                    if (punches != null && !punches.isEmpty()) {
                        subjectPunchDateAdapter.setSubjects(punches);
                    }else{
                        subjectPunchDateAdapter.setSubjects(new ArrayList<>());
                    }
                });
            }else{
                punchViewModel.getPunches(user_name,semester_name,subject_name).observe(this,punches -> {
                    if (punches != null && !punches.isEmpty()) {
                        subjectPunchDateAdapter.setSubjects(punches);
                    }else{
                        subjectPunchDateAdapter.setSubjects(new ArrayList<>());
                    }
                });
            }
        }
    }

    private List<Punch> loadPunchDate() {
        punchList = new ArrayList<>();
        if (user_name != null && semester_name != null && subject_name != null) {
            Log.d("PunchActivity","进入到loadPunchDate()  里面了");
            punchViewModel.getPunches(user_name,semester_name,subject_name).observe(this,punches->{
                if (punches != null && !punches.isEmpty()) {
                    Log.d("PunchActivity","进入到if (punches != null && !punches.isEmpty())里面了");
                    punchList.clear();
                    punchList.add(new Punch());
                    punchList.addAll(punches);
                    setupFocusSpinner();
                    setupAddImageView();
                    setupRecyclerView();
                }else{
                    Log.d("PunchActivity","拿到的数据为空");
                    punchList.clear();
                    punchList.add(new Punch());
                    setupFocusSpinner();
                    setupAddImageView();
                    setupRecyclerView();
                }
            });
        }
        return punchList;
    }

    private void setupRecyclerView() {
        rv_punch_date = findViewById(R.id.rv_punch_date);
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rv_punch_date.setLayoutManager(manager);

        subjectPunchDateAdapter = new SubjectPunchDateAdapter(this,getNonEmptyDateList(punchList),this::handleSubjectPunchDate);
        rv_punch_date.setAdapter(subjectPunchDateAdapter);
        rv_punch_date.setItemAnimator(new DefaultItemAnimator());
        rv_punch_date.addItemDecoration(new SpacesDecoration(1));
    }

    private void handleSubjectPunchDate(Punch punch) {
        Intent intent = new Intent(this, PostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("user_name",user_name);
        bundle.putString("semester_name",semester_name);
        bundle.putString("subject_name",subject_name);
        // 将Date对象作为Serializable存入Bundle
        bundle.putSerializable("punch_date",punch.getSubject_punch_date());
        Log.d("PunchActivity","准备向帖子页面发送数据"+DateUtils.convertCommonDate(punch.getSubject_punch_date()));
        Log.d("PunchActivity","准备向帖子页面发送数据"+punch.getSubject_punch_date());
        intent.putExtras(bundle);
        startActivity(intent);
    }



    private List<Punch> getNonEmptyDateList(List<Punch> punches){
        List<Punch> initDateList = new ArrayList<>();
        for (Punch punch : punches){
            if (punch.getSubject_punch_date() != null) {
                initDateList.add(punch);
                Log.d("PunchActivity","进入到了List<Punch> getNonEmptyDateList");
            }
        }
        return initDateList;
    }



    private void setupToolbar() {
        tl_head_punch = findViewById(R.id.tl_head_punch);
        setSupportActionBar(tl_head_punch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 从menu_overflow.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_activity_punch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); // 获取菜单项的编号
        if (id == android.R.id.home) { // 点击了工具栏左边的返回箭头
            finish(); // 结束当前页面
        } else if (id == R.id.menu_search) {
            //todo:筛选

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_add_punch) {
            Log.d("PunchActivity","进入到了if (id == R.id.iv_add)里面");
           Date today = DateUtils.getToday();
           punchViewModel.getPunches(user_name,semester_name,subject_name).observe(this,punches -> {
               int count = DateUtils.countTodaysDates(punches);

               if (count == 0) {
                   Punch punch = new Punch();
                   punch.setUser_name(user_name);
                   punch.setSemester_name(semester_name);
                   punch.setSubject_name(subject_name);
                   punch.setSubject_punch_date(today);

                   punchViewModel.insertPunch(punch).observe(this,isSuccess->{
                       Log.d("PunchActivity","进入到punchViewModel.insertPunch(punch)里面了");
                       if (isSuccess){
                           Log.d("PunchActivity","进入到isSuccess==true里面了");
                           Toast.makeText(this, "打卡成功", Toast.LENGTH_SHORT).show();
                           punchList.add(punch);
                           subjectPunchDateSpinnerAdapter.notifyDataSetChanged();
                           subjectPunchDateAdapter.setSubjects(getNonEmptyDateList(punchList));
                       }else{
                           Log.d("PunchActivity","进入到进入到isSuccess==false里面了");
                           Toast.makeText(this, "打卡失败", Toast.LENGTH_SHORT).show();
                       }
                   });
               }else{
                   Toast.makeText(this, "客官，你今天已经打卡了", Toast.LENGTH_SHORT).show();
               }
           });
        }
    }


}
