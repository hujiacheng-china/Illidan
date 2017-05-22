package com.hujiacheng.commonframe.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hujiacheng.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujiacheng on 2017/3/3.
 */

public class RecyclerViewActivity extends Activity implements View.OnClickListener {

    private Button btn_add;
    private Button btn_delete;
    private Button btn_list;
    private Button btn_grid;
    private Button btn_flow;
    private ImageButton imgbtn_back;
    private RecyclerView recyclerview;
    private TextView tv_title;
    private List<String> datas;
    private MyRecyclerViewAdapter adapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initView();

        initDatas();

        //设置RecyclerView适配器
        adapter = new MyRecyclerViewAdapter(RecyclerViewActivity.this,datas);
        recyclerview.setAdapter(adapter);
        //LayoutManager
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL,false));
        recyclerview.addItemDecoration(new MyDividerItemDecoration());
        //点击监听
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(mContext,"data="+data,Toast.LENGTH_SHORT).show();
            }
        });
        //设置动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());

    }

    private void initDatas() {
        //准备数据集合
        datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("Item_"+i);
        }
    }

    private void initView() {
        mContext = this;
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_list = (Button) findViewById(R.id.btn_list);
        btn_grid = (Button) findViewById(R.id.btn_grid);
        btn_flow = (Button) findViewById(R.id.btn_flow);
        imgbtn_back = (ImageButton) findViewById(R.id.imgbtn_back);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        tv_title = (TextView)findViewById(R.id.tv_title);
        //设置点击事件
        btn_add.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_grid.setOnClickListener(this);
        btn_flow.setOnClickListener(this);
        imgbtn_back.setOnClickListener(this);

        tv_title.setText(mContext.getClass().getSimpleName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.btn_add:
                adapter.addData(0,"New Data");
                recyclerview.scrollToPosition(0);
                break;
            case R.id.btn_delete:
                adapter.removeData(0);
                break;
            case R.id.btn_list:
                //List类型效果
                recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL,false));
                break;
            case R.id.btn_grid:
                //Grid类型效果
                recyclerview.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL,false));
                break;
            case R.id.btn_flow:
                //瀑布流类型效果
                recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                break;
            default:
                break;
        }
    }
}
