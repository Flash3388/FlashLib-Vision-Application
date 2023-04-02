package com.flash3388.flashlib.visionapp.vision.pipelines;

import com.flash3388.flashlib.vision.Pipeline;
import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.vision.analysis.Analysis;

public interface AnalysisSink extends Pipeline<Analysis> {

    class Empty implements AnalysisSink {
        @Override
        public void process(Analysis input) throws VisionException {
        }
    }
}
