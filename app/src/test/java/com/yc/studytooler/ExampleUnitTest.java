package com.yc.studytooler;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.yc.studytooler.bean.Punch;
import com.yc.studytooler.dao.PunchDao;
import com.yc.studytooler.viewmodel.PunchViewModel;
import com.yc.studytooler.viewmodel.PunchViewModelFactory;
import com.yc.studytooler.viewmodel.SubjectContentViewModel;
import com.yc.studytooler.viewmodel.SubjectContentViewModelFactory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest{

    private String user_name = "c";
    private String semester_name = "大一上";
    private String subject_name = "高数";
    private Date punch_date;
    private List<Punch> punchList;


    @Test
    public void addition_isCorrect() {

    }



}