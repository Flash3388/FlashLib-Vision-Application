package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionDetector;
import com.flash3388.flashlib.visionapp.vision.pipelines.detectors.BasicScoreVisionDetector;
import com.flash3388.flashlib.visionapp.vision.pipelines.detectors.ObjectTracker;
import com.typesafe.config.Config;

public enum DetectorType {
    NULL("null") {
        @Override
        public VisionDetector createFromConfig(Config config, StoredObject object) {
            return new VisionDetector.Empty();
        }
    },
    SCORE("score") {
        @Override
        public VisionDetector createFromConfig(Config config, StoredObject object) {
            String trackerName = config.getString("tracker");
            DetectorTrackerType trackerType = DetectorTrackerType.fromConfigName(trackerName);
            ObjectTracker tracker = trackerType.create();

            TargetConfiguration target = new TargetConfiguration(config.getConfig("target"));
            return new BasicScoreVisionDetector(target, tracker, object);
        }
    }
    ;

    private final String mConfigName;

    DetectorType(String configName) {
        mConfigName = configName;
    }

    public abstract VisionDetector createFromConfig(Config config, StoredObject object);

    public static DetectorType fromConfigName(String typeName) {
        for (DetectorType type : values()) {
            if (type.mConfigName.equals(typeName)) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(DetectorType.class, typeName);
    }
}
