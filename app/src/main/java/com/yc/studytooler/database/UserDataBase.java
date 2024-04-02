package com.yc.studytooler.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.dao.UserDao;

@Database(entities = {UserInfo.class},version = 1, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao userDao();
}
