package com.hujiacheng.commonframe.imageloader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hujiacheng.R;
import com.hujiacheng.commonframe.okhttp.DataBean;
import com.hujiacheng.commonframe.okhttp.Myadapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by hujiacheng on 2017/3/3.
 */

public class ImageLoaderActivity extends Activity {
    @Bind(R.id.pb_okhttplist)
    ProgressBar pbOkhttplist;
    @Bind(R.id.lv_okhttplist)
    ListView lvOkhttplist;
    @Bind(R.id.tv_nodata)
    TextView tvNodata;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    private Context mContext;

    private final static String TAG = ImageLoaderActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageloader);
        ButterKnife.bind(this);
        mContext = this;
        tvTitle.setText("ImageLoaderActivity");
        getDatasFromGetByHttpUtils();
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
    }

    @OnClick(R.id.imgbtn_back)
    public void onClick() {
        finish();
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
            lvOkhttplist.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            tvNodata.setVisibility(View.GONE);
//            switch (id) {
//                case 100:
//                    Toast.makeText(mContext, "http", Toast.LENGTH_SHORT).show();
//                    break;
//                case 101:
//                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
//                    break;
//            }
            if (response != null) {
                formatDatas(response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.i(TAG, "inProgress:" + progress);
            pbOkhttplist.setProgress((int) (100 * progress));
        }
    }

    private void formatDatas(String response) {
        Gson gson = new Gson();
        DataBean dataBean = gson.fromJson(response, new TypeToken<DataBean>() {
        }.getType());
        Myadapter myadapter = new Myadapter(mContext, dataBean.getTrailers());
        lvOkhttplist.setAdapter(myadapter);
    }
}
