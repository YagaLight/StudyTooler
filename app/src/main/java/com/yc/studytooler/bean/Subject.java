package com.yc.studytooler.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName Subject
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/19 18:09
 * @VERSION 1.0
 */
@Entity(tableName = "Subject")
public class Subject {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_name")
    private String user_name;

    @ColumnInfo(name = "semester_name")
    private String semester_name;

    @ColumnInfo(name = "subject_name")
    private String subject_name;



    //存储笔记和练习题：文本，文本+图片，文本+语音，文本+视频，文本+pdf文件


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

    public String getSemester_name() {
        return semester_name;
    }

    public void setSemester_name(String semester_name) {
        this.semester_name = semester_name;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }


}
