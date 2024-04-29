package com.yc.studytooler.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yc.studytooler.MainApplication;
import com.yc.studytooler.bean.ErrorBook;
import com.yc.studytooler.dao.ErrorBookDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ErrorBookRepository
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/17 0:01
 * @VERSION 1.0
 */
public class ErrorBookRepository {
    private ErrorBookDao errorBookDao;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public ErrorBookRepository() {
        errorBookDao = MainApplication.getInstance().getStudyToolerDateBase().errorBookDao();
    }

    public LiveData<Boolean> insertErrorBook(ErrorBook errorBook){
        MutableLiveData<Boolean> insertResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                errorBookDao.insertErrorBook(errorBook);
                insertResult.postValue(true);
            } catch (Exception e) {
                insertResult.postValue(false);
            }
        });
        return insertResult;
    }

    public LiveData<List<ErrorBook>> getErrorBooksByUser(String userName) {
        MutableLiveData<List<ErrorBook>> liveData = new MutableLiveData<>();
        executorService.execute(() -> {
            List<ErrorBook> errorBooks = errorBookDao.getAllErrorBooks(userName);
            liveData.postValue(errorBooks);
        });
        return liveData;
    }

    public LiveData<Boolean> deleteErrorBooks(List<ErrorBook> errorBooks){
        MutableLiveData<Boolean> deleteResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                int count = errorBookDao.deleteErrorBooks(errorBooks);
                deleteResult.postValue(count == errorBooks.size());
            } catch (Exception e) {
                deleteResult.postValue(false);
            }
        });
        return deleteResult;
    }


}
