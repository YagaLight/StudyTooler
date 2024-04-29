package com.yc.studytooler.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.yc.studytooler.bean.ErrorBook;
import com.yc.studytooler.bean.Punch;
import com.yc.studytooler.repository.ErrorBookRepository;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName ErrorBookViewModel
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/17 0:02
 * @VERSION 1.0
 */
public class ErrorBookViewModel extends ViewModel {

    private ErrorBookRepository errorBookRepository;

    public ErrorBookViewModel(ErrorBookRepository errorBookRepository) {
        this.errorBookRepository = errorBookRepository;
    }

    public LiveData<Boolean>insertErrorBook(ErrorBook errorBook){
        return errorBookRepository.insertErrorBook(errorBook);
    }

    public LiveData<List<ErrorBook>> getErrorBooksByUser(String userName) {
        return errorBookRepository.getErrorBooksByUser(userName);
    }

    public LiveData<Boolean> deleteErrorBook(List<ErrorBook> errorBooks) {
        return errorBookRepository.deleteErrorBooks(errorBooks);
    }

}
