package com.hujiacheng.commonframe.butterknife;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.hujiacheng.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HuJiaCheng on 2017/3/4.
 */

public class ButterKnifeActivity extends Activity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_butterknife)
    TextView tvButterknife;
    @Bind(R.id.cb_butterknife)
    CheckBox cbButterknife;
    @Bind(R.id.btn_butterknife)
    Button btnButterknife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterknife);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        tvTitle.setText("ButterKnife");
        tvButterknife.setText("我在使用ButterKnife");
    }

    @OnClick({R.id.tv_butterknife, R.id.cb_butterknife, R.id.btn_butterknife,R.id.imgbtn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.tv_butterknife:
                Toast.makeText(ButterKnifeActivity.this,"你点击了TextView",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cb_butterknife:
                Toast.makeText(ButterKnifeActivity.this,"你点击了CheckBox",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_butterknife:
                Toast.makeText(ButterKnifeActivity.this,"你点击了Button",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
