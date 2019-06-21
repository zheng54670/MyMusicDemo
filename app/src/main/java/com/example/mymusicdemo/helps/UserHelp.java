package com.example.mymusicdemo.helps;


/**
 * 用户自动登录
 *
 * 1、用户登录
 *      1、当用户登录应用程序时，利用SharePreferences保存登录用户的标记（phone）
 *      2、利用全局单例类UserHelp保存用户登录信息
 *          1、用户登录之后
 *          2、用户打开应用程序，检查SharePreferences中是否存在用户登录标记，
 *          如果存在则为UserHelp赋值，并进入主页，否则进入登录页面
 *
 * 2、用户退出
 *      1、删除SharePreferences中保存的用户标记，然后退出到用户登录页面
 */
public class UserHelp {

    private static UserHelp instance;

    private UserHelp(){}
    public static UserHelp getInstance(){
        if (instance == null){
            synchronized (UserHelp.class){
                if (instance ==null){
                    instance = new UserHelp();
                }
            }
        }
        return instance;
    }
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
