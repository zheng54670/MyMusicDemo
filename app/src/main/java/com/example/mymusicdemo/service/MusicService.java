package com.example.mymusicdemo.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import com.example.mymusicdemo.R;
import com.example.mymusicdemo.activities.WelcomeActivity;
import com.example.mymusicdemo.helps.MediaPlayerHelp;
import com.example.mymusicdemo.models.MusicModel;

/**
 * 1.通过service连接 PlayMusicView 和 MediaPlayerHelp
 * 2.PlayMusicView -- service
 * 1.播放音乐和暂停音乐
 * 2.启动service  绑定service 解除绑定service
 * 3.MediaPlayerHelp -- service
 * 1.播放音乐和暂停音乐
 * 2.监听音乐播放完成, 停止 service
 */
public class MusicService extends Service {

    private MediaPlayerHelp mMediaPlayerHelp;
    private MusicModel mMusicModel;

    //不可为0
    public static final int NOTIFICATION_ID = 1;


    public MusicService() {
    }

    public class MusicBind extends Binder {
        /**
         * 设置音乐的方法(MusicModel)
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void setMusic(MusicModel musicModel) {
            mMusicModel = musicModel;
//            startForeground();
        }


        /**
         * 播放音乐
         */
        public void playMusic() {
            /**
             * 1、判断当前音乐是否已经播放
             * 2、如果当前音乐是已经播放的状态，那么执行start()方法
             * 3、如果当前音乐不是需要播放的音乐，那么执行setPath()方法
             */

            if (mMediaPlayerHelp.getPath() != null && mMediaPlayerHelp.getPath().equals(mMusicModel.getPath())) {
                mMediaPlayerHelp.start();

            } else {
                mMediaPlayerHelp.setPath(mMusicModel.getPath());
                mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayerHelp.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        stopSelf();
                    }
                });
            }
        }


        /**
         * 暂停播放
         */
        public void stopMusic(){
            mMediaPlayerHelp.pause();
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mMediaPlayerHelp = MediaPlayerHelp.getInstance(this);

    }

    /**
     * 系统默认不允许不可见的后台服务播放音乐,
     * 借助Notification将服务可见化
     */

    /**
     * 设置服务在前台可见
     */
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void startForeground(){
//
//        //通知栏点击跳转的intent
//        PendingIntent pendingIntent = PendingIntent
//                .getActivity(this,0,new Intent(this, WelcomeActivity.class),PendingIntent.FLAG_CANCEL_CURRENT);
//
//         // 创建Notification
//
//        Notification notification = new Notification.Builder(this)
//                .setContentTitle(mMusicModel.getName())
//                .setContentText(mMusicModel.getAuthor())
//                .setSmallIcon(R.mipmap.logo)
//                .setContentIntent(pendingIntent)
//                .build();
//        /**
//         * 设置notification 在前台展示
//         */
//        startForeground(NOTIFICATION_ID,notification);
//
//
//    }

}
