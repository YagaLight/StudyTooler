package com.yc.studytooler.repository;

import android.app.Application;
import android.util.Log;

import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import com.yc.studytooler.database.UserDataBase;

/**
 * @ClassName TestUserRepository
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/2 5:16
 * @VERSION 1.0
 */
public class TestUserRepository extends MultiDexApplication {

    private final static String TAG = "MainApplication";
    private static TestUserRepository mApp; // 声明一个当前应用的静态实例


    private UserDataBase userDataBase;

    // 利用单例模式获取当前应用的唯一实例
    public static TestUserRepository getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        mApp = this; // 在打开应用时对静态的应用实例赋值

        //构建用户信息数据库实例
        userDataBase = Room.databaseBuilder(mApp,UserDataBase.class,"UserInfo")
                .addMigrations()
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }

    public UserDataBase getUserDB(){
        return userDataBase;
    }


}
