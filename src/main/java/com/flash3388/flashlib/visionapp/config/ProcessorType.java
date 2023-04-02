package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.visionapp.vision.color.ColorRange;
import com.flash3388.flashlib.visionapp.vision.pipelines.processors.ColorProcessor;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionProcessor;
import com.typesafe.config.Config;

public enum ProcessorType {
    COLOR("color-filter") {
        @Override
        public VisionProcessor createFromConfig(Config config, StoredObject object) {
            ColorRange colorRange = Helper.parseColorRange(config);
            return new ColorProcessor(colorRange, object);
        }
    }
    ;

    private final String mConfigName;

    ProcessorType(String configName) {
        mConfigName = configName;
    }

    public abstract VisionProcessor createFromConfig(Config config, StoredObject object);

    public static ProcessorType fromConfigName(String typeName) {
        for (ProcessorType type : values()) {
            if (type.mConfigName.equals(typeName)) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(ProcessorType.class, typeName);
    }
}
