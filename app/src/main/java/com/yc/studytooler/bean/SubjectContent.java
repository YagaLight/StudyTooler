package com.yc.studytooler.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * @ClassName SubjectContent
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/19 19:50
 * @VERSION 1.0
 */
@Entity(tableName = "SubjectContent")
public class SubjectContent {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int subject_content_id;

    @ColumnInfo(name = "user_name")
    private String user_name;//用户名

    @ColumnInfo(name = "semester_name")
    private String semester_name;//学期

    @ColumnInfo(name = "subject_name")
    private String subject_name;//学科名

    @ColumnInfo(name = "punch_time")
    private Date punch_time;//发布时间

    //还需要存储一些属性：存储笔记和练习题：纯文本，文本+图片，文本+语音，文本+视频，文本+pdf文件
    @ColumnInfo(name = "content_type")
    private String contentType;  //内容类型 例如 "text", "image", "audio", "video", "pdf"

    @ColumnInfo(name = "content_text")
    private String contentText;  // 纯文本内容


    @ColumnInfo(name = "title")
    private String title;  // 文件的标题



    public int getSubject_content_id() {
        return subject_content_id;
    }

    public void setSubject_content_id(int subject_content_id) {
        this.subject_content_id = subject_content_id;
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

    public Date getPunch_time() {
        return punch_time;
    }

    public void setPunch_time(Date punch_time) {
        this.punch_time = punch_time;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "SubjectContent{" +
                "subject_content_id=" + subject_content_id +
                ", user_name='" + user_name + '\'' +
                ", semester_name='" + semester_name + '\'' +
                ", subject_name='" + subject_name + '\'' +
                ", punch_time=" + punch_time +
                ", contentType='" + contentType + '\'' +
                ", contentText='" + contentText + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
