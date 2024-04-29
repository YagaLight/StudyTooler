package com.yc.studytooler.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.studytooler.PunchActivity;
import com.yc.studytooler.R;
import com.yc.studytooler.adapter.SemesterSpinnerAdapter;
import com.yc.studytooler.adapter.SubjectAdapter;
import com.yc.studytooler.adapter.SubjectSpinnerAdapter;
import com.yc.studytooler.bean.Semester;
import com.yc.studytooler.bean.Subject;
import com.yc.studytooler.viewmodel.SemesterViewModel;
import com.yc.studytooler.viewmodel.SemesterViewModelFactory;
import com.yc.studytooler.viewmodel.SharedViewModel;
import com.yc.studytooler.viewmodel.SubjectViewModel;
import com.yc.studytooler.viewmodel.SubjectViewModelFactory;
import com.yc.studytooler.widget.SpacesDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName PageOneFragment
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/2 4:14
 * @VERSION 1.0
 */
public class PageOneFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_START_DATE = "startDate";
    private ActivityResultLauncher mLauncher; // 声明一个活动结果启动器对象

    protected View mView; // 声明一个视图对象
    protected Context mContext; // 声明一个上下文对象

    private RecyclerView rv_subject;

    private String user_name;

    private String default_semester_name;

    private String selected_semester_name;

    private String selected_subject_name;
    public PageOneFragment() {
        // Required empty public constructor
    }


    private SharedViewModel sharedViewModel;

