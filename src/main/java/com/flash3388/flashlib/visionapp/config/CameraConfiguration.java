package com.flash3388.flashlib.visionapp.config;

import com.typesafe.config.Config;

public class CameraConfiguration extends ConfigurationBase {

    CameraConfiguration(Config config) {
        super(config);
    }

    public double getFovRadians() {
        return mConfig.getDouble("fovRadians");
    }
}
