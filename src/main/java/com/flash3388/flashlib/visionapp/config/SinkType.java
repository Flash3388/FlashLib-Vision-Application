package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.visionapp.vision.pipelines.AnalysisSink;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionAnalyzer;
import com.typesafe.config.Config;

public enum SinkType {
    VOID("void") {
        @Override
        public AnalysisSink createFromConfig(Config config, StoredObject object) {
            return new AnalysisSink.Empty();
        }
    }
    ;

    private final String mConfigName;

    SinkType(String configName) {
        mConfigName = configName;
    }

    public abstract AnalysisSink createFromConfig(Config config, StoredObject object);

    public static SinkType fromConfigName(String typeName) {
        for (SinkType type : values()) {
            if (type.mConfigName.equals(typeName)) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(SinkType.class, typeName);
    }
}
