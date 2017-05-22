package com.hujiacheng.commonframe.jsonparse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hujiacheng.R;
import com.hujiacheng.commonframe.okhttp.DataBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JsonPraseActivity extends AppCompatActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_native_tojavaobject)
    Button btnNativeTojavaobject;
    @Bind(R.id.btn_native_tojavalist)
    Button btnNativeTojavalist;
    @Bind(R.id.btn_native_complex)
    Button btnNativeComplex;
    @Bind(R.id.btn_native_special)
    Button btnNativeSpecial;
    @Bind(R.id.tv_native_orignal)
    TextView tvNativeOrignal;
    @Bind(R.id.tv_native_last)
    TextView tvNativeLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_json_prase);
        ButterKnife.bind(this);
        tvTitle.setText("JsonPrase");
    }

    @OnClick({R.id.imgbtn_back,R.id.btn_native_tojavaobject, R.id.btn_native_tojavalist, R.id.btn_native_complex})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_native_tojavaobject:
                jsonToJavaObjectByNative();
                break;
            case R.id.btn_native_tojavalist:
                jsonToJavaListByNative();
                break;
            case R.id.btn_native_complex:
                jsonToJavaObjectByGson();
                break;
            case R.id.imgbtn_back:
                finish();
                break;
        }
    }

    private void jsonToJavaObjectByNative() {
        //获取Json
        JSONObject jsonObject = null;
        String json = " {\n" +
                "            \"id\": 64768,\n" +
                "            \"movieName\": \"《银河护卫队2》中文预告片\",\n" +
                "            \"coverImg\": \"http://img5.mtime.cn/mg/2017/03/01/164416.40461950.jpg\",\n" +
                "            \"movieId\": 216008,\n" +
                "            \"url\": \"http://vfx.mtime.cn/Video/2017/03/01/mp4/170301133120672165_480.mp4\",\n" +
                "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2017/03/01/mp4/170301133120672165.mp4\",\n" +
                "            \"videoTitle\": \"银河护卫队2 中文终极版预告\",\n" +
                "            \"videoLength\": 143,\n" +
                "            \"rating\": -1,\n" +
                "            \"type\": [\n" +
                "                \"动作\",\n" +
                "                \"科幻\"\n" +
                "            ],\n" +
                "            \"summary\": \"废柴英雄斗嘴耍帅两不误\"\n" +
                "        }";
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DataBean.TrailersBean trailersBean = new DataBean.TrailersBean();
        trailersBean.setId(jsonObject.optInt("id"));
        trailersBean.setMovieName(jsonObject.optString("movieName"));

        tvNativeOrignal.setText(json);
        tvNativeLast.setText(trailersBean.toString());
    }

    private void jsonToJavaListByNative() {
        String json = "[\n" +
                "        {\n" +
                "            \"id\": 64768,\n" +
                "            \"movieName\": \"《银河护卫队2》中文预告片\",\n" +
                "            \"coverImg\": \"http://img5.mtime.cn/mg/2017/03/01/164416.40461950.jpg\",\n" +
                "            \"movieId\": 216008,\n" +
                "            \"url\": \"http://vfx.mtime.cn/Video/2017/03/01/mp4/170301133120672165_480.mp4\",\n" +
                "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2017/03/01/mp4/170301133120672165.mp4\",\n" +
                "            \"videoTitle\": \"银河护卫队2 中文终极版预告\",\n" +
                "            \"videoLength\": 143,\n" +
                "            \"rating\": -1,\n" +
                "            \"type\": [\n" +
                "                \"动作\",\n" +
                "                \"科幻\"\n" +
                "            ],\n" +
                "            \"summary\": \"废柴英雄斗嘴耍帅两不误\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 64705,\n" +
                "            \"movieName\": \"《乐高蝙蝠侠大电影》中文版终极预告\",\n" +
                "            \"coverImg\": \"http://img5.mtime.cn/mg/2017/02/24/170801.89976260.jpg\",\n" +
                "            \"movieId\": 223948,\n" +
                "            \"url\": \"http://vfx.mtime.cn/Video/2017/02/24/mp4/170224095810371082_480.mp4\",\n" +
                "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2017/02/24/mp4/170224095810371082.mp4\",\n" +
                "            \"videoTitle\": \"乐高蝙蝠侠大电影 中文版终极预告片\",\n" +
                "            \"videoLength\": 60,\n" +
                "            \"rating\": 8,\n" +
                "            \"type\": [\n" +
                "                \"动画\",\n" +
                "                \"喜剧\",\n" +
                "                \"动作\",\n" +
                "                \"冒险\",\n" +
                "                \"家庭\",\n" +
                "                \"奇幻\"\n" +
                "            ],\n" +
                "            \"summary\": \"哥谭孤胆侠颠覆画风\"\n" +
                "        }]";

        JSONArray jsonArray = null;
        List<DataBean.TrailersBean> dataBean = new ArrayList<>();
        try {
            jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length();i++) {
                DataBean.TrailersBean data = new DataBean.TrailersBean();
                JSONObject thisdata = (JSONObject) jsonArray.get(i);
                data.setId(thisdata.optInt("id"));
                data.setMovieName(thisdata.optString("movieName"));
                dataBean.add(data);
            }
            tvNativeOrignal.setText(json);
            tvNativeLast.setText(dataBean.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jsonToJavaObjectByGson() {
        String json = "[\n" +
                "        {\n" +
                "            \"id\": 64768,\n" +
                "            \"movieName\": \"《银河护卫队2》中文预告片\",\n" +
                "            \"coverImg\": \"http://img5.mtime.cn/mg/2017/03/01/164416.40461950.jpg\",\n" +
                "            \"movieId\": 216008,\n" +
                "            \"url\": \"http://vfx.mtime.cn/Video/2017/03/01/mp4/170301133120672165_480.mp4\",\n" +
                "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2017/03/01/mp4/170301133120672165.mp4\",\n" +
                "            \"videoTitle\": \"银河护卫队2 中文终极版预告\",\n" +
                "            \"videoLength\": 143,\n" +
                "            \"rating\": -1,\n" +
                "            \"type\": [\n" +
                "                \"动作\",\n" +
                "                \"科幻\"\n" +
                "            ],\n" +
                "            \"summary\": \"废柴英雄斗嘴耍帅两不误\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 64705,\n" +
                "            \"movieName\": \"《乐高蝙蝠侠大电影》中文版终极预告\",\n" +
                "            \"coverImg\": \"http://img5.mtime.cn/mg/2017/02/24/170801.89976260.jpg\",\n" +
                "            \"movieId\": 223948,\n" +
                "            \"url\": \"http://vfx.mtime.cn/Video/2017/02/24/mp4/170224095810371082_480.mp4\",\n" +
                "            \"hightUrl\": \"http://vfx.mtime.cn/Video/2017/02/24/mp4/170224095810371082.mp4\",\n" +
                "            \"videoTitle\": \"乐高蝙蝠侠大电影 中文版终极预告片\",\n" +
                "            \"videoLength\": 60,\n" +
                "            \"rating\": 8,\n" +
                "            \"type\": [\n" +
                "                \"动画\",\n" +
                "                \"喜剧\",\n" +
                "                \"动作\",\n" +
                "                \"冒险\",\n" +
                "                \"家庭\",\n" +
                "                \"奇幻\"\n" +
                "            ],\n" +
                "            \"summary\": \"哥谭孤胆侠颠覆画风\"\n" +
                "        }]";
        Gson gson = new Gson();
        List<DataBean.TrailersBean> dataBean = gson.fromJson(json,new TypeToken< List<DataBean.TrailersBean>>(){}.getType());
        tvNativeOrignal.setText(json);
        tvNativeLast.setText(dataBean.toString());
    }
}
