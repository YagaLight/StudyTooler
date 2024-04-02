package com.yc.studytooler.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.repository.UserRepository;

import java.util.List;

/**
 * @ClassName UserViewModel
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/1 12:45
 * @VERSION 1.0
 */
public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = UserRepository.getInstance(application);
    }

    public LiveData<Boolean> insert(UserInfo user){
        return repository.insert(user);
    }

    public LiveData<List<UserInfo>> getAllUsersInLogin(){
        return repository.getAllUsersInLogin();
    }

    public LiveData<List<UserInfo>> getAllUsers(){
        return repository.getAllUsers();
    }

    public LiveData<UserInfo> getUser(String username, String password) {
        return repository.getUser(username, password);
    }
}
