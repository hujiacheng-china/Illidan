package com.hujiacheng.custom.zoomlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hujiacheng.R;
import com.hujiacheng.fragment.adapter.CommonFrameFragmentAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZoomListActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.zoom_listview)
    ZoomListView zoomListview;
    private String[] datas;
    private CommonFrameFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_list);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        tvTitle.setText("头部可拉伸ListView");
        int size = 100;
        datas = new String[size];
        for (int i = 0; i < size; i++) {
            datas[i] = "item" + i;
        }
        adapter = new CommonFrameFragmentAdapter(this,datas);
        View view = View.inflate(this, R.layout.layout_zoomlist_header, null);
        zoomListview.addHeaderView(view);
        zoomListview.setImageView((ImageView) view.findViewById(R.id.iv_zoomlist_header));
        zoomListview.setProgressBar((ProgressBar) view.findViewById(R.id.progressbar));
        zoomListview.setAdapter(adapter);
    }

    @OnClick(R.id.imgbtn_back)
    public void onClick() {
        finish();
    }
}
