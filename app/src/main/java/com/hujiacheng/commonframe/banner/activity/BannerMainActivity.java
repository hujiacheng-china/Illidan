package com.hujiacheng.commonframe.banner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hujiacheng.MyApplication;
import com.hujiacheng.R;
import com.hujiacheng.commonframe.banner.adapter.SampleAdapter;
import com.hujiacheng.commonframe.banner.loader.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BannerMainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    static final int REFRESH_COMPLETE = 0X1112;
    SwipeRefreshLayout mSwipeLayout;
    ListView listView;
    Banner banner;
    TextView tv_title;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    String[] urls = getResources().getStringArray(R.array.url);
                    List list = Arrays.asList(urls);
                    List arrayList = new ArrayList(list);
                    //把新的图片地址加载到Banner
                    banner.update(arrayList);
                    //下拉刷新控件隐藏
                    mSwipeLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_banner_main);
        ButterKnife.bind(this);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        //设置下拉刷新监听
        mSwipeLayout.setOnRefreshListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("Banner横幅广告");

        listView = (ListView) findViewById(R.id.list);

        //加载Banner
        View header = LayoutInflater.from(this).inflate(R.layout.layout_banner_header, null);
        banner = (Banner) header.findViewById(R.id.banner);
        //设置Banner的高和宽
        banner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyApplication.H / 4));


        //Banner以头的方式添加到ListView中
        listView.addHeaderView(banner);


        //集合数据
        String[] data = getResources().getStringArray(R.array.demo_list);
        //设置ListView的适配器
        listView.setAdapter(new SampleAdapter(this, data));
        //设置ListView的item的点击事件
        listView.setOnItemClickListener(this);

        //简单使用--Banner加载图片地址
        banner.setImages(MyApplication.images)
                .setImageLoader(new GlideImageLoader())
                .start();
    }


    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }

    /**
     * item的点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                startActivity(new Intent(this, BannerAnimationActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, BannerStyleActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, IndicatorPositionActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, CustomBannerActivity.class));
                break;
        }
    }

    @OnClick(R.id.imgbtn_back)
    public void onClick() {
        finish();
    }
}
