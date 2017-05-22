package com.hujiacheng.commonframe.fresco.activity;

import android.app.Activity;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hujiacheng.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoWithCutActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_explain)
    TextView tvExplain;
    @Bind(R.id.sdv_fresco)
    SimpleDraweeView sdvFresco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_with_cut);
        ButterKnife.bind(this);
        tvTitle.setText("图片的不同裁剪");
    }

    @OnClick({R.id.imgbtn_back,R.id.btn_none,R.id.btn_center, R.id.btn_centercrop, R.id.btn_focuscrop, R.id.btn_centerinside, R.id.btn_fitcenter, R.id.btn_fitstart, R.id.btn_fitend, R.id.btn_fitxy})
    public void onClick(View view) {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy;
        switch (view.getId()) {
            case R.id.btn_center:
                tvExplain.setText("居中，无缩放");
                hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER).build();
                imageDisplay(hierarchy);
                break;
            case R.id.btn_centercrop:
                tvExplain.setText("保持宽高比缩小或放大，使得两边都大于或等于显示边界，居中显示");
                hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP).build();
                imageDisplay(hierarchy);
                break;
            case R.id.btn_focuscrop:
                tvExplain.setText("同centerCrop，但居中点不是中点，而是指定的某个点，这里我设置为图片的左上角那点");
                PointF point = new PointF(0,0);
                hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
                        .setActualImageFocusPoint(point).build();
                imageDisplay(hierarchy);
                break;
            case R.id.btn_centerinside:
                tvExplain.setText("使两边都显示在边界内，居中显示。如果图片尺寸大于显示边界，则保持长宽比缩小图片");
                hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE).build();
                imageDisplay(hierarchy);
                break;
            case R.id.btn_fitcenter:
                tvExplain.setText("保持宽高比缩小或放大，使得图片完全显示在边界内。居中显示");
                hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).build();
                imageDisplay(hierarchy);
                break;
            case R.id.btn_fitstart:
                tvExplain.setText("保持宽高比缩小或放大，使得图片完全显示在边界内，不居中，和显示边界左上角对齐");
                hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_START).build();
                imageDisplay(hierarchy);
                break;
            case R.id.btn_fitend:
                tvExplain.setText("保持宽高比缩小或放大，使得图片完全显示在边界内，不居中，和显示边界右下角对齐");
                hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_END).build();
                imageDisplay(hierarchy);
                break;
            case R.id.btn_fitxy:
                tvExplain.setText("不保持宽高比，填充显示边界");
                hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY).build();
                imageDisplay(hierarchy);
                break;
            case R.id.btn_none:
                tvExplain.setText("如果使用title model显示，需要设置为none");
                hierarchy = builder.setActualImageScaleType(null).build();
                imageDisplay(hierarchy);
            case R.id.imgbtn_back:
                finish();
                break;
        }
    }

    private void imageDisplay(GenericDraweeHierarchy hierarchy) {
        sdvFresco.setHierarchy(hierarchy);
        Uri uri = Uri.parse("http://desk.fd.zol-img.com.cn/t_s1366x768c5/g5/M00/00/01/ChMkJlZKiP2IWOWvAAiOsJVY6pAAAE_rQE1lV8ACI7I090.jpg");
        sdvFresco.setImageURI(uri);
    }
}
