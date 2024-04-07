package com.example.openglesbook

import android.content.Context
import android.content.res.AssetManager
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import javax.microedition.khronos.opengles.GL10

class MainActivity : AppCompatActivity() {
    init {
        System.loadLibrary("opengles")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set content view to OpenGL ES surface view
        setContentView(SurfaceView(this))
    }

    /**
     * A native method that is implemented by the 'openglesbook' native library,
     * which is packaged with this application.
     */
    external fun surfaceCreated(assetManager: AssetManager, id: Int)
    external fun surfaceChanged(width: Int, height: Int)
    external fun drawFrame(deltaTime: Float)
    external fun touchEvent(motion: Int, x: Float, y: Float)

    private inner class SurfaceRenderer : GLSurfaceView.Renderer {
        private var lastTime = System.currentTimeMillis()
        override fun onSurfaceCreated(gl10: GL10, eglconfig: javax.microedition.khronos.egl.EGLConfig) {
            surfaceCreated(assets, intent.getIntExtra("ExampleID", -1))
        }

        override fun onSurfaceChanged(gl10: GL10, width: Int, height: Int) {
            surfaceChanged(width, height)
        }

        override fun onDrawFrame(gl10: GL10) {
            // get current time
            val currentTime = System.currentTimeMillis()
            // compute delta time
            val deltaTime =(currentTime - lastTime) / 1000.0f
            // call native method
            drawFrame(deltaTime)
            // update last time
            lastTime = currentTime
        }
    }

    private inner class SurfaceView(context: Context) : GLSurfaceView(context) {

        init {
            setEGLContextClientVersion(3)
            setRenderer(SurfaceRenderer())
        }

        override fun onTouchEvent(event: MotionEvent?): Boolean {
            if (event != null) {
                when (event.action){
                    MotionEvent.ACTION_DOWN -> touchEvent(0, event.x, event.y)
                    MotionEvent.ACTION_MOVE -> touchEvent(1, event.x, event.y)
                    MotionEvent.ACTION_UP -> touchEvent(2, event.x, event.y)
                }
            }
            return true
        }
    }
}