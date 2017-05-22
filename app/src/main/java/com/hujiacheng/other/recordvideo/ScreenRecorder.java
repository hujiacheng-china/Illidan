package com.hujiacheng.other.recordvideo;

import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by hjc on 2017/2/24.
 *  对录屏数据进行编码
 */
public class ScreenRecorder extends Thread {

    private static final String TAG = ScreenRecorder.class.getSimpleName();

    private int mWidth;     //视频宽
    private int mHeight;    //视频高
    private int mBitRate;   //比特率
    private int mDpi;       //像素密度dpi
    private String mDstPath;//存储路径
    private MediaProjection mMediaProjection;
    /**
     * 转码参数
     */
    private static final String MIME_TYPE = "video/avc"; // H.264 视频编码
    private static final int FRAME_RATE = 30; // 30 fps帧率
    private static final int IFRAME_INTERVAL = 10; // 关键帧间隔
    private static final int TIMEOUT_US = 10000;  //Timeout

    private MediaCodec mEncoder;  //编解码器
    private Surface mSurface;    //surface
    private MediaMuxer mMuxer;    //写入文件
    private boolean mMuxerStarted = false;
    private int mVideoTrackIndex = -1;
    private AtomicBoolean mQuit = new AtomicBoolean(false);
    private BufferInfo mBufferInfo = new BufferInfo();
    private VirtualDisplay mVirtualDisplay;


    private String DIRNAME = "AudioRecord";
    private String VIDEONAME = "VideoResouse.mp4";
    private String AUDIONAME = "AudioResouse.amr";
    private String MIXNAME = "MixRecord.mp4";
    File file = new File(Environment.getExternalStorageDirectory(), "/"+DIRNAME+"/"+VIDEONAME);

    public ScreenRecorder(int width, int height, int bitrate, int dpi, MediaProjection mp) {
        super(TAG);
        mWidth = width;
        mHeight = height;
        mBitRate = bitrate;
        mDpi = dpi;
        mMediaProjection = mp;
    }

    /**
     * 停止任务
     */
    public final void quit() {
        mQuit.set(true);
    }

