package com.yc.studytooler.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @ClassName Semester
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/19 18:20
 * @VERSION 1.0
 */
@Entity(tableName = "Semester")
public class Semester {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_name")
    private String user_name;

    @ColumnInfo(name = "semester_name")
    private String Semester_name;

    @ColumnInfo(name = "is_default")
    private boolean isDefault;

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
        return Semester_name;
    }

    public void setSemester_name(String semester_name) {
        Semester_name = semester_name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "Semester{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", Semester_name='" + Semester_name + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
