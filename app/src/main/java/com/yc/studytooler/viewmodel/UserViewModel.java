package com.yc.studytooler.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.repository.UserRepository;

import java.util.List;

/**
 * @ClassName UserViewModel
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/13 16:05
 * @VERSION 1.0
 */
public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insert(UserInfo user){
       userRepository.insert(user);
    }

    public LiveData<List<UserInfo>> getAllUsersInLogin(){
        return userRepository.getAllUsersInLogin();
    }


    public LiveData<List<UserInfo>> getAllUsers(){
        return userRepository.getAllUsers();
    }

    public LiveData<UserInfo> getUser(String username,String password){
        return userRepository.getUser(username,password);
    }

    public void deleteAllUser() {
       userRepository.deleteAllUser();
    }
}
