package com.hujiacheng.custom.youkumenu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hujiacheng.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HuJiaCheng on 2017/3/13.
 */

public class MySpinnerAdapter extends BaseAdapter {
    ArrayList<String> arrayList;
    Context context;

    public MySpinnerAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_spinner, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tvContent.setText(arrayList.get(position));
        viewHolder.ivDelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.iv_delect)
        ImageView ivDelect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
