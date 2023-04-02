package com.flash3388.flashlib.visionapp.vision.pipelines.detectors;

import com.flash3388.flashlib.visionapp.vision.pipelines.Target;
import org.opencv.core.Mat;

public interface ScorableTarget extends Target {

    double score();
}
