package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.visionapp.vision.InstanceInfo;
import com.flash3388.flashlib.visionapp.vision.pipelines.PipelineImageSink;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class DisplaySinksConfiguration extends ConfigurationBase {

    private final ConfigObject mConfigObject;
    private final InstanceInfo mInfo;

    DisplaySinksConfiguration(ConfigObject config, InstanceInfo info) {
        super(config.toConfig());
        mConfigObject = config;
        mInfo = info;
    }

    public Collection<PipelineImageSink> getAll() throws IOException {
        Collection<PipelineImageSink> all = new ArrayList<>();
        for (String name : mConfigObject.keySet()) {
            Config singleConfig = mConfig.getConfig(name);
            DisplaySinkType type = DisplaySinkType.fromConfigName(name);

            all.add(type.createFromConfig(mInfo, singleConfig, null));
        }

        return all;
    }
}
