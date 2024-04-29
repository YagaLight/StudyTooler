package com.yc.studytooler.utils;

import com.yc.studytooler.bean.Punch;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DateUtils
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/9 15:18
 * @VERSION 1.0
 */
public class DateUtils {

     //获取当天的日期
    public static Date getToday(){
        // 获取当前日期和时间的 Calendar 实例
        Calendar calendar = Calendar.getInstance();

        // 从 Calendar 对象获取 Date 对象
        Date currentDate = calendar.getTime();

        return currentDate;
    }

    //对比两个日期谁先谁后,一般前者是打卡的当天日期，后者是截止日期
    public static Boolean stopDate(Date date1,Date date2){
        if (date1.equals(date2) || date1.before(date2)) {
            return true;
        }
        return false;
    }

    public static long diffDate(Date date1,Date date2){
        // 计算两个日期之间的天数差
        long diffInMillies = Math.abs(date2.getTime() - date1.getTime()); // 使用Math.abs确保差值为正数
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }


    //将Date类型的数据转换成YYYY-MM-DD形式
    public static String convertCommonDate(Date date){
        if (date == null) {
            return "";
        }else{
            // 创建一个SimpleDateFormat对象，并设置你想要的日期格式
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            // 使用format方法将Date对象转换成指定格式的字符串
            String formattedDate = formatter.format(date);

            return formattedDate;
        }
    }

    public static int countTodaysDates(List<Punch> punches) {
        LocalDate today = LocalDate.now(); // 当前日期
        int count = 0;

        for (Punch punch : punches) {
            // 将 Date 转换为 LocalDate
            LocalDate localDate = punch.getSubject_punch_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (localDate.equals(today)) {
                count++;
            }
        }

        return count;
    }

}
