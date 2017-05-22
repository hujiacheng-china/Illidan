package com.hujiacheng.utils;

import android.content.Context;

import com.hujiacheng.MyApplication;

/**
 * Created by HuJiaCheng on 2017/3/13.
 */

public class DensityUtil {
    /**
     * 根据手机分辨率从dip转化为px（像素）
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
    public static int dip2px(float dpValue){
        final float scale = MyApplication.mContext.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
    /**
     * 根据手机分辨率从px（像素）转化为dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
    public static int px2dip(float pxValue){
        final float scale = MyApplication.mContext.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
}
