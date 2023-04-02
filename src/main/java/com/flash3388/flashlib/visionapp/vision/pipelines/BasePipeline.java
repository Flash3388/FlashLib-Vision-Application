package com.flash3388.flashlib.visionapp.vision.pipelines;

import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.visionapp.vision.VisionData;
import org.opencv.core.Mat;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BasePipeline implements VisionPipeline {

    private final List<VisionProcessor> mProcessors;
    private final VisionDetector mDetector;
    private final VisionAnalyzer mAnalyzer;
    private final AnalysisSink mSink;

    private final PipelineImageSink mImageSink;

    public BasePipeline(List<VisionProcessor> processors,
                        VisionDetector detector,
                        VisionAnalyzer analyzer,
                        AnalysisSink sink,
                        PipelineImageSink imageSink) {
        mProcessors = processors;
        mDetector = detector;
        mAnalyzer = analyzer;
        mSink = sink;
        mImageSink = imageSink;
    }

    @Override
    public void process(VisionData input) throws VisionException {
        VisionData originalData = copyOfData(input);
        if (mImageSink != null) {
            mImageSink.handlePreProcessImage(originalData.getImage());
        }

        for (VisionProcessor processor : mProcessors) {
            input = processor.process(input);
        }

        if (mImageSink != null) {
            VisionData post = copyOfData(input);
            mImageSink.handlePostProcessImage(post.getImage());
        }

        Optional<Map<Integer, ? extends Target>> targetsOptional = mDetector.process(input, (mat)-> {
            if (mImageSink != null) {
                mImageSink.handlePostDetectionImage(mat);
            }
        });
        if (targetsOptional.isPresent()) {
            Map<Integer, ? extends Target> targets = targetsOptional.get();
            Optional<Analysis> analysisOptional = mAnalyzer.process(originalData, targets);
            if (analysisOptional.isPresent()) {
                Analysis analysis = analysisOptional.get();
                mSink.process(analysis);
            }
        }
    }

    private static VisionData copyOfData(VisionData data) {
        Mat mat = new Mat();
        data.getImage().copyTo(mat);

        return new VisionData(
                mat,
                data.getColorSpace()
        );
    }
}
