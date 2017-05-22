package com.hujiacheng.commonframe.fresco.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hujiacheng.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoWithProgressActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sdv_fresco)
    SimpleDraweeView sdvFresco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_with_progress);
        ButterKnife.bind(this);
        tvTitle.setText("带进度条的图片显示");
        Uri uri = Uri.parse("http://desk.fd.zol-img.com.cn/t_s1366x768c5/g5/M00/00/01/ChMkJlZKiP2IWOWvAAiOsJVY6pAAAE_rQE1lV8ACI7I090.jpg");
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder.setProgressBarImage(new ProgressBarDrawable()).build();
        sdvFresco.setHierarchy(hierarchy);
        sdvFresco.setImageURI(uri);
    }

    @OnClick(R.id.imgbtn_back)
    public void onClick() {
        finish();
    }
}
