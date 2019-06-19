package com.example.mymusicdemo.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;


//继承AppCompatImageView

/**
 * 为了使图片的宽高一样
 */
public class WEqualsHImageView extends AppCompatImageView {
    public WEqualsHImageView(Context context) {
        super(context);
    }

    public WEqualsHImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WEqualsHImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
//
////        获取View宽度
//        int width = MeasureSpec.getSize(widthMeasureSpec);
////        获取View模式（match_parent,wrap_content,具体的dp）
//        int mode = MeasureSpec.getMode(widthMeasureSpec);
    }
}
