package com.example.jumperbud.UIComponents

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView
import android.view.ViewGroup
import com.google.android.gms.vision.CameraSource

class Camera(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    //private val surfaceView: SurfaceView
    private val startRequested = false
    private val surfaceAvailable = false
    private val cameraSource: CameraSource? = null
    private val overlay: GraphicsOverlay? = null

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

    }
}