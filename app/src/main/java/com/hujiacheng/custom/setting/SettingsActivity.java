package com.hujiacheng.custom.setting;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hujiacheng.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends Activity {
    @Bind(R.id.mtb_choose)
    MyToggleButton mtbChoose;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        tvTitle.setText("设置界面");
    }

    @OnClick({R.id.imgbtn_back, R.id.mtb_choose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.mtb_choose:
                break;
        }
    }
}
