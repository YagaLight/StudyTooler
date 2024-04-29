package com.yc.studytooler.utils;

/**
 * @ClassName Event
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/13 19:51
 * @VERSION 1.0
 */
public class Event<T> {

    private T content;
    private boolean hasBeenHandled = false;

    public Event(T content) {
        this.content = content;
    }

    public T getContentIfNotHandled() {
        if (!hasBeenHandled) {
            hasBeenHandled = true;
            return content;
        }
        return null;
    }

    public T peekContent() {
        return content;
    }
}
