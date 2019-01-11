package com.example.fanyuanhua.netpower.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fanyuanhua.netpower.BuildConfig;
import com.example.fanyuanhua.netpower.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MindMapActivity extends AppCompatActivity {

   final String TAG="MindMap";
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_speed)
    TextView tvSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mind_map);
        ButterKnife.bind(this);
       tvSpeed.setText("服务器的地址："+BuildConfig.hostUrl);
        Log.d(TAG,"gradlew配置服务器地址");


    }
}
