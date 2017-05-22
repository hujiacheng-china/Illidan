package com.hujiacheng.commonframe.fresco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hujiacheng.R;
import com.hujiacheng.commonframe.fresco.activity.FrescoWithCircularActivity;
import com.hujiacheng.commonframe.fresco.activity.FrescoWithCutActivity;
import com.hujiacheng.commonframe.fresco.activity.FrescoWithGifActivity;
import com.hujiacheng.commonframe.fresco.activity.FrescoWithListenerActivity;
import com.hujiacheng.commonframe.fresco.activity.FrescoWithPluralActivity;
import com.hujiacheng.commonframe.fresco.activity.FrescoWithProgressActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
        ButterKnife.bind(this);
        tvTitle.setText("PrescoActivity");
    }

    @OnClick({R.id.btn_with_progress, R.id.btn_with_cut, R.id.btn_with_circular, R.id.btn_with_gradual, R.id.btn_with_gif, R.id.btn_with_plural, R.id.btn_with_listener, R.id.btn_with_rotate, R.id.btn_with_change, R.id.btn_with_dynamic})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_with_progress:
                intent.setClass(FrescoActivity.this, FrescoWithProgressActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_with_cut:
                intent.setClass(FrescoActivity.this, FrescoWithCutActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_with_circular:
                intent.setClass(FrescoActivity.this, FrescoWithCircularActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_with_gradual:
                intent.setClass(FrescoActivity.this, FrescoWithProgressActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_with_gif:
                intent.setClass(FrescoActivity.this, FrescoWithGifActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_with_plural:
                intent.setClass(FrescoActivity.this, FrescoWithPluralActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_with_listener:
                intent.setClass(FrescoActivity.this, FrescoWithListenerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_with_rotate:
                break;
            case R.id.btn_with_change:
                break;
            case R.id.btn_with_dynamic:
                break;
        }
    }

    @OnClick(R.id.imgbtn_back)
    public void onClick() {
        finish();
    }
}
