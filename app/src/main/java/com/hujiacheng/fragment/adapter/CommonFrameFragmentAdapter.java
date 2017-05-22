package com.hujiacheng.fragment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by HuJiaCheng on 2017/3/4.
 */

public class CommonFrameFragmentAdapter extends BaseAdapter{

    private Context mContext;
    private String[] datas;

    public CommonFrameFragmentAdapter(Context context, String[] datas){
        this.mContext = context;
        this.datas = datas;
    }
    
    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(mContext);
        textView.setPadding(40,40,0,40);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        textView.setText(datas[position]);
        return textView;
    }
}
