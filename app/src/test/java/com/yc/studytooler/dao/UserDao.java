package com.yc.studytooler.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yc.studytooler.bean.UserInfo;

import java.util.List;

@Dao
public interface UserDao {

    //插入一个用户
    @Insert(onConflict = OnConflictStrategy.REPLACE)//记录重复时替换原纪录
    void insertOneUser(UserInfo user);

    //根据用户名查询
    @Query("SELECT * FROM UserInfo WHERE user_name = :name")
    UserInfo getUserByName(String name);

    //登录使用
    @Query("SELECT * FROM UserInfo WHERE user_name = :name AND user_pwd = :password")
    LiveData<UserInfo> getUser(String name, String password);


    //查询所有用户,用于登录页面
    @Query("SELECT * FROM UserInfo")
    List<UserInfo> getAllUserInLogin();

    //查询所有用户,用于其他页面
    @Query("SELECT * FROM UserInfo")
    List<UserInfo> getAllUsers();
}
