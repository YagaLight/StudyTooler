package com.yc.studytooler.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserInfo")
public class UserInfo {

    @PrimaryKey(autoGenerate = true)
    private int user_id;

    @ColumnInfo(name = "user_name")
    private String user_name;

    @ColumnInfo(name = "user_pwd")
    private String user_pwd;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "signature")
    private String signature;

    @ColumnInfo(name = "qq")
    private String qq;

    @ColumnInfo(name = "weChat")
    private String weChat;

    @ColumnInfo(name = "remember")
    private int remember; //0是不记住密码，1是记住密码

    @ColumnInfo(name = "user_head")
    private byte[] user_head;


//    public UserInfo() {
//    }
//
//
//
//    public UserInfo(String user_name, String user_pwd) {
//        this.user_name = user_name;
//        this.user_pwd = user_pwd;
//    }



    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public int getRemember() {
        return remember;
    }

    public void setRemember(int remember) {
        this.remember = remember;
    }

    public byte[] getUser_head() {
        return user_head;
    }

    public void setUser_head(byte[] user_head) {
        this.user_head = user_head;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_pwd='" + user_pwd + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", signature='" + signature + '\'' +
                ", qq='" + qq + '\'' +
                ", weChat='" + weChat + '\'' +
                '}';
    }
}
