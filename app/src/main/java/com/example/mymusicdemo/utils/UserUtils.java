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
import com.example.mymusicdemo.helps.UserHelp;
import com.example.mymusicdemo.models.UserModel;

import java.util.List;

public class UserUtils {

    /**
     * 验证登录用户输入合法性
     */

    public static boolean validateLogin(Context context, String phone, String password) {

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

        /**
         * 1、判断当前手机号是否是已经注册的
         * 2、用户输入的密码是否匹配
         */
        if (!UserUtils.userExitOrNot(phone)) {
            Toast.makeText(context, "手机号未注册", Toast.LENGTH_SHORT).show();
            return false;
        }

        RealmHelp realmHelp = new RealmHelp();
        boolean result = realmHelp.validateUser(phone, EncryptUtils.encryptMD5ToString(password));

        if (!result) {
            Toast.makeText(context, "手机号或密码不正确！", Toast.LENGTH_SHORT).show();
            return false;
        }
//        保存用户登录标记
        boolean isSave = SharePreferencesUtils.saveUser(context, phone);
        if (!isSave) {
            Toast.makeText(context, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return false;
        }
//      保存用户标记，在全局单例类中
        UserHelp.getInstance().setPhone(phone);

//        保存音乐源数据
        realmHelp.setMusicSource(context);

        realmHelp.close();

        return true;
    }

    /**
     * 退出登录
     */
    public static void logout(Context context) {

//        删除SharedPreferences里保存的标记
        boolean isRemove = SharePreferencesUtils.removeUser(context);
        if (!isRemove) {
            Toast.makeText(context, "系统错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }


//        删除数据源
        RealmHelp realmHelp = new RealmHelp();
        realmHelp.removeMusicSource();
        realmHelp.close();


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

        realmHelp.close();
        return result;
    }

    /**
     * 验证是否存在已登录用户
     */

    public static boolean validateUserLogin(Context context) {
        return SharePreferencesUtils.isLoginUser(context);
    }

    /**
     * 修改密码
     * 1、数据验证
     * 1、原密码是否输入
     * 2、新密码是否输入且新密码与确认密码是否相同
     * 3、原密码是否输入正确
     * 1、Realm数据库中获取到当前登录的用户模型
     * 2、根据用户模型中保存的密码匹配用户原密码
     * 2、利用Realm模型自动更新特性来完成密码的修改
     */
    public static boolean changePassword(Context context, String oldPassword, String password, String passwordConfirm) {

        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(context, "原密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password) || !password.equals(passwordConfirm)) {
            Toast.makeText(context, "请确认密码！", Toast.LENGTH_SHORT).show();
            return false;
        }
//        验证原密码是否正确
        RealmHelp realmHelp = new RealmHelp();
        UserModel userModel = realmHelp.getUser();

        if (!EncryptUtils.encryptMD5ToString(oldPassword).equals(userModel.getPassword())) {
            Toast.makeText(context, "原密码不正确！", Toast.LENGTH_SHORT).show();
            return false;
        }
        realmHelp.changePasswod(EncryptUtils.encryptMD5ToString(password));

        realmHelp.close();

        return true;

    }


}
