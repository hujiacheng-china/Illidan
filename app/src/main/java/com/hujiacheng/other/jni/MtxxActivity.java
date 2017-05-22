package com.hujiacheng.other.jni;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hujiacheng.R;
import com.mt.mtxx.image.JNI;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MtxxActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_icon)
    ImageView iv_icon;

    private JNI jni;
    private Bitmap getbitmap;
    private Bitmap bitmap;
    private int[] getpixels;
    private int[] pixels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtxx);
        ButterKnife.bind(this);
        jni = new com.mt.mtxx.image.JNI();
        tvTitle.setText("美图秀秀SO库引用");
        getbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iv_girl);
        getpixels = new int[getbitmap.getWidth()*getbitmap.getHeight()];
        /**
         * 参数
         pixels       接收位图颜色值的数组
         offset      写入到pixels[]中的第一个像素索引值
         stride       pixels[]中的行间距个数值(必须大于等于位图宽度)。可以为负数
         x             从位图中读取的第一个像素的x坐标值。
         y             从位图中读取的第一个像素的y坐标值
         width       从每一行中读取的像素宽度
         height 　　读取的行数
         */
        //getbitmap.getPixels(getpixels,0,getbitmap.getWidth(),0,0,getbitmap.getWidth(),getbitmap.getHeight());
    }

    @OnClick({R.id.imgbtn_back, R.id.btn_main_1, R.id.btn_main_2, R.id.btn_main_3, R.id.btn_main_4,
            R.id.btn_main_5, R.id.btn_main_6, R.id.btn_main_7, R.id.btn_main_8})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.btn_main_1:
                getbitmap.getPixels(getpixels,0,getbitmap.getWidth(),0,0,getbitmap.getWidth(),getbitmap.getHeight());
                //装图片的像数
                pixels = getpixels;
                //把数组传入给C代码处理
                jni.StyleLomoHDR(pixels,getbitmap.getWidth(),getbitmap.getHeight());
                //把处理好的数组重新生成图片
                bitmap =  Bitmap.createBitmap(pixels,getbitmap.getWidth(),getbitmap.getHeight(), Bitmap.Config.RGB_565);
                iv_icon.setImageBitmap(bitmap);
                break;
            case R.id.btn_main_2:
                getbitmap.getPixels(getpixels,0,getbitmap.getWidth(),0,0,getbitmap.getWidth(),getbitmap.getHeight());
                pixels = getpixels;
                jni.StyleLomoC(pixels, getbitmap.getWidth(), getbitmap.getHeight());
                bitmap =  Bitmap.createBitmap(pixels,getbitmap.getWidth(),getbitmap.getHeight(), Bitmap.Config.RGB_565);
                iv_icon.setImageBitmap(bitmap);
                break;
            case R.id.btn_main_3:
                getbitmap.getPixels(getpixels,0,getbitmap.getWidth(),0,0,getbitmap.getWidth(),getbitmap.getHeight());
                pixels = getpixels;
                jni.StyleLomoB(pixels,getbitmap.getWidth(),getbitmap.getHeight());
                bitmap =  Bitmap.createBitmap(pixels,getbitmap.getWidth(),getbitmap.getHeight(), Bitmap.Config.RGB_565);
                iv_icon.setImageBitmap(bitmap);
                break;
            case R.id.btn_main_4:
                iv_icon.setImageResource(R.drawable.iv_girl);
                break;
            case R.id.btn_main_5:
                getbitmap.getPixels(getpixels, 0, getbitmap.getWidth(), 0, 0, getbitmap.getWidth(), getbitmap.getHeight());
                pixels = getpixels;
                jni.StyleJapanese(pixels, getbitmap.getWidth(), getbitmap.getHeight());
                bitmap = Bitmap.createBitmap(pixels, getbitmap.getWidth(), getbitmap.getHeight(), Bitmap.Config.RGB_565);
                iv_icon.setImageBitmap(bitmap);
                break;
            case R.id.btn_main_6:
                getbitmap.getPixels(getpixels, 0, getbitmap.getWidth(), 0, 0, getbitmap.getWidth(), getbitmap.getHeight());
                pixels = getpixels;
                jni.StyleImpression(pixels, getbitmap.getWidth(), getbitmap.getHeight());
                bitmap = Bitmap.createBitmap(pixels, getbitmap.getWidth(), getbitmap.getHeight(), Bitmap.Config.RGB_565);
                iv_icon.setImageBitmap(bitmap);
                break;
            case R.id.btn_main_7:
                getbitmap.getPixels(getpixels, 0, getbitmap.getWidth(), 0, 0, getbitmap.getWidth(), getbitmap.getHeight());
                pixels = getpixels;
                jni.SkinWhite(pixels, getbitmap.getWidth(), getbitmap.getHeight(),10);
                bitmap = Bitmap.createBitmap(pixels, getbitmap.getWidth(), getbitmap.getHeight(), Bitmap.Config.RGB_565);
                iv_icon.setImageBitmap(bitmap);
                break;
            case R.id.btn_main_8:
                getbitmap.getPixels(getpixels, 0, getbitmap.getWidth(), 0, 0, getbitmap.getWidth(), getbitmap.getHeight());
                pixels = getpixels;
                jni.StyleClassic(pixels, getbitmap.getWidth(), getbitmap.getHeight(),0.7d);
                bitmap = Bitmap.createBitmap(pixels, getbitmap.getWidth(), getbitmap.getHeight(), Bitmap.Config.RGB_565);
                iv_icon.setImageBitmap(bitmap);
                break;
        }
    }
}