    @Override
    public void run() {
        //创建录屏保存地址
        File path = new File(Environment.getExternalStorageDirectory(), DIRNAME);
        if (!path.exists()) {
            path.mkdirs();
        }
        try {
            try {
                prepareEncoder();
                mMuxer = new MediaMuxer(file.getAbsolutePath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mVirtualDisplay = mMediaProjection.createVirtualDisplay(TAG + "-display",
                    mWidth, mHeight, mDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
                    mSurface, null, null);
            Log.i(TAG, "Created virtual display: " + mVirtualDisplay);
            recordVirtualDisplay();
        } finally {
            release();
        }
    }

    private void recordVirtualDisplay() {
        //开始录音
        startMediaRecoder();
        while (!mQuit.get()) {
            int index = mEncoder.dequeueOutputBuffer(mBufferInfo, TIMEOUT_US);
            Log.i(TAG, "dequeue output buffer index=" + index);
            if (index == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                resetOutputFormat();

            } else if (index == MediaCodec.INFO_TRY_AGAIN_LATER) {
                Log.d(TAG, "retrieving buffers time out!");
                try {
                    // wait 10ms
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            } else if (index >= 0) {

                if (!mMuxerStarted) {
                    throw new IllegalStateException("MediaMuxer dose not call addTrack(format) ");
                }
                encodeToVideoTrack(index);

                mEncoder.releaseOutputBuffer(index, false);
            }
        }
    }

    private void encodeToVideoTrack(int index) {
        ByteBuffer encodedData = mEncoder.getOutputBuffer(index);

        if ((mBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
            // The codec config data was pulled out and fed to the muxer when we got
            // the INFO_OUTPUT_FORMAT_CHANGED status.
            // Ignore it.
            Log.d(TAG, "ignoring BUFFER_FLAG_CODEC_CONFIG");
            mBufferInfo.size = 0;
        }
        if (mBufferInfo.size == 0) {
            Log.d(TAG, "info.size == 0, drop it.");
            encodedData = null;
        } else {
            Log.d(TAG, "got buffer, info: size=" + mBufferInfo.size
                    + ", presentationTimeUs=" + mBufferInfo.presentationTimeUs
                    + ", offset=" + mBufferInfo.offset);
        }
        if (encodedData != null) {
            encodedData.position(mBufferInfo.offset);
            encodedData.limit(mBufferInfo.offset + mBufferInfo.size);
            mMuxer.writeSampleData(mVideoTrackIndex, encodedData, mBufferInfo);
            Log.i(TAG, "sent " + mBufferInfo.size + " bytes to muxer...");
        }
    }

    private void resetOutputFormat() {
        // should happen before receiving buffers, and should only happen once
        if (mMuxerStarted) {
            throw new IllegalStateException("output format already changed!");
        }
        MediaFormat newFormat = mEncoder.getOutputFormat();

        Log.i(TAG, "output format changed.\n new format: " + newFormat.toString());
        mVideoTrackIndex = mMuxer.addTrack(newFormat);
        mMuxer.start();
        mMuxerStarted = true;
        Log.i(TAG, "started media muxer, videoIndex=" + mVideoTrackIndex);
    }

    private void prepareEncoder() throws IOException {

        MediaFormat format = MediaFormat.createVideoFormat(MIME_TYPE, mWidth, mHeight);
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, mBitRate);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, IFRAME_INTERVAL);

        Log.d(TAG, "created video format: " + format);
        mEncoder = MediaCodec.createEncoderByType(MIME_TYPE);
        mEncoder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mSurface = mEncoder.createInputSurface();
        Log.d(TAG, "created input surface: " + mSurface);
        mEncoder.start();
    }

    private void release() {
        if (mEncoder != null) {
            mEncoder.stop();
            mEncoder.release();
            mEncoder = null;
        }
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
        }
        if (mMediaProjection != null) {
            mMediaProjection.stop();
        }
        if (mMuxer != null) {
            mMuxer.stop();
            mMuxer.release();
            mMuxer = null;
        }
        //停止录音
        stopMediaRecoder();
        //开始合成
        startCollectionFile();
    }

    //MediaRecorder录音
    private boolean isRecording = true;
    private MediaRecorder mRecorder;
    public void startMediaRecoder() {
        isRecording = true;
        String audio = Environment.getExternalStorageDirectory() + "/"+DIRNAME+"/"+AUDIONAME;
        initFile(audio);
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        }
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setOutputFile(audio);
        try {
            mRecorder.prepare();
            Log.i(TAG,"Prepare success!");
        } catch (IOException e) {
            Log.e(TAG,"Prepare failed!");
            e.printStackTrace();
        }
        mRecorder.start();
    }

    //停止录音
    public void stopMediaRecoder() {
        if (isRecording) {
            mRecorder.stop();
            mRecorder.release();
            isRecording = false;
            mRecorder = null;
        }
    }

    //音频合成
    public void startCollectionFile() {
        if(Environment.isExternalStorageEmulated()){
            String video = Environment.getExternalStorageDirectory() + "/"+DIRNAME+"/"+VIDEONAME;
            String audio = Environment.getExternalStorageDirectory() + "/"+DIRNAME+"/"+AUDIONAME;
            String mix = Environment.getExternalStorageDirectory() + "/"+DIRNAME+"/"+MIXNAME;
            if(new File(audio).exists()){
                if(new File(video).exists()){
                    initFile(mix);
                    try {
                        Movie countVideo = MovieCreator.build(video);
                        Movie countAudio = MovieCreator.build(audio);
                        countVideo.addTrack(countAudio.getTracks().get(0));
                        Container container = new DefaultMp4Builder().build(countVideo);
                        FileOutputStream fos = new FileOutputStream(new File(mix));
                        container.writeContainer(fos.getChannel());
                        Log.d(TAG, "合并成功文件为:"+mix);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.d(TAG, "视频文件:"+video+"不存在");
                }
            }else{
                Log.d(TAG, "音频文件:"+audio+"不存在");
            }
        }else{
            Log.d(TAG, "未挂载SD卡");
        }
    }

    //判断文件
    private void initFile(String video) {
        File videoFile = new File(video);
        if (videoFile.exists()) {
            videoFile.delete();
            Log.i(TAG,"File Delete..");
        }
        try {
            videoFile.createNewFile();
            Log.i(TAG,"File create success!");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG,"File create failed!");
        }
    }

}
