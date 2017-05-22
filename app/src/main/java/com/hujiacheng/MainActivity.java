package com.hujiacheng;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hujiacheng.fragment.CommonFrameFragment;
import com.hujiacheng.fragment.CustomFragment;
import com.hujiacheng.fragment.OtherFragment;
import com.hujiacheng.fragment.ThirdPartyFragment;
import com.hujiacheng.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {

    private RadioGroup rg_main;
    private List<BaseFragment> mBaseFragment;
    private int position;//Fragment标记
    private Fragment mContent;//上次切换的Fragment
    private TextView tv_title;
    private ImageButton imgbtn_back;
    private Context mContext;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();
        setListener();
    }

    private void setListener() {
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选择常用框架
        rg_main.check(R.id.rb_common_frame);
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new CommonFrameFragment());
        mBaseFragment.add(new ThirdPartyFragment());
        mBaseFragment.add(new CustomFragment());
        mBaseFragment.add(new OtherFragment());
    }

    private void initView() {
        //RadioGroup
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        tv_title = (TextView) findViewById(R.id.tv_title);
        imgbtn_back = (ImageButton) findViewById(R.id.imgbtn_back);
        imgbtn_back.setVisibility(View.GONE);
        tv_title.setText("");
        mContext = this;
    }

    private BaseFragment getFragment() {
        BaseFragment baseFragment = mBaseFragment.get(position);
        return baseFragment;
    }

    /**
     * @param from 刚才显示的Fragment马上被替换
     * @param to   即将显示的Fragment
     */
    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            mContent = to;
            //才切换
            //判断有没有被添加
            if (!to.isAdded()) {
                //to 没有被添加
                //from 隐藏
                if (from != null)
                    ft.hide(from);
                //添加 to
                if (to != null)
                    ft.add(R.id.fl_content, to).commit();
            } else {
                //to 已经被添加
                //from 隐藏
                if (from != null)
                    ft.hide(from);
                //显示 to
                if (to != null)
                    ft.show(to).commit();
            }
        }
    }

    @OnClick({R.id.rb_common_frame, R.id.rb_thirdparty, R.id.rb_custom, R.id.rb_other})
    public void onClick(View view) {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.rb_anim);
        switch (view.getId()) {
            case R.id.rb_common_frame:
                view.startAnimation(animation);
                break;
            case R.id.rb_thirdparty:
                view.startAnimation(animation);
                break;
            case R.id.rb_custom:
                view.startAnimation(animation);
                break;
            case R.id.rb_other:
                view.startAnimation(animation);
                break;
        }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedid) {
            switch (checkedid) {
                case R.id.rb_common_frame:
                    position = 0;
                    tv_title.setText("常用框架");
                    break;
                case R.id.rb_thirdparty:
                    position = 1;
                    tv_title.setText("第三方");
                    break;
                case R.id.rb_custom:
                    position = 2;
                    tv_title.setText("自定义控件");
                    break;
                case R.id.rb_other:
                    position = 3;
                    tv_title.setText("其他");
                    break;
                default:
                    position = 0;
                    tv_title.setText("常用框架");
                    break;
            }
            //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFragment(mContent, to);
        }

    }
}
