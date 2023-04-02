package com.flash3388.flashlib.visionapp.config;

import com.typesafe.config.Config;

public class RootConfiguration extends ConfigurationBase {

    public RootConfiguration(Config config) {
        super(config);
    }

    public InstancesConfiguration getInstances() {
        return new InstancesConfiguration(mConfig.getObject("instances"));
    }
}
