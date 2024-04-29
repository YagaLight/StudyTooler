package com.yc.studytooler.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yc.studytooler.MainApplication;
import com.yc.studytooler.dao.UserDao;
import com.yc.studytooler.bean.UserInfo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private UserDao userDao;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public UserRepository(){

        userDao = MainApplication.getInstance().getStudyToolerDateBase().userDao();
    }


    public void insert(UserInfo user){
        MutableLiveData<Boolean> insertResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                userDao.insertOneUser(user);
                insertResult.postValue(true);
            } catch (Exception e) {
                insertResult.postValue(false);
            }
        });
    }

    public LiveData<List<UserInfo>> getAllUsersInLogin(){
       return userDao.getAllUserInLogin();
    }


    public LiveData<List<UserInfo>> getAllUsers(){
       return userDao.getAllUsers();
    }

    public LiveData<UserInfo> getUser(String username,String password){
        return userDao.getUser(username,password);
    }

    public void deleteAllUser() {
        MutableLiveData<Boolean> deleteResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                userDao.deleteAllUser();
                deleteResult.postValue(true);
            } catch (Exception e) {
                deleteResult.postValue(false);
            }
        });
    }
}
