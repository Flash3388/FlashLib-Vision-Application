package com.flash3388.flashlib.visionapp.vision.pipelines.detectors;

import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.vision.cv.CvProcessing;
import com.flash3388.flashlib.visionapp.config.TargetConfiguration;
import com.flash3388.flashlib.visionapp.vision.VisionData;
import com.flash3388.flashlib.visionapp.vision.pipelines.Target;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionDetector;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class BasicScoreVisionDetector implements VisionDetector {

    private final ObjectDetector mObjectDetector;
    private final ObjectTracker mObjectTracker;

    public BasicScoreVisionDetector(TargetConfiguration targetConfiguration, ObjectTracker objectTracker) {
        mObjectDetector = new ScoreBasedDetector(new CvProcessing(), targetConfiguration);
        mObjectTracker = objectTracker;
    }

    @Override
    public Optional<Map<Integer, ? extends Target>> process(VisionData input, Consumer<Mat> postProcessOut)
            throws VisionException {
        Collection<? extends ScorableTarget> objects = mObjectDetector.detect(input);
        Map<Integer, ? extends ScorableTarget> targets = mObjectTracker.updateTracked(objects);

        Mat outputMat = new Mat();
        Imgproc.cvtColor(input.getImage(), outputMat, Imgproc.COLOR_GRAY2RGB);

        if (targets.isEmpty()) {

        } else {
            for (Map.Entry<Integer, ? extends ScorableTarget> entry : targets.entrySet()) {
                ScorableTarget target = entry.getValue();
                Point center = target.getCenter();

                target.drawOn(outputMat);
                Imgproc.putText(outputMat, String.valueOf(entry.getKey()), center,
                        Imgproc.FONT_HERSHEY_PLAIN, 1, new Scalar(23, 35, 100));
            }
        }

        postProcessOut.accept(outputMat);

        return Optional.of(targets);
    }
}
