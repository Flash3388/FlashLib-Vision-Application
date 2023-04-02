package com.flash3388.flashlib.visionapp;

import java.io.File;

public class ProgramOptions {

    private final File mConfigFile;
    private final boolean mShowPipelines;

    public ProgramOptions(File configFile, boolean showPipelines) {
        mConfigFile = configFile;
        mShowPipelines = showPipelines;
    }

    public File getConfigFile() {
        return mConfigFile;
    }

    public boolean shouldShowPipelines() {
        return mShowPipelines;
    }
}
