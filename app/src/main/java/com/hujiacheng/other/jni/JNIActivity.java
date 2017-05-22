package com.hujiacheng.other.jni;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.hujiacheng.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JNIActivity extends Activity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_jni)
    TextView tvJni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        ButterKnife.bind(this);
        initJNI();
    }

    private void initJNI() {
        tvTitle.setText("JNIActivity");
        JNI jni = new JNI();
        String show = "";
        show += "sayhello=" + jni.sayHello() + "\n";
        show += "add(10,6)=" + jni.add(10, 6) + "\n";
        int[] data = {1, 2, 3, 4, 5};
        int[] result = jni.increaseArrayEles(data);
        for (int i = 0; i < result.length; i++) {
            show += "result[" + i + "]=" + result[i] + "\n";
        }
        show += "checkpwd(123456)=" + jni.checkPwd("123456") + "\n";
        show += "checkpwd(1234567)=" + jni.checkPwd("1234567") + "\n";

        jni.callbackAdd();
        jni.callbackHelloFromJava();
        jni.callbackPrintString();
        jni.callbackSayHello();
        show();

        String packageDir = "/data/data/" + getPackageName();
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        //jni.uninstall(packageDir, sdkVersion);

        tvJni.setText(show);
    }

    public native void show();

    public void showToast() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(JNIActivity.this, "Toast.....", Toast.LENGTH_SHORT).show();
            }
        }, 5000);
    }

    @OnClick(R.id.imgbtn_back)
    public void onClick() {
        finish();
    }
}
