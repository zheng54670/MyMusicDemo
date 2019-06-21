package com.example.mymusicdemo.activities;

import android.os.Bundle;
import android.view.View;

import com.example.mymusicdemo.R;
import com.example.mymusicdemo.utils.UserUtils;
import com.example.mymusicdemo.views.InputView;

public class RegisterActivity extends BaseActivity {

    private InputView mInputPhone, mInputPassword, mInputPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }


    /**
     * 初始化View
     */
    private void initView() {
        initNavBar(true, "注册", false);
        mInputPhone = findViewById(R.id.input_register_phone);
        mInputPassword = findViewById(R.id.input_register_password);
        mInputPasswordConfirm = findViewById(R.id.confirm_register_password);
    }

    /**
     * 注册按钮点击事件
     * 1、对用户输入的合法性验证
     * 1、用户输入的手机号是不是合法
     * 2、用户是否输入了密码和确定密码，并且两次的内容是否相同
     * 3、用户输入的手机号是否已经被注册
     * 2、保存用户手机号和密码（MD5加密）
     */

    public void onRegisterClick(View view) {
        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();
        String passwordConfirm = mInputPasswordConfirm.getInputStr();

        boolean result = UserUtils.registerUser(this, phone, password, passwordConfirm);

        if (!result) {
            return;
        }

//        注册完成后退到登录页面
        onBackPressed();
    }

}
