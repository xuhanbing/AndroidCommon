#include <jni.h>
#include <stdio.h>
#include <string.h>

#ifdef __cplusplus
extern "C"
{
#endif

jstring native_print(JNIEnv * env, jclass clazz, jstring string)
{
	jsize size = env->GetStringUTFLength(string);
		char buffer[1024];


		env->GetStringUTFRegion(string, 0, size, buffer);


		char data[1024];
	//	strcat(data, buffer);
		sprintf(data, "Hello from JNI:%s\n", buffer);

		printf("%s", data);

		jstring result = env->NewStringUTF(data);

		jmethodID callbackMethodId = env->GetStaticMethodID(clazz, "onResult", "(Ljava/lang/String;)V");

		env->CallStaticVoidMethod(clazz, callbackMethodId, result);

		return result;
}

jstring native_doinbackground
  (JNIEnv * env, jclass clazz, jobject jobject)
{
	jclass subClazz = env->GetObjectClass(jobject);
	jmethodID callbackMethodId = env->GetMethodID(subClazz, "onResult", "(Ljava/lang/String;)V");

	env->CallVoidMethod(jobject, callbackMethodId, env->NewStringUTF("This is a callback from JNI"));

	return env->NewStringUTF("doInBackground");
}

//jstring Java_com_xhb_mytest_module_TestJni_print
//  (JNIEnv * env, jclass clazz, jstring string)
//{
//	jsize size = env->GetStringUTFLength(string);
//	char buffer[1024];
//
//	env->GetStringUTFRegion(string, 0, size, buffer);
//
//
//	char data[1024];
////	strcat(data, buffer);
//	sprintf(data, "Hello from JNI:%s\n", buffer);
//
//	printf("%s", data);
//
//	jstring result = env->NewStringUTF(data);
//
//	jmethodID callbackMethodId = env->GetStaticMethodID(clazz, "onResult", "(Ljava/lang/String;)V");
//
//	env->CallStaticVoidMethod(clazz, callbackMethodId, result);
//
//	return result;
//}

//jstring Java_com_xhb_mytest_module_TestJni_doInBackground
//  (JNIEnv * env, jclass clazz, jobject jobject)
//{
//	return native_doinbackground(env, clazz, jobject);
//}



static JNINativeMethod gMethods[] =
{
		{"print", "(Ljava/lang/String;)Ljava/lang/String;", (void*)native_print},
		{"doInBackground", "(Lcom/hanbing/mytest/module/TestJni$OnResultListener;)Ljava/lang/String;", (void*)native_doinbackground}
};

const char* className = "com/hanbing/mytest/module/TestJni";
static int registerNativeMethods(JNIEnv* env,const char* className,JNINativeMethod* gMethods,int numMethods)
{
        jclass clazz;
        clazz = env->FindClass(className);
        if(clazz == NULL){
                return JNI_FALSE;
        }
        if(env->RegisterNatives(clazz,gMethods,numMethods)<0){
                return JNI_FALSE;
        }
        return JNI_TRUE;
}
static int register_android_methods(JNIEnv *env)
{
        return registerNativeMethods(env,className,gMethods,sizeof(gMethods)/sizeof(gMethods[0]));
}



jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv* env = NULL;
//    LOGI("JNI_OnLoad!");

    if (vm->GetEnv((void**)&env, JNI_VERSION_1_4) != JNI_OK) {
//        LOGE("ERROR: GetEnv failed");
        return -1;
    }

    if(register_android_methods(env)<0){
//        LOGE("register_android_methods error.\n");
        return -1;
    }
     return JNI_VERSION_1_4;
}


#ifdef __cplusplus
}
#endif


