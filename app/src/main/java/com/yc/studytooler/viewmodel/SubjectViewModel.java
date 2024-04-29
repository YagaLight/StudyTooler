package com.yc.studytooler.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yc.studytooler.bean.Semester;
import com.yc.studytooler.bean.Subject;
import com.yc.studytooler.callback.DataCallback;
import com.yc.studytooler.repository.SubjectRepository;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SubjectViewModel
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/20 22:50
 * @VERSION 1.0
 */
public class SubjectViewModel extends ViewModel {

    private SubjectRepository subjectRepository;
    public SubjectViewModel(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public LiveData<Boolean> insertSubject(Subject subject){
        return subjectRepository.insertSubject(subject);
    }

    public LiveData<List<Subject>> getSubjects(String user_name,String semester_name){
        MutableLiveData<List<Subject>> liveData = new MutableLiveData<>();
        subjectRepository.getSubjects(user_name,semester_name, new DataCallback<List<Subject>>() {
            @Override
            public void onDataLoaded(List<Subject> data) {
                liveData.postValue(data);
            }

            @Override
            public void onError(Exception e) {
                liveData.postValue(null); // 或者处理错误
            }
        });
        return liveData;
    }


    public LiveData<List<Subject>> getSubject(String user_name,String semester_name,String subject_name){
        MutableLiveData<List<Subject>> liveData = new MutableLiveData<>();
        subjectRepository.getSubject(user_name,semester_name,subject_name, new DataCallback<List<Subject>>() {
            @Override
            public void onDataLoaded(List<Subject> data) {
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
