package com.yc.studytooler.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.yc.studytooler.MainApplication;
import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.dao.UserDao;
import com.yc.studytooler.database.UserDataBase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private UserDao userDao;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public UserRepository(){

        userDao = MainApplication.getInstance().getUserDB().userDao();
    }


    public LiveData<Boolean> insert(UserInfo user){
        MutableLiveData<Boolean> insertResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                userDao.insertOneUser(user);
                insertResult.postValue(true);
            } catch (Exception e) {
                insertResult.postValue(false);
            }
        });
        return insertResult;
    }

    public List<UserInfo> getAllUsersInLogin(){
       return userDao.getAllUserInLogin();
    }


    public List<UserInfo> getAllUsers(){
       return userDao.getAllUsers();
    }

    public UserInfo getUser(String username,String password){
        return userDao.getUser(username,password);
    }

    public LiveData<Boolean> deleteAllUser() {
        MutableLiveData<Boolean> deleteResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                userDao.deleteAllUser();
                deleteResult.postValue(true);
            } catch (Exception e) {
                deleteResult.postValue(false);
            }
        });
        return deleteResult;
    }
}
