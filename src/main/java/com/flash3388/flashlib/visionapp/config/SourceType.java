package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.visionapp.vision.sources.CvCameraSource;
import com.flash3388.flashlib.visionapp.vision.sources.VisionSource;
import com.typesafe.config.Config;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public enum SourceType {
    CV_CAMERA("cv-camera") {
        @Override
        public void checkConfig(Config config) {
            new CameraConfiguration(config.getConfig("camera"));
        }

        @Override
        public VisionSource createFromConfig(Config config) {
            CameraConfiguration cameraConfiguration =
                    new CameraConfiguration(config.getConfig("camera"));
            return new CvCameraSource(cameraConfiguration);
        }
    }
    ;

    private final String mConfigName;

    SourceType(String configName) {
        mConfigName = configName;
    }

    public abstract void checkConfig(Config config);
    public abstract VisionSource createFromConfig(Config config);

    public static SourceType fromConfigName(String typeName) {
        for (SourceType type : values()) {
            if (type.mConfigName.equals(typeName)) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(SourceType.class, typeName);
    }
}
