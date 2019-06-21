package com.example.mymusicdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.mymusicdemo.R;
import com.example.mymusicdemo.activities.LoginActivity;
import com.example.mymusicdemo.helps.RealmHelp;
import com.example.mymusicdemo.models.UserModel;

import java.util.List;

public class UserUtils {

    /**
     * 验证登录用户输入合法性
     */

    public static boolean validataLogin(Context context, String phone, String password) {

        //简单手机号码
//        RegexUtils.isMobileSimple(phone);
        //精确手机号码
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 退出登录
     */
    public static void logout(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
//        添加intent标志符，清理task栈，并新生成一个task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//        解决过度动画错乱，并且一定要在startActivity之后调用
        ((Activity) context).overridePendingTransition(R.anim.open_enter, R.anim.open_exit);
    }


    /**
     * 注册用户
     *
     * @param context         上下文
     * @param phone           用户手机号
     * @param password        密码
     * @param passwordConfirm 确认密码
     */
    public static boolean registerUser(Context context, String phone, String password, String passwordConfirm) {
        if (!RegexUtils.isMobileExact(phone)) {
            Toast.makeText(context, "无效手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(password) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认密码", Toast.LENGTH_SHORT).show();
            return false;
        }

//        用户当前输入的手机号是否被注册
        /**
         * 1、通过Realm获取到当前已经注册的所有用户
         * 2、根据用户输入的手机号匹配查询的所有用户，如果可以匹配则证明该手机号已经被注册，否则则手机号未被注册
         *
         */
        if (UserUtils.userExitOrNot(phone)) {
            Toast.makeText(context, "该手机号已被注册！", Toast.LENGTH_SHORT).show();
            return false;
        }


        UserModel userModel = new UserModel();
        userModel.setPhone(phone);
        userModel.setPassword(EncryptUtils.encryptMD5ToString(password));

        saveUser(userModel);
        return true;


    }

    /**
     * 保存用户到数据库
     *
     * @param userModel
     */
    public static void saveUser(UserModel userModel) {
        RealmHelp realmHelp = new RealmHelp();
        realmHelp.saveUser(userModel);
        realmHelp.close();
    }

    /**
     * 根据手机号判断用户是否存在
     */
    public static boolean userExitOrNot(String phone) {
        boolean result = false;

        RealmHelp realmHelp = new RealmHelp();
        List<UserModel> allUser = realmHelp.getAllUser();

        for (UserModel userModel : allUser) {
            if (userModel.getPhone().equals(phone)) {
//                当前手机号已经存在于数据库中
                result = true;
                break;
            }
        }

        return result;
    }

}
