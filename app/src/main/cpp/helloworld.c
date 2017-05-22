//
// Created by HuJiaCheng on 2017/3/15.
//
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <sys/inotify.h>
#include <unistd.h>
#include <jni.h>
#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)



/**
 * 把一个jstring转换成一个c语言的char* 类型.
 */
char* _JString2CStr(JNIEnv* env, jstring jstr) {
    char* rtn = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env,"GB2312");
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid, strencode); // String .getByte("GB2312");
    jsize alen = (*env)->GetArrayLength(env, barr);
    jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if(alen > 0) {
        rtn = (char*)malloc(alen+1); //"\0"
        memcpy(rtn, ba, alen);
        rtn[alen]=0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba,0);
    return rtn;
};

jstring Java_com_hujiacheng_other_jni_JNI_sayHello(JNIEnv* env,jobject job){
    //jstring     (*NewStringUTF)(JNIEnv*, const char*);
    char* text = "I am from c";
    LOGD("fromJava===%s\n",text);
    return (*env)->NewStringUTF(env,text);
};

jint Java_com_hujiacheng_other_jni_JNI_add(JNIEnv *env, jobject job, jint x, jint y){
    int result = x + y;
    return result;
};


jintArray Java_com_hujiacheng_other_jni_JNI_increaseArrayEles(JNIEnv *env, jobject job, jintArray jarray){
    //1.得到数组的长度
    //jsize       (*GetArrayLength)(JNIEnv*, jarray);
    jsize size = (*env)->GetArrayLength(env,jarray);
    //2.得到数组元素
    //jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);
    jint* intArray = (*env)->GetIntArrayElements(env,jarray,JNI_FALSE);
    //3.遍历数组，给每个元素加上10
    int i;
    for(i =0;i<size;i++){
        *(intArray+i) +=  10;
    }
    //4.返回结果
    return  jarray;
};

jint Java_com_hujiacheng_other_jni_JNI_checkPwd(JNIEnv *env, jobject job, jstring jstr){
    //服务器的密码是123456
    char* origin = "123456";
    char* fromUser = _JString2CStr(env,jstr);
    //函数比较字符串是否相同
    int code =  strcmp(origin,fromUser);
    LOGD("code===%d\n",code);
    return code == 0 ? 200 : 400;
};

