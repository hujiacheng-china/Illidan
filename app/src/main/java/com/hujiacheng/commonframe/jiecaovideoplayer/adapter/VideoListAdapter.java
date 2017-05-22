package com.hujiacheng.commonframe.jiecaovideoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.hujiacheng.MyApplication;
import com.hujiacheng.R;
import com.hujiacheng.commonframe.okhttp.DataBean;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by HujiaCheng on 17/3/18.
 */
public class VideoListAdapter extends BaseAdapter {

    public static final String TAG = "JieCaoVideoPlayer";
    List<DataBean.TrailersBean> datas = MyApplication.dataBeen.getTrailers();

    int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    Context context;
    int pager = -1;
    int pagershow = 6;

    public VideoListAdapter(Context context) {
        this.context = context;
    }

    public VideoListAdapter(Context context, int pager) {
        this.context = context;
        this.pager = pager;
    }

    @Override
    public int getCount() {
        return pager == -1 ? videoIndexs.length : pagershow;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.item_videoview, null);
            viewHolder.jcVideoPlayer = (JCVideoPlayerStandard) convertView.findViewById(R.id.videoplayer);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (pager == -1) {
            if(datas.size() < pagershow) {return convertView;}
            viewHolder.jcVideoPlayer.setUp(
                    datas.get(position).getHightUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    datas.get(position).getMovieName());

            Glide.with(convertView.getContext())
                    .load(datas.get(position).getCoverImg())
                    .centerCrop()
                    .into(viewHolder.jcVideoPlayer.thumbImageView);
        } else {
            if(datas.size() < pager*pagershow) {return convertView;}
            viewHolder.jcVideoPlayer.setUp(
                    datas.get(position+pager*pagershow).getHightUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    datas.get(position+pager*pagershow).getMovieName());

            Glide.with(convertView.getContext())
                    .load(datas.get(position+pager*pagershow).getCoverImg())
                    .centerCrop()
                    .into(viewHolder.jcVideoPlayer.thumbImageView);
        }
        return convertView;
    }

    class ViewHolder {
        JCVideoPlayerStandard jcVideoPlayer;
    }
}
