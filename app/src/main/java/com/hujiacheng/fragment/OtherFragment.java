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
import com.hujiacheng.fragment.adapter.CommonFrameFragmentAdapter;
import com.hujiacheng.fragment.base.BaseFragment;
import com.hujiacheng.other.jni.JNIActivity;
import com.hujiacheng.other.jni.MtxxActivity;
import com.hujiacheng.other.recordvideo.RecordVideoActivity;

/**
 * Created by hujiacheng on 2017/3/3.
 * 其他页面
 */

public class OtherFragment extends BaseFragment{

    private final static String TAG =  OtherFragment.class.getSimpleName();
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
        Log.e(TAG, "其他Fragment被创建了...");
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
        Log.e(TAG, "其他Fragment被初始化了...");
        datas = new String[]{"JNI之JAVA和C互调","美图秀秀SO库引用","录屏+录音"};
        adapter = new CommonFrameFragmentAdapter(mContext,datas);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String data = datas[position];
                Intent intent = new Intent();
                switch (data){
                    case "JNI之JAVA和C互调":
                        intent.setClass(getActivity(), JNIActivity.class);
                        startActivity(intent);
                        break;
                    case "美图秀秀SO库引用":
                        intent.setClass(getActivity(), MtxxActivity.class);
                        startActivity(intent);
                        break;
                    case "录屏+录音":
                        intent.setClass(getActivity(), RecordVideoActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
