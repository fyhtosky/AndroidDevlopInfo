package com.example.fanyuanhua.netpower.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioTrackManager {
    private ExecutorService fixedThreadPool;
    // 获取最小缓冲区
    private int bufSize;
    private AudioTrack audioTrack;

    public AudioTrackManager() {
        fixedThreadPool = Executors.newFixedThreadPool(1);
        bufSize = AudioTrack.getMinBufferSize(16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        // 实例化AudioTrack(设置缓冲区为最小缓冲区的2倍，至少要等于最小缓冲区)
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufSize, AudioTrack.MODE_STREAM);

    }

    public  void PlayMp3(final File file){
        if(null ==file ){
            return;
        }
        if (!file.exists()){
            return;
        }
        if(!file.getName().endsWith(".wav")){
            return;
        }
        if (fixedThreadPool == null) {
            fixedThreadPool = Executors.newFixedThreadPool(1);
        } else {
            if (fixedThreadPool.isShutdown()) {
                fixedThreadPool = Executors.newFixedThreadPool(1);
            }
        }
        if(audioTrack==null){
            return;
        }
        if(audioTrack.getPlayState()==AudioTrack.PLAYSTATE_PLAYING){
            audioTrack.stop();
        }
        fixedThreadPool.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {

                // 设置音量
                audioTrack.setVolume(2f) ;
                audioTrack.play();
                // 获取音乐文件输入流
                try {
                    InputStream is = new FileInputStream(file);
                    byte[] buffer = new byte[bufSize*2] ;
                    int len ;
                    try {
                        while((len=is.read(buffer,0,buffer.length)) != -1){
                            System.out.println("读取数据中...");
                            // 将读取的数据，写入Audiotrack
                            audioTrack.write(buffer,0,buffer.length) ;
                        }
                        is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        audioTrack.setPlaybackPositionUpdateListener(new AudioTrack.OnPlaybackPositionUpdateListener() {
            @Override
            public void onMarkerReached(AudioTrack track) {

            }

            @Override
            public void onPeriodicNotification(AudioTrack track) {

            }
        });
    }

    public void  stop(){
        if(null!=audioTrack){
            audioTrack.stop();
        }
    }
    public void release(){
        if(null!=audioTrack){
            audioTrack.release();
        }
    }



}
