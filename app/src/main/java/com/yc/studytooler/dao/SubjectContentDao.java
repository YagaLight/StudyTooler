package com.yc.studytooler.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.yc.studytooler.bean.MediaItem;
import com.yc.studytooler.bean.SubjectContent;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SubjectContentDao
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/20 20:54
 * @VERSION 1.0
 */
@Dao
public interface SubjectContentDao {

    @Insert
    long insertSubjectContent(SubjectContent new_subject_content);

    @Insert
    void insertMediaItems(List<MediaItem> mediaItems);

    @Transaction
    default void insertPostWithMedia(SubjectContent subjectContent, List<MediaItem> mediaItems) {
        // 首先插入帖子内容
        long subjectContentId = insertSubjectContent(subjectContent);

        // 如果有媒体项，设置每个媒体项的subjectContentId并插入
        if (mediaItems != null && !mediaItems.isEmpty()) {
            for (MediaItem item : mediaItems) {
                item.setSubjectContentId((int) subjectContentId);
            }
            insertMediaItems(mediaItems);
        }
    }


    //根据学期+姓名+科目+打卡的日期查找
    @Query("SELECT * FROM SubjectContent WHERE user_name = :user_name AND semester_name = :semester_name AND subject_name = :subject_name AND punch_time = :punch_date")
    List<SubjectContent> getDefaultSubjectContent(String user_name, String semester_name, String subject_name, Date punch_date);

    @Query("SELECT * FROM MediaItem WHERE subject_content_id = :postId")
    List<MediaItem> getMediaForPost(int postId);

    //根据学期+姓名+科目+打卡+类型的日期查找
    @Query("SELECT * FROM SubjectContent WHERE user_name = :user_name AND semester_name = :semester_name AND subject_name = :subject_name AND punch_time = :punch_date AND content_type = :contentType" )
    List<SubjectContent> getSubjectContentByType(String user_name, String semester_name, String subject_name, Date punch_date,String contentType);


    //根据学期+姓名+科目+打卡+关键字的日期查找
    @Query("SELECT * FROM SubjectContent WHERE user_name = :user_name AND semester_name = :semester_name AND subject_name = :subject_name AND punch_time = :punch_date AND content_text LIKE '%' || :tags || '%'")
    List<SubjectContent> getSubjectContentByTags(String user_name, String semester_name, String subject_name, Date punch_date,String tags);

    //根据学期+姓名+科目+打卡+关键字+类型的日期查找
    @Query("SELECT * FROM SubjectContent WHERE user_name = :user_name AND semester_name = :semester_name AND subject_name = :subject_name AND punch_time = :punch_date AND content_type = :content_type AND content_text LIKE '%' || :tags || '%'")
    List<SubjectContent> getSubjectContentByTypeAndTags(String user_name, String semester_name, String subject_name, Date punch_date,String content_type,String tags);
}
