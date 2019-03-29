package com.example.fanyuanhua.netpower.speech;

public interface OnTextResultListener {
    void onStartSpeech();

    void onSpeakBegin();

    void onSpeakPaused();

    void onSpeakResumed();

    void onSpeakProgress(int percent, int beginPos, int endPos);

    void onCompleted();

    void onError(String error);
}
