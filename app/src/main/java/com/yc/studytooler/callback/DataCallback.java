package com.yc.studytooler.callback;

/**
 * @ClassName DataCallback
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/13 21:40
 * @VERSION 1.0
 */
public interface DataCallback<T> {
    void onDataLoaded(T data);
    void onError(Exception e);
}
