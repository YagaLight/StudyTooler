package com.yc.studytooler.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @ClassName MediaItem
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/22 12:24
 * @VERSION 1.0
 */
@Entity(tableName = "MediaItem", foreignKeys = @ForeignKey(entity = SubjectContent.class,
        parentColumns = "id",
        childColumns = "subject_content_id",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "subject_content_id")})
public class MediaItem {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "subject_content_id")
    private int subjectContentId; // 关联到SubjectContent表的外键

    @ColumnInfo(name = "media_type")
    private String mediaType; // 媒体类型，例如 "image", "audio", "video", "pdf"


    @ColumnInfo(name = "media_title")
    private String mediaTitle;

    @ColumnInfo(name = "uri")
    private String uri; // 媒体文件的存储URI

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectContentId() {
        return subjectContentId;
    }

    public void setSubjectContentId(int subjectContentId) {
        this.subjectContentId = subjectContentId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "id=" + id +
                ", subjectContentId=" + subjectContentId +
                ", mediaType='" + mediaType + '\'' +
                ", mediaTitle='" + mediaTitle + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
