package com.example.mymusicdemo.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mymusicdemo.R;
import com.example.mymusicdemo.helps.MediaPlayerHelp;
import com.example.mymusicdemo.models.MusicModel;
import com.example.mymusicdemo.service.MusicService;

public class PlayMusicView extends FrameLayout {

    private Context mContext;
    private Intent mServiceIntent;
    private MusicService.MusicBind mMusicBind;
    private MusicModel mMusicModel;


    private View mView;
    private FrameLayout mFlPlayMusic;
    private ImageView mIvIcon, mIvNeedle, mIvPlay;


    private Animation mPlayMusicAnim, mPlayNeedleAnim, mStopNeedleAnim;

    private boolean isPlaying, isBindService;
    private String mPath;

    public PlayMusicView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = LayoutInflater.from(mContext).inflate(R.layout.play_music, this, false);
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mFlPlayMusic = mView.findViewById(R.id.fl_play_music);
        mFlPlayMusic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                trigger();
            }
        });
        mIvNeedle = mView.findViewById(R.id.iv_needle);
        mIvPlay = mView.findViewById(R.id.iv_play);
        /**
         * 1、定义所需执行的动画
         *      1、定义光盘转动动画
         *      2、指针指向光盘动画
         *      3、指针离开光盘动画
         * 2、startAnimation
         */

        mPlayMusicAnim = AnimationUtils.loadAnimation(mContext, R.anim.play_music_anim);
        mPlayNeedleAnim = AnimationUtils.loadAnimation(mContext, R.anim.play_needle_anim);
        mStopNeedleAnim = AnimationUtils.loadAnimation(mContext, R.anim.stop_needle_anim);

        addView(mView);

    }

    /**
     * 切换播放状态
     */
    private void trigger() {
        if (isPlaying) {
            stopMusic();
        } else {
            playMusic();
        }
    }

    /**
     * 播放音乐
     */
    public void playMusic() {
        isPlaying = true;
        mIvPlay.setVisibility(View.GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);


        startMusicService();
    }

    /**
     * 停止播放音乐
     */

    public void stopMusic() {
        isPlaying = false;
        mIvPlay.setVisibility(View.VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);


        if (mMusicBind != null) {

            mMusicBind.stopMusic();
        }
    }

    /**
     * 设置光盘上显示的音乐封面图片
     */
    public void setMusicIcon() {
        Glide.with(mContext)
                .load(mMusicModel.getPoster())
                .into(mIvIcon);
    }

    public void setMusic(MusicModel music) {
        mMusicModel = music;
        setMusicIcon();
    }

    /**
     * 启动音乐服务
     */
    private void startMusicService() {

//        启动service
        if (mServiceIntent == null) {
            mServiceIntent = new Intent(mContext, MusicService.class);
            mContext.startService(mServiceIntent);
        } else {
            mMusicBind.playMusic();
        }

//        绑定 service, 当前service 未绑定, 绑定服务
        if (!isBindService) {
            isBindService = true;
            mContext.bindService(mServiceIntent, conn, Context.BIND_AUTO_CREATE);
        }


    }

    /**
     * 解除绑定
     */
    public void destory() {
//        如果已经绑定了服务,解除绑定
        if (isBindService) {
            isBindService = false;
            mContext.unbindService(conn);
        }
    }


    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicBind = (MusicService.MusicBind) service;
            mMusicBind.setMusic(mMusicModel);
            mMusicBind.playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
