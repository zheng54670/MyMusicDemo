package com.example.mymusicdemo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mymusicdemo.R;
import com.example.mymusicdemo.utils.UserUtils;

public class MeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);


        initView();

    }

    private void initView() {
        initNavBar(true, "个人中心", false);
    }


    /**
     * 修改密码点击事件
     */

    public void onPasswordChangeClick(View view) {

        startActivity(new Intent(this, ChangePasswordActivity.class));
    }


    /**
     * 退出登录点击事件
     */

    public void onLoginOutClick(View view) {

        UserUtils.logout(this);


    }
}
