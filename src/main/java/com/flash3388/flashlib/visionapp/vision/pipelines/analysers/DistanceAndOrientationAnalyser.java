package com.flash3388.flashlib.visionapp.vision.pipelines.analysers;

import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.vision.analysis.AnalysisAlgorithms;
import com.flash3388.flashlib.vision.analysis.JsonAnalysis;
import com.flash3388.flashlib.visionapp.config.CameraConfiguration;
import com.flash3388.flashlib.visionapp.config.TargetConfiguration;
import com.flash3388.flashlib.visionapp.vision.VisionData;
import com.flash3388.flashlib.visionapp.vision.pipelines.Target;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionAnalyzer;
import org.opencv.core.Mat;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DistanceAndOrientationAnalyser implements VisionAnalyzer {

    private final CameraConfiguration mCameraConfiguration;
    private final TargetConfiguration mTargetConfiguration;

    public DistanceAndOrientationAnalyser(CameraConfiguration cameraConfiguration,
                                          TargetConfiguration targetConfiguration) {
        mCameraConfiguration = cameraConfiguration;
        mTargetConfiguration = targetConfiguration;
    }

    @Override
    public Optional<Analysis> process(VisionData originalData,
                                      Map<Integer, ? extends Target> targets) throws VisionException {
        Mat originalImage = originalData.getImage();

        JsonAnalysis.Builder builder = new JsonAnalysis.Builder();
        for (Map.Entry<Integer, ? extends Target> entry : targets.entrySet()) {
            int id = entry.getKey();
            Target target = entry.getValue();

            double distance = AnalysisAlgorithms.measureDistance(
                    originalImage.width(),
                    target.getWidthPixels(),
                    mTargetConfiguration.getWidthCm(),
                    mCameraConfiguration.getFovRadians());

            double angle = AnalysisAlgorithms.calculateHorizontalOffsetDegrees2(
                    target.getCenter().x,
                    originalImage.width(),
                    mCameraConfiguration.getFovRadians());

            builder.buildTarget()
                    .put("id", id)
                    .put("centerX", target.getCenter().x)
                    .put("centerY", target.getCenter().y)
                    .put("width", target.getWidthPixels())
                    .put("height", target.getHeightPixels())
                    .put("distance", distance)
                    .put("angle", angle)
                    .build();
        }

        return Optional.of(builder.build());
    }
}