JNIEXPORT void JNICALL
Java_com_hujiacheng_other_jni_JNI_callbackAdd(JNIEnv *env, jobject instance) {
    //1.得到字节码
    //jclass      (*FindClass)(JNIEnv*, const char*);
    jclass jclazz = (*env)->FindClass(env,"com/hujiacheng/other/jni/JNI");
    //2.得到方法
    //jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
    //最后一个参数是方法签名
    jmethodID jmethodIDs= (*env)->GetMethodID(env,jclazz,"addxy","(II)I");
    //3.实例化该类
    // jobject     (*AllocObject)(JNIEnv*, jclass);
    jobject  jobject =(*env)->AllocObject(env,jclazz);
    //4.调用方法
    //jint        (*CallIntMethod)(JNIEnv*, jobject, jmethodID, ...);
    jint value =  (*env)->CallIntMethod(env,jobject,jmethodIDs,99,1);
    //成功调用了public int addxy(int x, int y)
    LOGE("value===%d\n",value);

};
//调用java无参方法
JNIEXPORT void JNICALL
Java_com_hujiacheng_other_jni_JNI_callbackHelloFromJava(JNIEnv *env, jobject instance) {
    //    jclass      (*FindClass)(JNIEnv*, const char*);
    jclass jclazz = (*env)->FindClass(env,"com/hujiacheng/other/jni/JNI");
    //    jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
    jmethodID jmethodIDs = (*env)->GetMethodID(env,jclazz,"helloFromJava","()V");
    //    jobject     (*AllocObject)(JNIEnv*, jclass);
    jobject jobjects = (*env)->AllocObject(env,jclazz);
    //void (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
    (*env)->CallVoidMethod(env,jobjects,jmethodIDs);
};
//调用java带参方法
JNIEXPORT void JNICALL
Java_com_hujiacheng_other_jni_JNI_callbackPrintString(JNIEnv *env, jobject instance) {
    jclass jclazz = (*env)->FindClass(env,"com/hujiacheng/other/jni/JNI");
    jmethodID jmethodIDs = (*env)->GetMethodID(env,jclazz,"printString","(Ljava/lang/String;)V");
    jobject jobjects = (*env)->AllocObject(env,jclazz);
    jstring jst = (*env)->NewStringUTF(env,"hehe");
    (*env)->CallVoidMethod(env,jobjects,jmethodIDs,jst);
};
//调用java静态方法
JNIEXPORT void JNICALL
Java_com_hujiacheng_other_jni_JNI_callbackSayHello(JNIEnv *env, jobject instance) {
    jclass jclazz = (*env)->FindClass(env,"com/hujiacheng/other/jni/JNI");
    jmethodID jmethodIDs = (*env)->GetStaticMethodID(env,jclazz,"sayHello","(Ljava/lang/String;)V");
    jstring str = (*env)->NewStringUTF(env,"static");
    (*env)->CallStaticVoidMethod(env,jclazz,jmethodIDs,str);
};
//调用java刷新UI
JNIEXPORT void JNICALL
Java_com_hujiacheng_other_jni_JNIActivity_show(JNIEnv *env, jobject instance) {
    jclass jclazz = (*env)->FindClass(env,"com/hujiacheng/other/jni/JNIActivity");
    jmethodID jmethodIDs = (*env)->GetMethodID(env,jclazz,"showToast","()V");
    (*env)->CallVoidMethod(env,instance,jmethodIDs);
}
JNIEXPORT void JNICALL
Java_com_hujiacheng_other_jni_JNI_uninstall(JNIEnv *env, jobject instance, jstring packName,
                                            jint sdkVersion) {

    // 1，将传递过来的java的包名转为c的字符串
    char * pd = _JString2CStr(env, packName);

    // 2，创建当前进程的克隆进程
    pid_t pid = fork();

    // 3，根据返回值的不同做不同的操作,<0,>0,=0
    if (pid < 0) {
        // 说明克隆进程失败
        LOGE("current crate process failure");
    } else if (pid > 0) {
        // 说明克隆进程成功，而且该代码运行在父进程中
        LOGE("crate process success,current parent pid = %d", pid);
    } else {
        // 说明克隆进程成功，而且代码运行在子进程中
        LOGE("crate process success,current child pid = %d", pid);

        // 4，在子进程中监视/data/data/包名这个目录
        //初始化inotify进程
        int fd = inotify_init();
        if (fd < 0) {
            LOGE("inotify_init failed !!!");
            exit(1);
        }

        //添加inotify监听器
        int wd = inotify_add_watch(fd, pd, IN_DELETE);
        if (wd < 0) {
            LOGE("inotify_add_watch failed !!!");
            exit(1);
        }

        //分配缓存，以便读取event，缓存大小=一个struct inotify_event的大小，这样一次处理一个event
        void *p_buf = malloc(sizeof(struct inotify_event));
        if (p_buf == NULL) {
            LOGE("malloc failed !!!");
            exit(1);
        }

        //开始监听
        LOGE("start observer");
        ssize_t readBytes = read(fd, p_buf,sizeof(struct inotify_event));

        //read会阻塞进程，走到这里说明收到目录被删除的事件，注销监听器
        free(p_buf);
        inotify_rm_watch(fd, IN_DELETE);

        // 应用被卸载了，通知系统打开用户反馈的网页
        LOGE("app uninstall,current sdkversion = %d", sdkVersion);
        if (sdkVersion >= 17) {
            // Android4.2系统之后支持多用户操作，所以得指定用户
            execlp("am", "am", "start", "--user", "0", "-a",
                   "android.intent.action.VIEW", "-d", "http://www.baidu.com",
                   (char*) NULL);
        } else {
            // Android4.2以前的版本无需指定用户
            execlp("am", "am", "start", "-a", "android.intent.action.VIEW",
                   "-d", "http://www.baidu.com", (char*) NULL);
        }
    }

}