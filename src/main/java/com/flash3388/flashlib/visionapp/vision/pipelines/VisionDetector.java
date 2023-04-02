package com.flash3388.flashlib.visionapp.vision.pipelines;

import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.visionapp.vision.VisionData;
import org.opencv.core.Mat;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public interface VisionDetector {

    Optional<Map<Integer, ? extends Target>> process(VisionData input, Consumer<Mat> postProcessOut)
            throws VisionException;

    class Empty implements VisionDetector {

        @Override
        public Optional<Map<Integer, ? extends Target>> process(VisionData input, Consumer<Mat> postProcessOut) throws VisionException {
            return Optional.empty();
        }
    }
}
