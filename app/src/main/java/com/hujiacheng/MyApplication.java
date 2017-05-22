package com.hujiacheng;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hujiacheng.commonframe.okhttp.DataBean;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by HuJiaCheng on 2017/3/9.
 */

public class MyApplication extends Application {

    public static Context mContext;
    public static List<?> images=new ArrayList<>();
    public static List<String> titles=new ArrayList<>();
    public static DataBean dataBeen;
    //屏幕的高
    public static int H;
    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)

                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);



        //初始化Fresco
        Fresco.initialize(this);

        //初始化ImageLoader
        initImageLoader(this);

        //EventBus mEventBus = EventBus.builder().addIndex(new MyEventBusIndex()).build();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();

        //初始化Banner
        initBanner();
    }

    //初始化ImageLoader
    private void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY -2)               //线程优先级
                .denyCacheImageMultipleSizesInMemory()                  //当同一个uri获取不同大小的图片，缓存到内存时，只缓存一个，默认是缓存多个不同大小的图片
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候URI用MD5
                .tasksProcessingOrder(QueueProcessingType.LIFO)         //设置图片下载和显示的工作队列排序
                .writeDebugLogs()                                       //打印debug log
                .build();

        //全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

    private void initBanner() {
        H = this.getResources().getDisplayMetrics().heightPixels;
        //H=getScreenH(this);
        Fresco.initialize(this);

        //让软件状态还原的框架
//        Recovery.getInstance()
//                .debug(true)
//                .recoverInBackground(false)
//                .recoverStack(true)
//                .mainPage(MainActivity.class)
//                .init(this);


        String[] urls = getResources().getStringArray(R.array.url4);
        String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList(list);
        titles= Arrays.asList(tips);
    }
}
