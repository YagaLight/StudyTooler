package com.yc.studytooler.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yc.studytooler.bean.MediaItem;
import com.yc.studytooler.bean.Subject;
import com.yc.studytooler.bean.SubjectContent;
import com.yc.studytooler.callback.DataCallback;
import com.yc.studytooler.repository.SubjectContentRepository;
import com.yc.studytooler.repository.SubjectRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SubjectContentViewModel
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/20 22:51
 * @VERSION 1.0
 */
public class SubjectContentViewModel extends ViewModel {

    private SubjectContentRepository subjectContentRepository;

    public SubjectContentViewModel(SubjectContentRepository subjectContentRepository) {
        this.subjectContentRepository = subjectContentRepository;
    }

    public LiveData<Boolean> insertPostWithMedia(SubjectContent subjectContent,List<MediaItem> mediaItems){
        return subjectContentRepository.insertPostWithMedia(subjectContent,mediaItems);
    }



    public LiveData<Boolean> insertSubjectContent(SubjectContent subjectContent){
        return subjectContentRepository.insertSubjectContent(subjectContent);
    }



    public LiveData<List<SubjectContent>> getDefaultSubjectContent(String user_name, String semester_name, String subject_name, Date punch_date){
        return subjectContentRepository.getDefaultSubjectContent(user_name,semester_name,subject_name,punch_date);
    }

    public LiveData<List<SubjectContent>> getSubjectContentByType(String user_name, String semester_name, String subject_name, Date punch_date,String content_type){
        MutableLiveData<List<SubjectContent>> liveData = new MutableLiveData<>();
        subjectContentRepository.getSubjectContentByType(user_name,semester_name,subject_name,punch_date,content_type,new DataCallback<List<SubjectContent>>() {
            @Override
            public void onDataLoaded(List<SubjectContent> data) {
                liveData.postValue(data);
            }

            @Override
            public void onError(Exception e) {
                liveData.postValue(null); // 或者处理错误
            }
        });
        return liveData;
    }

    public LiveData<List<SubjectContent>> getSubjectContentByTags(String user_name, String semester_name, String subject_name, Date punch_date,String tags){
        MutableLiveData<List<SubjectContent>> liveData = new MutableLiveData<>();
        subjectContentRepository.getSubjectContentByTags(user_name,semester_name,subject_name,punch_date,tags,new DataCallback<List<SubjectContent>>() {
            @Override
            public void onDataLoaded(List<SubjectContent> data) {
                liveData.postValue(data);
            }

            @Override
            public void onError(Exception e) {
                liveData.postValue(null); // 或者处理错误
            }
        });
        return liveData;
    }

    public LiveData<List<SubjectContent>> getSubjectContentByTypeAndTags(String user_name, String semester_name, String subject_name, Date punch_date,String tags,String content_type){
        MutableLiveData<List<SubjectContent>> liveData = new MutableLiveData<>();
        subjectContentRepository.getSubjectContentByTypeAndTags(user_name,semester_name,subject_name,punch_date,content_type,tags,new DataCallback<List<SubjectContent>>() {
            @Override
            public void onDataLoaded(List<SubjectContent> data) {
                liveData.postValue(data);
            }

            @Override
            public void onError(Exception e) {
                liveData.postValue(null); // 或者处理错误
            }
        });
        return liveData;
    }


    public LiveData<Map<SubjectContent, List<MediaItem>>> getAllPosts(String user_name, String semester_name, String subject_name, Date punch_date) {
        return subjectContentRepository.getAllPosts(user_name, semester_name, subject_name, punch_date);
    }


}
