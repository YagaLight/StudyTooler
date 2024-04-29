package com.yc.studytooler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.yc.studytooler.bean.Subject;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SubjectDao
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/20 20:53
 * @VERSION 1.0
 */
@Dao
public interface SubjectDao {

    @Insert
    void insertSubject(Subject subject);

    @Query("DELETE FROM Subject Where user_name = :user_name AND subject_name = :subject_name")
    int deleteSubject(String user_name,String subject_name);

    @Transaction
    default void updateSubjectAndContents(String user_name,String oldName, String newName) {
        updateSubjectName(user_name,oldName, newName);
        updateSubjectContentName(user_name,oldName, newName);
    }

    @Query("UPDATE Subject set subject_name = :new_subject_name WHERE user_name = :user_name AND subject_name = :old_subject_name")
    int updateSubjectName(String user_name,String new_subject_name,String old_subject_name);

    @Query("UPDATE SubjectContent set subject_name = :new_subject_name WHERE user_name = :user_name AND subject_name = :old_subject_name")
    int updateSubjectContentName(String user_name,String new_subject_name,String old_subject_name);


    @Query("SELECT * FROM Subject WHERE user_name = :user_name AND semester_name = :semester_name")
    List<Subject> getAllSubjectsByUserName(String user_name,String semester_name);

    @Query("SELECT * FROM Subject WHERE user_name = :user_name AND semester_name = :semester_name AND subject_name = :subject_name")
    List<Subject> getSubjectByUserName(String user_name,String semester_name,String subject_name);


}
