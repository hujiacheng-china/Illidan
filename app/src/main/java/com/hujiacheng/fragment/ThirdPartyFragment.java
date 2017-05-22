package com.hujiacheng.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hujiacheng.fragment.base.BaseFragment;

/**
 * Created by hujiacheng on 2017/3/3.
 * 第三方控件页面
 */

public class ThirdPartyFragment extends BaseFragment{

    private final static String TAG =  ThirdPartyFragment.class.getSimpleName();
    private TextView textView;
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
        Log.e(TAG, "第三方控件Fragment被创建了...");
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initData() {
        Log.e(TAG, "第三方控件Fragment被初始化了...");
        textView.setText("第三方控件页面");
        super.initData();
    }
}
