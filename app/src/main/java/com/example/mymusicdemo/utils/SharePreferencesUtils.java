package com.example.mymusicdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.mymusicdemo.constant.SharePreferencesConstants;
import com.example.mymusicdemo.helps.UserHelp;

public class SharePreferencesUtils {

    /**
     * 当用户登录应用程序时，利用SharePreferences保存登录用户的标记（phone）
     */

    public static boolean saveUser(Context context, String phone) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SharePreferencesConstants.SP_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharePreferencesConstants.SP_KEY_PHONE, phone);
        boolean result = editor.commit();
        return result;
    }

    /**
     * 验证是否已经存在登录用户
     */

    public static boolean isLoginUser(Context context) {
        boolean result = false;

        SharedPreferences sharedPreferences = context.getSharedPreferences(SharePreferencesConstants.SP_NAME_USER, Context.MODE_PRIVATE);
        String phone = sharedPreferences.getString(SharePreferencesConstants.SP_KEY_PHONE,"");

        if (!TextUtils.isEmpty(phone)){
            result = true;
            UserHelp.getInstance().setPhone(phone);
        }
        return result;
    }

    /**
     * 删除用户标记
     */
    public static boolean removeUser(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharePreferencesConstants.SP_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(SharePreferencesConstants.SP_KEY_PHONE);
        boolean result = editor.commit();
        return result;

    }
}
