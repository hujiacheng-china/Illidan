package com.hujiacheng.commonframe.fresco.activity;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hujiacheng.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoWithCircularActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco)
    SimpleDraweeView sdvFresco;
    @Bind(R.id.imgbtn_back)
    ImageButton imgbtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_with_circular);
        ButterKnife.bind(this);
        tvTitle.setText("圆形和圆角图片");
    }

    @OnClick({R.id.btn_circle, R.id.btn_corner,R.id.imgbtn_back})
    public void onClick(View view) {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy;
        RoundingParams params;
        switch (view.getId()) {
            case R.id.btn_circle:
                params = RoundingParams.asCircle();
                hierarchy = builder.setRoundingParams(params).build();
                imageDisplay(hierarchy);
                break;
            case R.id.btn_corner:
                params = RoundingParams.fromCornersRadius(50f);
//                params.setOverlayColor(Color.parseColor("#22000000"));
                params.setBorder(Color.parseColor("#000000"),8.0f);
                hierarchy = builder.setRoundingParams(params).build();
                imageDisplay(hierarchy);
                break;
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
