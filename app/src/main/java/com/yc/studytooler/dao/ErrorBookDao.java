package com.yc.studytooler.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yc.studytooler.bean.ErrorBook;

import java.util.List;

/**
 * @ClassName ErrorBookDao
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/16 23:33
 * @VERSION 1.0
 */
@Dao
public interface ErrorBookDao {
    // 插入新的错题本
    @Insert
    void insertErrorBook(ErrorBook errorBook);

    // 更新已存在的错题本
    @Update
    void updateErrorBook(ErrorBook errorBook);

    // 删除错题本
    @Delete
    int deleteErrorBooks(List<ErrorBook> errorBooks);

    // 查询所有错题本
    @Query("SELECT * FROM ErrorBook WHERE user_name = :user_name")
    List<ErrorBook> getAllErrorBooks(String user_name);
}
