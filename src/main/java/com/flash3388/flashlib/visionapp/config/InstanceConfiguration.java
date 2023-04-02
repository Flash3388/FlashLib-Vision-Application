package com.flash3388.flashlib.visionapp.config;

import com.typesafe.config.ConfigObject;

public class InstanceConfiguration extends ConfigurationBase {

    private final String mName;

    InstanceConfiguration(ConfigObject config, String name) {
        super(config.toConfig());
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public SourceConfiguration getSource() {
        return new SourceConfiguration(mConfig.getConfig("source"));
    }
}
