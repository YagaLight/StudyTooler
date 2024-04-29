package com.yc.studytooler;

import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import com.yc.studytooler.database.StudyToolerDateBase;

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

    private StudyToolerDateBase studyToolerDateBase;

    public static MainApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        mApp = this; // 在打开应用时对静态的应用实例赋值


        studyToolerDateBase = Room.databaseBuilder(mApp,StudyToolerDateBase.class,"StudyToolerDateBase")
                .addMigrations()
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


    public StudyToolerDateBase getStudyToolerDateBase(){
        return studyToolerDateBase;
    }
}
