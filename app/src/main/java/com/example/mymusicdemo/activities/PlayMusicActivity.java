package com.example.mymusicdemo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymusicdemo.R;
import com.example.mymusicdemo.helps.RealmHelp;
import com.example.mymusicdemo.models.MusicModel;
import com.example.mymusicdemo.views.PlayMusicView;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlayMusicActivity extends BaseActivity {


    public static final String MUSIC_ID = "musicID";

    private ImageView mIvBg;
    private PlayMusicView mPlayMusicView;
    private String mMusicID;
    private MusicModel mMusicModel;
    private RealmHelp mRealmHelp;
    private TextView mTvName, mTvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        //隐藏statusBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initData();
        initView();

    }

    private void initData() {
        mMusicID = getIntent().getStringExtra(MUSIC_ID);
        mRealmHelp = new RealmHelp();
        mMusicModel = mRealmHelp.getMusic(mMusicID);
    }

    private void initView() {
        mIvBg = findViewById(R.id.iv_bg);
        mTvName = findViewById(R.id.tv_name);
        mTvAuthor = findViewById(R.id.tv_author);
        //glide-transformations图片的高斯模糊
        //引入网络图片
        Glide.with(this)
                .load(mMusicModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 10)))
                .into(mIvBg);
        mTvName.setText(mMusicModel.getName());
        mTvAuthor.setText(mMusicModel.getAuthor());

        mPlayMusicView = findViewById(R.id.play_music_view);

//        mPlayMusicView.setMusicIcon(mMusicModel.getPoster());
        mPlayMusicView.setMusic(mMusicModel);
        mPlayMusicView.playMusic();
    }


    /**
     * 后退按钮点击事件
     */
    public void onBackClick(View view) {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayMusicView.destory();
        mRealmHelp.close();
    }
}
