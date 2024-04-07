#ifndef OPENGLES_MAIN_H
#define OPENGLES_MAIN_H

#include <jni.h>
#include <string>

#include "global.h"
#include "utility/asset.h"
#include "utility/input.h"
#include "utility/scene.h"
#include "example/list.h"

extern Scene* scene;

void surfaceCreated(const AAssetManager* aAssetManager, const int id);
void surfaceChanged(const int width, const int height);
void drawFrame(const float deltaTime);
void touchEvent(const int motion, const float x, const float y);

extern "C"
JNIEXPORT void JNICALL
Java_com_example_openglesbook_MainActivity_surfaceCreated(JNIEnv *env, jobject thiz,
                                                          jobject asset_manager, jint id) {
    surfaceCreated(AAssetManager_fromJava(env, asset_manager), id);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_openglesbook_MainActivity_surfaceChanged(JNIEnv *env, jobject thiz, jint width,
                                                          jint height) {
    surfaceChanged(width, height);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_openglesbook_MainActivity_drawFrame(JNIEnv *env, jobject thiz, jfloat delta_time) {
    drawFrame(delta_time);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_openglesbook_MainActivity_touchEvent(JNIEnv *env, jobject thiz, jint motion,
                                                      jfloat x, jfloat y) {
    touchEvent(motion, x, y);
}

#endif // OPENGLES_MAIN_H
