package com.hujiacheng.commonframe.jiecaovideoplayer.customview;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hujiacheng.R;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Nathen
 * On 2016/04/22 00:54
 */
public class JCVideoPlayerStandardShowShareButtonAfterFullscreen extends JCVideoPlayerStandard {

    public ImageView shareButton;
    public DanmakuView danmakuView;

    public JCVideoPlayerStandardShowShareButtonAfterFullscreen(Context context) {
        super(context);
    }

    public JCVideoPlayerStandardShowShareButtonAfterFullscreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        shareButton = (ImageView) findViewById(R.id.share);
        shareButton.setOnClickListener(this);
        danmakuView = (DanmakuView) findViewById(R.id.opendanmu);
        initDanmaku();
    }

    private void initDanmaku() {
        List<IDanmakuItem> list = initItems(danmakuView);
        Collections.shuffle(list);
        danmakuView.addItem(list,true);
    }

    private List<IDanmakuItem> initItems(DanmakuView mDanmakuView) {
        List<IDanmakuItem> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            IDanmakuItem item = new DanmakuItem(getContext(), i + "老王双击666", mDanmakuView.getWidth());
            list.add(item);
        }

        String msg = "扎心了老铁   ";
        for (int i = 0; i < 100; i++) {
            ImageSpan imageSpan = new ImageSpan(getContext(), R.drawable.emoji_cry);
            SpannableString spannableString = new SpannableString(i + msg);
            spannableString.setSpan(imageSpan, spannableString.length() - 2, spannableString.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            IDanmakuItem item = new DanmakuItem(getContext(), spannableString, mDanmakuView.getWidth(), 0, 0, 0, 1.5f);
            list.add(item);
        }
        return list;
    }
    @Override
    public int getLayoutId() {
        return R.layout.layout_standard_with_share_button;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.share) {
            Toast.makeText(getContext(), "Whatever the icon means", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setUp(String url, int screen, Object... objects) {
        super.setUp(url, screen, objects);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            shareButton.setVisibility(View.VISIBLE);
            danmakuView.setVisibility(View.VISIBLE);
            danmakuView.show();
        } else {
            shareButton.setVisibility(View.INVISIBLE);
            danmakuView.setVisibility(View.INVISIBLE);
            danmakuView.hide();
        }
    }
}
