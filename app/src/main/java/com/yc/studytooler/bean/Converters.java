package com.yc.studytooler.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * @ClassName Converters
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/13 13:24
 * @VERSION 1.0
 */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    @TypeConverter
    //将Bitmap转换为byte[]
    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

    @TypeConverter
    //从byte[] 转换为 Bitmap
    public static Bitmap byteArrayToBitmap(byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
    }




}
