package com.yc.studytooler.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yc.studytooler.bean.Punch;
import com.yc.studytooler.callback.DataCallback;
import com.yc.studytooler.repository.PunchRepository;
import com.yc.studytooler.utils.Event;

import java.util.Date;
import java.util.List;

/**
 * @ClassName PunchViewModel
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/13 15:16
 * @VERSION 1.0
 */
public class PunchViewModel extends ViewModel {

    private PunchRepository punchRepository;

    public PunchViewModel(PunchRepository punchRepository) {
        this.punchRepository = punchRepository;
    }

    public LiveData<Boolean>insertPunch(Punch punch){
        return punchRepository.insert(punch);
    }

    // 在 PunchViewModel 中添加
    public LiveData<List<Punch>> getPunches(String user_name,String semester_name,String subject_name) {
        MutableLiveData<List<Punch>> liveData = new MutableLiveData<>();
        punchRepository.getPunches(user_name,semester_name,subject_name, new DataCallback<List<Punch>>() {
            @Override
            public void onDataLoaded(List<Punch> data) {
                liveData.postValue(data);
            }

            @Override
            public void onError(Exception e) {
                liveData.postValue(null); // 或者处理错误
            }
        });
        return liveData;
    }

    // 在 PunchViewModel 中添加
    public LiveData<List<Punch>> getPunchesByDate(String user_name,String semester_name,String subject_name, Date punch_date) {
        MutableLiveData<List<Punch>> liveData = new MutableLiveData<>();
        punchRepository.getPunchesByDate(user_name,semester_name,subject_name, punch_date,new DataCallback<List<Punch>>() {
            @Override
            public void onDataLoaded(List<Punch> data) {
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
