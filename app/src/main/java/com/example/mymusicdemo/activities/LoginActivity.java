package com.example.mymusicdemo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mymusicdemo.R;
import com.example.mymusicdemo.utils.UserUtils;
import com.example.mymusicdemo.views.InputView;

//NavigationBar
public class LoginActivity extends BaseActivity {

    private InputView mInputPhone, mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        initNavBar(false, "登录", false);
        mInputPhone = findViewById(R.id.input_phone);
        mInputPassword = findViewById(R.id.input_password);
    }

    /**
     * 跳转注册页面点击事件
     */

    public void onStartRegisterClick(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 登录按钮点击事件
     */

    public void onCommitClick(View view) {
        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();

//        验证用户输入是否合法
//        if (!UserUtils.validataLogin(this,phone,password)){
//            return;
//        }
//        跳转到应用主页
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
