package com.yc.studytooler.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yc.studytooler.MainApplication;
import com.yc.studytooler.bean.Punch;
import com.yc.studytooler.callback.DataCallback;
import com.yc.studytooler.dao.PunchDao;
import com.yc.studytooler.utils.Event;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName PunchRepository
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/12 23:06
 * @VERSION 1.0
 */
public class PunchRepository {

    private PunchDao punchDao;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public PunchRepository() {
        punchDao = MainApplication.getInstance().getStudyToolerDateBase().punchDao();
    }


    public LiveData<Boolean> insert(Punch punch){
        MutableLiveData<Boolean> insertResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                punchDao.insertPunch(punch);
                insertResult.postValue(true);
            } catch (Exception e) {
                insertResult.postValue(false);
            }
        });
        return insertResult;
    }


    public void getPunches(String user_name, String semester_name, String subject_name, DataCallback<List<Punch>> callback) {
        executorService.execute(() -> {
            try {
                List<Punch> punches = punchDao.getPunches(user_name, semester_name, subject_name);
                if (callback != null) {
                    callback.onDataLoaded(punches);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public void getPunchesByDate(String user_name, String semester_name, String subject_name, Date punch_date, DataCallback<List<Punch>> callback) {
        executorService.execute(() -> {
            try {
                List<Punch> punches = punchDao.getPunchesByDate(user_name, semester_name, subject_name,punch_date);
                if (callback != null) {
                    callback.onDataLoaded(punches);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

}
