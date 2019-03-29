package com.example.fanyuanhua.netpower.speech;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.example.fanyuanhua.netpower.file.FileUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * 语音合成
 */
public class SynthesizerManager implements SpeechLifeCallBack {
    public static final String TAG = "SynthesizerManager";
    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 默认发音人
    private String voicer = "xiaoyan";
    private String texts ;
    private String path;
    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;
    private OnTextResultListener onTextResultListener;

    public void setOnTextResultListener(OnTextResultListener onTextResultListener) {
        this.onTextResultListener = onTextResultListener;
    }

    public SynthesizerManager(Context context) {
        mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);

    }

    public String getPath() {
        return path;
    }

    public String getTexts() {
        return texts;
    }

    public void setTexts(String texts) {
        this.texts = texts;
    }

    public String getVoicer() {
        return voicer;
    }

    public void setVoicer(String voicer) {
        this.voicer = voicer;
    }


    @Override
    public void startSpeech() {
        if (null == mTts) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            Log.i(TAG, "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
            return;
        }
        setParam();
        if(TextUtils.isEmpty(texts)){
            Log.i(TAG, "语音合成失败,没有文本 " );
            return;
        }
        int code = mTts.startSpeaking(texts, mTtsListener);
        if (null != onTextResultListener) {
            onTextResultListener.onStartSpeech();
        }
        if (code != ErrorCode.SUCCESS) {
            Log.i(TAG, "语音合成失败,错误码: " + code);
        }
    }

    @Override
    public boolean isPlaying() {
        if (null != mTts) {
            return mTts.isSpeaking();
        }
        return false;
    }

    /**
     * 将文本转成音频 不播放
     */
    public void textToVoice() {
        if (null != mTts) {
            path=FileUtils.XUN_FEI_PATH + "voice.pcm";
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置采样率
            mTts.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
            mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY,"true");
            mTts.synthesizeToUri(texts, path, mTtsListener);
        }
    }


    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        path=FileUtils.XUN_FEI_PATH + "tts.wav";
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, path);
    }

    @Override
    public void stop() {
        if (null != mTts) {
            mTts.pauseSpeaking();
        }
    }

    public void onResume() {
        if (null != mTts) {
            mTts.resumeSpeaking();
        }
    }

    @Override
    public void cancel() {
        if (null != mTts) {
            mTts.stopSpeaking();
        }
    }

    @Override
    public void release() {
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }


    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Log.i(TAG, "初始化失败,错误码：" + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            Log.i(TAG, "开始播放");
            if (null != onTextResultListener) {
                onTextResultListener.onSpeakBegin();
            }
        }

        @Override
        public void onSpeakPaused() {
            Log.i(TAG, "暂停播放");
            if (null != onTextResultListener) {
                onTextResultListener.onSpeakPaused();
            }
        }

        @Override
        public void onSpeakResumed() {
            Log.i(TAG, "继续播放");
            if (null != onTextResultListener) {
                onTextResultListener.onSpeakResumed();
            }
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            mPercentForBuffering = percent;
            Log.i(TAG, String.format("缓冲进度为%d%%，播放进度为%d%%",
                    mPercentForBuffering, mPercentForPlaying));
        }

        @SuppressLint("LongLogTag")
        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            mPercentForPlaying = percent;
            Log.i(TAG, String.format("缓冲进度为%d%%，播放进度为%d%%",
                    mPercentForBuffering, mPercentForPlaying));

            SpannableStringBuilder style = new SpannableStringBuilder(texts);
            Log.e(TAG, "beginPos = " + beginPos + "  endPos = " + endPos);
//            style.setSpan(new BackgroundColorSpan(Color.RED),beginPos,endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (null != onTextResultListener) {
                onTextResultListener.onSpeakProgress(percent, beginPos, endPos);
            }
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                Log.i(TAG, "播放完成");
                if (null != onTextResultListener) {
                    onTextResultListener.onCompleted();
                }
            } else if (error != null) {
                Log.i(TAG, error.getPlainDescription(true));
                if (null != onTextResultListener) {
                    onTextResultListener.onError(error.getPlainDescription(true));
                }
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}

            if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
                byte[] buf = obj.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER);
                Log.e("MscSpeechLog", "buf is =" + buf);
            }

        }
    };
}
