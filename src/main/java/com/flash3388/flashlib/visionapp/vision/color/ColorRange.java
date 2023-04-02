package com.flash3388.flashlib.visionapp.vision.color;

import org.opencv.core.Range;
import org.opencv.core.Scalar;

import java.util.Collections;
import java.util.List;

public class ColorRange {

    private final ColorSpace mColorSpace;
    private final List<Range> mDimensions;

    public ColorRange(ColorSpace colorSpace, List<Range> dimensions) {
        mColorSpace = colorSpace;
        mDimensions = Collections.unmodifiableList(dimensions);
    }

    public ColorSpace getColorSpace() {
        return mColorSpace;
    }

    public List<Range> getDimensions() {
        return mDimensions;
    }

    public int[] getMinAsArray() {
        int[] values = {0, 0, 0, 0};
        for (int i = 0; i < mDimensions.size(); i++) {
            values[i] = mDimensions.get(i).start;
        }

        return values;
    }

    public int[] getMaxAsArray() {
        int[] values = {0, 0, 0, 0};
        for (int i = 0; i < mDimensions.size(); i++) {
            values[i] = mDimensions.get(i).end;
        }

        return values;
    }
}
