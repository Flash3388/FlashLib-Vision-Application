package com.flash3388.flashlib.visionapp.vision.pipelines.detectors;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

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

    @Override
    public void drawOn(Mat mat) {
        Imgproc.rectangle(mat, mRect.tl(), mRect.br(), new Scalar(78, 150, 200), 2);
    }
}
