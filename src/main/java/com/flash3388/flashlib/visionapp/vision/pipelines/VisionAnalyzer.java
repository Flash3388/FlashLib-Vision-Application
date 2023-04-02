package com.flash3388.flashlib.visionapp.vision.pipelines;

import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.vision.processing.Processor;
import com.flash3388.flashlib.visionapp.vision.VisionData;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VisionAnalyzer {

    Optional<Analysis> process(VisionData originalData,
                               Map<Integer, ? extends Target> targets) throws VisionException;

    class Empty implements VisionAnalyzer {

        @Override
        public Optional<Analysis> process(VisionData originalData,
                                          Map<Integer, ? extends Target> targets) throws VisionException {
            return Optional.empty();
        }
    }
}
