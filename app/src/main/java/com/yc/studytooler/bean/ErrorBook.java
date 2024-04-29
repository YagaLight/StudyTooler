package com.yc.studytooler.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @ClassName ErrorBook
 * @Descripttion TODO：错题本
 * @Author chaoyue
 * @Date 2024/4/16 21:58
 * @VERSION 1.0
 */
@Entity(tableName = "ErrorBook")
public class ErrorBook {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_name")
    private String user_name;
    @ColumnInfo(name = "subject_imageId")
    private int subjectImageId;  // Changed to String to store image path
    @ColumnInfo(name = "subject_name")
    private String subject_name;

    @Ignore // 表示此属性不应被数据库持久化
    private boolean isSelected = false; // 用于UI逻辑的选中状态标记

    @Ignore
    public ErrorBook() {
    }

    @Ignore
    public ErrorBook(String user_name) {
        this.user_name = user_name;
    }

    @Ignore
    public ErrorBook(String user_name, int subjectImageId, String subject_name) {
        this.user_name = user_name;
        this.subjectImageId = subjectImageId;
        this.subject_name = subject_name;
    }

    @Ignore
    public ErrorBook(int subjectImageId, String subject_name) {
        this.subjectImageId = subjectImageId;
        this.subject_name = subject_name;
    }

    public ErrorBook(int id, String user_name, int subjectImageId, String subject_name) {
        this.id = id;
        this.user_name = user_name;
        this.subjectImageId = subjectImageId;
        this.subject_name = subject_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getSubjectImageId() {
        return subjectImageId;
    }

    public void setSubjectImageId(int subjectImageId) {
        this.subjectImageId = subjectImageId;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
