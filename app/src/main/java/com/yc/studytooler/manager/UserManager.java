package com.yc.studytooler.manager;

/**
 * @ClassName UserManager
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/13 0:13
 * @VERSION 1.0
 */
public class UserManager {
    private static UserManager instance;
    private String userName;

    private UserManager() {}

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
