package com.hujiacheng.commonframe.jiecaovideoplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;

import com.bumptech.glide.Glide;
import com.hujiacheng.MyApplication;
import com.hujiacheng.R;
import com.hujiacheng.commonframe.okhttp.DataBean;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCUtils;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by HujiaCheng on 17/3/18.
 */

public class WebViewActivity extends AppCompatActivity {
    WebView mWebView;
    List<DataBean.TrailersBean> datas = MyApplication.dataBeen.getTrailers();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("AboutWebView");
        setContentView(R.layout.activity_webview);
        mWebView = (WebView) findViewById(R.id.webview);
        //设置值JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        //添加JavascriptInterface
        mWebView.addJavascriptInterface(new JCCallBack(), "jcvd");
        mWebView.loadUrl("file:///android_asset/jcvd.html");
    }

    public class JCCallBack {

        @JavascriptInterface
        public void adViewJieCaoVideoPlayer(final int width, final int height, final int top, final int left, final int index) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (index == 0) {
                        if(datas == null || datas.size() < 2){return;}
                        //代码实例化
                        JCVideoPlayerStandard webVieo = new JCVideoPlayerStandard(WebViewActivity.this);
                        webVieo.setUp(datas.get(0).getHightUrl(),
                                JCVideoPlayer.SCREEN_LAYOUT_LIST, datas.get(0).getMovieName());
                        Glide.with(WebViewActivity.this)
                                .load(datas.get(0).getCoverImg())
                                .centerCrop()
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = JCUtils.dip2px(WebViewActivity.this, top);
                        layoutParams.x = JCUtils.dip2px(WebViewActivity.this, left);
                        layoutParams.height = JCUtils.dip2px(WebViewActivity.this, height);
                        layoutParams.width = JCUtils.dip2px(WebViewActivity.this, width);
                        //添加到webview中
                        mWebView.addView(webVieo, layoutParams);
                    } else {
                        //代码实例化
                        JCVideoPlayerStandard webVieo = new JCVideoPlayerStandard(WebViewActivity.this);
                        webVieo.setUp(datas.get(1).getHightUrl(),
                                JCVideoPlayer.SCREEN_LAYOUT_LIST, datas.get(1).getMovieName());
                        Glide.with(WebViewActivity.this)
                                .load(datas.get(1).getCoverImg())
                                .centerCrop()
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = JCUtils.dip2px(WebViewActivity.this, top);
                        layoutParams.x = JCUtils.dip2px(WebViewActivity.this, left);
                        layoutParams.height = JCUtils.dip2px(WebViewActivity.this, height);
                        layoutParams.width = JCUtils.dip2px(WebViewActivity.this, width);
                        mWebView.addView(webVieo, layoutParams);
                    }

                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