//todo:全新的数据
    private Spinner sp_Semester;

    private Spinner sp_Subject;
    private CheckBox cb_default_semester;
    private ImageView iv_add;
    private List<Semester> semesterList;
    private List<Subject> subjectList;
    private SemesterSpinnerAdapter semesterSpinnerAdapter;

    private SubjectSpinnerAdapter subjectSpinnerAdapter;
    private SemesterViewModelFactory semesterViewModelFactory;
    private SemesterViewModel semesterViewModel;
    private SubjectViewModelFactory subjectViewModelFactory;
    private SubjectViewModel subjectViewModel;

    private SubjectAdapter subjectAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d("PageOneFragment","进入到onCreateView里了");
        mContext = getContext(); // 获取活动页面的上下文
        // 根据布局文件fragment_tab_first.xml生成视图对象
        mView = inflater.inflate(R.layout.fragment_tab_first, container, false);
        return mView;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("PageOneFragment","进入到onViewCreated里了");
        if (getActivity() != null) {
            Log.d("PageOneFragment","进入到getActivity()里了");
            sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        }
        setupToolbar();
        setupUserName();
        setupViewModel();
    }


    private void setupSubjectSpinner() {
        sp_Subject = mView.findViewById(R.id.sp_subject);
        subjectList = loadSubjects();
        subjectSpinnerAdapter = new SubjectSpinnerAdapter(mContext,R.layout.item_spinner_subject,subjectList);
        sp_Subject.setAdapter(subjectSpinnerAdapter);
        sp_Subject.setSelection(0);

        sp_Subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Subject selectedSubject = subjectList.get(position);
                selected_subject_name = selectedSubject.getSubject_name();
                //todo:更新下方的RecycleView
                updateRecyclerView();  // 只显示选中的科目
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateRecyclerView() {
        if (selected_semester_name != null) {
            if (selected_subject_name != null && !selected_subject_name.isEmpty()) {
                //显示选定学期和科目的具体信息
                subjectViewModel.getSubject(user_name, selected_semester_name, selected_subject_name).observe(getViewLifecycleOwner(), subjects -> {
                    if (subjects != null && !subjects.isEmpty()) {
                        subjectAdapter.setSubjects(subjects);
                    } else {
                        subjectAdapter.setSubjects(new ArrayList<>());
                    }
                });
            } else {
                // 如果选定了学期但没有选定具体科目，则显示该学期的所有科目
                subjectViewModel.getSubjects(user_name, selected_semester_name).observe(getViewLifecycleOwner(), subjects -> {
                    if (subjects != null && !subjects.isEmpty()) {
                        subjectAdapter.setSubjects(subjects);
                    } else {
                        subjectAdapter.setSubjects(new ArrayList<>());
                    }
                });
            }
        }else{
            // 如果没有选定学期，清空列表
            subjectAdapter.setSubjects(new ArrayList<>());
        }
    }


    private List<Subject> loadSubjects() {

        subjectList = new ArrayList<>();
        if (user_name != null) {
            subjectViewModel.getSubjects(user_name, default_semester_name).observe(getViewLifecycleOwner(), subjects -> {
                if (subjects != null && !subjects.isEmpty()) {
                    subjectList.add(new Subject());
                    subjectList.addAll(subjects);
                }else{
                    subjectList.clear();
                    subjectList.add(new Subject());
                }
            });
        }
        return subjectList;
    }


    private void setupUserName() {
        sharedViewModel.getUserName().observe(getViewLifecycleOwner(), userName -> {
            user_name = userName;
            // 更新UI
            if (userName != null) {
                Log.d("PageOneFragment","进入到setupUserName()里了，并且进入if语句里");
                Log.d("PageOneFragment","用户名是"+userName );
                setupSemesterSpinner();
                setupSubjectSpinner();
                setupRecyclerView();
                setupAddImageView();
            }
        });
    }

    private void setupAddImageView() {
        iv_add = mView.findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);
    }

    private void setupSemesterSpinner() {
        sp_Semester = mView.findViewById(R.id.sp_semester);
        cb_default_semester = mView.findViewById(R.id.cb_default_semester);
        semesterList = loadSemesters();
        semesterSpinnerAdapter = new SemesterSpinnerAdapter(mContext,R.layout.item_spinner_semester,semesterList);
        sp_Semester.setAdapter(semesterSpinnerAdapter);
        sp_Semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Semester selectedSemester = semesterList.get(position);
                selected_semester_name = selectedSemester.getSemester_name();
                cb_default_semester.setChecked(selectedSemester.isDefault());
                loadSpinnerSubjectsForSelectedSemester(selected_semester_name);
                selected_subject_name="";
                updateRecyclerView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                cb_default_semester.setChecked(false);
            }
        });

        cb_default_semester.setOnClickListener( v ->{
            if (cb_default_semester.isChecked()) {
                Semester currentSemester = (Semester) sp_Semester.getSelectedItem();
                showConfirmationDialog(currentSemester);
            }
        });
    }

    private void loadSpinnerSubjectsForSelectedSemester(String selected_semester) {
        subjectViewModel.getSubjects(user_name,selected_semester).observe(getViewLifecycleOwner(), subjects -> {
            if (subjects != null && !subjects.isEmpty()) {
                subjectList.clear();
                subjectList.add(new Subject());
                subjectList.addAll(subjects);
                subjectSpinnerAdapter.notifyDataSetChanged();  // 更新科目下拉框的数据
                sp_Subject.setSelection(0);  // 重置科目下拉框到默认状态或提示项
            }else{
                subjectList.clear();
                subjectList.add(new Subject());
                subjectSpinnerAdapter.notifyDataSetChanged();  // 更新科目下拉框的数据
                sp_Subject.setSelection(0);  // 重置科目下拉框到默认状态或提示项
            }
        });
    }


    /**
     * 加载学期数据
     * @return
     */
    private List<Semester> loadSemesters() {
        Log.d("PageOneFragment","进入到loadSemesters()");
        semesterList = new ArrayList<>();
        if (user_name != null) {
            Log.d("PageOneFragment","进入到loadSemesters()了，进入到user_name != null里了");
            semesterViewModel.getSemesterByUserName(user_name).observe(getViewLifecycleOwner(),semesters -> {
                if (semesters != null && !semesters.isEmpty()) {
                    Log.d("PageOneFragment","进入到loadSemesters()了，进入到semesters != null && !semesters.isEmpty()里了");
                    semesterList.clear();
                    semesterList.addAll(semesters);
                    semesterSpinnerAdapter.notifyDataSetChanged(); // 通知适配器数据已更新
                    updateSpinnerSelection(semesterList);
                }else {
                    // 可以处理数据为空的情况
                    semesterList.clear();
                    semesterSpinnerAdapter.notifyDataSetChanged();
                }
            });
        }
        return semesterList;
    }

    private void updateSpinnerSelection(List<Semester> semesterList) {
        for (int i = 0; i < semesterList.size(); i++) {
            Semester semester = semesterList.get(i);
            if (semester.isDefault()) {
                sp_Semester.setSelection(i);
                cb_default_semester.setChecked(true);
                default_semester_name = semester.getSemester_name();
                selected_semester_name = semester.getSemester_name();
                break;
            }
        }
    }

    private void showConfirmationDialog(Semester currentSemester) {
        new AlertDialog.Builder(mContext)
                .setTitle("设置默认学期")
                .setMessage("你真的要将"+currentSemester.getSemester_name()+"设置为默认学期吗？")
                .setPositiveButton("确定",(dialog,which) -> setDefaultSemester(currentSemester.getSemester_name()))
                .setNegativeButton("取消", (dialog, which) -> cb_default_semester.setChecked(false))
                .show();
    }

    private void setDefaultSemester(String currentSemesterName) {
        if (user_name != null) {
            semesterViewModel.updateSemester(user_name,currentSemesterName).observe(getViewLifecycleOwner(),isSuccess ->{
                if (isSuccess) {
                    Log.d("","修改成功");
                    //重新加载数据
                    semesterList = loadSemesters();
                    semesterSpinnerAdapter.notifyDataSetChanged();
                }else{
                    Log.d("","修改失败");
                }
            });
        }
    }



    private String getDefaultSemesterName(){
        semesterViewModel.getDefaultSemester(user_name).observe(getViewLifecycleOwner(),semester_name ->{
            if (semester_name != null) {
                default_semester_name = semester_name;
            }
        });
        return default_semester_name;
    }


    private void setupViewModel() {
          semesterViewModelFactory = new SemesterViewModelFactory();
          subjectViewModelFactory = new SubjectViewModelFactory();
          semesterViewModel = new ViewModelProvider(this,semesterViewModelFactory).get(SemesterViewModel.class);
          subjectViewModel = new ViewModelProvider(this,subjectViewModelFactory).get(SubjectViewModel.class);
    }


    private void setupRecyclerView() {
        Log.d("PageOneFragment","进入到setupRecyclerView");
        rv_subject = mView.findViewById(R.id.rv_subject);
        LinearLayoutManager manager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL, false);
        rv_subject.setLayoutManager(manager);

        subjectAdapter = new SubjectAdapter(mContext,getNonEmptySubjectList(subjectList),this::handleSubjectClick);
        rv_subject.setAdapter(subjectAdapter);
        rv_subject.setItemAnimator(new DefaultItemAnimator());
        rv_subject.addItemDecoration(new SpacesDecoration(1));
    }

    private void handleSubjectClick(Subject subject) {
        if (mContext != null) {
            Intent intent = new Intent(mContext, PunchActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("user_name",user_name);
            bundle.putString("semester_name",subject.getSemester_name());
            bundle.putString("subject_name",subject.getSubject_name());
            intent.putExtras(bundle);
            Log.d("PunchActivity","跳转成功1");
//            sharedViewModel.setSemester_name(subject.getSemester_name());
            Log.d("PunchActivity","跳转成功2"+subject.getSemester_name());
//            sharedViewModel.setSubject_name(subject.getSubject_name());
            Log.d("PunchActivity","跳转成功3"+subject.getSubject_name());
            startActivity(intent);
            Log.d("PunchActivity","跳转成功4");
        }else{
            Log.d("PunchActivity","没有跳转成功");
        }
    }

    private List<Subject> getNonEmptySubjectList(List<Subject> subjects){
        List<Subject> initSubjectList = new ArrayList<>();
        for(Subject subject:subjectList){
            if(subject.getSubject_name() != null && !subject.getSubject_name().isEmpty()){
                initSubjectList.add(subject);
            }
        }
        return initSubjectList;
    }

    private void setupToolbar() {
        Log.d("PageOneFragment","进入到setupToolbar");
        Toolbar tl_head = mView.findViewById(R.id.tl_tab_first_head);
        tl_head.setTitle("课程页面");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity != null){
            activity.setSupportActionBar(tl_head);// 使用tl_head替换系统自带的ActionBar
        }
        tl_head.setTitleTextColor(Color.BLACK);
        setHasOptionsMenu(true);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        Log.d("PageOneFragment","进入到onAttach里了");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("PageOneFragment","进入到onCreate了");
        super.onCreate(savedInstanceState);
        //设置标题栏的右边选项
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_one,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            //todo:
            return true;
        }else if (id == R.id.menu_search) {
            //todo:
            return true;
        } else if (id == android.R.id.home ) {
            if (getActivity() != null) {
                getActivity().finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        Log.d("PageOneFragment","进入到onStart()里了");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("PageOneFragment","进入到onResume()里了");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("PageOneFragment","进入到onPause()里了");

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("PageOneFragment","进入到onStop()里了");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("PageOneFragment","进入到onDestroyView()里了");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("PageOneFragment","进入到onDestroy()里了");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d("PageOneFragment","进入到onDetach()里了");
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_add) {
            //todo：一个对话框：第一行是一组单选按钮：添加新学期，添加新科目；然后根据选择的内容，对应下面区域所展示的视图也不相同，比如当我选择添加新学期时，下面区域视图是：第一行是：左边是文字，显示“请选择新学期”，然后，右边是一个下拉框，里面装有八个数据“大一上，大一下...大四上，大四下”，第二行是：左边是文字，右边是复选框；当我选择添加新科目时，下面区域视图为：左边是文字，右边是编辑框
            showCustomDialog();
        }
    }


    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_fragment_one_add, null);
        builder.setView(dialogView);

        // 配置其它部分的逻辑
        configureDialogInteractions(dialogView, builder);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //插入新科目
    private void addNewSubject(Subject new_subject) {
        subjectViewModel.insertSubject(new_subject).observe(getViewLifecycleOwner(),isSuccess->{
            if (isSuccess) {
                Toast.makeText(getContext(), "插入新科目成功", Toast.LENGTH_SHORT).show();
                subjectList.add(new_subject);
                subjectSpinnerAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getContext(), "插入新科目失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //插入新学期
    private void addNewSemester(Semester new_semester) {
        semesterViewModel.insertSemester(new_semester).observe(getViewLifecycleOwner(),isSuccess->{
            if (isSuccess) {
                Toast.makeText(getContext(), "插入新学期成功", Toast.LENGTH_SHORT).show();
                semesterList.add(new_semester);
                semesterSpinnerAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getContext(), "插入新学期失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //执行对话课的新配置
    private void configureDialogInteractions(View dialogView, AlertDialog.Builder builder) {
        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
        LinearLayout layoutAddSemester = dialogView.findViewById(R.id.layout_add_semester);
        LinearLayout layoutAddSubject = dialogView.findViewById(R.id.layout_add_subject);

        //设置添加新学期的下拉框
        Spinner spinnerSemester = dialogView.findViewById(R.id.sp_semester);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,R.layout.item_dialog_spinner_semester,getGivenSemesters());
        spinnerSemester.setAdapter(adapter);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_add_semester) {
                layoutAddSemester.setVisibility(View.VISIBLE);
                layoutAddSubject.setVisibility(View.GONE);


            } else if (checkedId == R.id.rb_add_subject) {
                layoutAddSemester.setVisibility(View.GONE);
                layoutAddSubject.setVisibility(View.VISIBLE);
            }
        });


        builder.setPositiveButton("确认", (dialog, which) -> {
            // 处理确认操作
            if (radioGroup.getCheckedRadioButtonId() == R.id.rb_add_semester) {
                //设置添加新学期的下拉框
                String selectedSemesterName = (String)spinnerSemester.getSelectedItem();
                // 检查是否设置为默认学期
                CheckBox checkBoxDefault = dialogView.findViewById(R.id.cb_default_semester);
                Semester new_semester = new Semester();
                new_semester.setUser_name(user_name);
                new_semester.setSemester_name(selectedSemesterName);
                if (checkBoxDefault.isChecked()) {
                    //这里要创建新学期
                    new_semester.setDefault(true);
                } else{
                    new_semester.setDefault(false);
                }
                addNewSemester(new_semester);
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_add_subject) {
                // 从编辑框获取新科目名称
                EditText editTextSubjectName = dialogView.findViewById(R.id.et_subject_name);
                String newSubjectName = editTextSubjectName.getText().toString();
                Subject new_subject = new Subject();
                // 将新科目添加到数据库
                new_subject.setUser_name(user_name);
                if (selected_semester_name != null || !selected_semester_name.isEmpty()) {
                    new_subject.setSemester_name(selected_semester_name);
                    new_subject.setSubject_name(newSubjectName);
                    addNewSubject(new_subject);
                } else{
                    // 显示警告对话框
                    showWarningDialog();
                }
            }
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            // 取消操作
            dialog.dismiss();
        });
    }

    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("警告");
        builder.setMessage("请先选择一个学期！");
        builder.setPositiveButton("确定", (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    private String[] getGivenSemesters(){

        Log.d("PageOneFragment","进入到getGivenSemesters()里了");
        String[] givenSemesters = {"大一上","大一下","大二上","大二下","大三上","大三下","大四上","大四下"};


        return givenSemesters;
    }


}
