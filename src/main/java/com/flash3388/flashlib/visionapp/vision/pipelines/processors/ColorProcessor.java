package com.flash3388.flashlib.visionapp.vision.pipelines.processors;

import com.flash3388.flashlib.net.obsr.StoredEntry;
import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.visionapp.vision.VisionData;
import com.flash3388.flashlib.visionapp.vision.color.ColorRange;
import com.flash3388.flashlib.visionapp.vision.color.ColorSpace;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ColorProcessor implements VisionProcessor {

    private final StoredEntry mColorSpace;
    private final StoredEntry mMin;
    private final StoredEntry mMax;

    public ColorProcessor(ColorRange colorRange, StoredObject object) {
        mColorSpace = object.getEntry("ColorSpace");
        mColorSpace.setString(colorRange.getColorSpace().name());

        mMin = object.getEntry("min");
        mMin.setIntArray(colorRange.getMinAsArray());

        mMax = object.getEntry("max");
        mMax.setIntArray(colorRange.getMaxAsArray());
    }

    @Override
    public VisionData process(VisionData input) throws VisionException {
        convertColorSpace(input.getImage(), input.getColorSpace(), input.getImage());
        filterColors(input.getImage(), input.getImage());

        return input;
    }

    private void convertColorSpace(Mat src, ColorSpace srcColorSpace, Mat dst) {
        ColorSpace dstColorSpace = ColorSpace.valueOf(mColorSpace.getString("RGB"));

        if (srcColorSpace != dstColorSpace) {
            int code = getColorSpaceConversionCode(srcColorSpace, dstColorSpace);
            Imgproc.cvtColor(src, dst, code);
        }
    }

    private void filterColors(Mat src, Mat dst) {
        Scalar min = arrayToScalar(mMin.getIntArray(null));
        Scalar max = arrayToScalar(mMin.getIntArray(null));
        Core.inRange(src, min, max, dst);
    }

    private int getColorSpaceConversionCode(ColorSpace src, ColorSpace dst) {
        if (src == ColorSpace.BGR && dst == ColorSpace.HSV) {
            return Imgproc.COLOR_BGR2HSV;
        }
        if (src == ColorSpace.BGR && dst == ColorSpace.RGB) {
            return Imgproc.COLOR_BGR2RGB;
        }

        throw new UnsupportedOperationException("not code for color space conversion");
    }

    private Scalar arrayToScalar(int[] arr) {
        double[] values = {0, 0, 0, 0};
        for (int i = 0; i < arr.length; i++) {
            values[i] = arr[i];
        }

        return new Scalar(values);
    }
}
