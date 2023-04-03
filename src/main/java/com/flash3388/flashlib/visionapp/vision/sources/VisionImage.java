package com.flash3388.flashlib.visionapp.vision.sources;

import com.flash3388.flashlib.visionapp.vision.color.ColorSpace;
import org.opencv.core.Mat;

public class VisionImage {

    private final Mat mImage;
    private final ColorSpace mColorSpace;

    public VisionImage(Mat mat, ColorSpace colorSpace) {
        mImage = mat;
        mColorSpace = colorSpace;
    }

    public Mat getImage() {
        return mImage;
    }

    public ColorSpace getColorSpace() {
        return mColorSpace;
    }
}
