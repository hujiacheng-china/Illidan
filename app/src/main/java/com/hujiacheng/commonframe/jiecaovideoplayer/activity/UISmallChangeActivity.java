package com.hujiacheng.commonframe.jiecaovideoplayer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.hujiacheng.MyApplication;
import com.hujiacheng.R;
import com.hujiacheng.commonframe.jiecaovideoplayer.customview.JCVideoPlayerStandardAutoCompleteAfterFullscreen;
import com.hujiacheng.commonframe.jiecaovideoplayer.customview.JCVideoPlayerStandardShowShareButtonAfterFullscreen;
import com.hujiacheng.commonframe.jiecaovideoplayer.customview.JCVideoPlayerStandardShowTextureViewAfterAutoComplete;
import com.hujiacheng.commonframe.jiecaovideoplayer.customview.JCVideoPlayerStandardShowTitleAfterFullscreen;
import com.hujiacheng.commonframe.okhttp.DataBean;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Nathen on 16/7/31.
 */
public class UISmallChangeActivity extends AppCompatActivity {
    JCVideoPlayerStandardShowShareButtonAfterFullscreen jcVideoPlayerStandardWithShareButton;
    JCVideoPlayerStandardShowTitleAfterFullscreen jcVideoPlayerStandardShowTitleAfterFullscreen;
    JCVideoPlayerStandardShowTextureViewAfterAutoComplete jcVideoPlayerStandardShowTextureViewAfterAutoComplete;
    JCVideoPlayerStandardAutoCompleteAfterFullscreen jcVideoPlayerStandardAutoCompleteAfterFullscreen;

    JCVideoPlayerStandard jcVideoPlayerStandard_1_1, jcVideoPlayerStandard_16_9;
    List<DataBean.TrailersBean> datas = MyApplication.dataBeen.getTrailers();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("自定义UI");
        setContentView(R.layout.activity_ui_small_change);
        initJCVideoPlayer();
    }

    private void initJCVideoPlayer() {
        if(datas == null){return;}

        jcVideoPlayerStandardWithShareButton = (JCVideoPlayerStandardShowShareButtonAfterFullscreen) findViewById(R.id.custom_videoplayer_standard_with_share_button);
        jcVideoPlayerStandardWithShareButton.setUp(datas.get(0).getHightUrl(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL
                , datas.get(0).getMovieName());
        Glide.with(this)
                .load(datas.get(0).getCoverImg())
                .into(jcVideoPlayerStandardWithShareButton.thumbImageView);


        jcVideoPlayerStandardShowTitleAfterFullscreen = (JCVideoPlayerStandardShowTitleAfterFullscreen) findViewById(R.id.custom_videoplayer_standard_show_title_after_fullscreen);
        jcVideoPlayerStandardShowTitleAfterFullscreen.setUp(datas.get(1).getHightUrl(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL
                , datas.get(1).getMovieName());
        Glide.with(this)
                .load(datas.get(1).getCoverImg())
                .into(jcVideoPlayerStandardShowTitleAfterFullscreen.thumbImageView);

        jcVideoPlayerStandardShowTextureViewAfterAutoComplete = (JCVideoPlayerStandardShowTextureViewAfterAutoComplete) findViewById(R.id.custom_videoplayer_standard_show_textureview_aoto_complete);
        jcVideoPlayerStandardShowTextureViewAfterAutoComplete.setUp(datas.get(2).getHightUrl(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL
                , datas.get(2).getMovieName());
        Glide.with(this)
                .load(datas.get(2).getCoverImg())
                .into(jcVideoPlayerStandardShowTextureViewAfterAutoComplete.thumbImageView);

        jcVideoPlayerStandardAutoCompleteAfterFullscreen = (JCVideoPlayerStandardAutoCompleteAfterFullscreen) findViewById(R.id.custom_videoplayer_standard_aoto_complete);
        jcVideoPlayerStandardAutoCompleteAfterFullscreen.setUp(datas.get(3).getHightUrl(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL
                , datas.get(3).getMovieName());
        Glide.with(this)
                .load(datas.get(3).getCoverImg())
                .into(jcVideoPlayerStandardAutoCompleteAfterFullscreen.thumbImageView);

        jcVideoPlayerStandard_1_1 = (JCVideoPlayerStandardAutoCompleteAfterFullscreen) findViewById(R.id.jc_videoplayer_1_1);
        jcVideoPlayerStandard_1_1.setUp(datas.get(4).getHightUrl(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL
                , datas.get(4).getMovieName());
        Glide.with(this)
                .load(datas.get(4).getCoverImg())
                .into(jcVideoPlayerStandard_1_1.thumbImageView);
        jcVideoPlayerStandard_1_1.widthRatio = 1;
        jcVideoPlayerStandard_1_1.heightRatio = 1;

        jcVideoPlayerStandard_16_9 = (JCVideoPlayerStandardAutoCompleteAfterFullscreen) findViewById(R.id.jc_videoplayer_16_9);
        jcVideoPlayerStandard_16_9.setUp(datas.get(5).getHightUrl(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL
                , datas.get(5).getMovieName());
        Glide.with(this)
                .load(datas.get(5).getCoverImg())
                .into(jcVideoPlayerStandard_16_9.thumbImageView);
        jcVideoPlayerStandard_16_9.widthRatio = 16;
        jcVideoPlayerStandard_16_9.heightRatio = 9;
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
