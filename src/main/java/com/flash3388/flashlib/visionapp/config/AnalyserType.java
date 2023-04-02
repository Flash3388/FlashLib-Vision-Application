package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionAnalyzer;
import com.flash3388.flashlib.visionapp.vision.pipelines.analysers.DistanceAndOrientationAnalyser;
import com.typesafe.config.Config;

public enum AnalyserType {
    NULL("null") {
        @Override
        public VisionAnalyzer createFromConfig(Config config, StoredObject object) {
            return new VisionAnalyzer.Empty();
        }
    },
    BASIC("basic") {
        @Override
        public VisionAnalyzer createFromConfig(Config config, StoredObject object) {
            CameraConfiguration camera = new CameraConfiguration(config.getConfig("camera"));
            TargetConfiguration target = new TargetConfiguration(config.getConfig("target"));
            return new DistanceAndOrientationAnalyser(camera, target);
        }
    }
    ;

    private final String mConfigName;

    AnalyserType(String configName) {
        mConfigName = configName;
    }

    public abstract VisionAnalyzer createFromConfig(Config config, StoredObject object);

    public static AnalyserType fromConfigName(String typeName) {
        for (AnalyserType type : values()) {
            if (type.mConfigName.equals(typeName)) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(AnalyserType.class, typeName);
    }
}
