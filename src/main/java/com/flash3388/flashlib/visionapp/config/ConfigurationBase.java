package com.flash3388.flashlib.visionapp.config;

import com.typesafe.config.Config;

abstract class ConfigurationBase {

    protected final Config mConfig;

    protected ConfigurationBase(Config config) {
        mConfig = config;
    }
}
