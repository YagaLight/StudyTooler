package com.yc.studytooler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yc.studytooler.bean.ErrorBook;
import com.yc.studytooler.repository.ErrorBookRepository;

/**
 * @ClassName ErrorBookViewModelFactory
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/17 0:02
 * @VERSION 1.0
 */
public class ErrorBookViewModelFactory implements ViewModelProvider.Factory{

    private ErrorBookRepository errorBookRepository;

    public ErrorBookViewModelFactory(ErrorBookRepository errorBookRepository) {
        this.errorBookRepository = errorBookRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ErrorBookViewModel.class)) {
            return (T) new ErrorBookViewModel(errorBookRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
