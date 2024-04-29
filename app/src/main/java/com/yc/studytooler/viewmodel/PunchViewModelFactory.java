package com.yc.studytooler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yc.studytooler.MainApplication;
import com.yc.studytooler.repository.PunchRepository;

/**
 * @ClassName PunchViewModelFactory
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/13 16:40
 * @VERSION 1.0
 */
public class PunchViewModelFactory implements ViewModelProvider.Factory{

    private PunchRepository punchRepository;

    public PunchViewModelFactory() {
        this.punchRepository = new PunchRepository();
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PunchViewModel.class)) {
            return (T) new PunchViewModel(punchRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
