package com.hujiacheng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hujiacheng.R;
import com.hujiacheng.commonframe.banner.activity.BannerMainActivity;
import com.hujiacheng.commonframe.butterknife.ButterKnifeActivity;
import com.hujiacheng.commonframe.eventbus.EventBusActivity;
import com.hujiacheng.commonframe.fresco.FrescoActivity;
import com.hujiacheng.commonframe.imageloader.ImageLoaderActivity;
import com.hujiacheng.commonframe.jiecaovideoplayer.activity.JieCaoVideoPlayerMainActivity;
import com.hujiacheng.commonframe.jsonparse.JsonPraseActivity;
import com.hujiacheng.commonframe.okhttp.OkHttpActivity;
import com.hujiacheng.commonframe.recyclerView.RecyclerViewActivity;
import com.hujiacheng.commonframe.swipetoloadlayout.SwipeToLoadRecyclerViewActivity;
import com.hujiacheng.fragment.adapter.CommonFrameFragmentAdapter;
import com.hujiacheng.fragment.base.BaseFragment;
import com.hujiacheng.fragment.base.MyAnimator;

/**
 * Created by hujiacheng on 2017/3/3.
 * 常用框架页面
 */

public class CommonFrameFragment extends BaseFragment{

    private final static String TAG =  CommonFrameFragment.class.getSimpleName();
    private ListView mListView;
    private String[] datas;
    private CommonFrameFragmentAdapter adapter;
    private LinearLayout slidelayout;
    private ImageView iv_contant;
    private boolean show;
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
        Log.e(TAG, "常用框架Fragment被创建了...");
        View view = View.inflate(mContext, R.layout.fragment_common_frame,null);
        mListView = (ListView) view.findViewById(R.id.listview_common_frame);
        slidelayout = (LinearLayout) view.findViewById(R.id.slidelayout);
        iv_contant = (ImageView) view.findViewById(R.id.iv_contant);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initData() {
        Log.e(TAG, "常用框架Fragment被初始化了...");
        datas = new String[]{"OkHttp","JsonPrase","ButterKnife","EventBus","ImageLoader"
                ,"Fresco","RecyclerView","SwipeToLoadLayout","JieCaoVideoPlayer+Danmaku","Banner","More"};
        adapter = new CommonFrameFragmentAdapter(mContext,datas);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String data = datas[position];
                Intent intent = new Intent();
                switch (data){
                    case "OkHttp":
                        intent.setClass(getActivity(), OkHttpActivity.class);
                        startActivity(intent);
                        break;
                    case "JsonPrase":
                        intent.setClass(getActivity(), JsonPraseActivity.class);
                        startActivity(intent);
                        break;
                    case "ButterKnife":
                        intent.setClass(getActivity(), ButterKnifeActivity.class);
                        startActivity(intent);
                        break;
                    case "EventBus":
                        intent.setClass(getActivity(), EventBusActivity.class);
                        startActivity(intent);
                        break;
                    case "ImageLoader":
                        intent.setClass(getActivity(), ImageLoaderActivity.class);
                        startActivity(intent);
                        break;
                    case "Fresco":
                        intent.setClass(getActivity(), FrescoActivity.class);
                        startActivity(intent);
                        break;
                    case "RecyclerView":
                        intent.setClass(getActivity(), RecyclerViewActivity.class);
                        startActivity(intent);
                        break;
                    case "SwipeToLoadLayout":
                        intent.setClass(getActivity(), SwipeToLoadRecyclerViewActivity.class);
                        startActivity(intent);
                        break;
                    case "JieCaoVideoPlayer+Danmaku":
                        intent.setClass(getActivity(), JieCaoVideoPlayerMainActivity.class);
                        startActivity(intent);
                        break;
                    case "Banner":
                        intent.setClass(getActivity(), BannerMainActivity.class);
                        startActivity(intent);
                        break;
                    case "More":
                        if(show){
                            show = false;
                            MyAnimator.slideDown(slidelayout,iv_contant);
                        }else {
                            show = true;
                            MyAnimator.slideUp(slidelayout,iv_contant);
                        }
                        break;
                    default:
                        break;

                }
            }
        });
    }
}
