package com.flash3388.flashlib.visionapp;

import com.flash3388.flashlib.util.logging.Logging;
import org.slf4j.Logger;

public class Globals {

    private Globals() {}

    public static final Logger MAIN_LOGGER = Logging.getLogger("Vision", "Main");

    public static Logger getVisionInstanceLogger(String name) {
        return Logging.getLogger("Vision", "Instances", name);
    }
}
