package com.flash3388.flashlib.visionapp.config;

import com.typesafe.config.Config;

public class TargetConfiguration extends ConfigurationBase {

    TargetConfiguration(Config config) {
        super(config);
    }

    public double getWidthCm() {
        return mConfig.getDouble("widthCm");
    }

    public double getDimensionsRatio() {
        return mConfig.getDouble("dimensionsRatio");
    }

    public double getMinSizePixels() {
        return mConfig.getDouble("minSizePixels");
    }

    public double getMinScore() {
        return mConfig.getDouble("minScore");
    }
}
