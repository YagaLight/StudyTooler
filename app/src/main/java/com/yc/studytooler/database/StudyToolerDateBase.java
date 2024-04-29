package com.yc.studytooler.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.yc.studytooler.bean.Converters;
import com.yc.studytooler.bean.ErrorBook;
import com.yc.studytooler.bean.MediaItem;
import com.yc.studytooler.bean.Punch;
import com.yc.studytooler.bean.Semester;
import com.yc.studytooler.bean.Subject;
import com.yc.studytooler.bean.SubjectContent;
import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.dao.ErrorBookDao;
import com.yc.studytooler.dao.PunchDao;
import com.yc.studytooler.dao.SemesterDao;
import com.yc.studytooler.dao.SubjectContentDao;
import com.yc.studytooler.dao.SubjectDao;
import com.yc.studytooler.dao.UserDao;

/**
 * @ClassName StudyToolerDateBase
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/16 19:31
 * @VERSION 1.0
 */
@Database(entities = {UserInfo.class,Punch.class, ErrorBook.class, Semester.class, Subject.class, SubjectContent.class, MediaItem.class}, version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class StudyToolerDateBase extends RoomDatabase {
    //打卡表
    public abstract PunchDao punchDao();
    //用户表
    public abstract UserDao userDao();

    //错题本
    public abstract ErrorBookDao errorBookDao();

    //学期
    public abstract SemesterDao semesterDao();

    public abstract SubjectDao subjectDao();

    public abstract SubjectContentDao subjectContentDao();
}
