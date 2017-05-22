package com.hujiacheng.commonframe.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hujiacheng.R;
import com.hujiacheng.commonframe.eventbus.event.MessageEvent;
import com.hujiacheng.commonframe.eventbus.event.StickyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusSendActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_show)
    TextView btnShow;
    boolean isFirstFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_send);
        ButterKnife.bind(this);
        tvTitle.setText("EventBusSendActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(EventBusSendActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void stickyEventBus(StickyEvent stickyEvent) {
        btnShow.setText(stickyEvent.msg);
    }

    @OnClick({R.id.imgbtn_back, R.id.btn_send, R.id.btn_received})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.btn_send:
                EventBus.getDefault().post(new MessageEvent("我是主线程发送过来的数据，哈哈哈哈"));
                finish();
                break;
            case R.id.btn_received:
                //注册
                if (isFirstFlag){
                    isFirstFlag = false;
                    EventBus.getDefault().register(EventBusSendActivity.this);
                }
                break;
        }
    }
}
