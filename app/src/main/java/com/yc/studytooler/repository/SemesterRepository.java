package com.yc.studytooler.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yc.studytooler.MainApplication;
import com.yc.studytooler.bean.Punch;
import com.yc.studytooler.bean.Semester;
import com.yc.studytooler.callback.DataCallback;
import com.yc.studytooler.dao.SemesterDao;
import com.yc.studytooler.utils.Event;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName SemesterRepository
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/19 18:46
 * @VERSION 1.0
 */
public class SemesterRepository {

    private SemesterDao semesterDao;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public SemesterRepository() {
        semesterDao = MainApplication.getInstance().getStudyToolerDateBase().semesterDao();
    }

    //增加
    public LiveData<Boolean> insertSemester(Semester semester){
        MutableLiveData<Boolean> insertResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                semesterDao.insertSemester(semester);
                insertResult.postValue(true);
            } catch (Exception e) {
                insertResult.postValue(false);
            }
        });
        return insertResult;
    }



    //删除
    public LiveData<Boolean> deleteSemester(String user_name,String semester_name){
        MutableLiveData<Boolean> deleteResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                semesterDao.deleteSemester(user_name,semester_name);
                deleteResult.postValue(true);
            } catch (Exception e) {
                deleteResult.postValue(false);
            }
        });
        return deleteResult;
    }

    public LiveData<Boolean> updateDefaultSemester(String user_name,String semester_name){
        MutableLiveData<Boolean> updateResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                semesterDao.updateDefaultSemester(user_name,semester_name);
                updateResult.postValue(true);
            } catch (Exception e) {
                updateResult.postValue(false);
            }
        });
        return updateResult;
    }



    public void getSemestersByUserAsync(String userName, DataCallback<List<Semester>> callback) {
        executorService.execute(() -> {
            try {
                List<Semester> semesterList = semesterDao.getAllSemestersByUser(userName);
                if (callback != null) {
                    callback.onDataLoaded(semesterList);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public void getDefaultSemesterAsync(String userName, DataCallback<String> callback) {
        executorService.execute(() -> {
            try {
                String default_semester = semesterDao.getDefaultSemester(userName);
                if (callback != null) {
                    callback.onDataLoaded(default_semester);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }




}
