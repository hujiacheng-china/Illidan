package com.hujiacheng.other.jni;

import android.util.Log;

/**
 * Created by HuJiaCheng on 2017/3/14.
 */

public class JNI {
    static {
        System.loadLibrary("helloworld");
    }
    public native String sayHello();
    public native int add(int x,int y);
    public native int[] increaseArrayEles(int[] intarray);
    public native int checkPwd(String pwd);
    /**
     * 当执行这个方法的时候，让C代码调用
     * public int add(int x, int y)
     */
    public native void callbackAdd();

    /**
     * 当执行这个方法的时候，让C代码调用
     * public void helloFromJava()
     */
    public native void callbackHelloFromJava();


    /**
     * 当执行这个方法的时候，让C代码调用void printString(String s)
     */
    public native void callbackPrintString();

    /**
     * 当执行这个方法的时候，让C代码静态方法 static void sayHello(String s)
     */
    public native void callbackSayHello();

    public native void uninstall(String packName,int sdkVersion);



    public int addxy(int x, int y) {
        Log.e("TAG", "add() x=" + x + " y=" + y);
        return x + y;
    }

    public void helloFromJava() {
        Log.e("TAG", "helloFromJava()");
    }

    public void printString(String s) {
        Log.e("TAG","C中输入的：" + s);
    }

    public static void sayHello(String s){
        Log.e("TAG",  "我是java代码中的JNI."
                + "java中的sayHello(String s)静态方法，我被C调用了:"+ s);
    }

}
