package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.vision.control.KnownVisionOptions;
import com.flash3388.flashlib.vision.control.VisionOption;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;

import java.util.HashMap;
import java.util.Map;

public class VisionOptionConfiguration extends ConfigurationBase {

    private final ConfigObject mConfigObject;

    VisionOptionConfiguration(ConfigObject config) {
        super(config.toConfig());
        mConfigObject = config;
    }

    public Map<VisionOption<?>, Object> getAll(KnownVisionOptions options) {
        ConfigValueAdapter adapter = new ConfigValueAdapter();

        Map<VisionOption<?>, Object> all = new HashMap<>();
        for (String name : mConfigObject.keySet()) {
            VisionOption<?> option = options.get(name);
            Object value = adapter.adapt(new NamedConfigValue(name, mConfig));

            all.put(option, value);
        }

        return all;
    }
}
