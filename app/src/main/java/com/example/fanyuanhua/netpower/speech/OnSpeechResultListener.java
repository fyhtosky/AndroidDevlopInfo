package com.example.fanyuanhua.netpower.speech;

public interface OnSpeechResultListener {
    void onStartSpeech();

    void onSpeechStop();

    void onBeginOfSpeech();

    void onEndOfSpeech();

    void onError(String error);

    void onResult(String result);

    void onVolumeChanged(int volume, byte[] data);
}
