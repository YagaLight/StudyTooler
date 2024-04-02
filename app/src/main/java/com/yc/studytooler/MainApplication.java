package com.yc.studytooler;

import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import com.yc.studytooler.database.UserDataBase;

/**
 * @ClassName MainApplication
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/2 10:07
 * @VERSION 1.0
 */
public class MainApplication extends MultiDexApplication {
    private final static String TAG = "MainApplication";

    private static MainApplication mApp;

    private UserDataBase userDataBase;


    public static MainApplication getInstance() {
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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public UserDataBase getUserDB(){
        return userDataBase;
    }
}
