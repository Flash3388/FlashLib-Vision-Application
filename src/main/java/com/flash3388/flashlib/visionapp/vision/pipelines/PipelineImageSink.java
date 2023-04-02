package com.flash3388.flashlib.visionapp.vision.pipelines;

import org.opencv.core.Mat;

public interface PipelineImageSink {

    void handlePreProcessImage(Mat image);
    void handlePostProcessImage(Mat image);
    void handlePostDetectionImage(Mat image);
}
