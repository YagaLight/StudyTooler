package com.yc.studytooler.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yc.studytooler.MainApplication;
import com.yc.studytooler.bean.MediaItem;
import com.yc.studytooler.bean.Subject;
import com.yc.studytooler.bean.SubjectContent;
import com.yc.studytooler.callback.DataCallback;
import com.yc.studytooler.dao.SubjectContentDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName SubjectContentRepository
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/20 22:49
 * @VERSION 1.0
 */
public class SubjectContentRepository {

    private SubjectContentDao subjectContentDao;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public SubjectContentRepository() {
        subjectContentDao = MainApplication.getInstance().getStudyToolerDateBase().subjectContentDao();
    }



    public LiveData<Boolean> insertPostWithMedia(SubjectContent subject_content, List<MediaItem> mediaItems){
        MutableLiveData<Boolean> insertResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                subjectContentDao.insertPostWithMedia(subject_content,mediaItems);
                insertResult.postValue(true);
            } catch (Exception e) {
                insertResult.postValue(false);
            }
        });
        return insertResult;
    }

    public LiveData<Boolean> insertSubjectContent(SubjectContent subject_content){
        MutableLiveData<Boolean> insertResult = new MutableLiveData<>();
        executorService.execute(() -> {
            try {
                subjectContentDao.insertSubjectContent(subject_content);
                insertResult.postValue(true);
            } catch (Exception e) {
                insertResult.postValue(false);
            }
        });
        return insertResult;
    }

    public void getDefaultSubjectContent(String user_name, String semester_name, String subject_name, Date punch_date,DataCallback<List<SubjectContent>> callback){
        executorService.execute(() -> {
            try {
                List<SubjectContent> subjectContentList = subjectContentDao.getDefaultSubjectContent(user_name,semester_name,subject_name,punch_date);
                if (callback != null) {
                    callback.onDataLoaded(subjectContentList);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public LiveData<List<SubjectContent>> getDefaultSubjectContent(String user_name, String semester_name, String subject_name, Date punch_date){
        MutableLiveData<List<SubjectContent>> result = new MutableLiveData<>();
        executorService.execute(() -> {
             try{
                 List<SubjectContent> subjectContentList = subjectContentDao.getDefaultSubjectContent(user_name, semester_name, subject_name, punch_date);
                 result.postValue(subjectContentList);
             }catch (Exception e){
                 result.postValue(null);
             }
        });
        return result;
    }




    //这个是根据得到的Id来
    public LiveData<Map<SubjectContent, List<MediaItem>>> getAllPosts(String user_name, String semester_name, String subject_name, Date punch_date) {
        MutableLiveData<Map<SubjectContent, List<MediaItem>>> result = new MutableLiveData<>();
        executorService.execute(() -> {
            try{
                List<SubjectContent> subjectContentList = subjectContentDao.getDefaultSubjectContent(user_name, semester_name, subject_name, punch_date);
                Log.d("SubjectContentRepository","subjectContentList大小是："+subjectContentList.size());
                Map<SubjectContent, List<MediaItem>> map = new HashMap<>();
                for (SubjectContent post : subjectContentList) {
                    Log.d("SubjectContentRepository","进入到for循环里了");
                    Log.d("SubjectContentRepository","post的id是"+post.getSubject_content_id());
                    List<MediaItem> mediaItems = subjectContentDao.getMediaForPost(post.getSubject_content_id());
                    Log.d("SubjectContentRepository","mediaItems的大小是："+mediaItems.size());
                    map.put(post, mediaItems);
                }
                result.postValue(map);
            }catch (Exception e){
                result.postValue(null);
            }
        });
        return result;
    }


    public void getSubjectContentByType(String user_name, String semester_name, String subject_name, Date punch_date,String content_type,DataCallback<List<SubjectContent>> callback){
        executorService.execute(() -> {
            try {
                List<SubjectContent> subjectContentList = subjectContentDao.getSubjectContentByType(user_name,semester_name,subject_name,punch_date,content_type);
                if (callback != null) {
                    callback.onDataLoaded(subjectContentList);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public void getSubjectContentByTags(String user_name, String semester_name, String subject_name, Date punch_date,String tags,DataCallback<List<SubjectContent>> callback){
        executorService.execute(() -> {
            try {
                List<SubjectContent> subjectContentList = subjectContentDao.getSubjectContentByTags(user_name,semester_name,subject_name,punch_date,tags);
                if (callback != null) {
                    callback.onDataLoaded(subjectContentList);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public void getSubjectContentByTypeAndTags(String user_name, String semester_name, String subject_name, Date punch_date,String content_type,String tags,DataCallback<List<SubjectContent>> callback){
        executorService.execute(() -> {
            try {
                List<SubjectContent> subjectContentList = subjectContentDao.getSubjectContentByTypeAndTags(user_name,semester_name,subject_name,punch_date,content_type,tags);
                if (callback != null) {
                    callback.onDataLoaded(subjectContentList);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }
}
