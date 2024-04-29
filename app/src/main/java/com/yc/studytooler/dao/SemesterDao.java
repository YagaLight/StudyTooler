package com.yc.studytooler.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.yc.studytooler.bean.Semester;

import java.util.List;

/**
 * @ClassName SemesterDao
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/19 18:24
 * @VERSION 1.0
 */
@Dao
public interface SemesterDao {
    //插入学期
    @Insert
    void insertSemester(Semester new_semester);

    //删除学期
    @Query("DELETE FROM Semester WHERE semester_name = :semester_name and user_name = :user_name")
    int deleteSemester(String user_name,String semester_name);

    //修改默认学期
    @Query("UPDATE semester SET is_default = 0 WHERE user_name = :userName")
    void resetDefaultSemesters(String userName);

    @Query("UPDATE semester SET is_default = 1 WHERE semester_name = :semesterName AND user_name = :userName")
    int setDefaultSemester(String userName, String semesterName);

    @Transaction
    default int updateDefaultSemester(String userName, String semesterName) {
        resetDefaultSemesters(userName);
        return setDefaultSemester(userName, semesterName);
    }

    @Query("SELECT semester_name FROM SEMESTER WHERE user_name = :user_name")
    String getDefaultSemester(String user_name);

    //查找该用户所创建的所有学期
    @Query("SELECT * FROM semester WHERE user_name = :userName")
    List<Semester> getAllSemestersByUser(String userName);
}
