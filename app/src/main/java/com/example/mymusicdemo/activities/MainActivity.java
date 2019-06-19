package com.example.mymusicdemo.activities;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mymusicdemo.R;
import com.example.mymusicdemo.adapters.MusicGridAdapter;
import com.example.mymusicdemo.adapters.MusicLinearAdapter;
import com.example.mymusicdemo.views.GridSpaceItemDecoration;

public class MainActivity extends BaseActivity {
    //项目：project
    //模块：module
    //statusBar

    private RecyclerView mRvGrid, mRvLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        initNavBar(false, "My Music", true);
        mRvGrid = findViewById(R.id.rv_grid);
        //取消RecyclerView自带滑动
        mRvGrid.setNestedScrollingEnabled(false);
        //加入RecyclerView分割线
        mRvGrid.addItemDecoration(new GridSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.marginAlbumSize),mRvGrid));
        mRvGrid.setLayoutManager(new GridLayoutManager(this,3));
        mRvGrid.setAdapter(new MusicGridAdapter(this));

        /**
         * 1、已知列表高度的情况下，可以直接在布局中把RecyclerView的高度定义上
         * 2、不知道列表高度的情况下，需要手动计算RecyclerView的高度
         */
        mRvLinear = findViewById(R.id.rv_linear);
        //取消RecyclerView自带滑动
        mRvLinear.setNestedScrollingEnabled(false);

        //加入RecyclerView分割线
        mRvLinear.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRvLinear.setLayoutManager(new LinearLayoutManager(this));
        mRvLinear.setAdapter(new MusicLinearAdapter(this,mRvLinear));
    }
}
