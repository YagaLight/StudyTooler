package com.yc.studytooler.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yc.studytooler.repository.SubjectContentRepository;
import com.yc.studytooler.repository.SubjectRepository;

/**
 * @ClassName SubjectContentViewModelFactory
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/20 22:50
 * @VERSION 1.0
 */
public class SubjectContentViewModelFactory implements ViewModelProvider.Factory {

    private SubjectContentRepository subjectContentRepository;

    public SubjectContentViewModelFactory() {
        subjectContentRepository = new SubjectContentRepository();
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SubjectContentViewModel.class)) {
            return (T) new SubjectContentViewModel(subjectContentRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
