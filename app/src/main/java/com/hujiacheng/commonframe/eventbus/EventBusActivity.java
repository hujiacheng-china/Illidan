package com.hujiacheng.commonframe.eventbus;

import android.app.Activity;
import android.content.Intent;
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

import static com.hujiacheng.R.id.tv_eventbus_result;

public class EventBusActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(tv_eventbus_result)
    TextView tvEventbusResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        ButterKnife.bind(this);
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(MessageEvent event){
        tvEventbusResult.setText(event.name);
    }

    private void initData() {
        tvTitle.setText("EventBusActivity");
        //注册
        EventBus.getDefault().register(EventBusActivity.this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(EventBusActivity.this);
    }

    @OnClick({R.id.imgbtn_back, R.id.btn_eventbus_send, R.id.btn_eventbus_sticky})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.btn_eventbus_send:
                startActivity(new Intent(EventBusActivity.this,EventBusSendActivity.class));
                break;
            case R.id.btn_eventbus_sticky:
                EventBus.getDefault().postSticky(new StickyEvent("我是粘性事件"));
                startActivity(new Intent(EventBusActivity.this,EventBusSendActivity.class));
                break;
        }
    }
}
