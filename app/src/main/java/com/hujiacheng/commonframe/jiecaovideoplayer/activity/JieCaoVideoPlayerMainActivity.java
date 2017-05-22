package com.hujiacheng.commonframe.jiecaovideoplayer.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hujiacheng.MyApplication;
import com.hujiacheng.R;
import com.hujiacheng.commonframe.okhttp.DataBean;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerSimple;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Request;

public class JieCaoVideoPlayerMainActivity extends AppCompatActivity implements View.OnClickListener {
    JCVideoPlayer.JCAutoFullscreenListener mSensorEventListener;
    //传感器
    SensorManager mSensorManager;
    JCVideoPlayerStandard mJcVideoPlayerStandard;
    JCVideoPlayerSimple mJcVideoPlayerSimple;
    Button mTinyWindow, mAutoTinyWindow, mAboutListView, mAboutUI, mPlayDirectly, mAboutWebView,mSend;
    ImageButton imgbtn_back;
    TextView title;
    EditText et_danmu;
    private Context mContext;
    DanmakuView danmakuView;
    private final static String TAG = JieCaoVideoPlayerMainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jie_cao_video_player_main);

        initView();
        //GetDatas
        getDatasFromGetByHttpUtils();

        initDanmaku();

    }

    private void initJCVideoPlayer() {
        if(MyApplication.dataBeen.getTrailers().size() < 2) {return;}
        List<DataBean.TrailersBean> datas = MyApplication.dataBeen.getTrailers();
        mJcVideoPlayerSimple = (JCVideoPlayerSimple) findViewById(R.id.simple_demo);
        mJcVideoPlayerSimple.setUp(datas.get(0).getUrl()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, datas.get(0).getMovieName());

        //标准的UI控件
        mJcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.jc_video);
        mJcVideoPlayerStandard.setUp(datas.get(1).getHightUrl()
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, datas.get(1).getMovieName());
        //设置封面
        Glide.with(this)
                .load(datas.get(1).getCoverImg())
                .centerCrop()
                .into(mJcVideoPlayerStandard.thumbImageView);
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        mSensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
    }

    private void initDanmaku() {
        List<IDanmakuItem> list = initItems(danmakuView);
        Collections.shuffle(list);
        danmakuView.addItem(list,true);
    }

    private List<IDanmakuItem> initItems(DanmakuView mDanmakuView) {
        List<IDanmakuItem> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            IDanmakuItem item = new DanmakuItem(this, i + "老王双击666", mDanmakuView.getWidth());
            list.add(item);
        }

        String msg = "扎心了老铁   ";
        for (int i = 0; i < 100; i++) {
            ImageSpan imageSpan = new ImageSpan(this, R.drawable.emoji_cry);
            SpannableString spannableString = new SpannableString(i + msg);
            spannableString.setSpan(imageSpan, spannableString.length() - 2, spannableString.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            IDanmakuItem item = new DanmakuItem(this, spannableString, mDanmakuView.getWidth(), 0, 0, 0, 1.5f);
            list.add(item);
        }
        return list;
    }

    private void initView() {
        //初始化
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mTinyWindow = (Button) findViewById(R.id.tiny_window);
        mAutoTinyWindow = (Button) findViewById(R.id.auto_tiny_window);
        mAboutUI = (Button) findViewById(R.id.play_directly_without_layout);
        mAboutListView = (Button) findViewById(R.id.about_listview);
        mPlayDirectly = (Button) findViewById(R.id.about_ui);
        mAboutWebView = (Button) findViewById(R.id.about_webview);
        mAboutWebView = (Button) findViewById(R.id.about_webview);
        imgbtn_back = (ImageButton) findViewById(R.id.imgbtn_back);
        title = (TextView) findViewById(R.id.tv_title);
        danmakuView = (DanmakuView) findViewById(R.id.opendanmu);
        mSend = (Button) findViewById(R.id.btn_send);
        et_danmu = (EditText) findViewById(R.id.et_danmu);
        title.setText("视频加弹幕");

        mTinyWindow.setOnClickListener(this);
        mAutoTinyWindow.setOnClickListener(this);
        mAboutListView.setOnClickListener(this);
        mAboutUI.setOnClickListener(this);
        mPlayDirectly.setOnClickListener(this);
        mAboutWebView.setOnClickListener(this);
        imgbtn_back.setOnClickListener(this);
        mSend.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册传感器
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        danmakuView.hide();
        //取消注册传感器
        mSensorManager.unregisterListener(mSensorEventListener);
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        danmakuView.clear();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.tiny_window:
                //小窗口播放视频
                mJcVideoPlayerStandard.startWindowTiny();
                break;
            case R.id.about_listview:
                startActivity(new Intent(this, ListViewActivity.class));
                break;
            case R.id.about_ui:
                startActivity(new Intent(this, UISmallChangeActivity.class));
                break;
            case R.id.about_webview:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            case R.id.btn_send:
                String input = et_danmu.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(JieCaoVideoPlayerMainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    IDanmakuItem item = new DanmakuItem(this, new SpannableString(input), danmakuView.getWidth(),0,R.color.my_item_color,0,1);
//                    IDanmakuItem item = new DanmakuItem(this, input, mDanmakuView.getWidth());
//                    item.setTextColor(getResources().getColor(R.color.my_item_color));
//                    item.setTextSize(14);
//                    item.setTextColor(textColor);
                    danmakuView.addItemToHead(item);
                }
                et_danmu.setText("");
                break;
        }
    }

    //使用HttpUtils Get请求
    public void getDatasFromGetByHttpUtils() {
        String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
//        OkHttpUtils
//                .get()
//                .url(url)
//                .id(100)
//                .build()
//                .execute(new BeanCallBack<DataBean>() {
//                             @Override
//                             public void onError(Call call, Exception e, int id) {
//                                 Log.e("OkHttpUtils", "onError: ", e);
//                             }
//
//                             @Override
//                             public void onResponse(DataBean response, int id) {
//                                 for (DataBean.TrailersBean trailersBean : response.getTrailers()) {
//                                     System.out.println(trailersBean.getMovieName());
//                                 }
//                             }
//                         }
//                );

//        OkHttpUtils
//                .get()
//                .url("http://short.quji.com/friendbonus/friend/list/:1")
//                .addHeader("Cookie","quji_platform=ANDROID;quji_version=221;quji_sid=e1cc66b0b6c9dfade7b7712748af6bc3")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.e("Http", "onError: " + e.toString() );
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e("Http", "onResponse: " + response );
//                    }
//                });

    }

    //HttpUtils 回调
    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {setTitle("loading...");}
        @Override
        public void onAfter(int id) {setTitle("Sample-okHttp");}
        @Override
        public void onError(Call call, Exception e, int id) {e.printStackTrace();}
        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            if (response != null) {formatDatas(response);}
        }
        @Override
        public void inProgress(float progress, long total, int id) {Log.i(TAG, "inProgress:" + progress);}
    }

    private void formatDatas(String response) {
        Gson gson = new Gson();
        DataBean dataBean = gson.fromJson(response, new TypeToken<DataBean>() {
        }.getType());
        MyApplication.dataBeen = dataBean;
        if(MyApplication.dataBeen != null){
            initJCVideoPlayer();
        }
    }

    class MyUserActionStandard implements JCUserActionStandard {
        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    danmakuView.setVisibility(View.VISIBLE);
                    danmakuView.show();
                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    danmakuView.setVisibility(View.GONE);
                    danmakuView.hide();
                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    danmakuView.setVisibility(View.VISIBLE);
                    danmakuView.show();
                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    danmakuView.setVisibility(View.GONE);
                    danmakuView.hide();
                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    danmakuView.setVisibility(View.VISIBLE);
                    danmakuView.show();
                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    danmakuView.setVisibility(View.GONE);
                    danmakuView.hide();
                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }


}
