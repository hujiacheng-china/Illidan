package com.hujiacheng.other.recordvideo;

import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hujiacheng.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordVideoActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_record)
    Button btnRecord;

    private static final int REQUEST_CODE = 1;
    private MediaProjectionManager mMediaProjectionManager;
    private ScreenRecorder mRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("录屏+录音");
        int sdkVersion = Build.VERSION.SDK_INT;
        if(sdkVersion < 21){
            btnRecord.setText("需要Android系统版本大于等于5.0");
            btnRecord.setEnabled(false);
        }else {
            mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        }
    }

    @OnClick({R.id.imgbtn_back, R.id.btn_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.btn_record:
                if (mRecorder == null) {
                    startRecorder();
                } else {
                    stopRecorder();
                }
                break;
        }
    }

    //开始录制
    private void startRecorder(){
        btnRecord.setText("点击结束录音录屏，并开始合合并");
        //开始录屏
        if (mRecorder == null) {
            Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
            startActivityForResult(captureIntent, REQUEST_CODE);
        }
    }

    //结束录制
    private void stopRecorder(){
        //停止录屏
        mRecorder.quit();
        mRecorder = null;
        btnRecord.setText("点击开始录音+录屏");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //sdk大于21 即5.0才能创建录屏功能
        MediaProjection mediaProjection = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
        }
        if (mediaProjection == null) {
            Toast.makeText(this,"系统版本大于等于安卓5.0才能执行录屏功能！",Toast.LENGTH_SHORT).show();
            return;
        }
        //录屏宽高设置
        final int width = 720;
        final int height = 1280;
        //视频源保存路径
        //比特率 4.5 Kbps
        final int bitrate = 1000*4500;
        //创建录屏类并启动
        mRecorder = new ScreenRecorder(width, height, bitrate, 1, mediaProjection);
        mRecorder.start();
        Toast.makeText(this, "视频开始录制中...", Toast.LENGTH_SHORT).show();
//        moveTaskToBack(true);
    }

    //销毁录屏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecorder != null) {
            mRecorder.quit();
            mRecorder = null;
            btnRecord.setText("结束了录音录屏及合并");
        }
    }
}
