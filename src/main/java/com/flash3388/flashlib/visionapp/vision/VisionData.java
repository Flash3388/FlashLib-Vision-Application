package com.flash3388.flashlib.visionapp.vision;

import org.opencv.core.Mat;

public class VisionData {

    private final Mat mImage;

    public VisionData(Mat mat) {
        mImage = mat;
    }

    public Mat getImage() {
        return mImage;
    }
}
