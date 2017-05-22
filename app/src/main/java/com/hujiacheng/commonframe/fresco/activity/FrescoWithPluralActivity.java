package com.hujiacheng.commonframe.fresco.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.hujiacheng.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoWithPluralActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco)
    SimpleDraweeView sdvFresco;
    Uri lowResUri = Uri.parse("http://desk.fd.zol-img.com.cn/t_s1024x768c5/g5/M00/0D/01/ChMkJlgq0z-IC78PAA1UbwykJUgAAXxIwMAwQcADVSH340.jpg");
    Uri heighResUri = Uri.parse("http://desk.fd.zol-img.com.cn/t_s1920x1080c5/g5/M00/0D/01/ChMkJlgq0z-IC78PAA1UbwykJUgAAXxIwMAwQcADVSH340.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_with_plural);
        ButterKnife.bind(this);
        tvTitle.setText("多图请求及图片复用");
    }

    @OnClick({R.id.imgbtn_back, R.id.btn_preview, R.id.btn_thumbnail, R.id.btn_plural})
    public void onClick(View view) {
        DraweeController controller;
        switch (view.getId()) {
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.btn_preview:
                controller = Fresco.newDraweeControllerBuilder()
                        .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
                        .setImageRequest(ImageRequest.fromUri(heighResUri))
                        .setOldController(sdvFresco.getController())
                        .build();
                sdvFresco.setController(controller);
                break;
            case R.id.btn_thumbnail:
                Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/AudioRecord/1.jpg"));
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setLocalThumbnailPreviewsEnabled(true)
                        .build();
                controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .build();
                sdvFresco.setController(controller);
                break;
            case R.id.btn_plural:
                //本地图片的复用
                //在请求之前，会向内存中请求一次图片，若没有则去本地，最后请求网络uri
                //本地准备复用的uri，如果不存在会自动加载下一个uri
                Uri uri1 = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/AudioRecord/2.jpg"));
                Uri uri2 = Uri.parse("http://desk.fd.zol-img.com.cn/t_s1024x768c5/g5/M00/0D/01/ChMkJlgq0z-IC78PAA1UbwykJUgAAXxIwMAwQcADVSH340.jpg");

                ImageRequest request1 = ImageRequest.fromUri(uri1);
                ImageRequest request2 = ImageRequest.fromUri(uri2);
                ImageRequest[] requests = {request1,request2};
                controller = Fresco.newDraweeControllerBuilder()
                        .setFirstAvailableImageRequests(requests)
                        .setOldController(sdvFresco.getController())
                        .build();
                sdvFresco.setController(controller);
                break;
        }
    }
}
