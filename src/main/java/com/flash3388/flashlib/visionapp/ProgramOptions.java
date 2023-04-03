package com.flash3388.flashlib.visionapp;

import java.io.File;

public class ProgramOptions {

    private final File mConfigFile;
    private final boolean mAutoStartPipelines;

    public ProgramOptions(File configFile, boolean autoStartPipelines) {
        mConfigFile = configFile;
        mAutoStartPipelines = autoStartPipelines;
    }

    public File getConfigFile() {
        return mConfigFile;
    }

    public boolean shouldStartPipelines() {
        return mAutoStartPipelines;
    }
}
