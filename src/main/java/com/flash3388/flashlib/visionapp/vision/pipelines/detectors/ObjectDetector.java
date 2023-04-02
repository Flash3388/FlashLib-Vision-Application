package com.flash3388.flashlib.visionapp.vision.pipelines.detectors;


import com.flash3388.flashlib.visionapp.vision.VisionData;

import java.util.Collection;

public interface ObjectDetector {

    Collection<? extends ScorableTarget> detect(VisionData visionData);
}
