package com.flash3388.flashlib.visionapp.vision.pipelines.detectors;

import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.vision.cv.CvProcessing;
import com.flash3388.flashlib.visionapp.config.TargetConfiguration;
import com.flash3388.flashlib.visionapp.vision.VisionData;
import com.flash3388.flashlib.visionapp.vision.pipelines.Target;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionDetector;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class BasicScoreVisionDetector implements VisionDetector {

    private final ObjectDetector mObjectDetector;
    private final ObjectTracker mObjectTracker;

    public BasicScoreVisionDetector(TargetConfiguration targetConfiguration, ObjectTracker objectTracker) {
        mObjectDetector = new ScoreBasedDetector(new CvProcessing(), targetConfiguration);
        mObjectTracker = objectTracker;
    }

    @Override
    public Optional<Map<Integer, ? extends Target>> process(VisionData input) throws VisionException {
        Collection<? extends ScorableTarget> objects = mObjectDetector.detect(input);
        Map<Integer, ? extends ScorableTarget> targets = mObjectTracker.updateTracked(objects);

        return Optional.of(targets);
    }
}
