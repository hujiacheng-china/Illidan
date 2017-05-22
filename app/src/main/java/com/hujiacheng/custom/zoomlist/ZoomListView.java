package com.hujiacheng.custom.zoomlist;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.hujiacheng.utils.DensityUtil;

/**
 * Created by HuJiaCheng on 2017/3/14.
 */

public class ZoomListView extends ListView{
    private int mOriIvHeight = 0;//图片的初始高度
    private ImageView mImageView;
    private ProgressBar progressBar;
    private Context context;
    public void setImageView(ImageView mImageView) {
        this.mImageView = mImageView;
    }

    public ZoomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ZoomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    public ZoomListView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(mOriIvHeight == 0)
            mOriIvHeight = mImageView.getHeight();
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        /**
         * deltaY:垂直方向滑动过度偏移量
         * -:下拉过度
         * +:上拉过度
         */
        if(deltaY < 0){
            if(mImageView.getHeight() < (mOriIvHeight+ DensityUtil.dip2px(context,150))){
                mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
                mImageView.requestLayout();
            }
        }else{
            if(mImageView.getHeight() > mOriIvHeight){
                mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
                mImageView.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        View headerView = (View) mImageView.getParent();
        int top = headerView.getTop();
        if(top < 0 && mImageView.getHeight() > mOriIvHeight){
            mImageView.getLayoutParams().height = mImageView.getHeight() + top;
            headerView.layout(headerView.getLeft(),0,headerView.getRight(),headerView.getHeight());
            mImageView.requestLayout();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_UP){
            if(mImageView.getHeight() != mOriIvHeight){
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ResetAnimation anim = new ResetAnimation(mImageView,mOriIvHeight);
                        anim.setDuration(300);
                        mImageView.startAnimation(anim);
                    }
                },2000);

            }
        }
        return super.onTouchEvent(ev);
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    private class ResetAnimation extends Animation{

        ImageView imageView;
        int curHeight;
        int extraHeight;

        public ResetAnimation(ImageView imageView, int oriHeight) {
            this.imageView = imageView;
            this.curHeight = imageView.getHeight();
            extraHeight = curHeight - oriHeight;
            this.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            imageView.getLayoutParams().height = (int) (curHeight - extraHeight * interpolatedTime);
            imageView.requestLayout();
            super.applyTransformation(interpolatedTime,t);
        }


    }
}
