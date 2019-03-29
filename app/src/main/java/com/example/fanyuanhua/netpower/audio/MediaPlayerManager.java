package com.example.fanyuanhua.netpower.audio;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MediaPlayerManager {
    public static final int STARTED = 0;
    public static final int PLAYING = 1;
    public static final int PAUSED = 2;
    private int play_State = STARTED;
    private final static int ACTION_UPDATE_PASS_TIME = 1;
    private MediaPlayer mediaPlayer;

    private MediaPlayerLifecycleListener mediaPlayerLifecycleListener;
    private boolean isPause;

    public MediaPlayerManager() {
        initMediaPlayer();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ACTION_UPDATE_PASS_TIME:
                    if (mediaPlayer != null) {
                        int passTime = mediaPlayer.getCurrentPosition(); // 播放器状态异常捕捉
                        int totalTime =mediaPlayer.getDuration();
                        if(null!=mediaPlayerLifecycleListener){
                            mediaPlayerLifecycleListener.onTimeChanged(passTime,totalTime);
                        }
                        if(play_State!=STARTED){
                            handler.sendEmptyMessageDelayed(ACTION_UPDATE_PASS_TIME, 100);
                        }
                    }
                    break;
            }
        }
    };



    public void setMediaPlayerLifecycleListener(MediaPlayerLifecycleListener mediaPlayerLifecycleListener) {
        this.mediaPlayerLifecycleListener = mediaPlayerLifecycleListener;
    }

    public int getPlay_State() {
        return play_State;
    }

    public void  pause(){
        if(null!=mediaPlayer){
            if(mediaPlayer.isPlaying()&&!isPause) {
                mediaPlayer.pause();//暂停播放
                isPause = true;
                play_State=PAUSED;
                if(null!=mediaPlayerLifecycleListener){
                    mediaPlayerLifecycleListener.onPauseState();
                }
            }
        }

    }
    public void  start(){
        if(null!=mediaPlayer){
            if(!mediaPlayer.isPlaying()&&isPause) {
                mediaPlayer.start();//继续播放
                isPause = false;
                play_State=PLAYING;
                if(null!=mediaPlayerLifecycleListener){
                    mediaPlayerLifecycleListener.onResumeStart();
                }
            }
        }
    }
    // 初始化MediaPlayer
    private void initMediaPlayer() {
        play_State=STARTED;
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        // 设置音量，参数分别表示左右声道声音大小，取值范围为0~1
        mediaPlayer.setVolume(0.5f, 0.5f);
        // 设置是否循环播放
        mediaPlayer.setLooping(false);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                handler.sendEmptyMessage(ACTION_UPDATE_PASS_TIME);
                play_State=PLAYING;
                mediaPlayer.start();
                if (null != mediaPlayerLifecycleListener) {
                    mediaPlayerLifecycleListener.onStartPlay();
                }
                isPause = false;
            }
        });
        // 设置播放错误监听
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                mediaPlayer.reset();
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }
        });
        // 设置播放完成监听
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                play_State=STARTED;
                handler.removeMessages(ACTION_UPDATE_PASS_TIME);
                if (null != mediaPlayerLifecycleListener) {
                    mediaPlayerLifecycleListener.OnPlayCompletion();
                }
            }
        });
    }

    public void release() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // 播放
    public void play(File file) {
        if (null == file) {
            return;
        }
        if (!file.exists()) {
            return;
        }
        if (!file.getName().endsWith(".wav")) {
            return;
        }
        try {
            if (mediaPlayer == null){
                initMediaPlayer();
            }

            if (isPause) {
                mediaPlayer.start();
                play_State=PLAYING;
                isPause = false;
            } else {
                // 重置mediaPlayer
                mediaPlayer.reset();
                // 重新加载音频资源
                mediaPlayer.setDataSource(file.getPath());
                // 准备播放（异步）
                mediaPlayer.prepareAsync();
                play_State=PLAYING;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("MediaPlayerManager", "播放异常：" + e.getLocalizedMessage());
        }
    }



    public void stop() {
        if (null != mediaPlayer) {
            mediaPlayer.stop();
            isPause = true;
            play_State=STARTED;
            handler.removeMessages(ACTION_UPDATE_PASS_TIME);
        }
    }

    public void seekTo(int position) {
        if (null != mediaPlayer) {
            mediaPlayer.seekTo(position);
            mediaPlayer.start();
            isPause = true;
        }

    }
}
