package com.example.fanyuanhua.netpower.speech;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.fanyuanhua.netpower.file.FileUtils;
import com.example.fanyuanhua.netpower.speech.utils.FucUtil;
import com.example.fanyuanhua.netpower.speech.utils.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 语音识别
 */
public class RecognizerManager implements SpeechLifeCallBack {
    public static final String TAG = "RecognizerManager";
    // 获取最小缓冲区
    private int bufSize;
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private boolean mTranslateEnable = false;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private OnSpeechResultListener onSpeechResultListener;
    //默认地址
    private String recoginPath = "lat";

    public RecognizerManager(Context context) {
        //使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(context, mInitListener);
    }

    public void setOnSpeechResultListener(OnSpeechResultListener onSpeechResultListener) {
        this.onSpeechResultListener = onSpeechResultListener;
    }

    @Override
    public void startSpeech() {
        if (null == mIat) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            Log.i(TAG, "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
            return;
        }
        setParam();
        //开始识别，并设置监听器
        mIat.startListening(mRecognizerListener);
        if (null != onSpeechResultListener) {
            onSpeechResultListener.onStartSpeech();
        }
    }

    public String getRecoginPath() {
        return recoginPath;
    }

    public void startSpeech(File file) {
        if (null == mIat) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            Log.i(TAG, "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
            return;
        }
        // 设置参数
        setParam();
        // 设置音频来源为外部文件
        mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        int ret = 0; // 函数调用返回值
        // 获取音乐文件输入流
        ret = mIat.startListening(mRecognizerListener);
        if (null != onSpeechResultListener) {
            onSpeechResultListener.onStartSpeech();
        }
        if (ret != ErrorCode.SUCCESS) {
            Log.i(TAG,"识别失败,错误码：" + ret);
        } else {
            byte[] audioData = FucUtil.readAudioFile(file);

            if (null != audioData) {
                Log.i(TAG,"开始识别数据流");
                // 一次（也可以分多次）写入音频文件数据，数据格式必须是采样率为8KHz或16KHz（本地识别只支持16K采样率，云端都支持），
                // 位长16bit，单声道的wav或者pcm
                // 写入8KHz采样的音频时，必须先调用setParameter(SpeechConstant.SAMPLE_RATE, "8000")设置正确的采样率
                // 注：当音频过长，静音部分时长超过VAD_EOS将导致静音后面部分不能识别。
                // 音频切分方法：FucUtil.splitBuffer(byte[] buffer,int length,int spsize);
                mIat.writeAudio(audioData, 0, audioData.length);

                stop();
            } else {
                cancel();
                Log.i(TAG,"读取音频流失败");
            }
        }

    }

    @Override
    public boolean isPlaying() {
        if (null != mIat) {
            return mIat.isListening();
        }
        return false;
    }

    @Override
    public void stop() {
        if (null != mIat) {
            mIat.stopListening();
            Log.i(TAG, "停止听写");
            if(null !=onSpeechResultListener){
                onSpeechResultListener.onSpeechStop();
            }
        }

    }

    @Override
    public void cancel() {
        if (null != mIat) {
            mIat.cancel();
            Log.i(TAG, "取消听写");
        }

    }

    @Override
    public void release() {
        if (null != mIat) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }


    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        //使用SpeechRecognizer对象，可根据回调消息自定义界面；
        //设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数，具体可参考 DEMO 的示例。
        mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        mIat.setParameter(SpeechConstant.SUBJECT, null);
        //设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        //设置采样率
        mIat.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
        //此处engineType为“cloud”
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        //设置语音输入语言，zh_cn为简体中文
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置结果返回语言
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
        //取值范围{1000～10000}
        mIat.setParameter(SpeechConstant.VAD_BOS, "5000");
        //设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
        //自动停止录音，范围{0~10000}
        mIat.setParameter(SpeechConstant.VAD_EOS, "5000");
        //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        recoginPath=FileUtils.XUN_FEI_PATH + "lat.wav";
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, recoginPath);

    }


    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            Log.i(TAG, "开始说话");
            if (null != onSpeechResultListener) {
                onSpeechResultListener.onBeginOfSpeech();
            }
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
//            if (mTranslateEnable && error.getErrorCode() == 14002) {
//                showTip(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
//            } else {
//                showTip(error.getPlainDescription(true));
//            }
            if (null != onSpeechResultListener) {
                onSpeechResultListener.onError(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEndOfSpeech() {
            Log.i(TAG, "结束说话");
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            if (null != onSpeechResultListener) {
                onSpeechResultListener.onEndOfSpeech();
            }
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.i(TAG, "返回的json :" + results.getResultString());
            if (mTranslateEnable) {
                printTransResult(results);
            } else {
                printResult(results);
            }

        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            Log.i(TAG, "音量的大小 :" + volume);
//            Log.i(TAG, "返回音频数据：" + data.length);
//            byte[] result = new byte[data.length/2 ];
//            int a = 0;
//            for (int i = 0; i < data.length; i++) {
//                Log.i(TAG, "音频元素：" + data[i]);
//                if(i%2==0){
//                    result[a] = (byte) (data[i]/2);
//                    a++;
//                }
//
//            }
            if (null != onSpeechResultListener) {
                onSpeechResultListener.onVolumeChanged(volume, data);
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
        }
    };


    private void printTransResult(RecognizerResult results) {
        String trans = JsonParser.parseTransResult(results.getResultString(), "dst");
        String oris = JsonParser.parseTransResult(results.getResultString(), "src");

        if (TextUtils.isEmpty(trans) || TextUtils.isEmpty(oris)) {
            Log.i(TAG, "解析结果失败，请确认是否已开通翻译功能。");
        } else {
            Log.i(TAG, "原始语言:\n" + oris + "\n目标语言:\n" + trans);
        }

    }

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        if (null != onSpeechResultListener) {
            onSpeechResultListener.onResult(resultBuffer.toString());
        }
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.i(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.i(TAG, "初始化失败，错误码：" + code);
            }
        }
    };

}
