package com.example.mymusicdemo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mymusicdemo.R;
import com.example.mymusicdemo.adapters.MusicLinearAdapter;
import com.example.mymusicdemo.helps.RealmHelp;
import com.example.mymusicdemo.models.AlbumModel;

public class AlbumListActivity extends BaseActivity {


    public static final String ALBUM_ID = "albumID";

    private RecyclerView mRvList;
    private MusicLinearAdapter mAdapter;
    private String mAlbumID;
    private RealmHelp mRealmHelp;
    private AlbumModel mAlbumModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        initData();
        initView();
    }

    private void initData(){
        mAlbumID = getIntent().getStringExtra(ALBUM_ID);
        mRealmHelp = new RealmHelp();
        mAlbumModel = mRealmHelp.getAlbum(mAlbumID);

    }
    private void initView() {
        initNavBar(true, "专辑列表", false);

        mRvList = findViewById(R.id.rv_album);
        mRvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MusicLinearAdapter(this, null,mAlbumModel.getList());
        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealmHelp.close();
    }
}
