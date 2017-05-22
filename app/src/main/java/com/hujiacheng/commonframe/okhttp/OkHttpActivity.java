package com.hujiacheng.commonframe.okhttp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hujiacheng.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HuJiaCheng on 2017/3/4.
 */

public class OkHttpActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_okhttp_get_post)
    Button btnOkhttpGet;
    @Bind(R.id.btn_okhttputils_get_post)
    Button btnOkhttpPost;
    @Bind(R.id.tv_okhttp)
    TextView tvOkhttp;
    @Bind(R.id.btn_downloadfile)
    Button btnDownloadfile;
    @Bind(R.id.pb_okhttp)
    ProgressBar pbOkhttp;
    @Bind(R.id.btn_uploadfile)
    Button btnUploadfile;
    @Bind(R.id.btn_downloadimage)
    Button btnDownloadimage;
    @Bind(R.id.iv_okhttp)
    ImageView ivOkhttp;
    @Bind(R.id.btn_openlist)
    Button btnOpenlist;
    private static final int GET = 1;
    private static final int POST = 2;
    private static final String TAG = OkHttpActivity.class.getSimpleName();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String URL = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
    private OkHttpClient client = new OkHttpClient();
    private boolean isokhttp_get = true;
    private boolean isokhttputils_get = true;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    tvOkhttp.setText("");
                    tvOkhttp.setText((String) msg.obj);
                    Toast.makeText(OkHttpActivity.this, "GET", Toast.LENGTH_SHORT).show();
                    break;
                case POST:
                    tvOkhttp.setText("");
                    tvOkhttp.setText((String) msg.obj);
                    Toast.makeText(OkHttpActivity.this, "POST", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("OKHttp");
    }

    @OnClick({R.id.imgbtn_back,R.id.btn_okhttp_get_post, R.id.btn_okhttputils_get_post, R.id.btn_downloadfile,R.id.btn_uploadfile,R.id.btn_downloadimage,R.id.btn_openlist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_back:
                finish();
            case R.id.btn_okhttp_get_post:
                if (isokhttp_get) {
                    getDatasFromGet();
                    isokhttp_get = false;
                } else {
                    getDatasFromPost();
                    isokhttp_get = true;
                }
                break;
            case R.id.btn_okhttputils_get_post:
                if (isokhttputils_get) {
                    getDatasFromGetByHttpUtils();
                    isokhttputils_get = false;
                } else {
                    getDatasFromPostByHttpUtils();
                    isokhttputils_get = true;
                }
                break;
            case R.id.btn_downloadfile:
                downloadFile();
                break;
            case R.id.btn_uploadfile:
                multiFileUpload();
                break;
            case R.id.btn_downloadimage:
                getImage();
                break;
            case R.id.btn_openlist:
                startActivity(new Intent(OkHttpActivity.this,OkHttpListActivity.class));
                break;
        }
    }


    //OkHttp原生方法

    /**
     * OkHttp3 GET 请求
     *
     * @param url 请求地址
     * @return 返回数据
     * @throws IOException
     */
    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * OkHttp3 POST 请求
     *
     * @param url 请求地址
     * @return 返回数据
     * @throws IOException
     */
    public String post(String url, String json) throws IOException {
        //RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                //.post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private String getDatasFromGet() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String result = null;
                try {
                    result = get(URL);
                    Log.e(TAG, "GET Result = " + result);
                    Message msg = Message.obtain();
                    msg.what = GET;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return "";
    }

    private String getDatasFromPost() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String result = null;
                try {
                    result = post(URL, null);
                    Log.e(TAG, "POST Result = " + result);
                    Message msg = Message.obtain();
                    msg.what = POST;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return "";
    }


    //OkHttpUtils
    //使用HttpUtils Get请求
    public void getDatasFromGetByHttpUtils() {
        OkHttpUtils
                .get()
                .url(URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    //使用HttpUtils Post请求
    public void getDatasFromPostByHttpUtils() {
        OkHttpUtils
                .post()
                .url(URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    //HttpUtils 回调
    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            tvOkhttp.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            tvOkhttp.setText("onResponse:" + response);

            switch (id) {
                case 100:
                    Toast.makeText(OkHttpActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OkHttpActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
            pbOkhttp.setProgress((int) (100 * progress));
        }
    }

    //HttpUtils 大文件下载
    public void downloadFile() {
        String url = "http://vfx.mtime.cn/Video/2017/03/01/mp4/170301133120672165_480.mp4";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "okhttp-utils.mp4")//
                {
                    @Override
                    public void onBefore(Request request, int id) {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        pbOkhttp.setProgress((int) (100 * progress));
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }

    //HttpUtils 一个或者多个文件上传
    public void multiFileUpload() {
        String mBaseUrl = "";
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test1#.txt");
        if (!file.exists()) {
            Toast.makeText(OkHttpActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "hujiacheng");
        params.put("password", "123");

        String url = mBaseUrl + "user!uploadFile";
        OkHttpUtils.post()//
                .addFile("mFile", "messenger_01.png", file)//
                .addFile("mFile", "test1.txt", file2)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    //HttpUtils 加载图片
    public void getImage() {
        tvOkhttp.setText("");
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//get方法
                .url(url)//URL
                .tag(this)//TAG
                .build()//创建
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback()//回调
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        tvOkhttp.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        Log.e("TAG", "onResponse：complete");
                        ivOkhttp.setImageBitmap(bitmap);
                    }
                });
    }
}
