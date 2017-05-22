package com.hujiacheng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hujiacheng.R;
import com.hujiacheng.custom.dragbubble.DragBubbleActivity;
import com.hujiacheng.custom.setting.SettingsActivity;
import com.hujiacheng.custom.youkumenu.YoukuMenuActivity;
import com.hujiacheng.custom.zoomlist.ZoomListActivity;
import com.hujiacheng.fragment.adapter.CommonFrameFragmentAdapter;
import com.hujiacheng.fragment.base.BaseFragment;

/**
 * Created by hujiacheng on 2017/3/3.
 * 自定义控件页面
 */

public class CustomFragment extends BaseFragment{

    private final static String TAG =  CustomFragment.class.getSimpleName();
    private ListView mListView;
    private String[] datas;
    private CommonFrameFragmentAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected View initView() {
        Log.e(TAG, "自定义控件Fragment被创建了...");
        View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        mListView = (ListView) view.findViewById(R.id.listview_common_frame);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initData() {
        Log.e(TAG, "自定义控件Fragment被初始化了...");
        datas = new String[]{"仿优酷半圆型弹出菜单","设置界面","可拉伸头部LisView","QQ拖拽气泡"};
        adapter = new CommonFrameFragmentAdapter(mContext,datas);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String data = datas[position];
                Intent intent = new Intent();
                switch (data){
                    case "仿优酷半圆型弹出菜单":
                        intent.setClass(getActivity(), YoukuMenuActivity.class);
                        startActivity(intent);
                        break;
                    case "设置界面":
                        intent.setClass(getActivity(), SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case "可拉伸头部LisView":
                        intent.setClass(getActivity(), ZoomListActivity.class);
                        startActivity(intent);
                        break;
                    case "QQ拖拽气泡":
                        intent.setClass(getActivity(), DragBubbleActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
