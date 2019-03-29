package com.example.fanyuanhua.netpower.speech;

public interface SpeechLifeCallBack {
    /**
     * 启动语音识别
     */
    void startSpeech();

    boolean isPlaying();

    /**
     * 暂停语音识别
     */
     void stop();

    /**
     * 取消语音识别
     */
    void  cancel();

    /**
     * 语音识别资源释放
     */
    void release();

}
