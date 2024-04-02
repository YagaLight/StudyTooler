package com.yc.studytooler.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * @ClassName ImageConverter
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/1 4:31
 * @VERSION 1.0
 */
public class ImageConverter {

    //将Bitmap转换为byte[]
    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

    //从byte[] 转换为 Bitmap
    public static Bitmap byteArrayToBitmap(byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
    }



}
