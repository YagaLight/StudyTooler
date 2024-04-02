package com.yc.studytooler;

import org.junit.Test;

import static org.junit.Assert.*;

import com.yc.studytooler.bean.UserInfo;
import com.yc.studytooler.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest{
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void TEST01() {
        List<UserInfo> userInfoList = new ArrayList<>();
        UserDao userDao = TestUserRepository.getInstance().getUserDB().userDao();
        userInfoList = userDao.getAllUsers();
        if (userInfoList == null ){
            System.out.println("是空的");
        }
//        System.out.println(userInfoList);
    }

}