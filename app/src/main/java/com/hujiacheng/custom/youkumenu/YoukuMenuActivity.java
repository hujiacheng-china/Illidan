package com.hujiacheng.custom.youkumenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hujiacheng.R;
import com.hujiacheng.utils.DensityUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YoukuMenuActivity extends Activity {

    @Bind(R.id.rl_lv3)
    RelativeLayout rlLv3;
    @Bind(R.id.rl_lv2)
    RelativeLayout rlLv2;
    @Bind(R.id.rl_lv1)
    RelativeLayout rlLv1;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_menu_main)
    ImageView ivMenuMain;
    @Bind(R.id.iv_home)
    ImageView ivHome;
    @Bind(R.id.iv_menu)
    ImageView ivMenu;
    @Bind(R.id.et_input)
    EditText etInput;
    boolean isShowLv1 = true;
    boolean isShowLv2 = true;
    boolean isShowLv3 = true;
    boolean isFirstShow = true;
    private PopupWindow popupWindow;
    private ListView listView;
    private ArrayList<String> message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youku_menu);
        ButterKnife.bind(this);
        initData();
        hideAll();
    }


    private void initData() {
        tvTitle.setText("仿优酷菜单效果");
        ivMenu.setVisibility(View.VISIBLE);
        message = new ArrayList();
        for (int i = 0; i < 20; i++) {
            message.add("item: "+i);
        }
        MySpinnerAdapter adapter = new MySpinnerAdapter(this,message);
        listView = new ListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                etInput.setText(message.get(position));
                if(popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
    }

    private void hideAll() {
        rlLv1.setVisibility(View.INVISIBLE);
        rlLv2.setVisibility(View.INVISIBLE);
        rlLv3.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.iv_show,R.id.imgbtn_back, R.id.iv_menu, R.id.iv_menu_main, R.id.iv_home})
    public void onClick(View view) {
        if(isFirstShow){
            isFirstShow = false;
            if (isShowLv1) {
                isShowLv1 = false;
                Tools.hideView(rlLv1, 0, 0);
            }
            if (isShowLv2) {
                isShowLv2 = false;
                Tools.hideView(rlLv2, 0, 0);
                if (isShowLv3) {
                    isShowLv3 = false;
                    Tools.hideView(rlLv3, 0, 0);
                }
            } else {
                isShowLv2 = true;
                Tools.showView(rlLv2, 0, 0);
                if (!isShowLv3) {
                    isShowLv3 = true;
                    Tools.showView(rlLv3, 0, 0);
                }
            }
            rlLv1.setVisibility(View.VISIBLE);
            rlLv2.setVisibility(View.VISIBLE);
            rlLv3.setVisibility(View.VISIBLE);
        }
        switch (view.getId()) {
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.iv_menu_main:
                if (isShowLv3) {
                    isShowLv3 = false;
                    Tools.hideView(rlLv3, 0);
                } else {
                    isShowLv3 = true;
                    Tools.showView(rlLv3, 0);
                }
                break;
            case R.id.iv_home:
                if (isShowLv2) {
                    isShowLv2 = false;
                    Tools.hideView(rlLv2, 0);
                    if (isShowLv3) {
                        isShowLv3 = false;
                        Tools.hideView(rlLv3, 150);
                    }
                } else {
                    isShowLv2 = true;
                    Tools.showView(rlLv2, 0);
                    if (!isShowLv3) {
                        isShowLv3 = true;
                        Tools.showView(rlLv3, 150);
                    }
                }
                break;
            case R.id.iv_menu:
                if (isShowLv1) {
                    isShowLv1 = false;
                    Tools.hideView(rlLv1, 0);
                    if (isShowLv2) {
                        isShowLv2 = false;
                        Tools.hideView(rlLv2, 100);
                        if (isShowLv3) {
                            isShowLv3 = false;
                            Tools.hideView(rlLv3, 200);
                        }
                    }
                } else {
                    isShowLv1 = true;
                    Tools.showView(rlLv1, 0);
                    if (!isShowLv2) {
                        isShowLv2 = true;
                        Tools.showView(rlLv2, 100);
                        if (!isShowLv3) {
                            isShowLv3 = true;
                            Tools.showView(rlLv3, 200);
                        }
                    }
                }
                break;
            case R.id.iv_show:
                if (isShowLv1) {
                    isShowLv1 = false;
                    Tools.hideView(rlLv1, 0);
                    if (isShowLv2) {
                        isShowLv2 = false;
                        Tools.hideView(rlLv2, 100);
                        if (isShowLv3) {
                            isShowLv3 = false;
                            Tools.hideView(rlLv3, 200);
                        }
                    }
                } else {
                    isShowLv1 = true;
                    Tools.showView(rlLv1, 0);
                }
                break;
        }
    }

    @OnClick(R.id.iv_down_arrow)
    public void onClick() {
        if(popupWindow == null){
            popupWindow = new PopupWindow(this);
            popupWindow.setWidth(etInput.getWidth());
            popupWindow.setHeight(DensityUtil.dip2px(this,200));
            popupWindow.setContentView(listView);
            popupWindow.setFocusable(true);
        }
        popupWindow.showAsDropDown(etInput,0,0);
    }
}
