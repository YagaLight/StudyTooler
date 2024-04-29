package com.yc.studytooler.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ClassName CustomRecyclerView
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/9 16:37
 * @VERSION 1.0
 */
public class CustomRecyclerView extends RecyclerView {

    private float startX;
    private float startY;
    private final int touchSlop;


    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration vc = ViewConfiguration.get(context);
        touchSlop = vc.getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = e.getX();
                startY = e.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = e.getX();
                float endY = e.getY();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                if (distanceX > touchSlop && distanceX > distanceY) {
                    getParent().requestDisallowInterceptTouchEvent(canScrollHorizontally(startX > endX ? 1 : -1));
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(e);
    }
}
