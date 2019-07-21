package com.example.mymusicdemo.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataUtils {

    /**
     * 读取资源文件中的数据
     * @return
     */
    public static String getJsonFromAssets(Context context , String fileName){

        /**
         * 1.StringBuilder 存放读取出的数据
         * 2.AssetManager资源管理器,open方法打开指定的资源文件,返回InputStream
         * 3.InputStreamReader (字节到字符的桥接器) ,BufferedReader (存放读取字符的缓冲区)
         * 4.循环利用BufferedReader 的 readLine 方法去取每一行的数据,并且把读取的数据放到StringBuilder里面
         * 5.返回读取出来的数据
         *
         */

//        1.创建StringBuilder 存放读取出的数据
        StringBuilder stringBuilder = new StringBuilder();

//        2.AssetManager资源管理器
        AssetManager assetManager = context.getAssets();

        try {
            InputStream inputStream = assetManager.open(fileName);

//            3.InputStreamReader (字节到字符的桥接器) ,BufferedReader (存放读取字符的缓冲区)
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

//            4.循环利用BufferedReader 的 readLine 方法去取每一行的数据,并且把读取的数据放到StringBuilder里面
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();


    }

}
