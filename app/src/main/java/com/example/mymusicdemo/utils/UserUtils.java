package com.example.mymusicdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.example.mymusicdemo.R;
import com.example.mymusicdemo.activities.LoginActivity;

public class UserUtils {

    /**
     * 验证登录用户输入合法性
     */

    public static boolean validataLogin(Context context, String phone, String password){

        //简单手机号码
//        RegexUtils.isMobileSimple(phone);
        //精确手机号码
        if (!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context,"无效手机号",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(context,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 退出登录
     */
    public static void logout(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
//        添加intent标志符，清理task栈，并新生成一个task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//        解决过度动画错乱，并且一定要在startActivity之后调用
        ((Activity)context).overridePendingTransition(R.anim.open_enter,R.anim.open_exit);
    }



}
