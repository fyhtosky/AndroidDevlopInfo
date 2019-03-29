package com.example.fanyuanhua.netpower.audio;

public interface MediaPlayerLifecycleListener {
    void onStartPlay();

    void  onPauseState();

    void onTimeChanged(int time,int totalTime);

    void  onResumeStart();

    void OnPlayCompletion();
}
