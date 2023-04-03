package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.vision.control.KnownVisionOptions;
import com.flash3388.flashlib.visionapp.vision.InstanceInfo;
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

    public InstanceInfo getInfo() {
        return new InstanceInfo(getName());
    }

    public SourceConfiguration getSource() {
        return new SourceConfiguration(mConfig.getConfig("source"));
    }

    public PipelineConfiguration getPipeline() {
        return new PipelineConfiguration(mConfig.getConfig("pipeline"));
    }

    public DisplaySinksConfiguration getDisplaySinks() {
        return new DisplaySinksConfiguration(mConfig.getObject("sinks"), getInfo());
    }

    public VisionOptionConfiguration getVisionOptions() {
        return new VisionOptionConfiguration(mConfig.getObject("options"));
    }
}
