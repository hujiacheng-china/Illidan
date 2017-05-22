package com.hujiacheng.commonframe.swipetoloadlayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hujiacheng.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hujiacheng on 2017/3/3.
 */

public class SwipeToLoadRecyclerViewActivity extends Activity implements OnRefreshListener, OnLoadMoreListener{

    private RecyclerView recyclerview;
    private List<String> datas;
    private SwipeToLoadRecyclerViewAdapter adapter;
    private Context mContext;
    private TextView tvTtitle;
    private SwipeToLoadLayout swipeToLoadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_to_load_recyclerview);
        ButterKnife.bind(this);
        initView();

        initDatas();

        //设置RecyclerView适配器
        adapter = new SwipeToLoadRecyclerViewAdapter(SwipeToLoadRecyclerViewActivity.this,datas);
        recyclerview.setAdapter(adapter);

        //LayoutManager
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL,false));

        //点击监听
        adapter.setOnItemClickListener(new SwipeToLoadRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(mContext,"data="+data,Toast.LENGTH_SHORT).show();
            }
        });

        //设置动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());

    }

    @OnClick(R.id.imgbtn_back)
    public void onClick() {
        finish();
    }

    private void initDatas() {
        tvTtitle.setText("SwipeToLoadActivity");
        //准备数据集合
        datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("Item_"+i);
        }
    }

    private void initView() {
        mContext = this;
        recyclerview = (RecyclerView) findViewById(R.id.swipe_target);
        tvTtitle = (TextView) findViewById(R.id.tv_title);
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

//        recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
//                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
//                        swipeToLoadLayout.setLoadingMore(true);
//                    }
//                }
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
                adapter.addData(adapter.getItemCount(),"Load Data");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerview.smoothScrollToPosition(recyclerview.getBottom());
                    }
                },1000);
            }
        },500);
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addData(0,"New Refresh Data");
                swipeToLoadLayout.setRefreshing(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerview.smoothScrollToPosition(0);
                    }
                },1000);
            }
        },500);
    }

}
