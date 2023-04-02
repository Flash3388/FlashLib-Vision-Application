package com.flash3388.flashlib.visionapp.config;

import com.castle.util.function.ThrowingSupplier;
import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.visionapp.vision.sources.VisionSource;
import com.typesafe.config.Config;

public class SourceConfiguration extends ConfigurationBase {

    private final SourceType mType;

    SourceConfiguration(Config config) {
        super(config);

        String typeName = config.getString("type");
        mType = SourceType.fromConfigName(typeName);
    }

    public SourceType getType() {
        return mType;
    }

    public ThrowingSupplier<VisionSource, VisionException> getOpener() {
        mType.checkConfig(mConfig);
        return ()-> {
            return mType.createFromConfig(mConfig);
        };
    }
}
