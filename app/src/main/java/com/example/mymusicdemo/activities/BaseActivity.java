package com.example.mymusicdemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymusicdemo.R;

/**
 * 整个项目中所有Activity的父类
 */
public class BaseActivity extends Activity {

    private ImageView mIvBack, mIvMe;
    private TextView mTvTitle;


    /**
     * 初始化NavigationBar
     *
     * @param isShowBack
     * @param title
     * @param isShowMe
     */
    protected void initNavBar(boolean isShowBack, String title, boolean isShowMe) {

        mIvBack = findViewById(R.id.iv_back);
        mIvMe = findViewById(R.id.iv_me);
        mTvTitle = findViewById(R.id.tv_title);

        mIvBack.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        mIvMe.setVisibility(isShowMe ? View.VISIBLE : View.GONE);
        mTvTitle.setText(title);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mIvMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BaseActivity.this, MeActivity.class));
            }
        });
    }
}
