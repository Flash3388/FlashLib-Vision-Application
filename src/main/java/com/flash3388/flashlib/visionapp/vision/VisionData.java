package com.flash3388.flashlib.visionapp.vision;

import com.flash3388.flashlib.visionapp.vision.color.ColorSpace;
import org.opencv.core.Mat;

public class VisionData {

    private final Mat mImage;
    private final ColorSpace mColorSpace;

    public VisionData(Mat mat, ColorSpace colorSpace) {
        mImage = mat;
        mColorSpace = colorSpace;
    }

    public VisionData(VisionData other, Mat newImage, ColorSpace colorSpace) {
        this(newImage, colorSpace);
    }

    public Mat getImage() {
        return mImage;
    }

    public ColorSpace getColorSpace() {
        return mColorSpace;
    }
}
