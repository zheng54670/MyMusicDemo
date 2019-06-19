package com.example.mymusicdemo.activities;

import android.os.Bundle;

import com.example.mymusicdemo.R;
import com.example.mymusicdemo.views.InputView;

public class RegisterActivity extends BaseActivity {

    private InputView mInputPhone, mInputPassword, mPasswordConfirm;

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
        mInputPhone = findViewById(R.id.input_phone);
        mInputPassword = findViewById(R.id.input_password);
        mPasswordConfirm = findViewById(R.id.input_password_confirm);
    }


}
