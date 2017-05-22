package com.hujiacheng.commonframe.swipetoloadlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hujiacheng.R;

import java.util.List;

/**
 * Created by hujiacheng on 2017/3/3.
 * RecyclerView适配器
 */

public class SwipeToLoadRecyclerViewAdapter extends RecyclerView.Adapter<SwipeToLoadRecyclerViewAdapter.MyViewHolder> {

    private List<String> datas;
    private Context context;

    public SwipeToLoadRecyclerViewAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }


    /**
     * 相当于getView方法中创建view和viewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_recyclerview, null);
        return new MyViewHolder(itemView);
    }

    /**
     * 数据和view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String data = datas.get(position);
        holder.tv_title.setText(data);

    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(int position, String data) {
        datas.add(position,data);
        //刷新适配器
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_icon;
        private TextView tv_title;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(view,datas.get(getLayoutPosition()));
                    }
                }
            });
            iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"点击图片="+getLayoutPosition(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 点击监听,得到数据
     */
    public interface OnItemClickListener{
        public void onItemClick(View view, String data);
    }
}
