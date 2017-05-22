package com.hujiacheng.commonframe.jiecaovideoplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hujiacheng.MyApplication;
import com.hujiacheng.R;
import com.hujiacheng.commonframe.okhttp.DataBean;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class RecyclerViewVideoAdapter extends RecyclerView.Adapter<RecyclerViewVideoAdapter.MyViewHolder> {

    int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private Context context;
    public static final String TAG = "RecyclerViewVideoAdapter";
    List<DataBean.TrailersBean> datas = MyApplication.dataBeen.getTrailers();

    public RecyclerViewVideoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_videoview, parent,
                false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(datas == null){return;}
        //设置播放地址和标题
        holder.jcVideoPlayer.setUp(
                datas.get(position).getHightUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                datas.get(position).getMovieName());

        //设置封面
        Glide.with(holder.jcVideoPlayer.getContext())
                .load(datas.get(position).getCoverImg())
                .centerCrop()
                .into(holder.jcVideoPlayer.thumbImageView);
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JCVideoPlayerStandard jcVideoPlayer;

        public MyViewHolder(View itemView) {
            super(itemView);
            jcVideoPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        }
    }

}
