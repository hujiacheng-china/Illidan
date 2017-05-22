package com.hujiacheng.commonframe.fresco.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.hujiacheng.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoWithListenerActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco)
    SimpleDraweeView sdvFresco;
    @Bind(R.id.tv_listener1)
    TextView tvListener1;
    @Bind(R.id.tv_listener2)
    TextView tvListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_with_listener);
        ButterKnife.bind(this);
        tvTitle.setText("图片加载监听");
    }

    @OnClick({R.id.imgbtn_back, R.id.btn_loading})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.btn_loading:
                ControllerListener controllerListener = new BaseControllerListener<ImageInfo>(){
                    //加载图片监听
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);

                        if(imageInfo == null){
                            return;
                        }
                        QualityInfo qualityInfo = imageInfo.getQualityInfo();

                        tvListener1.setText("Final image recevived!"
                                + "\nSize: " + imageInfo.getWidth()
                                + "x" + imageInfo.getHeight()
                                + "\nqualityInfo level: " + qualityInfo.getQuality()
                                + "\ngood enough: " + qualityInfo.isOfGoodEnoughQuality()
                                + "\nfull quality: " +  qualityInfo.isOfFullQuality());
                    }

                    //渐进式加载图片回调
                    @Override
                    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                        super.onIntermediateImageSet(id, imageInfo);
                        tvListener2.setText("IntermediateImageSet image receiced");
                    }
                    //失败回调
                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        super.onFailure(id, throwable);
                        tvListener1.setText("Error loading" + id);
                    }
                };
                Uri uri = Uri.parse("http://desk.fd.zol-img.com.cn/t_s1920x1080c5/g5/M00/0D/01/ChMkJlgq0z-IC78PAA1UbwykJUgAAXxIwMAwQcADVSH340.jpg");
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setProgressiveRenderingEnabled(true)
                        .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setOldController(sdvFresco.getController())
                    .setControllerListener(controllerListener)
                    .build();
                sdvFresco.setController(controller);
                break;
        }
    }
}
