package com.yc.studytooler.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

/**
 * @ClassName Punch
 * @Descripttion TODO：打卡
 * @Author chaoyue
 * @Date 2024/4/11 14:37
 * @VERSION 1.0
 */
@Entity(tableName = "Punch")
public class Punch {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_name")
    public String user_name;

    @ColumnInfo(name = "semester_name")
    public String semester_name;

    @ColumnInfo(name = "subject_name")
    public String subject_name;

    @ColumnInfo(name = "subject_punch_date")
    private Date subject_punch_date;


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

    public Date getSubject_punch_date() {
        return subject_punch_date;
    }

    public void setSubject_punch_date(Date subject_punch_date) {
        this.subject_punch_date = subject_punch_date;
    }

    @Override
    public String toString() {
        return "Punch{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", semester_name='" + semester_name + '\'' +
                ", subject_name='" + subject_name + '\'' +
                ", subject_punch_date=" + subject_punch_date +
                '}';
    }
}
