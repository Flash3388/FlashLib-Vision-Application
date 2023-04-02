package com.flash3388.flashlib.visionapp.vision.pipelines;

import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.visionapp.vision.VisionData;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BasePipeline implements VisionPipeline {

    private final List<VisionProcessor> mProcessors;
    private final VisionDetector mDetector;
    private final VisionAnalyzer mAnalyzer;
    private final AnalysisSink mSink;

    public BasePipeline(List<VisionProcessor> processors, VisionDetector detector, VisionAnalyzer analyzer, AnalysisSink sink) {
        mProcessors = processors;
        mDetector = detector;
        mAnalyzer = analyzer;
        mSink = sink;
    }

    @Override
    public void process(VisionData input) throws VisionException {
        VisionData originalData = input;
        for (VisionProcessor processor : mProcessors) {
            input = processor.process(input);
        }

        Optional<Map<Integer, ? extends Target>> targetsOptional = mDetector.process(input);
        if (targetsOptional.isPresent()) {
            Map<Integer, ? extends Target> targets = targetsOptional.get();
            Optional<Analysis> analysisOptional = mAnalyzer.process(originalData, targets);
            if (analysisOptional.isPresent()) {
                Analysis analysis = analysisOptional.get();
                mSink.process(analysis);
            }
        }
    }
}
