package com.flash3388.flashlib.visionapp.vision.pipelines.detectors;

import com.flash3388.flashlib.vision.cv.CvProcessing;
import com.flash3388.flashlib.visionapp.config.TargetConfiguration;

import com.flash3388.flashlib.visionapp.vision.VisionData;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScoreBasedDetector implements ObjectDetector {

    private final CvProcessing mCvProcessing;
    private final TargetConfiguration mTargetConfig;

    public ScoreBasedDetector(CvProcessing cvProcessing, TargetConfiguration targetConfig) {
        mCvProcessing = cvProcessing;
        mTargetConfig = targetConfig;
    }

    @Override
    public Collection<? extends ScorableTarget> detect(VisionData visionData) {
        Mat image = visionData.getImage();

        // Additional ideas
        // https://docs.wpilib.org/en/stable/docs/software/vision-processing/wpilibpi/morphological-operations.html

        List<MatOfPoint> contours = mCvProcessing.detectContours(image);
        return retrieveTargets(contours);
    }

    private Collection<RatioTarget> retrieveTargets(List<MatOfPoint> contours) {
        return rectifyContours(contours)
                .filter(rect -> rect.area() >= mTargetConfig.getMinSizePixels())
                .map(rect -> new RatioTarget(rect, mTargetConfig.getDimensionsRatio()))
                .filter(target -> target.score() >= mTargetConfig.getMinScore())
                .collect(Collectors.toList());
    }

    private Stream<Rect> rectifyContours(List<MatOfPoint> contours) {
        return contours.stream()
                .map(Imgproc::boundingRect);
    }
}
