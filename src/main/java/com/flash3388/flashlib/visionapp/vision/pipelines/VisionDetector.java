package com.flash3388.flashlib.visionapp.vision.pipelines;

import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.vision.processing.Processor;
import com.flash3388.flashlib.visionapp.vision.VisionData;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VisionDetector extends Processor<VisionData, Optional<Map<Integer, ? extends Target>>> {

    class Empty implements VisionDetector {

        @Override
        public Optional<Map<Integer, ? extends Target>> process(VisionData input) throws VisionException {
            return Optional.empty();
        }
    }
}
