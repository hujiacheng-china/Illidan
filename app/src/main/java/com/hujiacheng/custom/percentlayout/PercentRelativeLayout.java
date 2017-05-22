package com.hujiacheng.custom.percentlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hujiacheng.R;

/**
 * Created by HJC on 2017/2/24.
 */

public class PercentRelativeLayout  extends RelativeLayout{

    public PercentRelativeLayout(Context context) {
        super(context);
    }
    public PercentRelativeLayout(Context context, AttributeSet attrs) {super(context, attrs);}
    public PercentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new PercentLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        float widthPercent = 0;
        float heightPercent = 0;
        for(int i = 0;i < getChildCount();i++) {
            View childView = getChildAt(i);
            ViewGroup.LayoutParams params = childView.getLayoutParams();
            if(params instanceof PercentLayoutParams){
                widthPercent = ((PercentLayoutParams) params).getPercentWidth();
                heightPercent = ((PercentLayoutParams) params).getPercentHeight();
            }
            if(widthPercent > 0 && widthPercent <=1){
                params.width = (int) (width * widthPercent);
            }
            if(heightPercent > 0 && heightPercent <=1){
                params.height = (int) (height * widthPercent);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public static class PercentLayoutParams extends LayoutParams{

        private float percentWidth;
        private float percentHeight;

        public PercentLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.percentLayout);
            percentWidth = array.getFloat(R.styleable.percentLayout_percent_width, 0);
            percentHeight = array.getFloat(R.styleable.percentLayout_percent_height, 0);
        }

        public float getPercentWidth() {
            return percentWidth;
        }

        public void setPercentWidth(float percentWidth) {
            this.percentWidth = percentWidth;
        }

        public float getPercentHeight() {
            return percentHeight;
        }

        public void setPercentHeight(float percentHeight) {
            this.percentHeight = percentHeight;
        }

        public PercentLayoutParams(int w, int h) {super(w, h);}

        public PercentLayoutParams(ViewGroup.LayoutParams source) {super(source);}

        public PercentLayoutParams(MarginLayoutParams source) {super(source);}
    }
}
