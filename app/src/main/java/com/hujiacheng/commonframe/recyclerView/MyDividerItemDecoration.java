package com.hujiacheng.commonframe.recyclerView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.hujiacheng.utils.DensityUtil;

/**
 * Created by xiaobing on 2017/3/1.
 */

public class MyDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final static String TAG = "MyDividerItemDecoration";
    private int dividerWith = DensityUtil.dip2px(2);
    private Paint paint;
    private RecyclerView.LayoutManager layoutManager;

    public MyDividerItemDecoration() {
        super();
        initPaint();
        paint.setColor(0xffff0000);
    }

    private void initPaint() {
        if (paint == null) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
        }
    }

    public void setDividerWith(int dividerWith) {
        this.dividerWith = dividerWith;
    }

    public void setDividerColor(int dividercolor) {
        paint.setColor(dividercolor);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (layoutManager == null) {
            layoutManager = parent.getLayoutManager();
        }
        Log.i(TAG,layoutManager.getClass().getSimpleName());
        // 适用 LinearLayoutManager 和 GridLayoutManager
        if (layoutManager instanceof LinearLayoutManager) {
            Log.i(TAG,"LinearLayoutManager");
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                // 水平分割线将绘制在item底部
                outRect.bottom = dividerWith * 2;
            } else if (orientation == LinearLayoutManager.HORIZONTAL) {
                // 垂直分割线将绘制在item右侧
                outRect.right = dividerWith * 2;
            }
            if (layoutManager instanceof GridLayoutManager) {
                Log.i(TAG,"GridLayoutManager");
                GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                // 如果是 GridLayoutManager 则需要绘制另一个方向上的分割线
                if (orientation == LinearLayoutManager.VERTICAL && lp != null && lp.getSpanIndex() > 0) {
                    // 如果列表是垂直方向,则最左边的一列略过
                    outRect.left = dividerWith;
                } else if (orientation == LinearLayoutManager.HORIZONTAL && lp != null && lp.getSpanIndex() > 0) {
                    // 如果列表是水平方向,则最上边的一列略过
                    outRect.top = dividerWith;
                }
            }
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            Log.i(TAG,"StaggeredGridLayoutManager");
            // 如果是 StaggeredGridLayoutManager
            outRect.left = dividerWith;
            outRect.top = dividerWith;
            outRect.right = dividerWith;
            outRect.bottom = dividerWith;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        // 这个值是为了补偿横竖方向上分割线交叉处间隙
        int offSet = dividerWith;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            final int width = child.getWidth();
            final int height = child.getHeight();



            //左右边分隔
            final int top1 = child.getTop() - params.topMargin;
            final int bottom1 = child.getBottom() + params.bottomMargin;
            final int left1 = child.getRight() + params.rightMargin;
            final int right1 = left1 + offSet;
            //绘制分割线(矩形)
//            c.drawRect(left1, top1, right1, bottom1, paint);//右
//            c.drawRect(left1 - width - offSet, top1, right1 - width - offSet, bottom1, paint);//左

            //上下边分隔
            final int left2 = child.getLeft() - params.leftMargin - offSet;
            final int right2 = child.getRight() + params.rightMargin + offSet;
            final int top2 = child.getBottom() + params.bottomMargin;
            final int bottom2 = top2 + offSet;
            //绘制分割线(矩形)
//            c.drawRect(left2, top2, right2, bottom2, paint);//下边
//            c.drawRect(left2, top2 - height - offSet, right2, bottom2 - height - offSet, paint);//上边

//            if(i%2 == 0){
//                c.drawRect(left1, top1, right1, bottom1, paint);//右
//            }
//            c.drawRect(left2, top2, right2, bottom2, paint);//下边
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
