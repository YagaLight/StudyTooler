package com.yc.studytooler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yc.studytooler.repository.UserRepository;

/**
 * @ClassName UserViewModelFactory
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/13 16:39
 * @VERSION 1.0
 */
public class UserViewModelFactory implements ViewModelProvider.Factory{
    private Application mApplication;
    private UserRepository userRepository;


    public UserViewModelFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserViewModelFactory(Application mApplication, UserRepository userRepository) {
        this.mApplication = mApplication;
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(userRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
