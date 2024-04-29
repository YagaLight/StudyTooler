package com.yc.studytooler.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yc.studytooler.repository.PunchRepository;
import com.yc.studytooler.repository.SemesterRepository;

/**
 * @ClassName SemesterViewModelFactory
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/19 18:55
 * @VERSION 1.0
 */
public class SemesterViewModelFactory implements ViewModelProvider.Factory{

    private SemesterRepository semesterRepository;

    public SemesterViewModelFactory() {
        this.semesterRepository = new SemesterRepository();
    }



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SemesterViewModel.class)) {
             return (T) new SemesterViewModel(semesterRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
