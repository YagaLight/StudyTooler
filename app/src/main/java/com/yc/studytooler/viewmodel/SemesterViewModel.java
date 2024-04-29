package com.yc.studytooler.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yc.studytooler.bean.Punch;
import com.yc.studytooler.bean.Semester;
import com.yc.studytooler.callback.DataCallback;
import com.yc.studytooler.repository.SemesterRepository;

import java.util.List;

/**
 * @ClassName SemesterViewModel
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/19 18:55
 * @VERSION 1.0
 */
public class SemesterViewModel extends ViewModel {

    private SemesterRepository semesterRepository;

    private List<Semester> semesterList;

    public SemesterViewModel(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public LiveData<Boolean> insertSemester(Semester semester){
        return semesterRepository.insertSemester(semester);
    }

    public LiveData<Boolean> deleteSemester(String user_name,String semester_name){
        return semesterRepository.deleteSemester(user_name,semester_name);
    }

    /**
     * 修改默认日期
     * @param user_name
     * @return
     */
    public LiveData<Boolean> updateSemester(String user_name,String semester_name){
        return semesterRepository.updateDefaultSemester(user_name,semester_name);
    }

    public LiveData<List<Semester>> getSemesterByUserName(String user_name){
        MutableLiveData<List<Semester>> liveData = new MutableLiveData<>();
        semesterRepository.getSemestersByUserAsync(user_name, new DataCallback<List<Semester>>() {
            @Override
            public void onDataLoaded(List<Semester> data) {
                liveData.postValue(data);
            }

            @Override
            public void onError(Exception e) {
                liveData.postValue(null); // 或者处理错误
            }
        });
        return liveData;
    }

    /**
     * 获取默认日期
     * @param user_name
     * @return
     */
    public LiveData<String> getDefaultSemester(String user_name){
        MutableLiveData<String> liveData = new MutableLiveData<>();
        semesterRepository.getDefaultSemesterAsync(user_name, new DataCallback<String>() {
            @Override
            public void onDataLoaded(String data) {
                liveData.postValue(data);
            }

            @Override
            public void onError(Exception e) {
                liveData.postValue(null); // 或者处理错误
            }
        });
        return liveData;
    }


}
