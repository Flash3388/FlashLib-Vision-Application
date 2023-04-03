package com.flash3388.flashlib.visionapp.vision;

import com.flash3388.flashlib.vision.control.VisionOption;
import com.flash3388.flashlib.vision.control.VisionServer;
import com.flash3388.flashlib.visionapp.vision.color.ColorSpace;
import org.opencv.core.Mat;

import java.util.Optional;

public class VisionData {

    private final Mat mImage;
    private final ColorSpace mColorSpace;
    private final VisionServer mVisionServer;

    public VisionData(Mat mat, ColorSpace colorSpace, VisionServer visionServer) {
        mImage = mat;
        mColorSpace = colorSpace;
        mVisionServer = visionServer;
    }

    public VisionData(VisionData other, Mat newImage, ColorSpace colorSpace) {
        this(newImage, colorSpace, other.mVisionServer);
    }

    public Mat getImage() {
        return mImage;
    }

    public ColorSpace getColorSpace() {
        return mColorSpace;
    }

    public <T> Optional<T> getOption(VisionOption<T> option) {
        return mVisionServer.getOption(option);
    }

    public <T> T getOptionOrDefault(VisionOption<T> option, T defaultValue) {
        return mVisionServer.getOptionOrDefault(option, defaultValue);
    }
}
