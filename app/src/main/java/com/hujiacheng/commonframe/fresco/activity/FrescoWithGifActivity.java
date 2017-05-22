package com.hujiacheng.commonframe.fresco.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hujiacheng.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoWithGifActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco)
    SimpleDraweeView sdvFresco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_with_gif);
        ButterKnife.bind(this);
        tvTitle.setText("Gif动画图片");
    }

    @OnClick({R.id.imgbtn_back, R.id.btn_getgif, R.id.btn_startgif, R.id.btn_stopgif})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.btn_getgif:
                Uri uri = Uri.parse("http://bbs.gameres.com/data/attachment/forum/201503/04/103056azs4d9unw5s6ww4w.gif");
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(false)
                        .setOldController(sdvFresco.getController())
                        .build();
                sdvFresco.setController(controller);
                break;
            case R.id.btn_startgif:
                Animatable animatable = sdvFresco.getController().getAnimatable();
                if(animatable!=null && !animatable.isRunning())
                    animatable.start();
                break;
            case R.id.btn_stopgif:
                animatable = sdvFresco.getController().getAnimatable();
                if(animatable!=null && animatable.isRunning())
                    animatable.stop();
                break;
        }
    }
}
