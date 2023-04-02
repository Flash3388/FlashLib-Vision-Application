package com.flash3388.flashlib.visionapp.vision.pipelines.detectors;

import org.opencv.core.Point;
import org.opencv.core.Rect;

public class RatioTarget implements ScorableTarget {

    private final Rect mRect;
    private final double mExpectedDimensionRatio;

    public RatioTarget(Rect rect, double expectedDimensionRatio) {
        mRect = rect;
        mExpectedDimensionRatio = expectedDimensionRatio;
    }

    @Override
    public int getWidthPixels() {
        return mRect.width;
    }

    @Override
    public int getHeightPixels() {
        return mRect.height;
    }

    @Override
    public Point getCenter() {
        return new Point(
                mRect.x + mRect.width * 0.5,
                mRect.y + mRect.height * 0.5
        );
    }

    @Override
    public double score() {
        double ratio = mRect.height / (double) mRect.width;
        return ratio > mExpectedDimensionRatio ?
                mExpectedDimensionRatio / ratio :
                ratio / mExpectedDimensionRatio;
    }
}
