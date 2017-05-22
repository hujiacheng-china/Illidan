package com.hujiacheng.commonframe.okhttp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hujiacheng.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HuJiaCheng on 2017/3/6.
 */
public class Myadapter extends BaseAdapter {

    Context context;
    List<DataBean.TrailersBean> list;
    ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.drawable.ic_thirdparty)//下载时显示的图片
            .showImageForEmptyUri(R.drawable.ic_custom)//设置图片uri为空显示的图片
            .showImageOnFail(R.drawable.ic_other)//设置加载或解码过程中发生错误显示的图片
            .resetViewBeforeLoading(true)//设置图片下载前是否会重置复位
            .cacheInMemory(true)    //设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)      //设置下载的图片是否缓存在sd卡中
            .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示
            .bitmapConfig(Bitmap.Config.RGB_565)     //图片的解码类型
            .displayer(new RoundedBitmapDisplayer(20))//设置为圆角图片
//            .displayer(new FadeInBitmapDisplayer(300))//设置渐变显示图片
            .build();       //创建配置过的DisplayImageOptions对象

    public Myadapter(Context context, List<DataBean.TrailersBean> list) {
        this.list = list;
        this.context = context;
        }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //获取或创建ViewHolder
        Viewholder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_recyclerview, null);
            holder = new Viewholder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
        }
        //获取当前item数据

        //显示数据
        imageLoader.displayImage(list.get(position).getCoverImg(),holder.ivIcon,options);
        holder.tvTitle.setText(list.get(position).getMovieName());
        return convertView;
    }


    class Viewholder {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_title)
        TextView tvTitle;

        Viewholder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
