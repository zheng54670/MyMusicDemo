package com.example.mymusicdemo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mymusicdemo.R;
import com.example.mymusicdemo.adapters.MusicLinearAdapter;

public class AlbumListActivity extends BaseActivity {


    private RecyclerView mRvList;
    private MusicLinearAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        initView();
    }

    private void initView() {
        initNavBar(true, "专辑列表", false);

        mRvList = findViewById(R.id.rv_album);
        mRvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MusicLinearAdapter(this, null);
        mRvList.setAdapter(mAdapter);
    }


}
