package com.flash3388.flashlib.visionapp.vision.pipelines;

import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.vision.processing.Processor;
import com.flash3388.flashlib.visionapp.vision.VisionData;
import org.opencv.core.Mat;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class BasePipeline implements Processor<VisionData, Optional<Analysis>> {

    private final List<VisionProcessor> mProcessors;
    private final VisionDetector mDetector;
    private final VisionAnalyzer mAnalyzer;
    private final AnalysisSink mSink;

    private final Collection<PipelineImageSink> mImageSinks;

    public BasePipeline(List<VisionProcessor> processors,
                        VisionDetector detector,
                        VisionAnalyzer analyzer,
                        AnalysisSink sink,
                        Collection<PipelineImageSink> imageSinks) {
        mProcessors = processors;
        mDetector = detector;
        mAnalyzer = analyzer;
        mSink = sink;
        mImageSinks = imageSinks;
    }

    @Override
    public Optional<Analysis> process(VisionData input) throws VisionException {
        VisionData originalData = copyOfData(input);
        updateSink(originalData.getImage(), PipelineImageSink::handlePreProcessImage);

        for (VisionProcessor processor : mProcessors) {
            input = processor.process(input);
        }

        updateSink(input.getImage(), PipelineImageSink::handlePostProcessImage);

        Optional<Map<Integer, ? extends Target>> targetsOptional = mDetector.process(input, (mat)-> {
            updateSink(mat, PipelineImageSink::handlePostDetectionImage);
        });

        if (targetsOptional.isPresent()) {
            Map<Integer, ? extends Target> targets = targetsOptional.get();
            Optional<Analysis> analysisOptional = mAnalyzer.process(originalData, targets);
            if (analysisOptional.isPresent()) {
                Analysis analysis = analysisOptional.get();
                mSink.process(analysis);

                return Optional.of(analysis);
            }
        }

        return Optional.empty();
    }

    private static VisionData copyOfData(VisionData data) {
        Mat mat = new Mat();
        data.getImage().copyTo(mat);

        return new VisionData(
                data,
                mat,
                data.getColorSpace());
    }

    private void updateSink(Mat mat, BiConsumer<PipelineImageSink, Mat> func) {
        for (PipelineImageSink sink : mImageSinks) {
            func.accept(sink, mat);
        }
    }
}
