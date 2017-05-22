package com.hujiacheng.commonframe.okhttp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hujiacheng.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by HuJiaCheng on 2017/3/4.
 */

public class OkHttpListActivity extends Activity {
    @Bind(R.id.pb_okhttplist)
    ProgressBar pbOkhttplist;
    @Bind(R.id.lv_okhttplist)
    ListView lvOkhttplist;
    @Bind(R.id.tv_nodata)
    TextView tvNodata;
    private Context mContext;

    private final static String TAG = OkHttpListActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttplist);
        ButterKnife.bind(this);
        mContext = this;
        getDatasFromGetByHttpUtils();

    }

    @OnClick(R.id.imgbtn_back)
    public void onClick() {
        finish();
    }

    //使用HttpUtils Get请求
    public void getDatasFromGetByHttpUtils() {
        String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new OkHttpListActivity.MyStringCallback());
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
            switch (id) {
                case 100:
                    Toast.makeText(OkHttpListActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OkHttpListActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
            if(response != null){
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
        DataBean dataBean = gson.fromJson(response,new TypeToken<DataBean>() {}.getType());
        Myadapter myadapter = new Myadapter(mContext,dataBean.getTrailers());
        lvOkhttplist.setAdapter(myadapter);
    }

}
