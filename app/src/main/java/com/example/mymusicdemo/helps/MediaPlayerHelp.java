package com.example.mymusicdemo.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class MediaPlayerHelp {

    private static MediaPlayerHelp instanse;
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private String mPath;
    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;

    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }

    public static  MediaPlayerHelp getInstance(Context context){
        if (instanse == null){
            synchronized (MediaPlayerHelp.class){
                if (instanse == null){
                    instanse = new MediaPlayerHelp(context);
                }
            }
        }

        return instanse;
    }
    private MediaPlayerHelp(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 1、setPath：获取音乐的路径
     * 2、start：播放音乐
     * 3、pause：暂停播放
     */

    /**
     * 1、setPath：获取音乐的路径
     */

    public void setPath(String path){
        /**
         * 1、音乐正在播放，重置音乐播放状态
         * 2、设置播放音乐路径
         * 3、准备播放
         */

        mPath = path;
        //1、音乐正在播放，重置音乐播放状态
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.reset();
        }

        //2、设置播放音乐路径
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3、准备播放
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (onMediaPlayerHelperListener != null){
                    onMediaPlayerHelperListener.onPrepared(mp);
                }
            }
        });

    }

    /**
     * 返回正在播放的音乐的路径
     * @return
     */
    public String getPath(){
        return mPath;
    }
    /**
     * 2、start：播放音乐
     */
    public void start(){
        if (mMediaPlayer.isPlaying()){
            return;
        }
        mMediaPlayer.start();
    }

    /**
     *3、pause：暂停播放
     */
    public void pause(){
        mMediaPlayer.pause();
    }
    public interface OnMediaPlayerHelperListener{
        void onPrepared(MediaPlayer mp);
    }
}
