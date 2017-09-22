//
// Created by cx on 2017/9/19.
//

#include <stdio.h>
#include <stdlib.h>
#include <jni.h>

/*
*  env:结构体二级指针，该结构体中封装了大量的函数指针，
*       可以帮助程序员实现某些常用功能
*  thiz:本地方法调用者的对象(MainActivity的对象)
*/
jstring Java_com_cx_jnidemo_MainActivity_helloWorld(JNIEnv* env,jobject thiz){

    char* cstr="hello world!";
    //把c字符串 转换成java字符串
    jstring jstr =(*env)->NewStringUTF(env,cstr);
    return jstr;
}