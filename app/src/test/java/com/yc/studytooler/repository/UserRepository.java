package com.yc.studytooler.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.dao.UserDao;
import com.yc.studytooler.database.UserDataBase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private final static String TAG = "MainApplication";


    private UserDataBase userDataBase;

    private static UserRepository instance;
    private UserDao userDao;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    private UserRepository(Application application){
        //构建用户信息数据库实例
        userDataBase = Room.databaseBuilder(application,UserDataBase.class,"UserInfo")
                .addMigrations()
                .allowMainThreadQueries()
                .build();

        userDao = getInstance(application).getUserDB().userDao();
    }

    //采用单例模式获取当前应用的唯一实例
    public static synchronized UserRepository getInstance(Application application){
        if(instance == null){
            instance = new UserRepository(application);
        }
        return instance;
    }



    //获取用户信息数据库实例
    public UserDataBase getUserDB(){
        return userDataBase;
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

    public LiveData<List<UserInfo>> getAllUsersInLogin(){
        MutableLiveData<List<UserInfo>> liveData = new MutableLiveData<>();
        executorService.execute(() -> {
            List<UserInfo> userList = userDao.getAllUserInLogin();
            liveData.postValue(userList);
        });
        return liveData;
    }


    public LiveData<List<UserInfo>> getAllUsers(){
        MutableLiveData<List<UserInfo>> liveData = new MutableLiveData<>();
        executorService.execute(() -> {
            List<UserInfo> userList = userDao.getAllUsers();
            liveData.postValue(userList);
        });
        return liveData;
    }

    public LiveData<UserInfo> getUser(String username,String password){
        return userDao.getUser(username,password);
    }

}
