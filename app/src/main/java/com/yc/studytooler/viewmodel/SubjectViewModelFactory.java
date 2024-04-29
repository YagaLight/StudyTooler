package com.yc.studytooler.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yc.studytooler.bean.Subject;
import com.yc.studytooler.repository.SubjectRepository;

/**
 * @ClassName SubjectViewModelFactory
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/20 22:50
 * @VERSION 1.0
 */
public class SubjectViewModelFactory implements ViewModelProvider.Factory{

    private SubjectRepository subjectRepository;

    public SubjectViewModelFactory() {
        this.subjectRepository = new SubjectRepository();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SubjectViewModel.class)) {
            return (T) new SubjectViewModel(subjectRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
