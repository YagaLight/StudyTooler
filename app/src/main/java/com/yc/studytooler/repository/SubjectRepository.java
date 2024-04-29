package com.yc.studytooler.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yc.studytooler.MainApplication;
import com.yc.studytooler.bean.Semester;
import com.yc.studytooler.bean.Subject;
import com.yc.studytooler.callback.DataCallback;
import com.yc.studytooler.dao.SubjectDao;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName SubjectRepository
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/20 22:49
 * @VERSION 1.0
 */
public class SubjectRepository {

    private SubjectDao subjectDao;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public SubjectRepository() {
        subjectDao = MainApplication.getInstance().getStudyToolerDateBase().subjectDao();
    }


    public LiveData<Boolean> insertSubject(Subject subject){
        MutableLiveData<Boolean> insertResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                subjectDao.insertSubject(subject);
                insertResult.postValue(true);
            } catch (Exception e) {
                insertResult.postValue(false);
            }
        });
        return insertResult;
    }

    public void getSubjects(String user_name, String semester_name, DataCallback<List<Subject>> callback){
        executorService.execute(() -> {
            try {
                List<Subject> subjectList = subjectDao.getAllSubjectsByUserName(user_name,semester_name);
                if (callback != null) {
                    callback.onDataLoaded(subjectList);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public void getSubject(String user_name, String semester_name, String subject_name,DataCallback< List<Subject>> callback){
        executorService.execute(() -> {
            try {
                List<Subject> subject = subjectDao.getSubjectByUserName(user_name,semester_name,subject_name);
                if (callback != null) {
                    callback.onDataLoaded(subject);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }


}
