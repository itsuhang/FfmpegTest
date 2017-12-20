#include "ffmpeg.h"
#include "android_log.h"
#include <jni.h>

JNIEXPORT jint JNICALL
Java_com_suhang_ffmpegtest_MainActivity_ffmpegRun(JNIEnv *env, jobject type,
                                                  jobjectArray commands) {
    int argc = (*env)->GetArrayLength(env, commands);
    char *argv[argc];
    int i;
    for (i = 0; i < argc; i++) {
        jstring js = (jstring) (*env)->GetObjectArrayElement(env, commands, i);
        argv[i] = (char *) (*env)->GetStringUTFChars(env, js, 0);
    }
    return jxRun(argc, argv);
}