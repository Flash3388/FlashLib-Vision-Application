package com.flash3388.flashlib.visionapp.config;

import com.typesafe.config.ConfigObject;

import java.util.HashMap;
import java.util.Map;

public class InstancesConfiguration extends ConfigurationBase {

    private final ConfigObject mConfigObject;

    InstancesConfiguration(ConfigObject config) {
        super(config.toConfig());
        mConfigObject = config;
    }

    public Map<String, InstanceConfiguration> getAll() {
        Map<String, InstanceConfiguration> all = new HashMap<>();
        for (String name : mConfigObject.keySet()) {
            all.put(name, new InstanceConfiguration(mConfig.getObject(name), name));
        }

        return all;
    }

}
