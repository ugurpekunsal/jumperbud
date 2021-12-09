package com.jumperbud.motiondetection.motiondetection;

public interface MotionDetectorCallback {
    void onMotionDetected();
    void onTooDark();
}
