package com.flash3388.flashlib.visionapp.config;

import com.typesafe.config.Config;

public class NamedConfigValue {

    private final String mPath;
    private final Config mConfig;

    public NamedConfigValue(String path, Config config) {
        mPath = path;
        mConfig = config;
    }

    public String getPath() {
        return mPath;
    }

    public Config getConfig() {
        return mConfig;
    }
}
