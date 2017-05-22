package com.hujiacheng.commonframe.jiecaovideoplayer.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
public class ListViewMultiHolderActivity extends AppCompatActivity {
    ListView listView;
    VideoListAdapter mAdapter;
    List<DataBean.TrailersBean> datas = MyApplication.dataBeen.getTrailers();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("MultiHolderListView");


        listView = (ListView) findViewById(R.id.listview);
        mAdapter = new VideoListAdapter(this);
        listView.setAdapter(mAdapter);
    }


    public class VideoListAdapter extends BaseAdapter {

        int[] viewtype = {1, 0, 1, 1, 0, 1, 0, 1, 0, 0};//1 = jcvd, 0 = textView

        Context context;
        LayoutInflater mInflater;

        public VideoListAdapter(Context context) {
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return viewtype.length;
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
            //This is the point
            if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof VideoHolder) {
                ((VideoHolder) convertView.getTag()).jcVideoPlayer.release();
            }
            if (getItemViewType(position) == 1 && datas != null && datas.size() > viewtype.length) {
                VideoHolder viewHolder;
                if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof VideoHolder) {
                    viewHolder = (VideoHolder) convertView.getTag();
                } else {
                    viewHolder = new VideoHolder();
                    convertView = mInflater.inflate(R.layout.item_videoview, null);
                    viewHolder.jcVideoPlayer = (JCVideoPlayerStandard) convertView.findViewById(R.id.videoplayer);
                    convertView.setTag(viewHolder);
                }

                viewHolder.jcVideoPlayer.setUp(
                        datas.get(position).getHightUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        datas.get(position).getMovieName());

                Glide.with(ListViewMultiHolderActivity.this)
                        .load(datas.get(position).getCoverImg())
                        .centerCrop()
                        .into(viewHolder.jcVideoPlayer.thumbImageView);
            } else {
                TextViewHolder textViewHolder;
                if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof TextViewHolder) {
                    textViewHolder = (TextViewHolder) convertView.getTag();
                } else {
                    textViewHolder = new TextViewHolder();
                    LayoutInflater mInflater = LayoutInflater.from(context);
                    convertView = mInflater.inflate(R.layout.item_textview, null);
                    textViewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
                    convertView.setTag(textViewHolder);
                }
            }
            return convertView;
        }

        /**
         * 根据位置得到对应的类型
         * @param position
         * @return
         */
        @Override
        public int getItemViewType(int position) {
            return viewtype[position];
        }

        /**
         * 得到类型的总数
         * @return
         */
        @Override
        public int getViewTypeCount() {
            return 2;
        }

        class VideoHolder {
            JCVideoPlayerStandard jcVideoPlayer;
        }

        class TextViewHolder {
            TextView textView;
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
