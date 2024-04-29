package com.yc.studytooler.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yc.studytooler.bean.Punch;

import java.util.Date;

/**
 * @ClassName SharedViewModel
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/13 22:46
 * @VERSION 1.0
 */
public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> userName = new MutableLiveData<>();
    private final MutableLiveData<byte[]> avatarUrl = new MutableLiveData<>();

    private final MutableLiveData<String> semester_name = new MutableLiveData<>();

    private final MutableLiveData<String> subject_name = new MutableLiveData<>();

    private final MutableLiveData<Date> punch_date = new MutableLiveData<>();

    public void setUserName(String name) {
        userName.setValue(name);
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public void setAvatarUrl(byte[] url) {
        avatarUrl.setValue(url);
    }

    public LiveData<byte[]> getAvatarUrl() {
        return avatarUrl;
    }

    public void setSemester_name(String name){
        semester_name.setValue(name);
    }

    public LiveData<String> getSemester_name(){
        return semester_name;
    }

    public void setSubject_name(String name){
        subject_name.setValue(name);
    }

    public LiveData<String> getSubject_name(){
        return subject_name;
    }

    public void setPunch_date(Date date){
        punch_date.setValue(date);
    }

    public LiveData<Date> getPunch_date(){
        return punch_date;
    }

}
