package com.flash3388.flashlib.visionapp;

import java.io.File;

public class ProgramOptions {

    private final File mConfigFile;

    public ProgramOptions(File configFile) {
        mConfigFile = configFile;
    }

    public File getConfigFile() {
        return mConfigFile;
    }
}
