package com.flash3388.flashlib.visionapp.config;

import com.typesafe.config.Config;

public class TargetConfiguration extends ConfigurationBase {

    private final double mWidthCm;
    private final double mDimensionsRatio;
    private final int mMinSizePixels;
    private final double mMinScore;

    TargetConfiguration(Config config) {
        super(config);
        mWidthCm = mConfig.getDouble("widthCm");
        mDimensionsRatio = mConfig.getDouble("dimensionsRatio");
        mMinSizePixels = mConfig.getInt("minSizePixels");
        mMinScore = mConfig.getDouble("minScore");
    }

    public double getWidthCm() {
        return mWidthCm;
    }

    public double getDimensionsRatio() {
        return mDimensionsRatio;
    }

    public double getMinSizePixels() {
        return mMinSizePixels;
    }

    public double getMinScore() {
        return mMinScore;
    }
}
