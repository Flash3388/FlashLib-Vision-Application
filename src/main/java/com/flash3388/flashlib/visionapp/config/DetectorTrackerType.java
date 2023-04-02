package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.visionapp.vision.pipelines.detectors.CenteroidTracker;
import com.flash3388.flashlib.visionapp.vision.pipelines.detectors.ObjectTracker;

public enum DetectorTrackerType {
    CENTEROID("centeroid") {
        @Override
        public ObjectTracker create() {
            return new CenteroidTracker();
        }
    }
    ;

    private final String mConfigName;

    DetectorTrackerType(String configName) {
        mConfigName = configName;
    }

    public abstract ObjectTracker create();

    public static DetectorTrackerType fromConfigName(String typeName) {
        for (DetectorTrackerType type : values()) {
            if (type.mConfigName.equals(typeName)) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(DetectorTrackerType.class, typeName);
    }
}
