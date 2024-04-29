package com.yc.studytooler.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.yc.studytooler.bean.Punch;
import com.yc.studytooler.bean.Subject;

import java.util.Date;
import java.util.List;

/**
 * @ClassName PunchDao
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/12 22:52
 * @VERSION 1.0
 */
@Dao
public interface PunchDao {

    @Insert
    void insertPunch(Punch punch);

    @Query("SELECT * FROM Punch WHERE user_name = :user_name AND semester_name = :semester_name AND subject_name = :subject_name")
    List<Punch> getPunches(String user_name, String semester_name, String subject_name);

    @Query("SELECT * FROM Punch WHERE user_name = :user_name AND semester_name = :semester_name AND subject_name = :subject_name AND subject_punch_date = :punch_date")
    List<Punch> getPunchesByDate(String user_name, String semester_name, String subject_name, Date punch_date);

}
